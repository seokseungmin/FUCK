package gmarket_project_package;
import java.sql.*;
public class Customer {
	String cid;
	String password;
	String cname;
	String address;
	String phoneNo;
	
	Customer(String cid, String password, String cname, String address, String phoneNo){ // ��ü������
		this.cid = cid; this.password = password; this.cname = cname;
		this.address = address; this.phoneNo = phoneNo;
	}
	
	public String toString() { // toString �޼ҵ�
		return "���̵� : " + this.cid + " / �н����� : " + this.password + " / �̸� : " + this.cname + " / �ּ� : " + this.address + " / �ڵ�����ȣ : " + this.phoneNo;
	}
	
}
