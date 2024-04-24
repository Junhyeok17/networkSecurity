package monitordao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import monitordto.accesslogdto;
import monitordto.memberdto;
import monitordto.netstatusdto;
import monitordto.resourcedto;

public class monitordao {
	private final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
	private final String DB_URL ="jdbc:mysql://localhost:3306/networkmonitor?serverTimezone=UTC&characterEncoding=utf8";
	private final String USERNAME = "root";
	private final String PASSWORD = "";

	private java.sql.Connection con = null;
	private static java.sql.Statement stmt = null;
	private static ResultSet rs = null;
	private PreparedStatement pstmt;

	static int view_rows = 10;
	static int counts = 10;
	
	public monitordao(){
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Database ���� ����");
		}
	}
	
	public void Close() {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Database ���� ����");
		}
	}
	
	// ��й�ȣ ��ġ Ȯ��
	public String checkPassword(String id, String password) throws SQLException {
		String sql = "select loginattempt from t_member where id = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		int attempt = 0;
		if(rs.next()) {
			attempt = rs.getInt("loginattempt");
		}
		rs.close();
		pstmt.close();
		
		if(attempt >= 5) {
			sql = "update t_member set activation = ? where id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "0");
			pstmt.setString(2, id);
			pstmt.executeUpdate();
			pstmt.close();
			return "full";
		}
		
		sql = "select count(*) as cnt, adminflag from t_member"
				+ " where id = ? and password = ?";
		
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, password);
		rs = pstmt.executeQuery();
		while(rs.next()) { // ���� ���� ��ġ�� ��
			int cnt = rs.getInt("cnt");
			String result = rs.getString("adminflag");
			
			if(cnt >= 1) {
				rs.close();
				pstmt.close();
				return result;
			}
		}
		rs.close();
		pstmt.close();
		return "false";
	}
	
	public void updateLoginAttempt(String id, String result) throws SQLException {
		String sql = "select loginattempt from t_member where id = ?";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		int attempt = 0;
		if(rs.next()) {
			attempt = rs.getInt("loginattempt");
		}
		rs.close();
		pstmt.close();
		
		sql = "update t_member set loginattempt = ? where id = ?";
		pstmt = con.prepareStatement(sql);
		
		if(result.equals("����")) {
			pstmt.setString(1, "0");
		}
		else {
			pstmt.setString(1, String.valueOf(attempt+1));
		}
		pstmt.setString(2, id);
		pstmt.executeUpdate();
	}
	
	// CPU ��뷮 ��ȸ
	public ArrayList<resourcedto> searchCPUResource() throws SQLException{
		ArrayList<resourcedto> list = new ArrayList<>();
		
		String sql = "select resource, used, curtime from t_resource "
				+ "where resource = 'cpu' order by curtime desc limit 1";
		
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			resourcedto data = new resourcedto();
			data.setResource(rs.getString("resource"));
			data.setUsed(rs.getString("used"));
			data.setCurtime(rs.getString("curtime"));
			list.add(data);
		}
		rs.close();
		pstmt.close();
		return list;
	}
	
	// �޸� ��뷮 ��ȸ
	public ArrayList<resourcedto> searchMemoryResource() throws SQLException{
		ArrayList<resourcedto> list = new ArrayList<>();
		
		String sql = "select resource, used, curtime from t_resource "
				+ "where resource = 'memory' order by curtime desc limit 1";
		
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			resourcedto data = new resourcedto();
			data.setResource(rs.getString("resource"));
			data.setUsed(rs.getString("used"));
			data.setCurtime(rs.getString("curtime"));
			list.add(data);
		}
		rs.close();
		pstmt.close();
		return list;
	}
	
	// ��ũ ��뷮 ��ȸ
	public ArrayList<resourcedto> searchDiskResource() throws SQLException{
		ArrayList<resourcedto> list = new ArrayList<>();
		
		String sql = "select resource, used, curtime from t_resource "
				+ "where resource = 'disk' order by curtime desc limit 1";
		
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			resourcedto data = new resourcedto();
			data.setResource(rs.getString("resource"));
			data.setUsed(rs.getString("used"));
			data.setCurtime(rs.getString("curtime"));
			list.add(data);
		}
		rs.close();
		pstmt.close();
		return list;
	}
	
	// ���� �߰�
	public void registerMember(String name, String id, String email, 
			String password, String adminflag) throws SQLException {
		String sql = "insert into t_member (id, name, password, email, adminflag) "
				+ "values (?, ?, ?, ?, ?)";
		
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, name);
		pstmt.setString(3, password);
		pstmt.setString(4, email);
		pstmt.setString(5, adminflag);
		pstmt.executeUpdate();
		pstmt.close();
	}
	
	// ��ü ����ڼ�
	public int getUserTotalRecord() {
		int total_pages = 0;
		String sql = "select count(*) from t_member";
		ResultSet pageset = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pageset = pstmt.executeQuery();
			
			if(pageset.next()) {
				total_pages = pageset.getInt(1);
				pageset.close();
			}
		} catch (Exception e) {
			System.out.println("getUserTotalRecord catch ����");
		}
		return total_pages;
	}
	
	public String getUserPageNumber(int tpage) {
		String str = "";
		int total_pages = getUserTotalRecord();
		int page_count = total_pages / counts + 1;
		
		if(total_pages % counts == 0)
			page_count--;
		if(tpage < 1)
			tpage = 1;
		
		int start_page = tpage - (tpage % view_rows) + 1;
		int end_page = start_page + (counts-1);
		
		if(end_page > page_count)
			end_page = page_count;
		
		if(start_page > view_rows) {
			str += "<li class=\"page-item\"><a class=\"page-link\" href='usersearch?tpage=1&'>First</a></li>";
			str += "<li class=\"page-item\"><a class=\"page-link\" href='usersearch?tpage="
					+ (start_page-view_rows);
			str += "'>Previous</a></li>";
		}
		
		for(int i=start_page; i<=end_page;i++) {
			if(i==tpage)
				str += "<li class=\"page-item active\" aria-current=\"page\">"
						+ "<a class=\"page-link\" href='#'>"+i+"</a></li>";
			else
				str += "<li class=\"page-item\"><a class=\"page-link\" href='usersearch?tpage="
						+i+"'>"+i+"</a></li>";
		}
		if(page_count > end_page) {
			str += "<li class=\"page-item\"><a class=\"page-link\" href='usersearch?tpage="
					+(end_page+1)+"'>Next</a></li>";
			str += "<li class=\"page-item\"><a class=\"page-link\" href='usersearch?tpage="
					+page_count+"'>Last</a></li>";
		}
		return str;
	}
	
	// ��� ���� ���� ��ȸ
	public ArrayList<memberdto> searchAllMember(int tpage) throws SQLException{
		String sql = "select id, name, email, adminflag, loginattempt, activation, regdate "
				+ "from t_member order by regdate desc";
		
		int absolutepage = (tpage-1)*counts+1;
		pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		rs = pstmt.executeQuery();
		
		ArrayList<memberdto> list = new ArrayList<memberdto>();

		if(rs.next()) {
			// ù ��° ������ N���� ���
			rs.absolute(absolutepage);
			int count = 0;
			while(count < counts) {
				memberdto data = new memberdto();
				data.setId(rs.getString("id"));
				data.setName(rs.getString("name"));
				data.setEmail(rs.getString("email"));
				data.setAdminflag(rs.getString("adminflag"));
				data.setLoginattempt(rs.getString("loginattempt"));
				data.setActivation(rs.getString("activation"));
				data.setRegdate(rs.getString("regdate"));
				list.add(data);
				
				if(rs.isLast())
					break;
				rs.next();
				count++;
			}
		}
		rs.close();
		pstmt.close();
		return list;
	}
	
	public boolean changePassword(String id, String curpw, String newpw) throws SQLException {
		String sql = "select count(*) as cnt from t_member "
				+ "where id = ? and password = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, curpw);
		rs = pstmt.executeQuery();
		
		int cnt = 0;
		if(rs.next()) {
			cnt = rs.getInt("cnt");
		}
		rs.close();
		pstmt.close();
		
		if(cnt==0) {
			return false;
		}
		else {
			sql = "update t_member set password = ? where id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newpw);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
			return true;
		}
	}
	
	// ���� Ȱ��ȭ ���� ������Ʈ
	public void updateUserStatus(String id) throws SQLException {
		String sql = "update t_member set loginattempt = '0', activation='1' where id = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.executeUpdate();
	}

	// ���� ����
	public void deleteMember(String id) throws SQLException {
		String sql = "delete from t_member where id = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.executeUpdate();
		pstmt.close();
	}
	
	// ���� �α� ���
	public void insertAccessLog(String id, String ip, String machine, String activity) throws SQLException {
		String sql = "select name from t_member where id = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		
		String name = null;
		while(rs.next()) {
			name = rs.getString("name");
		}
		rs.close();
		pstmt.close();
		
		sql = "insert into t_accesslog (id, name, ip, machine, activity)"
				+ " values (?,?,?,?,?)";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, name);
		pstmt.setString(3, ip);
		pstmt.setString(4, machine);
		pstmt.setString(5, activity);
		pstmt.executeUpdate();
		pstmt.close();
	}

	// ��ü ����ڼ�
	public int getLogTotalRecord() {
		int total_pages = 0;
		String sql = "select count(*) from t_accesslog";
		ResultSet pageset = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pageset = pstmt.executeQuery();
			
			if(pageset.next()) {
				total_pages = pageset.getInt(1);
				pageset.close();
			}
		} catch (Exception e) {
			System.out.println("getLogTotalRecord catch ����");
		}
		return total_pages;
	}
	
	public String getLogPageNumber(int tpage) {
		String str = "";
		int total_pages = getLogTotalRecord();
		int page_count = total_pages / counts + 1;
		
		if(total_pages % counts == 0)
			page_count--;
		if(tpage < 1)
			tpage = 1;
		
		int start_page = tpage - (tpage % view_rows) + 1;
		int end_page = start_page + (counts-1);
		
		if(end_page > page_count)
			end_page = page_count;
		
		if(start_page > view_rows) {
			str += "<li class=\"page-item\"><a class=\"page-link\" href='accesslog?tpage=1&'>First</a></li>";
			str += "<li class=\"page-item\"><a class=\"page-link\" href='accesslog?tpage="
					+ (start_page-view_rows);
			str += "'>Previous</a></li>";
		}
		
		for(int i=start_page; i<=end_page;i++) {
			if(i==tpage)
				str += "<li class=\"page-item active\" aria-current=\"page\">"
						+ "<a class=\"page-link\" href='#'>"+i+"</a></li>";
			else
				str += "<li class=\"page-item\"><a class=\"page-link\" href='accesslog?tpage="
						+i+"'>"+i+"</a></li>";
		}
		if(page_count > end_page) {
			str += "<li class=\"page-item\"><a class=\"page-link\" href='accesslog?tpage="
					+(end_page+1)+"'>Next</a></li>";
			str += "<li class=\"page-item\"><a class=\"page-link\" href='accesslog?tpage="
					+page_count+"'>Last</a></li>";
		}
		System.out.println("str : "+str);
		return str;
	}
	
	// ���� �α� ��ȸ
	public ArrayList<accesslogdto> searchAccessLog(int tpage) throws SQLException{
		ArrayList<accesslogdto> list = new ArrayList<>();
		String sql = "select id, name, ip, machine, accesstime, activity "
				+ "from t_accesslog order by accesstime desc";

		int absolutepage = (tpage-1)*counts+1;
		pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		rs = pstmt.executeQuery();

		if(rs.next()) {
			// ù ��° ������ N���� ���
			rs.absolute(absolutepage);
			int count = 0;
			while(count < counts) {
				accesslogdto data = new accesslogdto();
				data.setId(rs.getString("id"));
				data.setName(rs.getString("name"));
				data.setIp(rs.getString("ip"));
				data.setMachine(rs.getString("machine"));
				data.setAccesstime(rs.getString("accesstime"));
				data.setActivity(rs.getString("activity"));
				list.add(data);
				
				if(rs.isLast())
					break;
				rs.next();
				count++;
			}
		}
		rs.close();
		pstmt.close();
		return list;
	}
	
	// ��Ʈ��ũ Ʈ���� ��ȸ
	public String searchNetTraffic() throws SQLException {
		String result = "[";
		String sql = "select count(*) as cnt, curtime from t_monitor group by curtime order by curtime";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			result += "[Date.parse(\""+rs.getString("curtime")+"\"),"+rs.getString("cnt")+"]";
			//result += "[\""+rs.getString("curtime")+"\","+rs.getString("cnt")+"]";
			
			if(!rs.isLast())
				result += ",";
		}
		System.out.println(result);
		result += "]";
		rs.close();
		pstmt.close();
		return result;
	}
	
	// ��Ʈ��ũ ���� ���� ��ȸ
	public String searchNetStatus() throws SQLException{
		String sql = "select localip, localport, remoteip, remoteport, status "
				+ "from t_netstat where curtime = (select max(curtime) from t_netstat)";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		String result = "[";
		double remote = 1.1;
		double status = 1000;
	    while(rs.next()) {
	    	if(remote==1.1) {
	    		result += "{ id: '0.0', parent: '', name: '"+
	    			    rs.getString("localip")+":"+rs.getString("localport")+"'},";
	    	}
	    	//if(rs.getString("status").equals("Established")) {
				result += "{ id: '"+String.valueOf(remote)+"', parent: '0.0', "
						+ "name: '"+rs.getString("remoteip")+":"+rs.getString("remoteport")+"'},";
				result += "{ id: '"+String.valueOf(status)+"', parent: '"+String.valueOf(remote)+"', "
						+ "name: '"+rs.getString("status")+"'}";
				remote += 1.1;
				status += 1000;
	    	//}
				if(!rs.isLast())
					result += ",";
		}
	    result += "]";
		rs.close();
		pstmt.close();
		return result;
	}
}
