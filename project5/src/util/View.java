package util;

public interface View { 

	int HOME = 1;				//게임접속화면
	int HOME_MAIN = 11;			//메인화면
	
	int USER = 2;
	int USER_SIGNUP = 21;		//회원가입
	int USER_LOGIN = 22;		//유저로그인
	int USER_UPDATE = 23;		//유저 정보수정
	int USER_LOGOUT = 24;		//로그아웃
	int USER_MANAGE = 29;

	
	int ADMIN = 3;			
	int ADMIN_LOGIN = 31;		//관리자 로그인
	int ADMIN_LOGOUT = 32;		//관리자 로그아웃
	
	int QUIZ = 4;
	int QUIZ_CATEGORY = 41;		 // 퀴즈 - 카테고리 선택
	int QUIZ_ITEM = 42;          // 퀴즈 - 아이템 사용 여부
	int QUIZ_START = 43;         // 퀴즈 - 시작
	int QUIZ_FAIL = 44;          // 퀴즈 - 실패
	int QUIZ_SUCCESS = 45;		 // 퀴즈 - 성공
	int QUIZ_NONSENSE = 46;		 // 퀴즈 - 넌센스
	int QUIZ_KOREAN = 47;		 // 퀴즈 - 우리말
	int QUIZ_COMMON_SENSE = 48;  // 퀴즈 - 상식
	int QUIZ_HISTORY = 49;		 // 퀴즈 - 역사
	int QUIZ_MANAGE = 499;		//퀴즈 - 문제조회 및 관리자 접근
	
	//게시판 혹은 유저가 관리자에게 요청사항 등  -- 수정필요
	int BOARD = 5;
	
	
	int MYPAGE = 6;
	int MYPAGE_FIND = 61;			//친구찾기
	int MYPAGE_WRITE = 62;			//내가 작성한 글
	int MYPAGE_WRITE_UPDATE = 621;	//작성한 글 수정
	int MYPAGE_WRITE_DELETE = 622;	//작성한 글 삭제
	int MYPAGE_UPDATE = 63;			//유저 정보 수정
	int MYPAGE_DELETE = 64;			//계정삭제
	
	int SHOP = 7;
	int SHOP_MAIN = 71;
	
	int ITEM = 8;
	int ITEM_DOUBLE = 81;
	int ITEM_HINT = 82;
	int ITEM_LIFE = 83;
	
	int ADMIN_MAIN = 9;			//관리자 메인
	int ADMIN_QUIZ = 91;		//관리자
	int ADMIN_QUIZ_INSERT = 91;
	
	int RANKING = 10;
	int RANKING_ALL = 101;
	
	//결제 관련된 상점이나 아이템
}
 