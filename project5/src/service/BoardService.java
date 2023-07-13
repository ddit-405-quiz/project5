package service;

import dao.BoardDAO;
import util.PrintUtil;
import util.ScanUtil;
import util.SessionUtil;
import util.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.Controller;

public class BoardService {
    private static BoardService instance = null;
    private BoardService() {}
    public static BoardService getInstance() {
        if (instance == null) instance = new BoardService();
        return instance;
    }

    private BoardDAO boardDAO = BoardDAO.getInstance();
       
    // 전체 페이지 수 계산
    public int getTotalPage() {
        int totalCount = BoardService.getInstance().getTotalBoardCount();
        int pageSize = 5; // 페이지당 게시글 수
        return (int) Math.ceil((double) totalCount / pageSize);
    }     
    
    // 페이지별로 게시글 리스트 가져오기
    public List<Map<String, Object>> getBoardListByPage(int page) {
        int pageSize = 5; // 페이지당 게시글 수
        int start = (page - 1) * pageSize + 1;
        int end = start + pageSize - 1;
        return boardDAO.getBoardListByPage(start, end);
    }

    // 전체 게시글 수 가져오기
    public int getTotalBoardCount() {
        return boardDAO.getTotalBoardCount();
    }
    
    // 게시글 상세조회
    public int read(int reqNo) {
        Map<String, Object> board = boardDAO.selectBoard(reqNo);
        if (board == null) {
        	PrintUtil.bar3();
			PrintUtil.centerAlignment("존재하지 않습니다.");
			PrintUtil.bar3();
            return View.BOARD;
        }

        PrintUtil.bar();
        System.out.println("【 게시물 정보 】");
        System.out.println("번호: " + board.get("REQ_NO"));
        System.out.println("제목: " + board.get("REQ_TITLE"));
        System.out.println("내용: " + board.get("REQ_DETAIL"));
        System.out.println("작성자: " + board.get("REQ_WRITER"));
        PrintUtil.bar2();
        System.out.println("① 수정 ② 삭제 ③ 뒤로가기");
        System.out.print("\n 【  선택  】");

        switch (ScanUtil.nextInt()) {
            case 1:
                if (currentUser(reqNo)) {
                    return update(reqNo);
                } else {
                	PrintUtil.bar3();
        			PrintUtil.centerAlignment("본인이 작성한 게시물이 아닙니다.");
        			PrintUtil.bar3();
                }
                break;
            case 2:
                if (currentUser(reqNo)) {
                    return delete(reqNo);
                } else {
                	PrintUtil.bar3();
        			PrintUtil.centerAlignment("본인이 작성한 게시물이 아닙니다.");
        			PrintUtil.bar3();
                }
                break;
            case 3:
                return View.BOARD;
            default:
            	PrintUtil.bar3();
    			PrintUtil.centerAlignment("잘못된 입력입니다.");
    			PrintUtil.bar3();
                break;
        }
        return View.BOARD;
    }
    

    // 게시글 수정

    public int update(int reqNo) {

    	Map<String, Object> board = boardDAO.selectBoard(reqNo);
    	if (board == null) {
    	    PrintUtil.bar3();
    	    PrintUtil.centerAlignment("존재하지 않는 게시글입니다.");
    	    PrintUtil.bar3();
    	    return View.BOARD;
    	}


        PrintUtil.bar();
        System.out.println(" 【 게시물 수정 】 ");
        PrintUtil.bar2();
        System.out.print("수정할 제목 입력: ");
        String newTitle = ScanUtil.nextLine();
        System.out.print("수정할 내용 입력: ");
        String newContent = ScanUtil.nextLine();
        System.out.print("수정할 작성자 입력: ");
        String newWriter = ScanUtil.nextLine();
        PrintUtil.bar2();
        
        System.out.println("정말로 수정하시겠습니까? (y / n)");
    	System.out.print("\n 【  선택  】");
    	String confirm=ScanUtil.nextLine();
        if(confirm.equalsIgnoreCase("y")) {
        	board.put("REQ_TITLE", newTitle);
        	board.put("REQ_DETAIL", newContent);
        	board.put("REQ_WRITER", newWriter);
        	
        	int result = boardDAO.updateBoard(board);
        	if (result > 0) {
        		PrintUtil.bar3();
        		PrintUtil.centerAlignment("게시글이 수정되었습니다.");
        		PrintUtil.bar3();
        	} else {
        		PrintUtil.bar3();
        		PrintUtil.centerAlignment("게시글 수정에 실패했습니다.");
        		PrintUtil.bar3();
        	}
        	}else {
        		PrintUtil.bar3();
        		PrintUtil.centerAlignment("게시글 수정을 취소하였습니다.");
        		PrintUtil.bar3();
        	}
        	return View.BOARD;
    }

    public int delete(int reqNo) {

    	Map<String, Object> board = boardDAO.selectBoard(reqNo);
    	if (board == null) {
    	    PrintUtil.bar3();
    	    PrintUtil.centerAlignment("존재하지 않는 게시글입니다.");
    	    PrintUtil.bar3();
    	    return View.BOARD;
    	}    	

    	System.out.println("정말로 삭제하시겠습니까? (y / n)");
    	System.out.print("\n 【  선택  】");
    	String confirm=ScanUtil.nextLine();
    	if(confirm.equalsIgnoreCase("y")) {
    		int result = boardDAO.deleteBoard(reqNo);
    		if (result > 0) {
    			PrintUtil.bar3();
        		PrintUtil.centerAlignment("게시글이 삭제되었습니다.");
        		PrintUtil.bar3();
    		} else {
    			PrintUtil.bar3();
        		PrintUtil.centerAlignment("게시글 삭제에 실패했습니다.");
        		PrintUtil.bar3();
    		}
    		} else {
    			PrintUtil.bar3();
        		PrintUtil.centerAlignment("게시글 삭제가 취소되었습니다");
        		PrintUtil.bar3();
    		}
    		return View.BOARD;
    }
    //게시글 생성
    public int create() {
        PrintUtil.bar();
        System.out.println(" 【 게시물 생성 】");
        PrintUtil.bar2();

        // 현재 로그인된 회원의 USER_NO 가져오기
        String userNo = SessionUtil.getCurrentUserNo();

        // 제목 입력 받기
        System.out.print("제목 입력 : ");
        String title = ScanUtil.nextLine();
        // 내용 입력 받기
        System.out.print("내용 입력 : ");
        String detail = ScanUtil.nextLine();
        // 작성자 입력 받기
        System.out.print("작성자 입력 : ");
        String writer = ScanUtil.nextLine();
        
        // 게시글 생성
        Map<String, Object> board = new HashMap<>();
        	board.put("REQ_TITLE", title);
        	board.put("REQ_DETAIL", detail);
        	board.put("REQ_WRITER", writer);
        	board.put("USER_NO", userNo);
        	int result = boardDAO.createBoard(board);

        	if (result > 0) {
        		PrintUtil.bar3();
        		PrintUtil.centerAlignment("게시글이 생성되었습니다.");
        		PrintUtil.bar3();
        	} else {
        		PrintUtil.bar3();
        		PrintUtil.centerAlignment("게시글 생성에 실패했습니다.");
        		PrintUtil.bar3();

        	}
        	return View.BOARD;
    }
        
  //게시글 작성자가 현재 유저인지 확인
    public boolean currentUser(int reqNo) {
        String currentUserNo = SessionUtil.getCurrentUserNo();
        Map<String, Object> board = boardDAO.selectBoard(reqNo);
        if (board == null) {
            return false;
        }
        // 작성자의 회원번호 가져오기
        String writerUserNo = board.get("USER_NO").toString();
        // 비교
        return currentUserNo.equals(writerUserNo);
    }
}