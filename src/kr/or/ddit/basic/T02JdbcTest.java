package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class T02JdbcTest {

//	  문제1) 사용자로부터 lprod_id값을 입력받아 입력한 값보다 lprod_id가 큰 자료를 줄력하시오
//    문제2) lprod_id값을 2개 입력받아 두 값중 작은 값부터 큰 값사이의 자료를 출력하시오

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "sjr", pwd = "java";
		Scanner sc = new Scanner(System.in);

		// 1번문제
//		String input = sc.next();
//		String sql = "SELECT * FROM LPROD WHERE LPROD_ID > " + input;

		// 2번문제
		int input1 = Integer.parseInt(sc.next());
		int input2 = Integer.parseInt(sc.next());

		int max = Math.max(input1, input2);
		int min = Math.min(input1, input2);

		String sql = "SELECT * FROM LPROD WHERE LPROD_ID BETWEEN " + min + "AND " + max;

		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, user, pwd);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);

		while (rs.next()) {
			int id = rs.getInt("LPROD_ID");
			String gu = rs.getString("LPROD_GU");
			String nm = rs.getString("LPROD_NM");
			System.out.printf("LPROD_ID: %d, LPROD_GU: %s, LPROD_NM: %s", id, gu, nm);
			System.out.println();
		}

		rs.close();
		st.close();
		con.close();

	}
}
