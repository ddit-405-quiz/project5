package util;

import java.util.Map;
import util.View;
import service.UserService;

public class GameManager {
	
	private static GameManager instance = null;
	private GameManager() {}
	public static GameManager getInstance() {
		if(instance == null) instance = new GameManager();
		return instance;
	}
	
	// 현재 로그인한 유저 정보를 반환
	public Map<String, Object> getUserInfo(){
		return UserService.getInstance().getUserInfo();
	}

	// 현재 정답 개수 확인 변수 및 get, set 메소드
	private int correctCount = 0;
	public void setCorrectCount() {
		correctCount++;
	}
	public int getCorrectCount() {
		return correctCount;
	}
	public void resetCorrectCount() {
		correctCount = 0;
	}
	
	
	// 현재 아이템 사용 유무 확인 변수 및 get, set 메소드
	private boolean useDouble = false;
	private boolean useHint = false;
	private boolean useLife = false;
	public void useItem(int itemCode) {
		switch (itemCode) {
		case View.ITEM_DOUBLE:
			useDouble = true;
			return;
		case View.ITEM_HINT:
			useHint = true;
			return;
		case View.ITEM_LIFE:
			useLife = true;
			return;
		default :
			System.out.println("오류! 아이템 선택 오류");
		}
	}
	public boolean hasItem(int itemCode) {
		switch (itemCode) {
		case View.ITEM_DOUBLE:
			return useDouble;
		case View.ITEM_HINT:
			return useHint;
		case View.ITEM_LIFE:
			return useLife;
		default :
			System.out.println("오류! 아이템 선택 오류");
			return false;
		}
	}
	public void resetItem() {
		useDouble = false;
		useHint = false;
		useLife = false;
	}
}
