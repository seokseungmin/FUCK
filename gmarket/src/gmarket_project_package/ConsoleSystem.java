package gmarket_project_package;

import java.util.Vector;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConsoleSystem {
	
	static Customer cLogined = null; // �α��ε� ȸ����ü�� ������
	static Vendor vLogined = null; // �α��ε� �Ǹž�ü ��ü�� ������
	
	public static void printAllProduct() { // ��� ��ǰ���� ����ϴ� �޼ҵ�
		Vector<Product> products = DB.getVectorSelectedProduct("select * from product;"); // ��� ��ǰ �������� �����´�
		
		for(int i = 0; i < products.size(); i++) {
			System.out.println("--------------------------------");
			System.out.println("��ǰ��ȣ : " + products.get(i).pno);
			System.out.println("��ǰ�̸� : " + products.get(i).pname);
			System.out.println("���� : " + products.get(i).price);
			System.out.println("��ϳ�¥ : " + products.get(i).enrolDate);
			System.out.println("--------------------------------");
		}
	}
	
	public static void printPurchasedProduct() { // ������ ��� �������� ����ϴ� �޼ҵ�
		Vector<Purchase> purchases = DB.getVectorSelectedPurchase("select * from purchase where cid = '"+cLogined.cid+"';"); // ������������ �����´�
		System.out.println("----������ ��ǰ����----");
		for(int i = 0; i < purchases.size(); i++) {
			System.out.println("�ֹ���ȣ : " + purchases.get(i).orderNo);
			System.out.println("��ǰ��ȣ : " + purchases.get(i).pno);
			System.out.println("���԰��� : " + purchases.get(i).orderCount);
			System.out.println("�����ݾ� : " + purchases.get(i).paymentAmount);
			System.out.println("������� : " + purchases.get(i).paymentMethod);
			System.out.println("�ֹ���¥ : " + purchases.get(i).orderDate);
			System.out.println("�ֹ�ó����Ȳ : " + purchases.get(i).orderStatus);
			System.out.println("-----------------------");
		}
	}
	
	public static void printAllDelivery() { // ������ ��� ������� ����ϴ� �޼ҵ�
		Vector<Deliver> delivers = DB.getVectorSelectedDeliver("select * from deliver where cid = '"+cLogined.cid+"';"); // �α��� ����ü�� ����������� �����´�
		System.out.println("----��� �������----");
		for(int i = 0; i < delivers.size(); i++) {
			System.out.println("�����ȣ : " + delivers.get(i).dno);
			System.out.println("��ǰ��ȣ : " + delivers.get(i).pno);
			System.out.println("�߼��� : " + delivers.get(i).sendDate);
			System.out.println("������ : " + delivers.get(i).arrivalDate);
			System.out.println("��ۻ��� : " + delivers.get(i).deliveryStatus);
			System.out.println("-----------------------");
		}
	}
	
	public static void printTakedOrderProduct() { // �ֹ� ���� ���� ���� ����ϴ� �޼ҵ�
		Vector<Product> products = DB.getVectorSelectedProduct("select * from product where vid = '"+vLogined.vid+"';");
		Vector<Purchase> purchases = null;
		for(int i = 0; i < products.size(); i++) {
			purchases = DB.getVectorSelectedPurchase("select * from purchase where pno = '"+products.get(i).pno+"' and orderStatus = '"+"�ֹ�ó����"+"';"); // �ֹ��������� �����´�
		}
		System.out.println("----��û���� �ֹ� ����----");
		for(int i = 0; i < purchases.size(); i++) {
			System.out.println("�ֹ���ȣ : " + purchases.get(i).orderNo);
			System.out.println("��ǰ��ȣ : " + purchases.get(i).pno);
			System.out.println("���԰��� : " + purchases.get(i).orderCount);
			System.out.println("�����ݾ� : " + purchases.get(i).paymentAmount);
			System.out.println("������� : " + purchases.get(i).paymentMethod);
			System.out.println("�ֹ���¥ : " + purchases.get(i).orderDate);
			System.out.println("-----------------------");
		}
	}
	
	public static String getCurrntTimePlusDay(int day) { // ���糯¥�� �Ű������� �־��� �ϼ���ŭ ���ؼ� ����ϴ� �޼ҵ�
		LocalDate now = LocalDate.now(); // ���糯¥ ���ϱ�
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd"); // ����
		now = now.plusDays(day); // �Ű����� �ϼ���ŭ ����
		String result = now.format(formatter);
		return result;
	}
	
	public static void joinCustomerMembership() { // �� ȸ������ �޼ҵ�
		String cid = SkScanner.getStringWithPrompt("���̵� : ");
		String password = SkScanner.getStringWithPrompt("�н����� : ");
		String cname = SkScanner.getStringWithPrompt("�̸� : ");
		String address = SkScanner.getStringWithPrompt("�ּ� : ");
		String phoneNo = SkScanner.getStringWithPrompt("�ڵ��� ��ȣ : ");
		
		Customer c = new Customer(cid, password, cname, address, phoneNo);
		DB.insertCustomerObject(c); // �� ���� �޼ҵ� ȣ��
	}
	
	public static void joinVendorMembership() { // �Ǹž�ü ȸ������ �޼ҵ�
		String vid = SkScanner.getStringWithPrompt("���̵� : ");
		String password = SkScanner.getStringWithPrompt("�н����� : ");
		String vname = SkScanner.getStringWithPrompt("�̸� : ");
		String address = SkScanner.getStringWithPrompt("�ּ� : ");
		String phoneNo = SkScanner.getStringWithPrompt("�ڵ��� ��ȣ : ");
		
		Vendor c = new Vendor(vid, password, vname, address, phoneNo);
		DB.insertVendorObject(c); // �Ǹž�ü ���� �޼ҵ� ȣ��
	}
	
	
	// ------------------------------------------ �ܼ� ȭ�� �޼ҵ� ------------------------------------------------
	public static void enterStartDisplay() { // ó�� ���� ȭ�� �޼ҵ�(�ܼ� ���� �޼ҵ�)
		DB.getConnection(); // DB����
		System.out.println("�ߡߡߡߡ�  �����Ͽ� ���Ű��� ȯ���մϴ� �ߡߡߡߡ� \n");
		System.out.println("1) �� �α��� \n2) �Ǹž�ü �α���  \n3) ȸ������");
		
		int no = SkScanner.getIntWithPrompt("�Է�  >> ");
		if(no == 1) { // �� �α����� ���� ���� ���
			String cid = SkScanner.getStringWithPrompt("���̵� : ");
			String password = SkScanner.getStringWithPrompt("�н����� : ");
			
			cLogined = DB.searchCustomer(cid, password); // �α���
			
			if(cLogined == null) { // ��ȯ�� ��ü�� ��� ������ ���� �������
				System.out.println("�α��� ���� : ���̵� �������� �ʰų�, �н����带 �߸� �Է��ϼ̽��ϴ�.");
				enterStartDisplay(); // �α��� ����, �ٽ� ����ȭ������
			}
			else { // �α��ο� ������ ���
				System.out.println("�α��� ���� ) " + cLogined.toString());
				enterCustomerHomeDisplay(); // Ȩȭ������
			}
		}
		if(no == 2) { // �Ǹž�ü �α����� ���� ���� ���
			String vid = SkScanner.getStringWithPrompt("���̵� : ");
			String password = SkScanner.getStringWithPrompt("�н����� : ");
			
			vLogined = DB.searchVendor(vid, password); // �α���
			
			if(vLogined == null) { // ��ȯ�� ��ü�� ��� ������ ���� �������
				System.out.println("�α��� ���� : ���̵� �������� �ʰų�, �н����带 �߸� �Է��ϼ̽��ϴ�.");
				enterStartDisplay(); // �α��� ����, �ٽ� ����ȭ������
			}
			else { // �α��ο� ������ ���
				System.out.println("�α��� ���� ) " + vLogined.toString());
				enterVendorHomeDisplay(); // �Ǹž�ü Ȩȭ������
			}
		}
		else if(no == 3) { // ȸ�������� �������� ���
			System.out.println("�ɼǼ��� )) 1. �� ȸ������    2. �Ǹž�ü ȸ������"); // �� ȸ����������, �Ǹž�ü ȸ���������� ����
			int no2 = SkScanner.getIntWithPrompt("�Է�>> ");
			if(no2 == 1) { // �� ȸ������
				joinCustomerMembership();
				enterStartDisplay(); // �ٽ� ���� ȭ������
			}
			else if(no2 == 2) { // �Ǹž�ü ȸ������
				joinVendorMembership();
				enterStartDisplay(); // �ٽ� ���� ȭ������
			}
			else {
				System.out.println("!!�߸��� ��ȣ�� �Է��ϼ̽��ϴ�!!\n\n");
				enterStartDisplay();
			}
		}
		else {
			System.out.println("!!�߸��� ��ȣ�� �Է��ϼ̽��ϴ�!!\n\n");
			enterStartDisplay();
		}
	}
	
	public static void enterCustomerHomeDisplay() { // �α��� �� �� Ȩ ȭ�� �޼ҵ� (�ܼ����� �޼ҵ�)
		System.out.println("\n������ �۾��� �����ϼ���");
		System.out.println("1) ��ǰ ����  \n2) ���� ��ȸ \n3) ��� ��ȸ \n4) �α׾ƿ�");
		
		int no = SkScanner.getIntWithPrompt("�Է�  >> ");
		System.out.println();
		if(no == 1) { // ��ǰ ����
			enterBuyProductDisplay();
		}
		else if(no == 2) { // ���� ��ȸ
			enterCheckOrderListDisplay();
		}
		else if(no == 3) { // ��� ��ȸ
			enterCheckDeliveryListDisplay();
		}
		else if(no == 4) { // �α׾ƿ�
			cLogined = null; // logined�� null�� �ʱ�ȭ
			System.out.println("�α׾ƿ� �Ͽ����ϴ�\n");
			enterStartDisplay();
		}
		else {
			System.out.println("!!�߸��� ��ȣ�� �Է��ϼ̽��ϴ�!!\n\n");
			enterCustomerHomeDisplay();
		}
	}
	
	public static void enterBuyProductDisplay() {
		printAllProduct(); // ��ǰ ���� �޼ҵ� ȣ��
		System.out.println("�ɼ��� �����ϼ��� ))  1. ������ ��ǰ��ȣ �Է�    2. �ڷΰ���");
		int no = SkScanner.getIntWithPrompt("�Է�  >> ");
		if(no == 1) { // ������ ��ǰ��ȣ �Է�
			int pno = SkScanner.getIntWithPrompt("��ǰ��ȣ �Է� >> ");
			enterInputPurchaseInfoDisplay(pno); // �ڼ��� ��ǰ ���� ������ �Է��ϴ� ȭ������
		}
		else if(no == 2) { // �ڷΰ���
			enterCustomerHomeDisplay();
		}
		else {
			System.out.println("!!�߸��� ��ȣ�� �Է��ϼ̽��ϴ�!!\n\n");
			enterBuyProductDisplay();
		}
	}
	
	public static void enterInputPurchaseInfoDisplay(int pno) { // �������� ���� ȭ�� �޼ҵ�
		Vector<Product> products = DB.getVectorSelectedProduct("select * from product;");
		int tmpCnt = 0; // �����ϴ� ��ǰ��ȣ���� Ȯ���ϱ� ���� �ӽ÷� ���� ����
		for(int i = 0; i < products.size(); i++) {
			if(products.get(i).pno == pno) tmpCnt++;
		}
		if(tmpCnt == 0) {
			System.out.println("!!�߸��� ��ȣ�� �Է��ϼ̽��ϴ�!!\n\n");
			enterBuyProductDisplay(); // �ٽ� ���� ȭ������
		}
		
		int orderNo = DB.getNewPurchaseOrderNo();
		String cid = cLogined.cid;
//		int pno = pno;
		int orderCount = SkScanner.getIntWithPrompt("���� ���� �Է� >> ");
		int paymentAmount = DB.calculatePaymentAmount(pno, orderCount);
		String orderDate = getCurrntTimePlusDay(0);
		String paymentMethod = "";
		String orderStatus = "�ֹ�ó����";
		System.out.println("��������� �����ϼ���))  1. �ſ�ī��    2. �������Ա�");
		int no = SkScanner.getIntWithPrompt("���� >> ");
		if(no == 1) {
			paymentMethod = "�ſ�ī��";
		}
		else if(no == 2) {
			paymentMethod = "�������Ա�";
		}
		else {
			System.out.println("!!�߸��� ��ȣ �Է�!!");
			enterBuyProductDisplay(); // �ٽ� ��ǰ���� ȭ������
		}
		
		Purchase p = new Purchase(orderNo, cid, pno, orderCount, paymentAmount, orderDate, paymentMethod, orderStatus);
		DB.insertPurchaseObject(p);
		System.out.println("�ش� ��ǰ ���Կ� �����Ͽ����ϴ�.");
		enterBuyProductDisplay(); // �ٽ� �� ȭ������
	}
	
	public static void enterCheckOrderListDisplay() { // �ֹ� ����Ʈ Ȯ�� ȭ�� �޼ҵ�
		printPurchasedProduct(); // ������ ��ǰ�������� ���
		System.out.println("�ɼ� ���� )) 1. �ֹ����    2. �ڷΰ���"); // ���� ��� ��� �� ������ �۾� ����
		int no = SkScanner.getIntWithPrompt("�Է� >> ");
		if(no == 1) {
			// �ֹ����
			cancelOrderDisplay();
		}
		else if(no == 2) {
			enterCustomerHomeDisplay(); // Ȩȭ������
		}
		else {
			System.out.println("!!�߸��� ��ȣ�� �Է��ϼ̽��ϴ�!! Ȩȭ������ ���ư��ϴ� \n\n");
			enterCustomerHomeDisplay();
		}
	}
	
	public static void cancelOrderDisplay() {
		int orderNo = SkScanner.getIntWithPrompt("����� �ֹ���ȣ �Է� >> ");
		int cnt = DB.deletePurchaseObject(cLogined, orderNo); // �α��� ��ü�� �Էµ� ��ǰ��ȣ�� �޼ҵ忡 ����
		if(cnt == 0) {
			System.out.println("!!�ֹ� ��� ����!!");
		}
		else {
			System.out.println("��� �Ϸ�Ǿ����ϴ�.");
		}
		enterCheckOrderListDisplay(); // �� ȭ������ ���ư�
	}
	
	public static void enterCheckDeliveryListDisplay() { // ��� ��ȸ ȭ�� �޼ҵ�
		printAllDelivery(); // ������� ��� �޼ҵ� ȣ��
		int cnt = SkScanner.getIntWithPrompt("�ڷΰ����� �ƹ�Ű�� �������� >> ");
		enterCustomerHomeDisplay(); // Ȩ ȭ������
	}
	
	//-------------------------------- �ؿ������ʹ� �Ǹž�ü ���� �ܼ�ȭ���̴� ------------------------------------
	
	public static void enterVendorHomeDisplay() {
		System.out.println("\n������ �۾��� �����ϼ���");
		System.out.println("1) ��ǰ ���  \n2) �ֹ� ó��  \n3) �α׾ƿ�");
		
		int no = SkScanner.getIntWithPrompt("�Է�  >> ");
		System.out.println();
		if(no == 1) { // ��ǰ ���
			enterEnrolProductDisplay();
		}
		else if(no == 2) { // �ֹ� ó��
			enterProcessOrderDisplay();
		}
		else if(no == 3) { // �α׾ƿ�
			vLogined = null; // logined�� null�� �ʱ�ȭ
			System.out.println("�α׾ƿ� �Ͽ����ϴ�\n");
			enterStartDisplay(); // ���� �α��� ȭ������
		}
		else {
			System.out.println("!!�߸��� ��ȣ�� �Է��ϼ̽��ϴ�!!\n\n");
			enterVendorHomeDisplay();
		}
	}
	
	public static void enterEnrolProductDisplay() { // ��ǰ ��� ȭ��
		int pno = DB.getNewProductNo(); // ��ǰ��ȣ ��� �޼ҵ� ȣ��
		String pname = SkScanner.getStringWithPrompt("��ǰ �̸� �Է� >> ");
		int price = SkScanner.getIntWithPrompt("��ǰ ���� �Է� >> ");
		String vid = vLogined.vid; // ����� ��ü ���̵�� �α��� �� ��ü�� ���̵�
		String enrolDate = getCurrntTimePlusDay(0); // ���糯¥�� ��ϳ�¥
		
		System.out.println("��ǰ ����� ...");
		Product p = new Product(pno, pname, price, vid, enrolDate);
		DB.insertProductObject(p);
		System.out.println("��� �Ϸ�Ǿ����ϴ�!!");
		enterVendorHomeDisplay(); // Ȩȭ������
	}
	
	public static void enterProcessOrderDisplay() {
		 printTakedOrderProduct(); // ���Ӱ� �ֹ����� ��ǰ ��� ���
		 System.out.println("�ɼǼ��� )) 1. ���ó��    2. �ڷΰ���");
		 int no = SkScanner.getIntWithPrompt("�Է� >> ");
		 if(no == 1) {
			 int orderNo = SkScanner.getIntWithPrompt("���ó�� �� �ֹ���ȣ �Է� >> ");
			 Vector<Purchase> purchases = DB.getVectorSelectedPurchase("select * from purchase where orderNo = '"+orderNo+"';");
			 
			 String sendDate = getCurrntTimePlusDay(0);
			 String arrivalDate = getCurrntTimePlusDay(3); // ���糯¥���� 3�� ��
			 
			 Deliver d = new Deliver(DB.getNewDeliverNo(), vLogined.vid, purchases.get(0).cid, purchases.get(0).pno, sendDate, arrivalDate, "��۽���"); // ��۰�ü ����
			 DB.insertDeliverObject(d); // ��ü�� ���̺� ����
			 DB.updateOrderStatus(orderNo); // �ֹ���Ȳ ������Ʈ
			 System.out.println("���ó�� �Ϸ�Ǿ����ϴ�!!");
			 enterProcessOrderDisplay();
		 }
		 else if(no == 2) {
			 enterVendorHomeDisplay();
		 }
		 else {
			 System.out.println("!!�߸��� ��ȣ�� �Է��ϼ̽��ϴ�!!\n\n");
			 enterVendorHomeDisplay();
		 }
	}
}
