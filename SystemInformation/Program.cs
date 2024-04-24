using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MySql.Data.MySqlClient;
using System.Diagnostics;
using System.Threading;
using System.Management;
using System.IO;
using System.Net;
using System.Net.NetworkInformation;
using System.Net.Sockets;

namespace SystemInformation
{
    class Program
    {
        private static string connectionString = "Server=localhost;Database=networkmonitor;Uid=root;Pwd=;";
        private static MySqlConnection connection;

        static void Main(string[] args)
        {
            connection = new MySqlConnection(connectionString);
            connection.Open();
            MySqlCommand command = null;
            // PerformanceCounter 객체 생성
            PerformanceCounter cpuCounter = new PerformanceCounter("Processor", "% Processor Time", "_Total");

            // 메모리 정보
            int itotalMemMB = 0;
            int ifreeMemMB = 0;

            ManagementClass cls = new ManagementClass("Win32_OperatingSystem");

            // 디스크 정보
            DriveInfo[] drives = DriveInfo.GetDrives();

            long totalspace = 0, freespace = 0;
            foreach(DriveInfo drive in drives)
            {
                totalspace += (drive.TotalSize / 1024 / 1024 / 1024);
                freespace += (drive.TotalFreeSpace / 1024 / 1024 / 1024);
            }

            while (true)
            {

                float CpuUsage = cpuCounter.NextValue();
                if ((int)CpuUsage != 0)
                {
                    
                    Console.WriteLine($"CPU 사용량: " + CpuUsage + " %");
                    ManagementObjectCollection moc = cls.GetInstances();
                    foreach (ManagementObject mo in moc)
                    {
                        itotalMemMB = int.Parse(mo["TotalVisibleMemorySize"].ToString()) / 1024;
                        ifreeMemMB = int.Parse(mo["FreePhysicalMemory"].ToString()) / 1024;
                    }

                    Console.WriteLine($"총 메모리 사용량 : {100 - (int)(((double)ifreeMemMB / itotalMemMB) * 100)}");
                    Console.WriteLine($"총 디스크 사용량 : {100 - (int)((double)freespace / totalspace * 100)}\n");

                    int cpuUsage = (int)CpuUsage;
                    int usedMemory = 100 - (int)(((double)ifreeMemMB / itotalMemMB) * 100);
                    int usedDisk = 100 - (int)((double)freespace / totalspace * 100);

                    // cpu 사용량 DB에 저장
                    string insertCpuSql = "insert into t_resource (resource, used) values ('cpu', '"+cpuUsage+"')";
                    command = new MySqlCommand(insertCpuSql, connection);

                    if (command.ExecuteNonQuery() == 1)
                    {
                        Console.WriteLine($"insert success");
                    }
                    else
                    {
                        Console.WriteLine($"insert failed");
                    }

                    // 메모리 사용량 DB에 저장
                    string insertMemorySql = "insert into t_resource (resource, used) values ('memory', '" + usedMemory + "')";
                    command = new MySqlCommand(insertMemorySql, connection);

                    if (command.ExecuteNonQuery() == 1)
                    {
                        Console.WriteLine($"insert success");
                    }
                    else
                    {
                        Console.WriteLine($"insert failed");
                    }

                    // 디스크 사용량 DB에 저장
                    string insertDiskSql = "insert into t_resource (resource, used) values ('disk', '" + usedDisk + "')";
                    command = new MySqlCommand(insertDiskSql, connection);

                    if (command.ExecuteNonQuery() == 1)
                    {
                        Console.WriteLine($"insert success");
                    }
                    else
                    {
                        Console.WriteLine($"insert failed");
                    }
                    
                    // 현재 네트워크 연결 상태 확인
                    IPGlobalProperties iPGlobalProperties = IPGlobalProperties.GetIPGlobalProperties();
                    TcpConnectionInformation[] tcpConnectionInformation = iPGlobalProperties.GetActiveTcpConnections();

                    //Console.WriteLine("▼ GetActiveTcpConnections ----------------");
                    //Console.WriteLine("Local\t\t\t ┃Remote\t\t\t ┃State");

                    string localip = "127.0.0.1";
                    foreach (TcpConnectionInformation tcpConnInfo in tcpConnectionInformation)
                    {
                        TcpState tcpState = tcpConnInfo.State;
                        IPEndPoint localEndPoint = tcpConnInfo.LocalEndPoint;
                        IPEndPoint remotrEndPoint = tcpConnInfo.RemoteEndPoint;

                        if (!tcpConnInfo.LocalEndPoint.Address.ToString().Contains(localip)
                            && !tcpConnInfo.RemoteEndPoint.Address.ToString().Contains(localip))
                        {
                            Console.WriteLine(string.Format("{0}:{1}\t ┃{2}:{3}\t ┃{4}",
                              tcpConnInfo.LocalEndPoint.Address, tcpConnInfo.LocalEndPoint.Port,
                              tcpConnInfo.RemoteEndPoint.Address, tcpConnInfo.RemoteEndPoint.Port,
                              tcpConnInfo.State.ToString()));
                            
                            
                            string insertNetstatQuery = "insert into t_netstat (localip, localport, remoteip, remoteport, status) " +
                                "values ('" + tcpConnInfo.LocalEndPoint.Address.ToString() + "','" + tcpConnInfo.LocalEndPoint.Port.ToString() +
                                "','" + tcpConnInfo.RemoteEndPoint.Address.ToString() + "','" + tcpConnInfo.RemoteEndPoint.Port.ToString() + 
                                "','" + tcpConnInfo.State.ToString() + "')";

                            command = new MySqlCommand(insertNetstatQuery, connection);
                            if (command.ExecuteNonQuery() != 1)
                            {
                                Console.WriteLine("Insert error");
                            }
                        }
                    }

                    System.Threading.Thread.Sleep(5000); // 5초 대기   
                }
            }
        }
    }
}
