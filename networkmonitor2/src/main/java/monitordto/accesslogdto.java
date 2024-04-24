package monitordto;

public class accesslogdto {
	private String id;
	private String name;
	private String ip;
	private String machine;
	private String accesstime;
	private String activity;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public String getAccesstime() {
		return accesstime;
	}
	public void setAccesstime(String accesstime) {
		this.accesstime = accesstime;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
}
