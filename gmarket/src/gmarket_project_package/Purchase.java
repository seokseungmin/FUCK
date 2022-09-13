package gmarket_project_package;

public class Purchase {
	int orderNo;
	String cid;
	int pno;
	int orderCount;
	int paymentAmount;
	String orderDate;
	String paymentMethod;
	String orderStatus;
	
	Purchase(int orderNo, String cid, int pno, int orderCount, int paymentAmount, String orderDate, String paymentMethod, String orderStatus){
		this.orderNo = orderNo; this.cid = cid; this.pno = pno; 
		this.orderCount = orderCount; this.paymentAmount = paymentAmount;
		this.orderDate = orderDate; this.paymentMethod = paymentMethod;
		this.orderStatus = orderStatus;
	}
}
