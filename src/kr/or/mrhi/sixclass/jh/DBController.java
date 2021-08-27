package kr.or.mrhi.sixclass.jh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class DBController {
	//�л� �Է�
	public static int studentScoreInsertTBL(StudentScore studentScore) {
		Connection con = DBUtility.getConnection();
		String insertQuery = "CALL insertScore(?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		int count = 0;

		try {
			preparedStatement = con.prepareStatement(insertQuery);
			preparedStatement.setString(1, studentScore.getStudentNumber());
			preparedStatement.setString(2, studentScore.getStudentName());
			preparedStatement.setInt(3, studentScore.getJavaScore());
			preparedStatement.setInt(4, studentScore.getKotlinScore());
			preparedStatement.setInt(5, studentScore.getSqlScore());
		
			

			// ���̺��� ���� �ٶ� excuteUpdate
			count = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		return count;
	}
	//�л� �˻�
	public static List<StudentScore> studentScoreSearchTBL(String searchData, int searchNumber) {

		List<StudentScore> list = new ArrayList<StudentScore>();

		// �����ͺ��̽��� ����
		Connection con = DBUtility.getConnection();
		String searchQuery = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			switch (searchNumber) {
			case 1:
				searchQuery = "select * from studentScoretbl where studentNumber like ?";
				break;
			case 2:
				searchQuery = "select * from studentScoretbl where studentName like ?";
				break;
			case 3:
				searchQuery = "select * from studentScoretbl where grade like ?";
				break;
			default:
				System.out.println("�ٽ� �Է��ϼ���.");
				return list;
			}

			preparedStatement = con.prepareStatement(searchQuery);
			searchData = "%" + searchData + "%";
			preparedStatement.setString(1, searchData);

			resultSet = preparedStatement.executeQuery();

			if (!resultSet.isBeforeFirst()) {
				return list;
			}

			while (resultSet.next()) {
				String studentNumber = resultSet.getString(1);
				String studentName = resultSet.getString(2);
				int javaScore = resultSet.getInt(3);
				int kotlinScore = resultSet.getInt(4);
				int sqlScore = resultSet.getInt(5);
				int total = resultSet.getInt(6);
				double avg = resultSet.getDouble(7);
				String grade = resultSet.getString(8);

				StudentScore studentScore = new StudentScore(studentNumber, studentName, javaScore, kotlinScore, sqlScore, total, avg, grade);
				list.add(studentScore);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}

		return list;
}
	//�л� ����
	public static int studentScoreDeleteTBL(String studentNumber) {
		// �����ͺ��̽��� ����
				Connection con = DBUtility.getConnection();
				String deletetQuery = "delete from studentScoreTBL where studentNumber like ?";
				PreparedStatement preparedStatement = null;
				int count = 0;
				
				try {
					preparedStatement = con.prepareStatement(deletetQuery);
					String strNumber = "%" + studentNumber + "%";
					preparedStatement.setString(1, strNumber);
					count = preparedStatement.executeUpdate();

					

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (preparedStatement != null && !preparedStatement.isClosed()) {
							preparedStatement.close();
						}
						if (con != null && !con.isClosed()) {
							con.close();
						}
					} catch (SQLException e) {
					}
				}
		return count;
	}
	//�л� ����
	public static boolean studentScoreUpdateTBL(String studentNumber, String javaScore, String kotlinScore, String sqlScore) {
		// �����ͺ��̽��� ����
				Connection con = DBUtility.getConnection();
				String updateQuery = "SELECT updateTBL(?, ?, ?, ?)";
				PreparedStatement preparedStatement = null;
				ResultSet resultSet = null;
				
				try {
					preparedStatement = con.prepareStatement(updateQuery);
					preparedStatement.setString(1, studentNumber);
					preparedStatement.setString(2, javaScore);
					preparedStatement.setString(3, kotlinScore);
					preparedStatement.setString(4, sqlScore);
					resultSet = preparedStatement.executeQuery();
					
					if(resultSet.next()) {
						return true;
					} else {
						return false;
					}

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (preparedStatement != null && !preparedStatement.isClosed()) {
							preparedStatement.close();
						}
						if (con != null && !con.isClosed()) {
							con.close();
						}
					} catch (SQLException e) {
					}
				}
		return false;
	}
	//�л� ���
	public static List<StudentScore> studentScoreSelectTBL() {
		
		List<StudentScore> list = new ArrayList<StudentScore>();
		
		//DB����
		Connection con = DBUtility.getConnection();
		String selectQuery = "select * from studentscoretbl";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = con.prepareStatement(selectQuery);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String studentNumber = resultSet.getString(1);
				String studentName = resultSet.getString(2);
				int javaScore = resultSet.getInt(3);
				int kotlinScore = resultSet.getInt(4);
				int sqlScore = resultSet.getInt(5);
				int total = resultSet.getInt(6);
				double avg = resultSet.getDouble(7);
				String grade = resultSet.getString(8);
				
				StudentScore studentScore = new StudentScore(studentNumber, studentName, javaScore, kotlinScore, sqlScore, total, avg, grade);
				list.add(studentScore);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		return list;
	}
}
