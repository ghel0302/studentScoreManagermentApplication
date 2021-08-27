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
		// 메뉴선택
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
			System.out.println("숫자범위초과");
			break;
		}

	} // end of while
	System.out.println("프로그램 종료");
		
		
		
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
			System.out.print("내용을 입력하세요(********) >> ");
			studentNumber = scan.nextLine();
			if (studentNumber.length() != 8) {
				System.out.println("형식에 맞게 입력하세요");
				continue;
			} 
			
			boolean checkstudentNumber = duplicateStudentNumberCheck(studentNumber);
			if(checkstudentNumber == true) {
				System.out.println("중복되는 학생 번호입니다. 다시 입력해주세요.");
				continue;
			}
			break;
		} // end of while

		while (true) {
			System.out.println("이름을 입력하세요(공백없이 입력)");
			studentName = scan.nextLine();
			if (studentName.length() < 2 || studentName.length() > 7) {
				System.out.println("형식에 맞게 이름을 입력하세요");
				continue;
			} else {
				break;
			}
		}

		while (true) {
			System.out.print("자바 점수를 입력하세요. >> ");
			javaScore = scan.nextInt();
			if (javaScore > 100 || javaScore <= 0 ) {
				System.out.println("점수 범위에 맞게 입력하세요");
				continue;
			} else {
				break;
			}
		}

		while (true) {
			System.out.print("코틀린 점수를 입력하세요. >> ");
			kotlinScore = scan.nextInt();
			if (kotlinScore > 100 || kotlinScore <= 0 ) {
				System.out.println("점수 범위에 맞게 입력하세요");
				continue;
			} else {
				break;
			}
		}
		
		while (true) {
			System.out.print("sql 점수를 입력하세요. >> ");
			sqlScore = scan.nextInt();
			if (sqlScore > 100 || sqlScore <= 0 ) {
				System.out.println("점수 범위에 맞게 입력하세요");
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
			System.out.println("입력성공");
		} else {
			System.out.println("입력실패");
		}
		
		// DB에 연결
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
				System.out.println("번호 입력 >> ");
				searchData = scan.nextLine();
				searchNumber = STUDENTNAME;
				break;
			case STUDENTNUMBER:
				System.out.println("이름 입력 >> ");
				searchData = scan.nextLine();
				searchNumber = STUDENTNUMBER;
				break;
			case GRADE :
				System.out.println("등급 입력 >> ");
				searchData = scan.nextLine();
				searchNumber = GRADE;
				break;
			case EXIT :
				//함수 종료
				return;
			default:
				System.out.println("다시 입력하세요.");
				continue;
			}
			flag = true;
		}
		list = DBController.studentScoreSearchTBL(searchData, searchNumber);
		
		if(list.size() <= 0) {
			System.out.println("검색 오류거나 찾을 데이터가 없습니다.");
			return;
		}
		
		for(StudentScore ss : list) {
			System.out.println(ss);
		}
		
	}
	
	private static void studentScoreDelete() {
		System.out.println("삭제할 학번 입력 >> ");
		String studentNumber = scan.nextLine();

		int count = DBController.studentScoreDeleteTBL(studentNumber);
		
		if (count == 1) {
			System.out.println(studentNumber + "번 삭제 성공.");
		} else {
			System.out.println(studentNumber + "번 삭제 실패.");
		}
		
	}
	
	private static void studentScoreSelect() {
		
		List<StudentScore> list = new ArrayList<StudentScore>();
		
		list = DBController.studentScoreSelectTBL();
		
		if(list.size() <= 0) {
			System.out.println("검색 오류거나 찾을 데이터가 없습니다.");
			return;
		}
		
		for(StudentScore ss : list) {
			System.out.println(ss);
		}
		
	}
	
	private static void studentScoreUpdate() {
		System.out.println("수정할 사람의 학번을 입력하세요");
		String studentNumber = scan.nextLine();
		
		System.out.println("수정할 자바 점수 입력");
		String javaScore = scan.nextLine();
		
		System.out.println("수정할 코틀린 점수 입력");
		String kotlinScore = scan.nextLine();
		
		System.out.println("수정할 sql 점수 입력");
		String sqlScore = scan.nextLine();
		boolean count = DBController.studentScoreUpdateTBL(studentNumber,javaScore,kotlinScore,sqlScore);
		if(count == true) {
			System.out.println(studentNumber + "수정성공");
		}else {
			System.out.println(studentNumber + "수정실패");
		}
		
	}
	
	private static int displayMenu() {
		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("==========================================");
			System.out.println("=> 1.입력 2.검색 3. 삭제 4. 수정 5. 출력 6. 종료");
			System.out.println("==========================================");
			System.out.print("번호선택 >>  ");

			// 번호입력
			try {
				selectNumber = Integer.parseInt(scan.nextLine());

			} catch (InputMismatchException e) {
				System.out.println("입력 오류: 숫자만 입력 요망");
				continue;
			} catch (Exception e) {
				System.out.println("입력 오류: 입력 과정 오류");
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
			System.out.println("검색 선택 >>> 1. 번호 검색 2. 이름 검색 3. 등급 검색 4.나가기");
			System.out.println("====================================================");
			System.out.print("번호선택 >>  ");
			try {
				selectNumber = Integer.parseInt(scan.nextLine());

			} catch (InputMismatchException e) {
				System.out.println("입력 오류: 숫자만 입력 요망");
				continue;
			} catch (Exception e) {
				System.out.println("입력 오류: 입력 과정 오류");
				continue;
			}
			break;
		}
		return selectNumber;
	}
	
}

