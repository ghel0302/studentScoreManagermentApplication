package kr.or.mrhi.sixclass.jh;

import java.util.Objects;

public class StudentScore {
	
	private static int SUBJECT_NUM = 3;
	
	private String studentNumber;
	private String studentName;
	private int javaScore ;
	private int kotlinScore;
	private int sqlScore;
	private int total;
	private double avg;
	private String grade;
	
	
	
	public StudentScore(String studentNumber, String studentName, int javaScore, int kotlinScore, int sqlScore,
			int total, double avg, String grade) {
		super();
		this.studentNumber = studentNumber;
		this.studentName = studentName;
		this.javaScore = javaScore;
		this.kotlinScore = kotlinScore;
		this.sqlScore = sqlScore;
		this.total = total;
		this.avg = avg;
		this.grade = grade;
	}

	public StudentScore(String studentNumber, String studentName, int javaScore, int kotlinScore, int sqlScore) {
		
		this.studentNumber = studentNumber;
		this.studentName = studentName;
		this.javaScore = javaScore;
		this.kotlinScore = kotlinScore;
		this.sqlScore = sqlScore;
		this.total = 0;
		this.avg = 0.0;
		this.grade = null;
	}

	public void calTotal ( ) {
		this.total = this.javaScore + this.kotlinScore + this.sqlScore;
	}
	
	public void calAvg () {
		this.avg = this.total / (double)SUBJECT_NUM;
	}
	
	public void calGrade() {
		switch ((int)(this.avg/10)) {
		case 10 :
		case 9 : this.grade = "A"; break;
		case 8 : this.grade = "B"; break;
		case 7 : this.grade = "C"; break;
		case 6 : this.grade = "D"; break;
		default : this.grade = "F"; break;
		}
	}
	
	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setName(String studentName) {
		this.studentName = studentName;
	}

	public int getJavaScore() {
		return javaScore;
	}

	public void setJavaScore(int javaScore) {
		this.javaScore = javaScore;
	}

	public int getKotlinScore() {
		return kotlinScore;
	}

	public void setKotlinScore(int kotlinScore) {
		this.kotlinScore = kotlinScore;
	}

	public int getSqlScore() {
		return sqlScore;
	}

	public void setSqlScore(int sqlScore) {
		this.sqlScore = sqlScore;
	}


	@Override
	public boolean equals(Object obj) {
		if(obj instanceof StudentScore) {
			StudentScore studentScore = (StudentScore)obj;
			return this.getStudentNumber().equals(studentScore.getSqlScore());
		}
		return false;
	}
		
	@Override
	public int hashCode() {
		return Objects.hash(studentNumber);
	}

	@Override
	public String toString() {
		return studentNumber + "\t" + studentName + "\t" + javaScore
				+ "\t" + kotlinScore + "\t" + sqlScore + "\t" + total + "\t" + String.format("%.2f", avg) + "\t" + grade;
	}
	
	
	
	
}
