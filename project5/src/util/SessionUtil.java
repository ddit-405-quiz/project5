package util;

public class SessionUtil {
    private static String currentUserNo;

    // ���� �α��ε� ȸ���� USER_NO ����
    public static void setCurrentUserNo(String userNo) {
        currentUserNo = userNo;
    }

    // ���� �α��ε� ȸ���� USER_NO ��ȯ
    public static String getCurrentUserNo() {
        return currentUserNo;
    }
}