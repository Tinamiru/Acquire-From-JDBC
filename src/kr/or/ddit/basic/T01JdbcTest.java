package kr.or.ddit.basic;

import java.sql.*;
import oracle.sql.*;
import oracle.jdbc.*;

public class T01JdbcTest {

//	JDBC를 이용한 데이터베이스 처리 순서
//	
//	순서 : JDBC 드라이버 로딩 -> 해당 DB에 접속 -> 질의(SQL명령을 수행)
//		   -> 질의 결과를 받아서 처리한다. -> 종료(자원반납)
//		   
//	1. JDBC드라이버 로딩
//	   => JDBC 드라이버는 DB를 만든 회사에서 제공한다.
//	   Class.forName("oracle.jdbc.driver.OracleDriver");
//	
//	2. 접속하기 : 접속이 성공하면 Connection 객체가 생성된다.
//		          DriverManger.getConnection()메서드를 이용한다.
//	
//	3. 질의 : Statement 객체 또는 PreparedStatement 객체를 이용하여
//			  SQL 문장을 실행한다.
//
//  4. 결과 : 
//     1) SQL문이 select일 경우 => ResultSet 객체가 만들어진다.
//  	  ResultSet 객체에는 select한 결과가 저장된다.
//     2) SQL문이 insert, update, delete 일 경우 => 정수값을 반환한다.
//	      (정수값은 보통 실행에 성공한 레코드 수를 말한다.)

	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
// 			1. 드라이버 로딩(옵션)
			Class.forName("oracle.jdbc.driver.OracleDriver");

//			2. DB에 접속 (Connection 객체 생성)

			String url = "jdbc:oracle:thin:@localhost:1521:XE", user = "SJR", pwd = "java";

			con = DriverManager.getConnection(url, user, pwd);

//			3. Statement객체 생성 => Connection 객체를 이용한다.
			stmt = con.createStatement();

//			4. SQL문을 Satement 객체를 이용하여 실행하고 실행 결과를 ResultSet 객체에 저장한다.
			String sql = "select * from lprod"; // 실행할 쿼리문

//			SQL문이 select인 경우 executeQuery()메서드를 사용한다.
//			그 외의 경우에는 executeUpdate()를 사용한다.

			rs = stmt.executeQuery(sql);

//			5. ResultSet 객체에 저장되엉 있는 데이터를 반복문과
//			   next()메서드를 이용하여 차례대로 읽어와 처리한다.
			System.out.println("실행한 쿼리문 : " + sql);
			System.out.println("=== 쿼리문 실행 결과 ===");

//			rs.next() => ResultSet 객체의 데이터를 가리키는 포인터를
//						 다음 레코드로 이동시키고 그 곳에 데이터가
//						 있으면 true, 없으면 false를 반환.

			System.out.println("-------------------");
			while (rs.next()) {
				// 컬럼의 자료를 가져오는 방법
				// 방법1) rs.get자료형이름("컬럼명")
				// 방법2) rs.get자료형이름(컬럼번호) => 컬럼번호는 1부터 시작
				System.out.println("lprod_id : " + rs.getInt("lprod_id"));
				System.out.println("lprod_gu : " + rs.getString("lprod_gu"));
				System.out.println("-------------------");

			}
			System.out.println("출력 끝...");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
//			6. 종료 (사용했던 자원을 모두 반납한다.)
			// 6. 종료 (사용했던 자원을 모두 반납한다.)
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}

		}
	}
}