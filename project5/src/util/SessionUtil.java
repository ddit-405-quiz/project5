package util;

public class SessionUtil {
    private static String currentUserNo;

    // 현재 로그인된 회원의 USER_NO 설정
    public static void setCurrentUserNo(String userNo) {
        currentUserNo = userNo;
    }

    // 현재 로그인된 회원의 USER_NO 반환
    public static String getCurrentUserNo() {
        return currentUserNo;
    }
}