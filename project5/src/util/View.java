package util;

public interface View { 

	int HOME = 1;				//��������ȭ��
	int HOME_MAIN = 11;			//����ȭ��
	
	int USER = 2;
	int USER_SIGNUP = 21;		//ȸ������
	int USER_LOGIN = 22;		//�����α���
	int USER_UPDATE = 23;		//���� ��������
	int USER_LOGOUT = 24;		//�α׾ƿ�
	int USER_MANAGE = 29;

	
	int ADMIN = 3;			
	int ADMIN_LOGIN = 31;		//������ �α���
	int ADMIN_LOGOUT = 32;		//������ �α׾ƿ�
	
	int QUIZ = 4;
	int QUIZ_CATEGORY = 41;		 // ���� - ī�װ� ����
	int QUIZ_ITEM = 42;          // ���� - ������ ��� ����
	int QUIZ_START = 43;         // ���� - ����
	int QUIZ_FAIL = 44;          // ���� - ����
	int QUIZ_SUCCESS = 45;		 // ���� - ����
	int QUIZ_NONSENSE = 46;		 // ���� - �ͼ���
	int QUIZ_KOREAN = 47;		 // ���� - �츮��
	int QUIZ_COMMON_SENSE = 48;  // ���� - ���
	int QUIZ_HISTORY = 49;		 // ���� - ����
	int QUIZ_MANAGE = 499;		//���� - ������ȸ �� ������ ����
	
	//�Խ��� Ȥ�� ������ �����ڿ��� ��û���� ��  -- �����ʿ�
	int BOARD = 5;
	
	int MYPAGE = 6;
	int MYPAGE_FIND = 61;			//ģ��ã��
	int MYPAGE_WRITE = 62;			//���� �ۼ��� ��
	int MYPAGE_WRITE_UPDATE = 621;	//�ۼ��� �� ����
	int MYPAGE_WRITE_DELETE = 622;	//�ۼ��� �� ����
	int MYPAGE_UPDATE = 63;			//���� ���� ����
	int MYPAGE_DELETE = 64;			//��������
	
	int SHOP = 7;
	int SHOP_MAIN = 71;
	
	int ITEM = 8;
	int ITEM_DOUBLE = 81;
	int ITEM_HINT = 82;
	int ITEM_LIFE = 83;
	
	int ADMIN_MAIN = 9;			//������ ����
	int ADMIN_QUIZ = 91;		//������
	int ADMIN_QUIZ_INSERT = 91;
	
	//���� ���õ� �����̳� ������

}
 