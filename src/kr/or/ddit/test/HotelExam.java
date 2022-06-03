package kr.or.ddit.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import kr.or.ddit.util.JDBCUtil;

public class HotelExam {

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    private Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        HotelExam he = new HotelExam();
        he.view();
    }

    public void view() throws InterruptedException, NumberFormatException {
        System.out.println("호텔 시스템을 시작합니다.");
        System.out.println();
        Thread.sleep(500);
        System.out.println("시스템을 불러옵니다.");
        System.out.println();
        Thread.sleep(500);
        System.out.print("로딩중");
        for (int i = 0; i < 25; i++) {
            Thread.sleep(80);
            System.out.print(".");
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("***********************************************************");
        System.out.println("                        환영합니다.");
        System.out.println("***********************************************************");
        System.out.println();
        front();
    }

    public void front() throws InterruptedException {

        while (true) {
            try {
                Thread.sleep(1000);
                System.out.println();
                System.out.println("***********************************************************");
                System.out.println("어떤 업무를 하시겠습니까?");
                System.out.println("1. 체크인  2. 체크아웃 3. 객실상태 4. 업무종료");
                System.out.println("***********************************************************");
                System.out.println();
                System.out.print("메뉴선택 => ");
                int input = Integer.parseInt(sc.nextLine());
                switch (input) {
                    case 1:
                        checkIn();
                        break;
                    case 2:
                        checkOut();
                        break;
                    case 3:
                        state();
                        break;
                    case 4:
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    default:
                        System.out.println("메뉴에 해당하는 숫자만 입력 해 주십시오.");
                        System.out.println();
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하여 주십시오.");
                System.out.println();
            }
        }
    }

    private void checkIn() throws NumberFormatException {
        String roomNum;
        boolean isExist = false; // 중복 체크

        do {
            System.out.println();
            System.out.println("어느방에 체크인 하시겠습니까?");
            System.out.print("방번호 입력 => ");

            roomNum = sc.next();

            isExist = checkRoom(roomNum);

            if (isExist) {
                System.out.println("방번호 " + roomNum + "는 이미 체크인 되었습니다.");
                System.out.println("다시 입력해주세요");
            }

        } while (isExist);
        System.out.println("누구를 체크인 하시겠습니까?");
        System.out.print("이름 입력 => ");
        String roomName = sc.next();

        sc.nextLine();

        try {
            conn = JDBCUtil.getConnection();

            String sql = "INSERT INTO HOTEL_MNG " + " ( room_num, guest_name) " + " VALUES (?, ?) ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, roomNum);
            pstmt.setString(2, roomName);

            int cnt = pstmt.executeUpdate();

            if (cnt > 0) {
                System.out.println(roomNum + " 체크인 되었습니다.");
            } else {
                System.out.println(roomNum + " 체크인 하지 못했습니다.");

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn, stmt, pstmt, rs);
        }

    }

    private boolean checkRoom(String roomNum) {
        boolean isExist = false;

        try {
            conn = JDBCUtil.getConnection();
            String sql = "select count(*) as cnt from HOTEL_MNG where room_num = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, roomNum);

            rs = pstmt.executeQuery();

            int cnt = 0;
            if (rs.next()) {
                cnt = rs.getInt("cnt");
            }

            isExist = cnt > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn, stmt, pstmt, rs);
        }

        return isExist;
    }

    private void checkOut() throws InterruptedException, NumberFormatException {
        String roomNum = "";
        boolean isExist = false; // 중복 체크
        try {

            do {
                System.out.println();
                System.out.println("어느방을 체크아웃 하시겠습니까?");
                System.out.print("방번호 입력 => ");

                roomNum = sc.next();

                sc.nextLine();

                isExist = checkRoom(roomNum);

                if (!isExist) {
                    System.out.println("방번호 " + roomNum + "는 체크인 되지 않았습니다.");
                    System.out.println("다시 입력해주세요");
                }

            } while (!isExist);

            conn = JDBCUtil.getConnection();
            String sql = "delete from HOTEL_MNG where ROOM_NUM = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, roomNum);

            int cnt = pstmt.executeUpdate();

            if (cnt > 0) {
                System.out.println(roomNum + " 체크아웃 되었습니다.");
            } else {
                System.out.println(roomNum + " 체크아웃 되지 않았습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            JDBCUtil.close(conn, stmt, pstmt, rs);
        }
    }

    private void state() {
        try {
            conn = JDBCUtil.getConnection();

            System.out.println("-----------------------");
            System.out.println("방번호\t이 름");
            System.out.println("-----------------------");

            String sql = "select * from HOTEL_MNG order by ROOM_NUM";

            stmt = conn.createStatement();

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String roomNum = rs.getString("room_num");
                String roomName = rs.getString("GUEST_NAME");

                System.out.println(roomNum + "\t" + roomName);

            }
            System.out.println("-------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("회원자료 가져오기 실패!!!");
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn, stmt, pstmt, rs);

        }
    }

}