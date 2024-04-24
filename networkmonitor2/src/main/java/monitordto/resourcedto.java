package monitordto;

public class resourcedto {
	private String resource;
	private String used;
	private String total;
	private String curtime;
	
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getCurtime() {
		return curtime;
	}
	public void setCurtime(String curtime) {
		this.curtime = curtime;
	}
}
