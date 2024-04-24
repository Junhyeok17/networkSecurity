package monitordto;

public class netstatusdto {
	private String localip;
	private String localport;
	private String remoteip;
	private String remoteport;
	private String status;
	public String getLocalip() {
		return localip;
	}
	public void setLocalip(String localip) {
		this.localip = localip;
	}
	public String getLocalport() {
		return localport;
	}
	public void setLocalport(String localport) {
		this.localport = localport;
	}
	public String getRemoteip() {
		return remoteip;
	}
	public void setRemoteip(String remoteip) {
		this.remoteip = remoteip;
	}
	public String getRemoteport() {
		return remoteport;
	}
	public void setRemoteport(String remoteport) {
		this.remoteport = remoteport;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
