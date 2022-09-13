package gmarket_project_package;
import java.io.*;

/**
 ** SkScanner: �輺�� ������ �ۼ��� �Է��� ���� Ŭ����
 **    o �� SkScanner Ŭ������ �����Ӱ� �Էµ� ������ �߿��� ����, ����, �Ǽ�, ���ڿ�, boolean, �ĺ��ڸ� 
 **        ã�Ƽ� �Է��ϴ� �޼ҵ���� ����
 **    o SkScanner Ŭ������ ��ü�� �������� �ʰ� SkScanner Ŭ������ �޼ҵ带 ����� �� �ֵ��� 
 **        ��� �޼ҵ�� static �޼ҵ�
 **
 **    o ��� �� :  
 **        char c = SkScanner.getChar(); (�Ǵ� SkScanner.getchar(); - �̴� C ���� �����Ǳ� ����)
 **        int n = SkScanner.getInt();             // �ԷµǴ� ���ڿ����� ù ��° ������ ã�� int ������ ��ȯ
 **        int n = SkScanner.getLong();            // �ԷµǴ� ���ڿ����� ù ��° ������ ã�� long ������ ��ȯ
 **        double d = SkScanner.getDouble()        // �ԷµǴ� ���ڿ����� ù ��° �Ǽ��� ã�� double ������ ��ȯ
 **        float f = SkScanner.getFloat()          // �ԷµǴ� ���ڿ����� ù ��° �Ǽ��� ã�� float ������ ��ȯ
 **        char[] name = SkScanner.getString();    // '\n'�� �Էµ� ������ �Էµ� ���ڵ��� char �迭�� ��ȯ
 **        String name = SkScanner.getString();    // '\n'�� �Էµ� ������ �Էµ� ���ڵ��� String ������ ��ȯ
 **        boolean bool = SkScanner.getBoolean();  // �ԷµǴ� ���ڿ����� true �Ǵ� false�� ã�� boolean ������ ��ȯ
 **        String id = SkScanner.getIdentifier();  // �ԷµǴ� ���ڿ����� Java�� �ĺ��ڸ� ã�� String ������ ��ȯ
 **                                                //   �ĺ����� ù ����: (������ �ѱ� _ $), �����κ�: (������ �ѱ� ���� _ $)
 **
 **        char c = SkScanner.getCharNonWhite();   // ���鹮��(white space: blank, tab, newline ��)�� ������ ù ���ڸ� �Է� 
 *
 **    o ����: (2021-04-04): ���๮�ڰ� 'n'�� ��� ����� ���๮�� ���� �ȵǴ� ������ �ذ���                   
 *            (2022-01-04): ���� �迭 ��ȯ�ϴ� �޼ҵ� geCharArray() �߰��� - Java���� �н����� ���� String�� �ƴ� ���� �迭�� ������ �����ϹǷ� �̸� ����   geCharArray() �߰�                                              
 ** 
 **    o �������
 **      (1) �� Ŭ������ �޼ҵ���� ǥ���Է��� Ű������ ��쿡�� �� �����Ѵ�. 
 **          �׷��� ǥ���Է��� Ű���尡 �ƴϰ� ���� ������ ����� ���¿��� �Է� �޼ҵ尡 ȣ��Ǹ�
 **          ó�� �߿� getcharPrivate()���� EOF(End Of File) ���ڸ� ������ -1�� ��ȯ�Ǹ�, 
 **          �̶��� �Է��� ����� ó������ �ʰ� ������ �߻��� �� ����
 **          �̸� ���� ���� ó���� �� �� ������ �����ϰ� ����ϰ� �ϱ� ���� ���� ó���� ���� �ʾ���
 *
 *       (2) getString() �޼ҵ�� ����Ű�� �ԷµǱ� ������ �Էµ� ���ڵ��� ���ڿ��� ��ȯ�ϴ� �޼ҵ��̹Ƿ�
 *           java.util.Scanner Ŭ������ nextLine() �޼ҵ�� ������ ����� �޼ҵ���
 *           
 *           java.util.Scanner Ŭ������ next() �޼ҵ忡 �ش�Ǵ� �޼ҵ�� ����
 *                                                        
 */
 
public class SkScanner { 
	final static int MAX_LENGTH_OF_PEEKCHARS = 100;
	final static int MAX_LENGTH_OF_STRING = 2000;
	
	static java.io.BufferedReader in;

	static {
		try {
			in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		} catch(Exception e) { 
			
		}
	}
	
	private static int[] peekChars = new int[MAX_LENGTH_OF_PEEKCHARS]; // �ִ� 100���� ���� ���ڸ� �ٽ� �б� ���� �����ϴ� peekChars �迭 
	private static int peekcIndex = -1;	       // peekChars �迭������ ������ ���� ��ġ, -1�̸� ����� ���ڰ� ���� 
	
	private static int sign;  // ���� ���� �Ǵ� �Ǽ��� ��ȣ�� ����ϱ� ���� ����

	private SkScanner()  {
	}

	// getcharPrivate() : �� ���ڸ� �б� ���� ���� ���� �޼ҵ�. 
	//  - peekcIndex�� -1�� �ƴϸ� peekChars[peekcIndex]�� ��ȯ
	//  - peekcIndex�� -1�̸� in.read()�� �̿��Ͽ� �� ���ڸ� �о����
	private static int getcharPrivate() {	
		int retval;

		try {
			if (peekcIndex >=  0) { 
				// peekcIndex�� -1�� �ƴϸ� peekChars[peekcIndex]�� ��ȯ�ϰ�, peekcIndex ����
				retval = peekChars[peekcIndex--]; 

				return retval;
			}

			return in.read();  // Ű���忡�� �Էµ� ���ڸ� �о� ��ȯ
		} catch (IOException e)  {  return -1;}
	}


	// ungetc(): in.read()�� �̿��Ͽ� �̹� �Է��� ���ڸ� �ٽ� ���� �� �ֵ��� peekChars �迭�� �����ϴ� �޼ҵ�
	private  static void ungetc(int c) {	
		peekChars[++peekcIndex] = c;	// peekcIndex�� 1 ������ �� �־��� ���ڸ� peekChars[peekcIndex]�� ����
	} 

	// skipUntilDigit() : ù ����Ʈ�� ���� ������ ���ڵ��� skip�Ͽ� ù ����Ʈ�� ��ȯ�ϴ� �޼ҵ�
	//  - �� ������ȣ�� ����Ʈ �տ� ���� ������ sign �ʵ带 -1�� �����.
	private static int skipUntilDigit() {	
		int c;

		sign = 1;    // sign�� +�� �����ϰ� ��
		while((c=getcharPrivate()) != -1)  {
			if (c>='0' && c <= '9')  // ���� ���ڰ� ����Ʈ�̸� �̸� ��ȯ
				return c;
			else if (c == '-')       // ���� ���ڰ� '-'�̸� sign�� ����(-1)��
				sign = -1;	     	
			else
				sign = 1;	         // �׿ܴ� �����ϰ� sign�� ���(1)�� set
		}

		return -1;
	}
	
	// skipUntilDigitOrDot() : ù ����Ʈ �Ǵ� '.'�� ���� ������ ���ڵ��� skip�Ͽ� ù ����Ʈ�� ��ȯ�ϴ� �޼ҵ�
	//  - �� ������ȣ�� ����Ʈ �տ� ���� ������ sign �ʵ带 -1�� �����.
	//  - ��ŵ�� �� �� ��ȣ(+/-)�� ��Ÿ���� �̸� ǥ���ϱ� ���� sing ������ 1/-1���� ������
	//  - �Ǽ����� +.123 �Ǵ� -.123 ���� ������ ���� '.' ������ �Էµ� ��ȣ�� ó���� 
	private static int skipUntilDigitOrDot() {	
		int c, cPriv = -1;

		sign = 1;    // sign�� +�� �����ϰ� ��
		
		while((c=getcharPrivate()) != -1)  {
			if (c>='0' && c <= '9' || c == '.')  // ���� ���ڰ� ����Ʈ �Ǵ� '.'�̸� �̸� ��ȯ
				return c;
			else if (c == '+' || (c=='.' && cPriv=='+'))         // ���� ���ڰ� '+' �Ǵ� "+."�̸� sign�� ���(1)��
				sign = 1;	     	
			else if (c == '-' || (c=='.' && cPriv=='-'))         // ���� ���ڰ� '-' �Ǵ� "-."�̸� sign�� ����(-1)��
				sign = -1;	     	
			else 
				sign = 1;	     // �׿ܴ� �����ϰ� sign�� ���(1)�� set
			
			cPriv = c;
		}

		return -1;
	}

	// removeLastNewLineChar(): ����, �Ǽ�, ���ڿ�, �ĺ��� ���� �б� ���� �Էµ� ���๮��('\r', '\n')�� ���Ž�Ŵ
	//  - ����, �Ǽ�, ���ڿ�, �ĺ��� ���� �б� ���� �Էµ� ������ ���๮�ڸ� ���Ž��� 
	//  - ������ getChar()�� ���� �� ���๮�ڰ� �Ǵٽ� �������� �ʰ� ��
	//  - �� �޼ҵ�� �ݵ�� getLong(), getDouble(), getString(),  getIdentifier()�޼ҵ��� ���������� ȣ��Ǿ�� ��
	//  - (2021-04-04 ����): ���๮�ڰ� 'n'�� ��� ����� ���๮�� ���� �ȵǴ� ���� �ذ��� 
	static void removeLastNewLineChar() {
		int c1, c2;
		if ( (c1 = getcharPrivate()) == '\r') {
			if ( (c2 = getcharPrivate()) == '\n')		
				return;  // ���๮�ڰ� '\r', '\n'�� ���(������ �ý����� ���๮��) �̸� �о� ���Ž�Ŵ 
			else {
				ungetc(c2);
				ungetc(c1);
			}
		} 
		else if (c1 == '\n')   // 2021-04-04 ����: ���๮�ڰ� '\n'�� ���(���н��� �������� ���๮��)�� ���� '\n'�� ������
			return;
		else
			ungetc(c1);  // ���๮�� �ƴϸ� ���� ���ڸ� �ٽ� �е��� ungetc
	}

	// getChar() : ǥ���Է¿��� ���ڸ� �о� ��ȯ�ϴ� �޼ҵ�
	public static  char getChar() {
		return (char) getcharPrivate();
	}

	// getchar() : ǥ���Է¿��� ���ڸ� �о� ��ȯ�ϴ� �޼ҵ�
	public static char getchar() {
		return getChar();
	}
	
	// getCharNonWhite() : ǥ���Է¿��� ���� ���ڸ� �����ϰ� ù ��° ���ڸ� �о� ��ȯ�ϴ� �޼ҵ�   
	public static char getCharNonWhite() {
		
		while(true) {
			int c = getcharPrivate();
			
			if (c != ' ' && c != '\t' && c != '\n')
				return (char) c;
		}
	}

	// getLong() : ǥ���Է¿����� ���� ���ڵ��� �о� long Ÿ���� ������ ��ȯ�Ͽ� ��ȯ�ϴ� �޼ҵ�
	//  - ����� �ƴ϶� ������ ó��, ���� ���� ����Ʈ�� �ƴ� ���ڴ� ��ŵ��
	//  - ���� �߰����� _�� ������(1_234_567, 1_____234______567)
	public static long getLong() {
		int c;

		c = skipUntilDigit();   // ù ��° ����Ʈ�� ���� ������ ���ڸ� �о� ������		
		ungetc(c);              // ���� ù ��° ����Ʈ�� �ٽ� �б� ���� ungetc��Ŵ
		
		long l = getLongWithoutSkip(); // ��ŵ ���� ������ �о� long ������ ��ȯ
		
		removeLastNewLineChar();       // ����, �Ǽ�, ���ڿ�, �ĺ��� ���� �б� ���� �Էµ� '\r', '\n'�� ���Ž�Ŵ
		return l;
	}
		
	// getInt() : ǥ���Է¿����� ���� ���ڵ��� �о� int ������ ��ȯ�Ͽ� ��ȯ�ϴ� �޼ҵ�
	//  - ����� �ƴ϶� ������ ó��, ���� ���� ����Ʈ�� �ƴ� ���ڴ� ��ŵ��
	public static  int getInt() {
		return (int) getLong() ;
	}

	// getLongStringWithoutSkip() : ǥ���Է¿����� ��ŵ���� ����Ʈ ���ڵ��� �о� String Ÿ���� ������ ��ȯ�Ͽ� ��ȯ�ϴ� �޼ҵ�
	//  - ����Ʈ�� �ƴ� ���ڴ� ��� ��ŵ�� ���¿��� ȣ���
	//  - Java������ ���� �߰��� _�� ����Ͽ� �� �ڸ����� ������ �б� ���� ǥ��(1_234_567, 1_____234______567)�ϹǷ�
	//    ���� �߰��� _�� ������
	private static String getLongStringWithoutSkip() {
		int c;
		String s = "";

		int countUnderScore = 0;  // ���� �߰��� ��Ÿ���� ���ӵ� _�� ����

		while((c = getcharPrivate()) >='0' && c <='9' || c == '_')  { // ���ӵǴ� ����Ʈ �Ǵ� _�� ���Ͽ�			
			if (c >= '0' && c <= '9') {
				s = s + (char) c;         //   ���ڿ��� �����Ͽ� ��ȯ
				countUnderScore = 0;
			}
			else if (c == '_') {  // -�� �ԷµǸ�
				countUnderScore++; // ���ӵ� _�� ������ 1 ������Ŵ
			}
		}

		if (countUnderScore > 0) // ������ ���� �� �Էµ� _�� ��� ungetc��Ŵ
			for (int i=0; i<countUnderScore; i++)
				ungetc('_');

		
		ungetc(c);           // ������ ���� ���ڴ� ���� getcharPrivate()�� �е��� ����
				
		return s ;
	}
	
	// getLongWithoutSkip() : ǥ���Է¿����� ��ŵ���� ����Ʈ ���ڵ��� �о� long Ÿ���� ������ ��ȯ�Ͽ� ��ȯ�ϴ� �޼ҵ�
	//  - ����� �ƴ϶� ������ ó��, ����Ʈ�� �ƴ� ���ڴ� ��� ��ŵ�� ���¿��� ȣ���
	//  - �� �޼ҵ尡 ȣ��Ǳ� ���� ��ȣ�� ó���Ǿ� sign ������ ����Ǿ� �����Ƿ� sign�� ������ 
	private static  long getLongWithoutSkip() {
		return sign * Long.parseLong(getLongStringWithoutSkip());
	}
	// getintWithoutSkip() : ǥ���Է¿����� ��ŵ���� �������ڵ��� �о� int Ÿ���� ������ ��ȯ�Ͽ� ��ȯ�ϴ� �޼ҵ�
	//  - ����� �ƴ϶� ������ ó��, ����Ʈ�� �ƴ� ���ڴ� ��� ��ŵ�� ���¿��� ȣ���
	private static  int getIntWithoutSkip() {
		return (int) getLongWithoutSkip();
	}

	// getDouble() : 23.45, -12.3, -0.0123, +.01234, -.1234e33 1.234e+12, 1.234E-12 ��
	//   Java�� �Ǽ� ���� �Է��Ͽ� double ������ ��ȯ�ϴ� �޼ҵ�
	//  -  �Ǽ� ����: "[+/-������].[�Ҽ���]E/e[+/-������]" 
	//
	//  -  �Ǽ� �Է��� ���� �ݵ�� ���ڰ� . ���� ��Ÿ������ . ������ ���ڰ� ��Ÿ���� ��
	//  -  �ùٸ� �Ǽ��� �Է��ϴ� ���� �ټ� �����
	//       ���� ��� "...---.-12.345.012"�� �ԷµǸ� -12.345���� �Է��ؾ��ϹǷ�
	//  -  �����ΰ� �����鼭 '.' ������ �Ҽ��� ����Ʈ�� �Էµ��� ������ �ٽ� �Ǽ� �Է��� ���۵Ǿ�� �� 
	public static double getDouble() {
		long n = 0;          // ������ ������ ���� 
		double d = 0;       // �Ҽ��� ������ ����
		long exp = 0;        // ������ ������ ����
		
		int signInt = 1;    // ��ȣ�� ������ ����
		int c;

		c = skipUntilDigitOrDot();   // ù ��° ����Ʈ �Ǵ� '.'�� ��´�	
		signInt = sign;       // �������� ��ȣ�� ����

		ungetc(c);           // ��� ���� ���ڸ� �ٽ� �б� ���� ungetc()

//		System.out.println("  <for debug >c1 = " + c + " " + (char) c);
		
		boolean isThereIntPart = false;         // ������ ���� ������ ����
		boolean isThereUnderPointPart = false;  // �Ҽ��� ���� ������ ����
		
		if (c>='0' && c<='9') { // �����ΰ� �����ϸ�
			n = getLongWithoutSkip();         // ����Ʈ �ƴ� ���ڿ� ���� ��ŵ ���� ������ �Է�
			isThereIntPart = true;
		}

		c = getcharPrivate();  // �Ҽ��θ� Ȯ���ϱ� ���� ���ڸ� ����

		if (c=='.') {   // ������  '.' ���ڰ� ������ ��Ÿ���� �Ҽ��ΰ� ���۵� ���ɼ��� �����Ƿ� 
			int c2 = getcharPrivate();
			if (c2>='0' && c2 <='9') {      // '.' ���� ���� ���ڰ� ���� ����Ʈ �����̸� �Ҽ��ΰ� �����ϹǷ� �̸� ó��
			    ungetc(c2);                 // ��� ���� ���ڸ� �ٽ� �б� ���� ungetc()
				d = getDoubleUnderPoint();  // �Ҽ��θ� �Է�
				
				isThereUnderPointPart = true;
			}
			else {      // '.' ������ �Ҽ��ΰ� ��Ÿ���� �ʴ� ����̹Ƿ�
				if (isThereIntPart) {  // �����ΰ� ������ �Ҽ��δ� 0��
					d = 0;  // �Ҽ��δ� 0
					ungetc(c2);           // ���� ���ڸ� �ٽ� �б� ���� ����
				}
				else {  // �����ε� ���� �Ҽ��ε� ������ �Ǽ��� �Է��� �ȵ� �����̹Ƿ� '.'�� c�� �����ϰ� �ٽ� �Ǽ� �Է� �õ�
					ungetc(c2);           // ���� ���ڸ� �ٽ� �б� ���� ����
					return getDouble();   // ���ݱ����� �Է��� �����ϰ� ���ο� �Ǽ��� �Է��ϹǷ� ��� ȣ���� ����
				}
			}		
		}
		else
			ungetc(c); // �Ҽ��ΰ� ������ ���� ���ڸ� ungetc()
		
		c = getcharPrivate();  // �����θ� Ȯ���ϱ� ���� ���ڸ� ����
		if (c=='e' || c=='E') { // e �Ǵ� E�� �����ΰ� ���۵Ǹ�
			int c2 = getcharPrivate();
			
			if (c2 >= '0' && c2 <= '9') {  // ��ȣ�� ���� �����ΰ� ������
				ungetc(c2); // ���� ����Ʈ ���ڸ� �ٽ� �б� ���� ����
				
				exp = getIntWithoutSkip();         // ����Ʈ �ƴ� ���ڿ� ���� ��ŵ ���� ������ �Է��Ͽ� ������ ������ ����				
			}
			else { // ��ȣ �ִ� �����ΰ� �ִ°��� üũ�Ͽ� ó����
				if (c2 == '+' || c2 == '-') {
					int c3 = getcharPrivate();
					if (c3 >= '0' && c3 <= '9') {  // +/- ������ �������� ������ ������
						ungetc(c3); // ���� ���ڸ� �ٽ� �б� ���� ����

						exp = getIntWithoutSkip(); // ����Ʈ �ƴ� ���ڿ� ���� ��ŵ ���� ������ �Է��Ͽ� ������ ������ ����			
						exp = exp * ( (c2 == '+') ? 1 : -1 ) ;   // +�����δ� 1, -�����δ� -1�� ����	
					}
					else {  // e�� �����ϰ� +/-�� �ԷµǾ�����  +/- ������ ���� �ƴϸ� �����ΰ� ���� ������ ó�� 
						ungetc(c3);
						ungetc(c2);
						ungetc(c);
					}
				}
				else { // e�� ���������� ����, + , -�� �ƴϸ� �����ΰ� ���� ������ ó��
					ungetc(c2);
					ungetc(c);				
				}
			}
	    }
		else
			ungetc(c); // �����ΰ� ������ ���� ���ڸ� ungetc() 

		if (signInt == 1) 	// ���� �������� ��ȣ�� ����
			d = n + d;	    // ��ȣ�� ����̸� �Ҽ��θ� �����ο� �ܼ��� ������
		else 
			d = n - d;     // ��ȣ�� �����̸� �Ҽ��θ� �����ο��� ���־�� ��

		if (exp != 0)      //  �����ΰ� ������ �����θ�ŭ ������
			d = d * power10(exp);
		
		removeLastNewLineChar();  // ����, �Ǽ�, ���ڿ�, �ĺ��� ���� �б� ���� �Էµ� '\r', '\n'�� ���Ž�Ŵ
		
		return d;
	}

	// getFoloat() : 23.45, -12.3, -0.012_3, +.012_34, -.1234e3_3 1.234e+12, 1.234E-1_2 ��
	//    Java�� �Ǽ� ���� �Է��Ͽ� float ������ ��ȯ�ϴ� �޼ҵ�
	//    o �Ǽ� ����: "[+/-������].[�Ҽ���]E/e[+/-������]" 
	public static float getFloat() {
		return (float) getDouble();
	}

	// �Ҽ��� ������ �Ǽ��θ� �о����
	private static double getDoubleUnderPoint() {
		String longString = getLongStringWithoutSkip();  // �Ҽ��� ������ ������ �о� ���ڿ��� ��ȯ
		
		long  nUnerPoint  = Long.parseLong(longString);  // �Ҽ��� ������ long ���� ����
		int noDigit = longString.length();   // �Ҽ��� ������ ������ �ڸ��� ����		 

		return  (double) nUnerPoint / power10(noDigit);  // '.' ���� �����θ� 10^�ڸ����� ������ �Ҽ��η� ��ȯ
	}

	// �־��� ������ �ڸ����� �����Ͽ� ��ȯ
	public static int getNoDigit(int n) {
		int no = 0;

		while (n != 0) {
			no++;
			n = n / 10;
		}

		return no;
	}

	// 10�� n ������ ��ȯ
	public static double power10(long n) {
		double pow = 1;

		if (n > 0) {
			while(n-- > 0) 
				pow = pow * 10;
		}
		else if (n < 0){
			while(n++ < 0) 
				pow = pow / 10;
		}

		//	System.out.println("\n  <for debug >n = " + n + ", exp = " + pow);
		
		return pow;
	}

    // nextInt(): Java�� Scanner Ŭ������ �ִ� �޼ҵ�� ����
	public static int nextInt() {
		return getInt();
	}

	// getBoolean() : "true" �Ǵ� "false"�� �Է��Ͽ� true �Ǵ� false�� boolean ������ ��ȯ�ϴ� �޼ҵ�
	//  -  "true" �Ǵ� "false"�� �Է��ϱ� ���� �ĺ��ڰ� �ݵ�� ���� ��Ÿ���� ��
	//  -  �Էµ� �ĺ��ڰ� "true" �Ǵ� "false"�̸� boolean Ÿ���� true �Ǵ� false �� ��ȯ,
	//     �׷��� ���� ��� ���� �ĺ��ڸ� �Է��Ͽ� Ȯ���� �ݺ���
	public static boolean getBoolean()  {
		while (true) {
			String id = getIdentifier();
			
//			System.out.println("  o identifier = '" + id + "'"); 
			
			if (id.equals("true"))
				return true;
			else if (id.equals("false"))
				return false;			
		}
	}

	// getIdentifier() : Java�� �ĺ��ڸ� �Է��Ͽ� ��ȯ�ϴ� �޼ҵ�
	//  - �ĺ����� ù ����: ������, �ѱ�, '_', '$'
	//  - �ĺ����� �����κ�: ������, �ѱ�, ����, '_', '$'
	//  - (�׸��� ����, �߱��� ����, �Ϻ��� ���� � �ĺ��ڿ� ���Եǳ� ���⿡���� ó������ ����)
	public static String getIdentifier()  {
		int head = skipUntilFirstCharOfIdentifier(); // �ĺ����� ù ���ڸ� ã�� �Է��Ͽ� ��ȯ
//		System.out.println("  o head of identifier = '" + (char) head + "' (" + head + ")"); 
		
		String identifier = getTailOfId(head); // �ĺ����� �����κ��� �Է��Ͽ� ù ���� head�� �����Ͽ� ��ȯ
		
		removeLastNewLineChar();  // ����, �Ǽ�, ���ڿ�, �ĺ��� ���� �б� ���� �Էµ� '\r', '\n'�� ���Ž�Ŵ

		return identifier;		
	}
	
	// skipUntilFirstCharOfIdentifier() : �ĺ����� ù ������ ������, �ѱ�, '_', '$'�� ���� ������ 
	//   ���ڵ��� skip�Ͽ� �ĺ����� ù ���ڸ� ��ȯ�ϴ� �޼ҵ�
	public static int skipUntilFirstCharOfIdentifier() {
		int c;

		while(true)  {
			c = getcharPrivate();
			
			if (isAlphabet(c))      // ���� ���ڰ� ���ĺ�Ʈ�̸� �̴� �ĺ��� ù ���ڿ� �ش�, �̸� ��ȯ
				return c;
			else if (c == '_' || c=='$')    // ���� ���ڰ� '_' �Ǵ� "$"�̸� �̴� �ĺ��� ù ���ڿ� �ش�, �̸� ��ȯ
				return c;    	
		}
	}

	// isAlphabet() : �־��� ���ڰ� Java�� �ĺ����� ���ĺ�Ʈ�� �ش�Ǵ����� �˻��ϴ�  �޼ҵ�
	//  - ���ĺ�Ʈ: ������, �ѱ�, '_', '$'
	//  - (�׸��� ����, �߱��� ����, �Ϻ��� ���� � ���ĺ�Ʈ�� ���Եǳ� ���⿡���� ó������ ����)
   public static boolean isAlphabet(int c) {
		if (c>='A' && c <= 'Z' || c>='a' && c <= 'z' )  // ���� ���ڰ� �������̸� �̸� ��ȯ
			return true;
		// �����ڵ忡�� �ѱ� ������ ������ '��'(12593)���� '��'(12643)���� ������
		// �����ڵ忡�� �ѱ� ���ڴ� '��'('\uAC00': 44032)���� '�R'('\uD7A3': 55203)���� ������
		else if (c >= 12593 && c <= 12643)   // ���� ���ڰ� �ѱ� ���� �Ǵ� �����̸� �̴� �ĺ��� ù ���ڿ� �ش��
			return true;
		else if (c >= 44032 && c <= 55203)   // ���� ���ڰ� �ѱ� �����̸� �̴� �ĺ��� ù ���ڿ� �ش��
			return true;

		return false;
    	
    }
    
	// getTailOfId(int head): Java �ĺ����� �����κ��� �Է��Ͽ� �־��� ��幮�� head�� �Բ� ��ü �ĺ��ڸ� �����Ͽ� ���ڿ��� ��ȯ
	//  - �ĺ����� ù ����: ������, �ѱ�, '_', '$'
	//  - �ĺ����� �����κ�: ������, �ѱ�, ����, '_', '$'
	public  static String getTailOfId(int head) {
		int i=0;

		int c;

		char cs[] = new char[MAX_LENGTH_OF_STRING];
 
		cs[0] = (char) head;
		i++;
		
		while(true)  {
			c = getcharPrivate();
//			System.out.println("  o tail of identifier = '" + (char) c + "' (" + c + ")"); 
			
			if (isAlphabet(c))              // ���� ���ڰ� ���ĺ�Ʈ�̸� �̴� �ĺ��� ������ �ش��
				cs[i++] = (char) c; 
			else if (c == '_' || c=='$')    // ���� ���ڰ� '_' �Ǵ� "$"�̸� �̴� �ĺ��� ������ �ش��
				cs[i++] = (char) c;   	
			else if (c >= '0' && c<='9')    // ���� ���ڰ� ����Ʈ�̸� �̴� �ĺ��� ������ �ش��
				cs[i++] = (char) c;    	
			else
				break;
		}
		
		ungetc(c); // ������ ���� ���ڴ� �ĺ��� ���� �ƴϹǷ� ������ ���� �� �ֵ��� ungetc��Ŵ

		cs[i] = 0;

		String s =  new String(cs, 0, i);  // ���� �迭�� ���ڿ��� ��ȯ 

		return s;	

	}  
	

	// getString() : '\n', '\r', -1�� �Էµ� ������ �Էµ� ���ڵ��� String ������ ��ȯ
	//  - ����Ű�� '\n', '\r' �ΰ��� ���ڷ� �̷������ ��츦 ���� 
	//  - ó���� ��Ÿ���� '\n', '\r' ���ڴ� �����Ѵ�.  
	public  static char[] getCharArray()  {
		int i=0;

		int c;

		char cs[] = new char[MAX_LENGTH_OF_STRING];

		while((c = getcharPrivate()) != '\n' &&  c != '\r' && c != -1) 
			cs[i++] = (char) c;
		
		ungetc(c);
		
		cs[i] = 0;
		
		char[] csReturn = new char[i];
		
		System.arraycopy(cs,  0, csReturn,  0,  i);
		
		removeLastNewLineChar();  // ����, �Ǽ�, ���ڿ�, �ĺ��� ���� �б� ���� �Էµ� '\r', '\n'�� ���Ž�Ŵ

		return csReturn;	 
	}
	
	// getString() : '\n', '\r', -1�� �Էµ� ������ �Էµ� ���ڵ��� String ������ ��ȯ
	//  - ����Ű�� '\n', '\r' �ΰ��� ���ڷ� �̷������ ��츦 ���� 
	//  - ó���� ��Ÿ���� '\n', '\r' ���ڴ� �����Ѵ�.  
	public  static String getString()  {
		char[] cs = getCharArray();

		String s =  new String(cs, 0, cs.length);
		
		return s;	 
	}

	
	// ����Ʈ ���ڰ� �Էµ� �������� ���ڵ��� �о� String ���ڿ��� ��ȯ
	public  static String getStringUntilNotDigit()  {
		int i=0;

		int c;

		char cs[] = new char[MAX_LENGTH_OF_STRING];


		while((c =  getcharPrivate()) >= '0' &&  c <= '9' ) 
			cs[i++] = (char) c;
		
		ungetc(c);  // ������ ���� ���ڸ� �ٽ� ���� �� �ֵ��� ungetc()

		cs[i] = 0;

		String s =  new String(cs, 0, i);  // ���ڹ迭�� String ��ü�� ��ȯ

		return s;	 
	}

	// nextString(): Java�� Scanner Ŭ������ �ִ� �޼ҵ�� ����
	public static String nextString() {
		return getString();
	} 


	public static String toKSC5601(String str) // throws java.io.UnsupportedEncodingException 
	{ 

		if (str == null) return null; 

		try {
			return new String(str.getBytes("8859_1"), "KSC5601"); 
		}
		catch(Exception e) {}

		return null;

	} 
	

	/*
	 * ������Ʈ �޽����� ����� �� ������ �Է��ϴ� �޼ҵ��
	 * 
	 *  - �Է��� �� ������Ʈ �޽��� ���� �Է��� �ϸ� ����ڴ� ������ ��ٸ��� ��찡 �߻�
	 *  - �̷��� ��츦 �����ϱ� ���� �Է� �� �Է� ����  �޽����� �̸� ����Ͽ� 
	 *  - �Է��� �������� �뵵�� ����� �˷��ִ� �޽����� ������Ʈ �޽����� ��
	 * 
	 *  - Ư�� Ű���忡���� �Է� �� ������Ʈ �޽����� �߿��ϸ�
	 *    ������ �޼ҵ��� ������Ʈ �޽��� ��°� �̓��� ���ÿ� �����ϹǷ� ���� 
	 */
	
	// ������Ʈ �޽����� ���� �Է�
	public static char getCharWithPrompt(String msg) {
		System.out.print(msg);
        return getChar();
	}

	// ������Ʈ �޽����� ���� �Է�
	public static char getChar(String prompt) {	
		return getCharWithPrompt(prompt);
	}
	
	// ������Ʈ �޽����� int ���� �Է�
	public static int getIntWithPrompt(String msg) {
		System.out.print(msg);
		return getInt();
	}
	
	// ������Ʈ �޽����� int ���� �Է�
	public static int getInt(String prompt) {
		return getIntWithPrompt(prompt);
	}

	// ������Ʈ �޽����� long ���� �Է�
	public static long getLongWithPrompt(String msg) {
		System.out.print(msg);
		return getLong();
	}
	
	// ������Ʈ �޽����� long ���� �Է�
	public static long getLong(String prompt) {
		return getLongWithPrompt(prompt);
	}

	// ������Ʈ �޽����� double �Ǽ� �Է�
	public static double getDoubleWithPrompt(String msg) {
		System.out.print(msg);
		return getDouble();
	}

	// ������Ʈ �޽����� double �Ǽ� �Է�
	public static double getDouble(String prompt) {		
		return getDoubleWithPrompt(prompt);
	}
	
	// ������Ʈ �޽����� float �Ǽ� �Է�
	public static float getFloatWithPrompt(String msg) {
		System.out.print(msg);
		return getFloat();
	}
	
	// ������Ʈ �޽����� float �Ǽ� �Է�
	public static float getFloat(String prompt) {		
		return getFloatWithPrompt(prompt);
	}
	
	// ������Ʈ �޽����� boolean �� �Է�
	public static boolean getBooleanWithPrompt(String msg) {
		System.out.print(msg);
		return getBoolean();
	}
	
	// ������Ʈ �޽����� boolean �� �Է�
	public static boolean getBoolean(String prompt) {		
		return getBooleanWithPrompt(prompt);
	}
	
	// ������Ʈ �޽����� ���� �迭 �Է�
	public static char[] getCharArrayWithPrompt(String msg) {
		System.out.print(msg);
		return getCharArray();
	}
	
	// ������Ʈ �޽����� ���� �迭 �Է�
	public static char[] getCharArray(String msg) {
		return getCharArrayWithPrompt(msg);
	}

	// ������Ʈ �޽����� ���ڿ� �Է�
	public static String getStringWithPrompt(String msg) {
		System.out.print(msg);
		return getString();
	}
	
	// ������Ʈ �޽����� ���ڿ� �Է�
	public static String getString(String prompt) {
		return getStringWithPrompt(prompt);
	}
	
	// ������Ʈ �޽����� �ĺ��� �Է�
	public static String getIdentifierWithPrompt(String msg) {
		System.out.print(msg);
		return getIdentifier();
	}
	
	// ������Ʈ �޽����� �ĺ��� �Է�
	public static String getIdentifier(String prompt) {
		return getIdentifierWithPrompt(prompt);
	}

	public static void main(String[] args) {	
		while(true) {

			/*
			 *  �� �κ��� �׽�Ʈ�� ���� �κ���
			 *  
			 *  �Է� �޼ҵ带 �ѹ��� ȣ����
			 */
			
//
//			System.out.print("\n  o input int (�ִ밪: " + (0x7FFF_FFFF) +") > ");
//			int no = SkScanner.getInt(); 
//			System.out.println("  o int = " + no);
//
//			System.out.print("\n  o input long(�ִ밪: " + (0x7FFF_FFFF_FFFF_FFFFL) +") > ");
//			long l = SkScanner.getLong(); 
//			System.out.println("  o long = " + l);
//
			System.out.print("\n  o input charArray > ");
			char[] cs = SkScanner.getCharArray();
			System.out.print("\n  * charArray = '");
			for (char c : cs)
				System.out.print(c);
			System.out.println("'");
			
			System.out.print("\n  o input String > ");
			String s = SkScanner.getString();
			System.out.println("  o String = '" + s + "'");  
	
			System.out.print("\n  o input identifier > ");
			String id = SkScanner.getIdentifier();
			System.out.println("  o identifier = " + id);  

			System.out.print("\n  o input boolean (true or false) > ");
			boolean b = SkScanner.getBoolean();
			System.out.println("  o boolean = " + b);  
			
			System.out.print("\n  o input double > ");
			double  d = SkScanner.getDouble();
			System.out.println("  o double = " + d);
			
			System.out.print("\n  o input char > ");
			char c = SkScanner.getChar(); 
			System.out.println("  o char = "+ c +" (" + (int) c + ")");



			/*
			 * ������Ʈ �޽����� ����ϴ� �Է� �޼ҵ� ȣ��
			 */
			c = SkScanner.getCharWithPrompt("\n  o input char > "); 
			System.out.println("  o char = "+ c +" (" + (int) c + ")");

			int no = SkScanner.getIntWithPrompt("\n  o input int > "); 
			System.out.println("  o int = " + no);

			d = SkScanner.getDoubleWithPrompt("\n  o input double > ");
			System.out.println("  o double = " + d);

			s = SkScanner.getString("\n  o input String > ");
			System.out.println("  o String = '" + s + "'");  
	
			id = SkScanner.getIdentifier("\n  o input identifier > ");
			System.out.println("  o identifier = " + id);  

			b = SkScanner.getBoolean("\n  o input boolean (true or false) > ");
			System.out.println("  o boolean = " + b);  
			
			char c1=0;
			System.out.println("  * Hit any char and hit Enter key!!" );  
			while(c1 != '\n') {
				c1=getchar();

				System.out.print((int)c1 + " ");
			}
			
			System.out.print("End");
		}
	}
}

