using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MySql.Data.MySqlClient;

using System.Net;
using System.Net.Sockets;
using System.Net.NetworkInformation;
using PacketDotNet;
using SharpPcap;
using System.Threading;

namespace detectarp
{
    class Program
    {
        private static string connectionString = "Server=localhost;Database=networkmonitor;Uid=root;Pwd=;";
        private static MySqlConnection connection;

        private static string myIp = null;

        private static ICaptureDevice device;

        private static int pcnt = 0; // 캡쳐한 패킷 개수
        private static int total = 0; // 캡쳐한 전체 패킷 바이트

        static void Main(string[] args)
        {
            getMyIp();
            Console.WriteLine($"my ip is {myIp}");
            
            connection = new MySqlConnection(connectionString);
            connection.Open();
            MySqlCommand command = null;
            
            CaptureDeviceList devices = CaptureDeviceList.Instance;
            device = devices[0]; // 0번이 WIFI
            device.OnPacketArrival += device_OnPacketArrival;
            device.Open(DeviceMode.Normal, 1000);
            device.StartCapture();
            Console.WriteLine("엔터를 누르면 캡쳐를 멈춥니다.");
            Console.ReadLine();
            device.StopCapture();
            device.Close();
            connection.Close();
        }

        public static void getMyIp()
        {
            IPAddress[] addresses = Dns.GetHostEntry(Dns.GetHostName()).AddressList;
            foreach(IPAddress address in addresses)
            {
                if(address.AddressFamily == AddressFamily.InterNetwork)
                {
                    myIp = address.ToString();
                    break;
                }
            }
        }

        private static void device_OnPacketArrival(object sender, CaptureEventArgs e)
        {
            //while (true)
            {
                
                RawCapture rawData = e.Packet;//device.GetNextPacket();
                
                byte[] data = rawData.Data;
                
                string dMac = string.Format("{0:X2}", data[0]);
                for(int i = 1; i < 6; i++)
                {
                    dMac += string.Format("-{0:X2}", data[i]);
                }

                string sMac = string.Format("{0:X2}", data[6]);
                for (int i = 7; i < 12; i++)
                {
                    sMac += string.Format("-{0:X2}", data[i]);
                }

                string protocol = null;
                int which = BitConverter.ToInt16(data.Skip<byte>(12).Take(2).Reverse<byte>().ToArray(), 0);

                switch (which)
                {
                    case 0x0800:
                        protocol = "IP";
                        break;
                    case 0x0200:
                        protocol = "Xerox PUP";
                        break;
                    case 0x0500:
                        protocol = "Sprite";
                        break;
                    case 0x0806:
                        protocol = "Address resolution";
                        break;
                    case 0x8035:
                        protocol = "Reverse ARP";
                        break;
                    case 0x809B:
                        protocol = "AppleTalk protocol";
                        break;
                    case 0x80F3:
                        protocol = "AppleTalk ARP";
                        break;
                    case 0x8100:
                        protocol = "IEEE 802.1Q VLAN tagging";
                        break;
                    case 0x8137:
                        protocol = "IPX";
                        break;
                    case 0x86dd:
                        protocol = "IP protocol version 6";
                        break;
                    case 0x9000:
                        protocol = "used to test interfaces";
                        break;
                    default:
                        protocol = "Unknown";
                        break;
                }

                Console.WriteLine($"protocol : {protocol}");

                if (protocol.Equals("IP"))
                {
                    byte[] srcIpByte = new byte[4];
                    byte[] dstIpByte = new byte[4];
                    Array.Copy(data, 14 + 12, srcIpByte, 0, 4);
                    Array.Copy(data, 14 + 16, dstIpByte, 0, 4);

                    string srcIp = string.Join(".", srcIpByte);
                    string dstIp = string.Join(".", dstIpByte);

                    byte protocol2_data = data[14 + 9];
                    which = Convert.ToInt32(protocol2_data);
                    string protocol2 = null;

                    int srcPort = 0, dstPort = 0;
                    switch (which)
                    {
                        case 1:
                            protocol2 = "ICMP";
                            break;
                        case 6:
                            protocol2 = "TCP";
                            srcPort = BitConverter.ToUInt16(data, 14 + 20);
                            dstPort = BitConverter.ToUInt16(data, 14 + 22);
                            break;
                        case 17:
                            protocol2 = "UDP";
                            srcPort = BitConverter.ToUInt16(data, 14 + 20);
                            dstPort = BitConverter.ToUInt16(data, 14 + 22);
                            break;
                        default:
                            protocol2 = "unknown";
                            break;
                    }

                    if (dstIp.Equals(myIp))
                    {
                        Console.WriteLine($"protocol2 : {protocol2}");
                        Console.WriteLine($"source mac : {sMac}");
                        Console.WriteLine($"destination mac : {dMac}");
                        Console.WriteLine($"srcIp : {srcIp}\ndstIp : {dstIp}");
                        Console.WriteLine($"srcPort : {srcPort}\ndstPort : {dstPort}");
                        Console.WriteLine($"{rawData.Data.Length}bytes");
                        total += rawData.Data.Length;

                        string insertCpuSql = "insert into t_monitor (localip, localport, remoteip, remoteport, protocol) " +
                            "values ('" + dstIp + "', '" + dstPort + "', '" + srcIp + "', '" + srcPort + "', '" + protocol2 + "')";
                        MySqlCommand command = new MySqlCommand(insertCpuSql, connection);

                        if (command.ExecuteNonQuery() == 1)
                        {
                            Console.WriteLine($"insert success");
                        }
                        else
                        {
                            Console.WriteLine($"insert failed");
                        }
                    }
                }
                
            }
        }
    }
}
