package monitordto;

public class memberdto {
	private String name;
	private String id;
	private String password;
	private String email;
	private String adminflag;
	private String loginattempt;
	private String activation;
	private String regdate;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAdminflag() {
		return adminflag;
	}
	public void setAdminflag(String adminflag) {
		this.adminflag = adminflag;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getLoginattempt() {
		return loginattempt;
	}
	public void setLoginattempt(String loginattempt) {
		this.loginattempt = loginattempt;
	}
	public String getActivation() {
		return activation;
	}
	public void setActivation(String activation) {
		this.activation = activation;
	}
}
