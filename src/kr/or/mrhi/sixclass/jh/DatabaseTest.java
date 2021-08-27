package kr.or.mrhi.sixclass.jh;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class DatabaseTest {

	public static final Scanner scan = new Scanner(System.in);
	public static final int INSERT = 1, SEARCH = 2, DELETE = 3, UPDATE = 4, SHOWTB = 5, FINISH = 6;
	
	public static void main(String[] args) throws Exception {
		
	boolean flag = false;
	int selectNumber = 0;
	
	while (!flag) {
		// �޴�����
		selectNumber = displayMenu();

		switch (selectNumber) {
		case INSERT:
			studentScoreInsert(); // insert information
			break;
		case SEARCH:
			studentScoreSearch(); // search information
			break;
		case DELETE:
			studentScoreDelete(); // delete information
			break;
		case UPDATE:
			studentScoreUpdate(); // update information
			break;
		case SHOWTB:
			studentScoreSelect(); // update information
			break;
		case FINISH:
			flag = true;
			break;
		default:
			System.out.println("���ڹ����ʰ�");
			break;
		}

	} // end of while
	System.out.println("���α׷� ����");
		
		
		
	} //end of main
	
	private static void studentScoreInsert() {
		String studentNumber = null;
		String studentName = null;
		int javaScore = 0;
		int kotlinScore = 0;
		int sqlScore = 0;
		int total = 0;
		double avg = 0.0;
		char grade = 0;
		
		while (true) {
			System.out.print("������ �Է��ϼ���(********) >> ");
			studentNumber = scan.nextLine();
			if (studentNumber.length() != 8) {
				System.out.println("���Ŀ� �°� �Է��ϼ���");
				continue;
			} 
			
			boolean checkstudentNumber = duplicateStudentNumberCheck(studentNumber);
			if(checkstudentNumber == true) {
				System.out.println("�ߺ��Ǵ� �л� ��ȣ�Դϴ�. �ٽ� �Է����ּ���.");
				continue;
			}
			break;
		} // end of while

		while (true) {
			System.out.println("�̸��� �Է��ϼ���(������� �Է�)");
			studentName = scan.nextLine();
			if (studentName.length() < 2 || studentName.length() > 7) {
				System.out.println("���Ŀ� �°� �̸��� �Է��ϼ���");
				continue;
			} else {
				break;
			}
		}

		while (true) {
			System.out.print("�ڹ� ������ �Է��ϼ���. >> ");
			javaScore = scan.nextInt();
			if (javaScore > 100 || javaScore <= 0 ) {
				System.out.println("���� ������ �°� �Է��ϼ���");
				continue;
			} else {
				break;
			}
		}

		while (true) {
			System.out.print("��Ʋ�� ������ �Է��ϼ���. >> ");
			kotlinScore = scan.nextInt();
			if (kotlinScore > 100 || kotlinScore <= 0 ) {
				System.out.println("���� ������ �°� �Է��ϼ���");
				continue;
			} else {
				break;
			}
		}
		
		while (true) {
			System.out.print("sql ������ �Է��ϼ���. >> ");
			sqlScore = scan.nextInt();
			if (sqlScore > 100 || sqlScore <= 0 ) {
				System.out.println("���� ������ �°� �Է��ϼ���");
				continue;
			} else {
				break;
			}
		}

		StudentScore studentScore = new StudentScore(studentNumber, studentName, javaScore, kotlinScore, sqlScore);
		
		int count = DBController.studentScoreInsertTBL(studentScore);
		
		studentScore.calTotal();
		studentScore.calAvg();
		studentScore.calGrade();
		
		if (count == 1) {
			System.out.println("�Է¼���");
		} else {
			System.out.println("�Է½���");
		}
		
		// DB�� ����
	}

	private static boolean duplicateStudentNumberCheck(String studentNumber) {
		String checkQuery = "select * from StudentScoretbl";
		List<StudentScore> list = DBController.studentScoreSelectTBL();

		for (StudentScore checkScore : list) {
			if (studentNumber.equals(checkScore.getStudentNumber())) {
				return false;
			}
		}
		return true;
	}

	private static void studentScoreSearch() {
		final int STUDENTNAME = 1, STUDENTNUMBER = 2, GRADE = 3, EXIT =4;
		
		List<StudentScore> list = new ArrayList<StudentScore>();
		boolean flag = false;
		String searchData = null;
		int searchNumber = 0;
		

		while (!flag) {
			int selectNumber = displaySearchMenu();

			switch (selectNumber) {
			case STUDENTNAME:
				System.out.println("��ȣ �Է� >> ");
				searchData = scan.nextLine();
				searchNumber = STUDENTNAME;
				break;
			case STUDENTNUMBER:
				System.out.println("�̸� �Է� >> ");
				searchData = scan.nextLine();
				searchNumber = STUDENTNUMBER;
				break;
			case GRADE :
				System.out.println("��� �Է� >> ");
				searchData = scan.nextLine();
				searchNumber = GRADE;
				break;
			case EXIT :
				//�Լ� ����
				return;
			default:
				System.out.println("�ٽ� �Է��ϼ���.");
				continue;
			}
			flag = true;
		}
		list = DBController.studentScoreSearchTBL(searchData, searchNumber);
		
		if(list.size() <= 0) {
			System.out.println("�˻� �����ų� ã�� �����Ͱ� �����ϴ�.");
			return;
		}
		
		for(StudentScore ss : list) {
			System.out.println(ss);
		}
		
	}
	
	private static void studentScoreDelete() {
		System.out.println("������ �й� �Է� >> ");
		String studentNumber = scan.nextLine();

		int count = DBController.studentScoreDeleteTBL(studentNumber);
		
		if (count == 1) {
			System.out.println(studentNumber + "�� ���� ����.");
		} else {
			System.out.println(studentNumber + "�� ���� ����.");
		}
		
	}
	
	private static void studentScoreSelect() {
		
		List<StudentScore> list = new ArrayList<StudentScore>();
		
		list = DBController.studentScoreSelectTBL();
		
		if(list.size() <= 0) {
			System.out.println("�˻� �����ų� ã�� �����Ͱ� �����ϴ�.");
			return;
		}
		
		for(StudentScore ss : list) {
			System.out.println(ss);
		}
		
	}
	
	private static void studentScoreUpdate() {
		System.out.println("������ ����� �й��� �Է��ϼ���");
		String studentNumber = scan.nextLine();
		
		System.out.println("������ �ڹ� ���� �Է�");
		String javaScore = scan.nextLine();
		
		System.out.println("������ ��Ʋ�� ���� �Է�");
		String kotlinScore = scan.nextLine();
		
		System.out.println("������ sql ���� �Է�");
		String sqlScore = scan.nextLine();
		boolean count = DBController.studentScoreUpdateTBL(studentNumber,javaScore,kotlinScore,sqlScore);
		if(count == true) {
			System.out.println(studentNumber + "��������");
		}else {
			System.out.println(studentNumber + "��������");
		}
		
	}
	
	private static int displayMenu() {
		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("==========================================");
			System.out.println("=> 1.�Է� 2.�˻� 3. ���� 4. ���� 5. ��� 6. ����");
			System.out.println("==========================================");
			System.out.print("��ȣ���� >>  ");

			// ��ȣ�Է�
			try {
				selectNumber = Integer.parseInt(scan.nextLine());

			} catch (InputMismatchException e) {
				System.out.println("�Է� ����: ���ڸ� �Է� ���");
				continue;
			} catch (Exception e) {
				System.out.println("�Է� ����: �Է� ���� ����");
				continue;
			}
			break;
		}
		return selectNumber;
	}
	
	private static int displaySearchMenu() {
		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("====================================================");
			System.out.println("�˻� ���� >>> 1. ��ȣ �˻� 2. �̸� �˻� 3. ��� �˻� 4.������");
			System.out.println("====================================================");
			System.out.print("��ȣ���� >>  ");
			try {
				selectNumber = Integer.parseInt(scan.nextLine());

			} catch (InputMismatchException e) {
				System.out.println("�Է� ����: ���ڸ� �Է� ���");
				continue;
			} catch (Exception e) {
				System.out.println("�Է� ����: �Է� ���� ����");
				continue;
			}
			break;
		}
		return selectNumber;
	}
	
}

