package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import kr.or.ddit.util.JDBCUtil;

/*
    LPROD 테이블에 새로운 데이터를 추가하기
    
    lprod_gu와 lprod_nm은 직접 입력받아 처리하고
    lprod_id는 현재의 lprod_id들 중 제일 큰 값보다 1 증가된 값으로 한다.
    (기타사항 : lprod_gu도 중복되는지 검사한다.)
 */
public class T04JdbcTest {

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Scanner scan = new Scanner(System.in);

		try {
			/*
			 * // 1. 드라이버 로딩(옵션) Class.forName("oracle.jdbc.driver.OracleDriver");
			 * 
			 * // 2. DB에 접속 (Connection객체 생성) String url =
			 * "jdbc:oracle:thin:@localhost:1521/xe"; String userId = "sem"; String password
			 * = "java";
			 * 
			 * conn = DriverManager.getConnection(url, userId, password);
			 * 
			 */
			conn = JDBCUtil.getConnection();

			stmt = conn.createStatement();

			// lprod_id 의 최대값을 가져와서 1 증가시키기
			String sql = "select max(lprod_id) from lprod";

			rs = stmt.executeQuery(sql);
			int num = 0;
			while (rs.next()) {
				num = rs.getInt(1);
			}
			num++;

			int cnt = 0;

			String sql2 = "select count(*) as cnt from lprod where lprod_gu = ?";

			pstmt = conn.prepareStatement(sql2);

			String gu = "";
			do {
				System.out.print("상품 분류코드(LPROD_GU) 입력 : ");
				gu = scan.next();
				pstmt.setString(1, gu);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					cnt = rs.getInt("cnt");
				}

				if (cnt > 0) {
					System.out.println("상품 분류 코드 " + gu + "은(는)" + " 이미 있는 상품입니다.");
					System.out.println("다시 입력하세요.");
					System.out.println();
				}

			} while (cnt > 0);

			System.out.print("상품 이름(LPROD_NM) 입력 : ");
			String nm = scan.next();

			scan.close();

			sql2 = " insert into lprod (lprod_id, lprod_gu, lprod_nm) " + " values (?, ?, ?) ";
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, num);
			pstmt.setString(2, gu);
			pstmt.setString(3, nm);

			cnt = pstmt.executeUpdate();

			if (cnt > 0) {
				System.out.println(gu + "를 추가했습니다.");
			} else {
				System.out.println(gu + "를 추가하는데 실패했습니다.");
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			/*
			 * if(rs != null) try {rs.close();} catch(SQLException ex) {} if(stmt != null)
			 * try {stmt.close();} catch(SQLException ex) {} if(pstmt != null) try
			 * {pstmt.close();} catch(SQLException ex) {} if(conn != null) try
			 * {conn.close();} catch(SQLException ex) {}
			 */
			JDBCUtil.close(conn, stmt, pstmt, rs);
		}

	}
}
