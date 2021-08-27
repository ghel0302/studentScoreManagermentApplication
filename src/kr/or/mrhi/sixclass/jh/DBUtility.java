package kr.or.mrhi.sixclass.jh;

import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;



public class DBUtility {
	public static Connection getConnection() {
	      Connection con = null;

	      // Collection Framework�� Properties ����ؼ� ����̹�, URL, ID, Password ��������

	      try {
	         Properties properties = new Properties();
	         // URL ���ڵ��ϱ�
	         String filePath = DatabaseTest.class.getResource("db.properties").getPath();
	         filePath = URLDecoder.decode(filePath, "utf-8");
	         properties.load(new FileReader(filePath));
	         // ���ε� properties. getProperty(�������� ���� ���� key�Է�)
	         String driver = properties.getProperty("DRIVER");
	         String url = properties.getProperty("URL");
	         String userID = properties.getProperty("userID");
	         String userPassword = properties.getProperty("userPassword");
	         // jdbc driver�� load
	         Class.forName(driver);
	         // MySql Database connection
	         con = DriverManager.getConnection(url, userID, userPassword); // con = mysql�� ����� Ű
	      } catch (Exception e) {
	         System.out.println("MySQL Database connection failed");
	         e.printStackTrace();
	      }

	      return con;
	   }
}