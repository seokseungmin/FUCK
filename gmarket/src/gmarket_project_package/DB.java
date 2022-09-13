package gmarket_project_package;

import java.sql.*;
import java.util.Vector;


/**
 * [ DB ] 클래스: 학생 등록, 학생 검색 등을 선택적으로 수행하는 클래스
 *   
 *  o 이 클래스의 모든 멤버변수와 메소드는 static으로 정의
 *  
 *  o main() 메소드에서 DB 클래스 객체 생성하지 않고서 
 *    static 메소드를 호출하여 작업 수행
 */

public class DB {

	static Connection con  = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	
	public static void getConnection() {
        
		String database 	= "gmarketproject"; 
        String user_name    = "root";
        String password     = "onlyroot";        
             
        // 드라이버 로딩
        try {
            	Object o = Class.forName("com.mysql.jdbc.Driver");
        } catch ( java.lang.ClassNotFoundException e ) {
                System.err.println("  !! <JDBC 오류 > Driver load 오류: " + e.getMessage() );
                e.printStackTrace();
        }
        
        try {
                // 연결하기 - 주어진 serverID의 서버와 연결시키기 위해 server[] 배열 사용
                con  = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database + "?characterEncoding=euckr",
	                                                              user_name, password );
                // Statement 생성
                stmt = con.createStatement();
 
		} catch( SQLException ex ) {               
                System.err.println("conn 오류:" + ex.getMessage() );
                ex.printStackTrace();
  	    }	
	}
	
	public static void close() { // 닫기 수행하는 메소드	
    	try {
     		if( stmt != null ) stmt.close();
    		if( con != null ) con.close();
                    
    	} catch (SQLException ex ) {}; 	
    }
	
	
	// ------------------------------ 각 테이블 정보들을 백터 객체에 담아서 반환하는 메소드들 ---------------------------------
	
	public static Vector<Customer> getVectorSelectedCustomer(String sql){ // 주어진 sql문에 준수한 고객의 정보들을 고객 클래스 백터 객체에 담아서 반환
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
			System.err.println("SQL문 결과 추출 에러:" + e.getMessage() );        
		}
		return customers;
	}
	
	public static Vector<Vendor> getVectorSelectedVendor(String sql){ // 주어진 sql문에 준수한 판매업체의 정보들을 판매업체 클래스 백터 객체에 담아서 반환
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
			System.err.println("SQL문 결과 추출 에러:" + e.getMessage() );        
		}
		return vendors;
	}
	
	public static Vector<Product> getVectorSelectedProduct(String sql){ // 주어진 sql문에 준수한 상품의 정보들을 상품 클래스 백터 객체에 담아서 반환
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
			System.err.println("SQL문 결과 추출 에러:" + e.getMessage() );        
		}
		return products;
	}
	
	public static Vector<Purchase> getVectorSelectedPurchase(String sql){ // 주어진 sql문에 준수한 구입 정보들을 구입 클래스 백터 객체에 담아서 반환
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
			System.err.println("SQL문 결과 추출 에러:" + e.getMessage() );        
		}
		return purchases;
	}
	
	public static Vector<Deliver> getVectorSelectedDeliver(String sql){ // 주어진 sql문에 준수한 배송 정보들을 배송 클래스 백터 객체에 담아서 반환
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
			System.err.println("SQL문 결과 추출 에러:" + e.getMessage() );        
		}
		return delivers;
	}
	
	// -------------------------------------------------------------------------------------------------
	
	// ------------------------------  회원과 판매업체 회원가입 및 로그인 기능 DB메소드  ---------------------------------
	public static void insertCustomerObject(Customer c) { // 회원 클래스 객체를 받아서 DB에 고객 회원 추가하는 메소드
		String sql = "insert into customer values('"+c.cid+"', '"+c.password+"', '"+c.cname+"', '"+c.address+"', '"+c.phoneNo+"');"; // insert sql문
		try {
			int cnt = stmt.executeUpdate(sql); // insert문 전달
			if(cnt == 1) { // 위 sql문이 전달되어서 1을 성공적으로 반환하면
				System.out.println("회원 가입이 완료되었습니다"); // 회원가입 성공
			}
			else {
				System.out.println("회원 가입에 실패하였습니다");
			}
		}
		catch (SQLException e) { // 중복되는 ID(Primary key)가 있을경우 예외처리됨과 동시에 회원가입 실패를 알려주도록 함
			System.err.println("중복되는 아이디가 존재합니다 !! : " + e.getMessage());
//			e.printStackTrace();
		}
	}
	
	public static Customer searchCustomer(String cid, String password) { // DB에서 입력된 아이디와 패스워드가 일치하는 회원을 찾아서 반환하는 메소드 (로그인 기능에 쓰임)
		String sql = "select * from customer where cid = '" +cid+ "' and password = '" +password+ "';"; // 매개변수로 받은 아이디와 패스워드가 일치하는 회원을 select로 검색
		try {
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) { // 한줄씩 읽음 (rs가 받은 결과값이 없을경우 이 while문은 작동되지 않는다)
				Customer c = new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)); // rs가 받은 값들을 이용해서 c객체에 저장
				return c;
			}
		}
		catch (SQLException e) {
			System.err.println("SQL 오류 : " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return null; // while문이 작동안되면 바로 null을 반환한다
	}
	
	public static void insertVendorObject(Vendor v) { // 판매업체 클래스 객체를 받아서 DB에 회원 추가하는 메소드
		String sql = "insert into vendor values('"+v.vid+"', '"+v.password+"', '"+v.vname+"', '"+v.address+"', '"+v.phoneNo+"');"; // insert sql문
		try {
			int cnt = stmt.executeUpdate(sql); // insert문 전달
			if(cnt == 1) { // 위 sql문이 전달되어서 1을 성공적으로 반환하면
				System.out.println("회원 가입이 완료되었습니다"); // 회원가입 성공
			}
			else {
				System.out.println("회원 가입에 실패하였습니다");
			}
		}
		catch (SQLException e) { // 중복되는 ID(Primary key)가 있을경우 예외처리됨과 동시에 회원가입 실패를 알려주도록 함
			System.err.println("중복되는 아이디가 존재합니다 !! : " + e.getMessage());
//			e.printStackTrace();
		}
	}
	
	public static Vendor searchVendor(String vid, String password) { // DB에서 입력된 아이디와 패스워드가 일치하는 판매업체 회원을 찾아서 반환하는 메소드 (판매업체 로그인 기능에 쓰임)
		String sql = "select * from vendor where vid = '" +vid+ "' and password = '" +password+ "';"; // 매개변수로 받은 아이디와 패스워드가 일치하는 회원을 select로 검색
		try {
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) { // 한줄씩 읽음 (rs가 받은 결과값이 없을경우 이 while문은 작동되지 않는다)
				Vendor v = new Vendor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)); // rs가 받은 값들을 이용해서 c객체에 저장
				return v;
			}
		}
		catch (SQLException e) {
			System.err.println("SQL 오류 : " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return null; // while문이 작동안되면 바로 null을 반환한다
	}
	// -------------------------------------------------------------------------------------------------
	
	public static void insertProductObject(Product p) { // 상품 테이블에 새로운 상품 객체 삽입
		String sql = "insert into product values('"+p.pno+"', '"+p.pname+"', '"+p.price+"', '"+p.vid+"', '"+p.enrolDate+"')";
		try {
			int cnt = stmt.executeUpdate(sql);
			if(cnt != 1) {
				System.err.println("정상적으로 삽입되지 않았습니다");
			}
		} catch (SQLException e) {
			System.err.println("SQL 오류 : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void insertDeliverObject(Deliver d) { // 배송 테이블에 새로운 배송 객체 삽입
		String sql = "insert into deliver values('"+d.dno+"', '"+d.vid+"', '"+d.cid+"', '"+d.pno+"', '"+d.sendDate+"', '"+d.arrivalDate+"', '"+d.deliveryStatus+"')";
		try {
			int cnt = stmt.executeUpdate(sql);
			if(cnt != 1) {
				System.err.println("정상적으로 삽입되지 않았습니다");
			}
		} catch (SQLException e) {
			System.err.println("SQL 오류 : " + e.getMessage());
			e.printStackTrace();
		}
	}
		
	public static void insertPurchaseObject(Purchase p) { // 구입 테이블에 새로운 구입 객체 삽입
		String sql = "insert into purchase values('"+p.orderNo+"', '"+p.cid+"', '"+p.pno+"', '"+p.orderCount+"', '"+p.paymentAmount+"', '"+p.orderDate+"', '"+p.paymentMethod+"', '"+p.orderStatus+"');";
		try {
			int cnt = stmt.executeUpdate(sql);
			if(cnt != 1) {
				System.err.println("정상적으로 삽입되지 않았습니다");
			}
		} catch (SQLException e) {
			System.err.println("SQL 오류 : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static int calculatePaymentAmount(int pno, int orderCount) { // 상품번호와 구입 개수를 매개변수로 받아 총 결제금액 계산해서 반환하는 메소드
		Vector<Product> product = getVectorSelectedProduct("select * from product where pno = '"+pno+"';");
		int paymentAmount = product.get(0).price * orderCount;
		return paymentAmount;
	}
	
	public static int deletePurchaseObject(Customer c, int orderNo) { // 해당 고객의 주문 삭제 처리 메소드
		String sql = "delete from purchase where cid = '"+c.cid+"' and orderNo = '"+orderNo+"'";
		try {
			int cnt = stmt.executeUpdate(sql);
			return cnt;
		} catch (SQLException e) {
			System.err.println("SQL 오류 : " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getNewPurchaseOrderNo() { // 새로운 구입 객체가 생성될때 새롭게 생성될 주문번호를 계산해서 반환
		try {
			rs = stmt.executeQuery("select * from purchase;");
			rs.last(); // 마지막 커서로 이동
			int lastOrderNo = rs.getInt("orderNo");
			return lastOrderNo + 1; // 마지막 주문번호 + 1을 반환
		} catch (SQLException e) {
			System.err.println("SQL 오류 : " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getNewProductNo() { // 새로운 상품 객체가 생성될때 새롭게 생성될 상품번호를 계산해서 반환
		try {
			rs = stmt.executeQuery("select * from Product;");
			rs.last(); // 마지막 커서로 이동
			int lastPno = rs.getInt("pno");
			return lastPno + 100; // 마지막 번호 + 100을 반환
		} catch (SQLException e) {
			System.err.println("SQL 오류 : " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getNewDeliverNo() { // 새로운 배송 객체가 생성될때 새롭게 생성될 송장번호를 계산해서 반환
		try {
			rs = stmt.executeQuery("select * from deliver;");
			rs.last(); // 마지막 커서로 이동
			int lastDno = rs.getInt("dno");
			return lastDno + 100; // 마지막 번호 + 100을 반환
		} catch (SQLException e) {
			System.err.println("SQL 오류 : " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
	
//	public static Vendor getVendorNameByPno(int pno) { // 상품번호가 주어지면 해당 상품을 등록한 판매업체 객체를 반환
//		try {
//			rs = stmt.executeQuery("select * from product where pno = '"+pno+"';");
//			String vid = rs.getString("vid"); // 해당 상품을 등록한 판매업체 아이디를 가져온다
//			rs = stmt.executeQuery("select * from vendor where vid = '"+vid+"';");
//			while (rs.next()) { // 한줄씩 읽음 (rs가 받은 결과값이 없을경우 이 while문은 작동되지 않는다)
//				Vendor v = new Vendor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)); // rs가 받은 값들을 이용해서 v객체에 저장
//				return v;
//			}
//		} catch (SQLException e) {
//			System.err.println("SQL 오류 : " + e.getMessage());
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	public static void updateOrderStatus(int orderNo) { // 배송처리하면 주문처리현황 업데이트 해주는 메소드 (주문번호를 매개변수로 받음)
		try {
			int cnt = stmt.executeUpdate("update purchase set orderStatus = '"+"주문처리완료"+"' where orderNo = '"+orderNo+"';");
		} catch (SQLException e) {
			System.err.println("SQL 오류 : " + e.getMessage());
			e.printStackTrace();
		}
	}
}
