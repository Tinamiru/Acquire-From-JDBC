package ex1;

import java.sql.*;

public class Program {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String sql = "SELECT * FROM NOTICE";
		String user = "JR95", pwd = "java";

		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, user, pwd);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);

		if (rs.next()) {
			int id = rs.getInt("ID");
			String title = rs.getString("TITLE");
			String writerID = rs.getString("WRITER_ID");
			Date regDate = rs.getDate("REGDATE");
			String content = rs.getString("CONTENT");
			int hit = rs.getInt("HIT");
			System.out.printf("id: %d, title: %s,writerId: %s, regDate: %s, content: %s, hit:%d\n", id, title, writerID,
					regDate, content, hit);
		}

		rs.close();
		st.close();
		con.close();

	}
}
