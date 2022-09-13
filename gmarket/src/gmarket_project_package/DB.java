package gmarket_project_package;

import java.sql.*;
import java.util.Vector;


/**
 * [ DB ] Ŭ����: �л� ���, �л� �˻� ���� ���������� �����ϴ� Ŭ����
 *   
 *  o �� Ŭ������ ��� ��������� �޼ҵ�� static���� ����
 *  
 *  o main() �޼ҵ忡�� DB Ŭ���� ��ü �������� �ʰ� 
 *    static �޼ҵ带 ȣ���Ͽ� �۾� ����
 */

public class DB {

	static Connection con  = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	
	public static void getConnection() {
        
		String database 	= "gmarketproject"; 
        String user_name    = "root";
        String password     = "onlyroot";        
             
        // ����̹� �ε�
        try {
            	Object o = Class.forName("com.mysql.jdbc.Driver");
        } catch ( java.lang.ClassNotFoundException e ) {
                System.err.println("  !! <JDBC ���� > Driver load ����: " + e.getMessage() );
                e.printStackTrace();
        }
        
        try {
                // �����ϱ� - �־��� serverID�� ������ �����Ű�� ���� server[] �迭 ���
                con  = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database + "?characterEncoding=euckr",
	                                                              user_name, password );
                // Statement ����
                stmt = con.createStatement();
 
		} catch( SQLException ex ) {               
                System.err.println("conn ����:" + ex.getMessage() );
                ex.printStackTrace();
  	    }	
	}
	
	public static void close() { // �ݱ� �����ϴ� �޼ҵ�	
    	try {
     		if( stmt != null ) stmt.close();
    		if( con != null ) con.close();
                    
    	} catch (SQLException ex ) {}; 	
    }
	
	
	// ------------------------------ �� ���̺� �������� ���� ��ü�� ��Ƽ� ��ȯ�ϴ� �޼ҵ�� ---------------------------------
	
	public static Vector<Customer> getVectorSelectedCustomer(String sql){ // �־��� sql���� �ؼ��� ���� �������� �� Ŭ���� ���� ��ü�� ��Ƽ� ��ȯ
		Vector<Customer> customers = new Vector<Customer>();

		try {
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String cid = rs.getString("cid");
				String password = rs.getString("password");
				String cname = rs.getString("cname");
				String address = rs.getString("address");
				String phoneNo = rs.getString("phoneNo");
				
				Customer ct = new Customer(cid, password, cname, address, phoneNo);
				customers.add(ct);
			}
		}
		catch(SQLException e) {
			System.err.println("SQL�� ��� ���� ����:" + e.getMessage() );        
		}
		return customers;
	}
	
	public static Vector<Vendor> getVectorSelectedVendor(String sql){ // �־��� sql���� �ؼ��� �Ǹž�ü�� �������� �Ǹž�ü Ŭ���� ���� ��ü�� ��Ƽ� ��ȯ
		Vector<Vendor> vendors = new Vector<Vendor>();

		try {
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String vid = rs.getString("vid");
				String password = rs.getString("password");
				String vname = rs.getString("vname");
				String address = rs.getString("address");
				String phoneNo = rs.getString("phoneNo");
				
				Vendor vd = new Vendor(vid, password, vname, address, phoneNo);
				vendors.add(vd);
			}
		}
		catch(SQLException e) {
			System.err.println("SQL�� ��� ���� ����:" + e.getMessage() );        
		}
		return vendors;
	}
	
	public static Vector<Product> getVectorSelectedProduct(String sql){ // �־��� sql���� �ؼ��� ��ǰ�� �������� ��ǰ Ŭ���� ���� ��ü�� ��Ƽ� ��ȯ
		Vector<Product> products = new Vector<Product>();

		try {
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int pno = rs.getInt("pno");
				String pname = rs.getString("pname");
				int price = rs.getInt("price");
				String vid = rs.getString("vid");
				String enrolDate = rs.getString("enrolDate");
				
				Product pd = new Product(pno, pname, price, vid, enrolDate);
				products.add(pd);
			}
		}
		catch(SQLException e) {
			System.err.println("SQL�� ��� ���� ����:" + e.getMessage() );        
		}
		return products;
	}
	
	public static Vector<Purchase> getVectorSelectedPurchase(String sql){ // �־��� sql���� �ؼ��� ���� �������� ���� Ŭ���� ���� ��ü�� ��Ƽ� ��ȯ
		Vector<Purchase> purchases = new Vector<Purchase>();

		try {
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int orderNo = rs.getInt("orderNo");
				String cid = rs.getString("cid");
				int pno = rs.getInt("pno");
				int orderCount = rs.getInt("orderCount");
				int paymentAmount = rs.getInt("paymentAmount");
				String orderDate = rs.getString("orderDate");
				String paymentMethod = rs.getString("paymentMethod");
				String orderStatus = rs.getString("orderStatus");
				Purchase pc = new Purchase(orderNo, cid, pno, orderCount, paymentAmount, orderDate, paymentMethod, orderStatus);
				purchases.add(pc);
			}
		}
		catch(SQLException e) {
			System.err.println("SQL�� ��� ���� ����:" + e.getMessage() );        
		}
		return purchases;
	}
	
	public static Vector<Deliver> getVectorSelectedDeliver(String sql){ // �־��� sql���� �ؼ��� ��� �������� ��� Ŭ���� ���� ��ü�� ��Ƽ� ��ȯ
		Vector<Deliver> delivers = new Vector<Deliver>();

		try {
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int dno = rs.getInt("dno");
				String vid = rs.getString("vid");
				String cid = rs.getString("cid");
				int pno = rs.getInt("pno");
				String sendDate = rs.getString("sendDate");
				String arrivalDate = rs.getString("arrivalDate");
				String deliveryStatus = rs.getString("deliveryStatus");
				
				Deliver dv = new Deliver(dno, vid, cid, pno, sendDate, arrivalDate, deliveryStatus);
				delivers.add(dv);
			}
		}
		catch(SQLException e) {
			System.err.println("SQL�� ��� ���� ����:" + e.getMessage() );        
		}
		return delivers;
	}
	
	// -------------------------------------------------------------------------------------------------
	
	// ------------------------------  ȸ���� �Ǹž�ü ȸ������ �� �α��� ��� DB�޼ҵ�  ---------------------------------
	public static void insertCustomerObject(Customer c) { // ȸ�� Ŭ���� ��ü�� �޾Ƽ� DB�� �� ȸ�� �߰��ϴ� �޼ҵ�
		String sql = "insert into customer values('"+c.cid+"', '"+c.password+"', '"+c.cname+"', '"+c.address+"', '"+c.phoneNo+"');"; // insert sql��
		try {
			int cnt = stmt.executeUpdate(sql); // insert�� ����
			if(cnt == 1) { // �� sql���� ���޵Ǿ 1�� ���������� ��ȯ�ϸ�
				System.out.println("ȸ�� ������ �Ϸ�Ǿ����ϴ�"); // ȸ������ ����
			}
			else {
				System.out.println("ȸ�� ���Կ� �����Ͽ����ϴ�");
			}
		}
		catch (SQLException e) { // �ߺ��Ǵ� ID(Primary key)�� ������� ����ó���ʰ� ���ÿ� ȸ������ ���и� �˷��ֵ��� ��
			System.err.println("�ߺ��Ǵ� ���̵� �����մϴ� !! : " + e.getMessage());
//			e.printStackTrace();
		}
	}
	
	public static Customer searchCustomer(String cid, String password) { // DB���� �Էµ� ���̵�� �н����尡 ��ġ�ϴ� ȸ���� ã�Ƽ� ��ȯ�ϴ� �޼ҵ� (�α��� ��ɿ� ����)
		String sql = "select * from customer where cid = '" +cid+ "' and password = '" +password+ "';"; // �Ű������� ���� ���̵�� �н����尡 ��ġ�ϴ� ȸ���� select�� �˻�
		try {
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) { // ���پ� ���� (rs�� ���� ������� ������� �� while���� �۵����� �ʴ´�)
				Customer c = new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)); // rs�� ���� ������ �̿��ؼ� c��ü�� ����
				return c;
			}
		}
		catch (SQLException e) {
			System.err.println("SQL ���� : " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return null; // while���� �۵��ȵǸ� �ٷ� null�� ��ȯ�Ѵ�
	}
	
	public static void insertVendorObject(Vendor v) { // �Ǹž�ü Ŭ���� ��ü�� �޾Ƽ� DB�� ȸ�� �߰��ϴ� �޼ҵ�
		String sql = "insert into vendor values('"+v.vid+"', '"+v.password+"', '"+v.vname+"', '"+v.address+"', '"+v.phoneNo+"');"; // insert sql��
		try {
			int cnt = stmt.executeUpdate(sql); // insert�� ����
			if(cnt == 1) { // �� sql���� ���޵Ǿ 1�� ���������� ��ȯ�ϸ�
				System.out.println("ȸ�� ������ �Ϸ�Ǿ����ϴ�"); // ȸ������ ����
			}
			else {
				System.out.println("ȸ�� ���Կ� �����Ͽ����ϴ�");
			}
		}
		catch (SQLException e) { // �ߺ��Ǵ� ID(Primary key)�� ������� ����ó���ʰ� ���ÿ� ȸ������ ���и� �˷��ֵ��� ��
			System.err.println("�ߺ��Ǵ� ���̵� �����մϴ� !! : " + e.getMessage());
//			e.printStackTrace();
		}
	}
	
	public static Vendor searchVendor(String vid, String password) { // DB���� �Էµ� ���̵�� �н����尡 ��ġ�ϴ� �Ǹž�ü ȸ���� ã�Ƽ� ��ȯ�ϴ� �޼ҵ� (�Ǹž�ü �α��� ��ɿ� ����)
		String sql = "select * from vendor where vid = '" +vid+ "' and password = '" +password+ "';"; // �Ű������� ���� ���̵�� �н����尡 ��ġ�ϴ� ȸ���� select�� �˻�
		try {
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) { // ���پ� ���� (rs�� ���� ������� ������� �� while���� �۵����� �ʴ´�)
				Vendor v = new Vendor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)); // rs�� ���� ������ �̿��ؼ� c��ü�� ����
				return v;
			}
		}
		catch (SQLException e) {
			System.err.println("SQL ���� : " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return null; // while���� �۵��ȵǸ� �ٷ� null�� ��ȯ�Ѵ�
	}
	// -------------------------------------------------------------------------------------------------
	
	public static void insertProductObject(Product p) { // ��ǰ ���̺� ���ο� ��ǰ ��ü ����
		String sql = "insert into product values('"+p.pno+"', '"+p.pname+"', '"+p.price+"', '"+p.vid+"', '"+p.enrolDate+"')";
		try {
			int cnt = stmt.executeUpdate(sql);
			if(cnt != 1) {
				System.err.println("���������� ���Ե��� �ʾҽ��ϴ�");
			}
		} catch (SQLException e) {
			System.err.println("SQL ���� : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void insertDeliverObject(Deliver d) { // ��� ���̺� ���ο� ��� ��ü ����
		String sql = "insert into deliver values('"+d.dno+"', '"+d.vid+"', '"+d.cid+"', '"+d.pno+"', '"+d.sendDate+"', '"+d.arrivalDate+"', '"+d.deliveryStatus+"')";
		try {
			int cnt = stmt.executeUpdate(sql);
			if(cnt != 1) {
				System.err.println("���������� ���Ե��� �ʾҽ��ϴ�");
			}
		} catch (SQLException e) {
			System.err.println("SQL ���� : " + e.getMessage());
			e.printStackTrace();
		}
	}
		
	public static void insertPurchaseObject(Purchase p) { // ���� ���̺� ���ο� ���� ��ü ����
		String sql = "insert into purchase values('"+p.orderNo+"', '"+p.cid+"', '"+p.pno+"', '"+p.orderCount+"', '"+p.paymentAmount+"', '"+p.orderDate+"', '"+p.paymentMethod+"', '"+p.orderStatus+"');";
		try {
			int cnt = stmt.executeUpdate(sql);
			if(cnt != 1) {
				System.err.println("���������� ���Ե��� �ʾҽ��ϴ�");
			}
		} catch (SQLException e) {
			System.err.println("SQL ���� : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static int calculatePaymentAmount(int pno, int orderCount) { // ��ǰ��ȣ�� ���� ������ �Ű������� �޾� �� �����ݾ� ����ؼ� ��ȯ�ϴ� �޼ҵ�
		Vector<Product> product = getVectorSelectedProduct("select * from product where pno = '"+pno+"';");
		int paymentAmount = product.get(0).price * orderCount;
		return paymentAmount;
	}
	
	public static int deletePurchaseObject(Customer c, int orderNo) { // �ش� ���� �ֹ� ���� ó�� �޼ҵ�
		String sql = "delete from purchase where cid = '"+c.cid+"' and orderNo = '"+orderNo+"'";
		try {
			int cnt = stmt.executeUpdate(sql);
			return cnt;
		} catch (SQLException e) {
			System.err.println("SQL ���� : " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getNewPurchaseOrderNo() { // ���ο� ���� ��ü�� �����ɶ� ���Ӱ� ������ �ֹ���ȣ�� ����ؼ� ��ȯ
		try {
			rs = stmt.executeQuery("select * from purchase;");
			rs.last(); // ������ Ŀ���� �̵�
			int lastOrderNo = rs.getInt("orderNo");
			return lastOrderNo + 1; // ������ �ֹ���ȣ + 1�� ��ȯ
		} catch (SQLException e) {
			System.err.println("SQL ���� : " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getNewProductNo() { // ���ο� ��ǰ ��ü�� �����ɶ� ���Ӱ� ������ ��ǰ��ȣ�� ����ؼ� ��ȯ
		try {
			rs = stmt.executeQuery("select * from Product;");
			rs.last(); // ������ Ŀ���� �̵�
			int lastPno = rs.getInt("pno");
			return lastPno + 100; // ������ ��ȣ + 100�� ��ȯ
		} catch (SQLException e) {
			System.err.println("SQL ���� : " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getNewDeliverNo() { // ���ο� ��� ��ü�� �����ɶ� ���Ӱ� ������ �����ȣ�� ����ؼ� ��ȯ
		try {
			rs = stmt.executeQuery("select * from deliver;");
			rs.last(); // ������ Ŀ���� �̵�
			int lastDno = rs.getInt("dno");
			return lastDno + 100; // ������ ��ȣ + 100�� ��ȯ
		} catch (SQLException e) {
			System.err.println("SQL ���� : " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
	
//	public static Vendor getVendorNameByPno(int pno) { // ��ǰ��ȣ�� �־����� �ش� ��ǰ�� ����� �Ǹž�ü ��ü�� ��ȯ
//		try {
//			rs = stmt.executeQuery("select * from product where pno = '"+pno+"';");
//			String vid = rs.getString("vid"); // �ش� ��ǰ�� ����� �Ǹž�ü ���̵� �����´�
//			rs = stmt.executeQuery("select * from vendor where vid = '"+vid+"';");
//			while (rs.next()) { // ���پ� ���� (rs�� ���� ������� ������� �� while���� �۵����� �ʴ´�)
//				Vendor v = new Vendor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)); // rs�� ���� ������ �̿��ؼ� v��ü�� ����
//				return v;
//			}
//		} catch (SQLException e) {
//			System.err.println("SQL ���� : " + e.getMessage());
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	public static void updateOrderStatus(int orderNo) { // ���ó���ϸ� �ֹ�ó����Ȳ ������Ʈ ���ִ� �޼ҵ� (�ֹ���ȣ�� �Ű������� ����)
		try {
			int cnt = stmt.executeUpdate("update purchase set orderStatus = '"+"�ֹ�ó���Ϸ�"+"' where orderNo = '"+orderNo+"';");
		} catch (SQLException e) {
			System.err.println("SQL ���� : " + e.getMessage());
			e.printStackTrace();
		}
	}
}
