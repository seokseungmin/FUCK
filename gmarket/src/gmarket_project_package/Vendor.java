package gmarket_project_package;

public class Vendor {
	String vid;
	String password;
	String vname;
	String address;
	String phoneNo;
	
	public Vendor(String vid, String password, String vname, String address, String phoneNo) {
		this.vid = vid; this.password = password; this.vname = vname;
		this.address = address; this.phoneNo = phoneNo;
	}
	
	public String toString() { // toString �޼ҵ�
		return "���̵� : " + this.vid + " / �н����� : " + this.password + " / ��ü�� : " + this.vname + " / �ּ� : " + this.address + " / ��ȭ��ȣ : " + this.phoneNo;
	}
}
