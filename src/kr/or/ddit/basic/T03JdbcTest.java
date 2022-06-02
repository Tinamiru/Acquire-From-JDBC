package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class T03JdbcTest {

//	   lprod_id: 101, lprod_gu: N101, lprod_nm: 농산물
//	   lprod_id: 102, lprod_gu: N102, lprod_nm: 수산물
//	   lprod_id: 103, lprod_gu: N103, lprod_nm: 축산물

//		위의 자료를 추가하기

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;

		try {
			// 1. 드라이버 로딩(옵션)
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. DB에 접속 (Connection객체 생성)
			String url = "jdbc:oracle:thin:@localhost:1521/xe";
			String userId = "sem";
			String password = "java";

			conn = DriverManager.getConnection(url, userId, password);

			/*
			 * // 3. Statement객체 생성 => Connection객체를 이용한다. stmt = conn.createStatement();
			 * 
			 * String sql = " insert into lprod (lprod_id, lprod_gu, lprod_nm) " +
			 * " values (101, 'N101', '농산물') ";
			 * 
			 * int cnt = stmt.executeUpdate(sql); System.out.println("첫번째 처리결과(cnt) : " +
			 * cnt);
			 * 
			 * sql = " insert into lprod (lprod_id, lprod_gu, lprod_nm) " +
			 * " values (102, 'N102', '수산물') ";
			 * 
			 * cnt = stmt.executeUpdate(sql); System.out.println("두번째 처리결과(cnt) : " + cnt);
			 * 
			 * sql = " insert into lprod (lprod_id, lprod_gu, lprod_nm) " +
			 * " values (103, 'N103', '축산물') ";
			 * 
			 * cnt = stmt.executeUpdate(sql); System.out.println("세번째 처리결과(cnt) : " + cnt);
			 */
			// 3. PreparedStatement객체를 이용한 자료 추가 방법
			String sql = " insert into lprod (lprod_id, lprod_gu, lprod_nm) " + " values (?, ?, ?) ";

			// PreparedStatement객체를 생성할때 SQL문을 넣어서 생성한다.
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 101);
			pstmt.setString(2, "N101");
			pstmt.setString(3, "농산물");

			int cnt = pstmt.executeUpdate();
			System.out.println("첫번째 반환값 : " + cnt);
			// ---------------------------------------------------
			pstmt.setInt(1, 102);
			pstmt.setString(2, "N102");
			pstmt.setString(3, "수산물");

			cnt = pstmt.executeUpdate();
			System.out.println("두번째 반환값 : " + cnt);
			// ---------------------------------------------------
			pstmt.setInt(1, 103);
			pstmt.setString(2, "N103");
			pstmt.setString(3, "축산물");

			cnt = pstmt.executeUpdate();
			System.out.println("세번째 반환값 : " + cnt);

			System.out.println("작업 끝...");

		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}

	}
}
