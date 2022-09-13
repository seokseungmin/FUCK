package gmarket_project_package;

import java.util.Vector;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConsoleSystem {
	
	static Customer cLogined = null; // 로그인된 회원객체를 저장함
	static Vendor vLogined = null; // 로그인된 판매업체 객체를 저장함
	
	public static void printAllProduct() { // 모든 상품정보 출력하는 메소드
		Vector<Product> products = DB.getVectorSelectedProduct("select * from product;"); // 모든 상품 정보들을 가져온다
		
		for(int i = 0; i < products.size(); i++) {
			System.out.println("--------------------------------");
			System.out.println("상품번호 : " + products.get(i).pno);
			System.out.println("상품이름 : " + products.get(i).pname);
			System.out.println("가격 : " + products.get(i).price);
			System.out.println("등록날짜 : " + products.get(i).enrolDate);
			System.out.println("--------------------------------");
		}
	}
	
	public static void printPurchasedProduct() { // 구입한 모든 구입정보 출력하는 메소드
		Vector<Purchase> purchases = DB.getVectorSelectedPurchase("select * from purchase where cid = '"+cLogined.cid+"';"); // 구입정보들을 가져온다
		System.out.println("----구입한 상품정보----");
		for(int i = 0; i < purchases.size(); i++) {
			System.out.println("주문번호 : " + purchases.get(i).orderNo);
			System.out.println("상품번호 : " + purchases.get(i).pno);
			System.out.println("구입개수 : " + purchases.get(i).orderCount);
			System.out.println("결제금액 : " + purchases.get(i).paymentAmount);
			System.out.println("결제방법 : " + purchases.get(i).paymentMethod);
			System.out.println("주문날짜 : " + purchases.get(i).orderDate);
			System.out.println("주문처리현황 : " + purchases.get(i).orderStatus);
			System.out.println("-----------------------");
		}
	}
	
	public static void printAllDelivery() { // 구입한 모든 배송정보 출력하는 메소드
		Vector<Deliver> delivers = DB.getVectorSelectedDeliver("select * from deliver where cid = '"+cLogined.cid+"';"); // 로그인 고객객체의 배송정보들을 가져온다
		System.out.println("----모든 배송정보----");
		for(int i = 0; i < delivers.size(); i++) {
			System.out.println("송장번호 : " + delivers.get(i).dno);
			System.out.println("상품번호 : " + delivers.get(i).pno);
			System.out.println("발송일 : " + delivers.get(i).sendDate);
			System.out.println("도착일 : " + delivers.get(i).arrivalDate);
			System.out.println("배송상태 : " + delivers.get(i).deliveryStatus);
			System.out.println("-----------------------");
		}
	}
	
	public static void printTakedOrderProduct() { // 주문 받은 구입 정보 출력하는 메소드
		Vector<Product> products = DB.getVectorSelectedProduct("select * from product where vid = '"+vLogined.vid+"';");
		Vector<Purchase> purchases = null;
		for(int i = 0; i < products.size(); i++) {
			purchases = DB.getVectorSelectedPurchase("select * from purchase where pno = '"+products.get(i).pno+"' and orderStatus = '"+"주문처리전"+"';"); // 주문정보들을 가져온다
		}
		System.out.println("----요청받은 주문 정보----");
		for(int i = 0; i < purchases.size(); i++) {
			System.out.println("주문번호 : " + purchases.get(i).orderNo);
			System.out.println("상품번호 : " + purchases.get(i).pno);
			System.out.println("구입개수 : " + purchases.get(i).orderCount);
			System.out.println("결제금액 : " + purchases.get(i).paymentAmount);
			System.out.println("결제방법 : " + purchases.get(i).paymentMethod);
			System.out.println("주문날짜 : " + purchases.get(i).orderDate);
			System.out.println("-----------------------");
		}
	}
	
	public static String getCurrntTimePlusDay(int day) { // 현재날짜에 매개변수에 주어진 일수만큼 더해서 출력하는 메소드
		LocalDate now = LocalDate.now(); // 현재날짜 구하기
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd"); // 포맷
		now = now.plusDays(day); // 매개변수 일수만큼 더함
		String result = now.format(formatter);
		return result;
	}
	
	public static void joinCustomerMembership() { // 고객 회원가입 메소드
		String cid = SkScanner.getStringWithPrompt("아이디 : ");
		String password = SkScanner.getStringWithPrompt("패스워드 : ");
		String cname = SkScanner.getStringWithPrompt("이름 : ");
		String address = SkScanner.getStringWithPrompt("주소 : ");
		String phoneNo = SkScanner.getStringWithPrompt("핸드폰 번호 : ");
		
		Customer c = new Customer(cid, password, cname, address, phoneNo);
		DB.insertCustomerObject(c); // 고객 삽입 메소드 호출
	}
	
	public static void joinVendorMembership() { // 판매업체 회원가입 메소드
		String vid = SkScanner.getStringWithPrompt("아이디 : ");
		String password = SkScanner.getStringWithPrompt("패스워드 : ");
		String vname = SkScanner.getStringWithPrompt("이름 : ");
		String address = SkScanner.getStringWithPrompt("주소 : ");
		String phoneNo = SkScanner.getStringWithPrompt("핸드폰 번호 : ");
		
		Vendor c = new Vendor(vid, password, vname, address, phoneNo);
		DB.insertVendorObject(c); // 판매업체 삽입 메소드 호출
	}
	
	
	// ------------------------------------------ 콘솔 화면 메소드 ------------------------------------------------
	public static void enterStartDisplay() { // 처음 시작 화면 메소드(콘솔 전용 메소드)
		DB.getConnection(); // DB연결
		System.out.println("◆◆◆◆◆  지마켓에 오신것을 환영합니다 ◆◆◆◆◆ \n");
		System.out.println("1) 고객 로그인 \n2) 판매업체 로그인  \n3) 회원가입");
		
		int no = SkScanner.getIntWithPrompt("입력  >> ");
		if(no == 1) { // 고객 로그인을 선택 했을 경우
			String cid = SkScanner.getStringWithPrompt("아이디 : ");
			String password = SkScanner.getStringWithPrompt("패스워드 : ");
			
			cLogined = DB.searchCustomer(cid, password); // 로그인
			
			if(cLogined == null) { // 반환된 객체가 없어서 저장이 되지 않은경우
				System.out.println("로그인 실패 : 아이디가 존재하지 않거나, 패스워드를 잘못 입력하셨습니다.");
				enterStartDisplay(); // 로그인 실패, 다시 시작화면으로
			}
			else { // 로그인에 성공한 경우
				System.out.println("로그인 성공 ) " + cLogined.toString());
				enterCustomerHomeDisplay(); // 홈화면으로
			}
		}
		if(no == 2) { // 판매업체 로그인을 선택 했을 경우
			String vid = SkScanner.getStringWithPrompt("아이디 : ");
			String password = SkScanner.getStringWithPrompt("패스워드 : ");
			
			vLogined = DB.searchVendor(vid, password); // 로그인
			
			if(vLogined == null) { // 반환된 객체가 없어서 저장이 되지 않은경우
				System.out.println("로그인 실패 : 아이디가 존재하지 않거나, 패스워드를 잘못 입력하셨습니다.");
				enterStartDisplay(); // 로그인 실패, 다시 시작화면으로
			}
			else { // 로그인에 성공한 경우
				System.out.println("로그인 성공 ) " + vLogined.toString());
				enterVendorHomeDisplay(); // 판매업체 홈화면으로
			}
		}
		else if(no == 3) { // 회원가입을 선택했을 경우
			System.out.println("옵션선택 )) 1. 고객 회원가입    2. 판매업체 회원가입"); // 고객 회원가입인지, 판매업체 회원가입인지 선택
			int no2 = SkScanner.getIntWithPrompt("입력>> ");
			if(no2 == 1) { // 고객 회원가입
				joinCustomerMembership();
				enterStartDisplay(); // 다시 시작 화면으로
			}
			else if(no2 == 2) { // 판매업체 회원가입
				joinVendorMembership();
				enterStartDisplay(); // 다시 시작 화면으로
			}
			else {
				System.out.println("!!잘못된 번호를 입력하셨습니다!!\n\n");
				enterStartDisplay();
			}
		}
		else {
			System.out.println("!!잘못된 번호를 입력하셨습니다!!\n\n");
			enterStartDisplay();
		}
	}
	
	public static void enterCustomerHomeDisplay() { // 로그인 후 고객 홈 화면 메소드 (콘솔전용 메소드)
		System.out.println("\n수행할 작업을 선택하세요");
		System.out.println("1) 상품 구입  \n2) 구매 조회 \n3) 배송 조회 \n4) 로그아웃");
		
		int no = SkScanner.getIntWithPrompt("입력  >> ");
		System.out.println();
		if(no == 1) { // 상품 구입
			enterBuyProductDisplay();
		}
		else if(no == 2) { // 구매 조회
			enterCheckOrderListDisplay();
		}
		else if(no == 3) { // 배송 조회
			enterCheckDeliveryListDisplay();
		}
		else if(no == 4) { // 로그아웃
			cLogined = null; // logined를 null로 초기화
			System.out.println("로그아웃 하였습니다\n");
			enterStartDisplay();
		}
		else {
			System.out.println("!!잘못된 번호를 입력하셨습니다!!\n\n");
			enterCustomerHomeDisplay();
		}
	}
	
	public static void enterBuyProductDisplay() {
		printAllProduct(); // 상품 나열 메소드 호출
		System.out.println("옵션을 선택하세요 ))  1. 구입할 상품번호 입력    2. 뒤로가기");
		int no = SkScanner.getIntWithPrompt("입력  >> ");
		if(no == 1) { // 구입할 상품번호 입력
			int pno = SkScanner.getIntWithPrompt("상품번호 입력 >> ");
			enterInputPurchaseInfoDisplay(pno); // 자세한 상품 구입 정보들 입력하는 화면으로
		}
		else if(no == 2) { // 뒤로가기
			enterCustomerHomeDisplay();
		}
		else {
			System.out.println("!!잘못된 번호를 입력하셨습니다!!\n\n");
			enterBuyProductDisplay();
		}
	}
	
	public static void enterInputPurchaseInfoDisplay(int pno) { // 구입정보 적는 화면 메소드
		Vector<Product> products = DB.getVectorSelectedProduct("select * from product;");
		int tmpCnt = 0; // 존재하는 상품번호인지 확인하기 위해 임시로 쓰일 변수
		for(int i = 0; i < products.size(); i++) {
			if(products.get(i).pno == pno) tmpCnt++;
		}
		if(tmpCnt == 0) {
			System.out.println("!!잘못된 번호를 입력하셨습니다!!\n\n");
			enterBuyProductDisplay(); // 다시 구입 화면으로
		}
		
		int orderNo = DB.getNewPurchaseOrderNo();
		String cid = cLogined.cid;
//		int pno = pno;
		int orderCount = SkScanner.getIntWithPrompt("구입 개수 입력 >> ");
		int paymentAmount = DB.calculatePaymentAmount(pno, orderCount);
		String orderDate = getCurrntTimePlusDay(0);
		String paymentMethod = "";
		String orderStatus = "주문처리전";
		System.out.println("결제방법을 선택하세요))  1. 신용카드    2. 무통장입금");
		int no = SkScanner.getIntWithPrompt("선택 >> ");
		if(no == 1) {
			paymentMethod = "신용카드";
		}
		else if(no == 2) {
			paymentMethod = "무통장입금";
		}
		else {
			System.out.println("!!잘못된 번호 입력!!");
			enterBuyProductDisplay(); // 다시 상품구입 화면으로
		}
		
		Purchase p = new Purchase(orderNo, cid, pno, orderCount, paymentAmount, orderDate, paymentMethod, orderStatus);
		DB.insertPurchaseObject(p);
		System.out.println("해당 상품 구입에 성공하였습니다.");
		enterBuyProductDisplay(); // 다시 전 화면으로
	}
	
	public static void enterCheckOrderListDisplay() { // 주문 리스트 확인 화면 메소드
		printPurchasedProduct(); // 구입한 상품정보들을 출력
		System.out.println("옵션 선택 )) 1. 주문취소    2. 뒤로가기"); // 구입 목록 출력 후 수행할 작업 선택
		int no = SkScanner.getIntWithPrompt("입력 >> ");
		if(no == 1) {
			// 주문취소
			cancelOrderDisplay();
		}
		else if(no == 2) {
			enterCustomerHomeDisplay(); // 홈화면으로
		}
		else {
			System.out.println("!!잘못된 번호를 입력하셨습니다!! 홈화면으로 돌아갑니다 \n\n");
			enterCustomerHomeDisplay();
		}
	}
	
	public static void cancelOrderDisplay() {
		int orderNo = SkScanner.getIntWithPrompt("취소할 주문번호 입력 >> ");
		int cnt = DB.deletePurchaseObject(cLogined, orderNo); // 로그인 객체와 입력된 상품번호를 메소드에 전달
		if(cnt == 0) {
			System.out.println("!!주문 취소 실패!!");
		}
		else {
			System.out.println("취소 완료되었습니다.");
		}
		enterCheckOrderListDisplay(); // 전 화면으로 돌아감
	}
	
	public static void enterCheckDeliveryListDisplay() { // 배송 조회 화면 메소드
		printAllDelivery(); // 배송정보 출력 메소드 호출
		int cnt = SkScanner.getIntWithPrompt("뒤로가려면 아무키나 누르세요 >> ");
		enterCustomerHomeDisplay(); // 홈 화면으로
	}
	
	//-------------------------------- 밑에서부터는 판매업체 전용 콘솔화면이다 ------------------------------------
	
	public static void enterVendorHomeDisplay() {
		System.out.println("\n수행할 작업을 선택하세요");
		System.out.println("1) 상품 등록  \n2) 주문 처리  \n3) 로그아웃");
		
		int no = SkScanner.getIntWithPrompt("입력  >> ");
		System.out.println();
		if(no == 1) { // 상품 등록
			enterEnrolProductDisplay();
		}
		else if(no == 2) { // 주문 처리
			enterProcessOrderDisplay();
		}
		else if(no == 3) { // 로그아웃
			vLogined = null; // logined를 null로 초기화
			System.out.println("로그아웃 하였습니다\n");
			enterStartDisplay(); // 시작 로그인 화면으로
		}
		else {
			System.out.println("!!잘못된 번호를 입력하셨습니다!!\n\n");
			enterVendorHomeDisplay();
		}
	}
	
	public static void enterEnrolProductDisplay() { // 상품 등록 화면
		int pno = DB.getNewProductNo(); // 상품번호 계산 메소드 호출
		String pname = SkScanner.getStringWithPrompt("상품 이름 입력 >> ");
		int price = SkScanner.getIntWithPrompt("상품 가격 입력 >> ");
		String vid = vLogined.vid; // 등록한 업체 아이디는 로그인 한 업체의 아이디
		String enrolDate = getCurrntTimePlusDay(0); // 현재날짜가 등록날짜
		
		System.out.println("상품 등록중 ...");
		Product p = new Product(pno, pname, price, vid, enrolDate);
		DB.insertProductObject(p);
		System.out.println("등록 완료되었습니다!!");
		enterVendorHomeDisplay(); // 홈화면으로
	}
	
	public static void enterProcessOrderDisplay() {
		 printTakedOrderProduct(); // 새롭게 주문받은 상품 목록 출력
		 System.out.println("옵션선택 )) 1. 배송처리    2. 뒤로가기");
		 int no = SkScanner.getIntWithPrompt("입력 >> ");
		 if(no == 1) {
			 int orderNo = SkScanner.getIntWithPrompt("배송처리 할 주문번호 입력 >> ");
			 Vector<Purchase> purchases = DB.getVectorSelectedPurchase("select * from purchase where orderNo = '"+orderNo+"';");
			 
			 String sendDate = getCurrntTimePlusDay(0);
			 String arrivalDate = getCurrntTimePlusDay(3); // 현재날짜에서 3일 후
			 
			 Deliver d = new Deliver(DB.getNewDeliverNo(), vLogined.vid, purchases.get(0).cid, purchases.get(0).pno, sendDate, arrivalDate, "배송시작"); // 배송객체 생성
			 DB.insertDeliverObject(d); // 객체를 테이블에 삽입
			 DB.updateOrderStatus(orderNo); // 주문현황 업데이트
			 System.out.println("배송처리 완료되었습니다!!");
			 enterProcessOrderDisplay();
		 }
		 else if(no == 2) {
			 enterVendorHomeDisplay();
		 }
		 else {
			 System.out.println("!!잘못된 번호를 입력하셨습니다!!\n\n");
			 enterVendorHomeDisplay();
		 }
	}
}
