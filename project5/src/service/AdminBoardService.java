package service;

import dao.AdminBoardDAO;
import util.GameManager;
import util.JDBCUtil;
import util.PrintUtil;
import util.ScanUtil;
import util.SessionUtil;
import util.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminBoardService {
    private static AdminBoardService instance = null;
    private AdminBoardService() {}
    public static AdminBoardService getInstance() {
        if (instance == null) {
            instance = new AdminBoardService();
        }
        return instance;
    }

    private AdminBoardDAO adminBoardDAO = AdminBoardDAO.getInstance();
		
    public List<Map<String, Object>> boardList() {
        return adminBoardDAO.boardList();
    }

    // 게시물 목록 출력 메소드
    public int printBoardList(List<Map<String, Object>> boardList) {
        if (boardList != null && !boardList.isEmpty()) {
            PrintUtil.bar();
            System.out.println("no\t\ttitle\t\twriter");
            for (Map<String, Object> board : boardList) {
                System.out.print(board.get("REQ_NO") + "\t\t" + board.get("REQ_TITLE") + "\t\t" + board.get("REQ_WRITER"));
                System.out.println();
            }
            PrintUtil.bar();
        } else {
            System.out.println("게시물이 없습니다.");
        }
        
        return View.ADMIN_MAIN;
    }

    // 상세조회
    public int read(int reqNo) {
        Map<String, Object> board = adminBoardDAO.selectBoard(reqNo);
        if (board == null) {
            PrintUtil.bar3();
            PrintUtil.centerAlignment("존재하지 않는 게시물입니다.");
            PrintUtil.bar3();
            return View.ADMIN_BOARD;
        }

        PrintUtil.bar();
        PrintUtil.centerAlignment("【 게시물 정보 】");
        System.out.println("번호 : " + board.get("REQ_NO"));
        System.out.println("제목 : " + board.get("REQ_TITLE"));
        System.out.println("내용 : " + board.get("REQ_DETAIL"));
        System.out.println("작성자 : " + board.get("REQ_WRITER"));
        System.out.println("작성자번호 :" + board.get("USER_NO"));
        PrintUtil.bar2();
        // 댓글 출력
        List<Map<String, Object>> commentList = adminBoardDAO.getComments(reqNo);
        if (commentList != null && !commentList.isEmpty()) {
            System.out.println("【 댓글 목록 】");
            for (Map<String, Object> comment : commentList) {

                if(comment.get("REQ_RESP") != null) {
                    System.out.println("댓글: " + comment.get("REQ_RESP"));
                    System.out.println("작성자: 관리자");
                } else {
                	System.out.println("댓글이 작성되지 않았습니다.");
                }

//              System.out.println("작성자: " + comment.get("REQ_WRITER") + " " + AdminService.getInstance().getAdminInfo().get("ADMIN_NAME"));
                System.out.println();
            }
        } else {
            System.out.println("댓글이 없습니다.");
        }
        System.out.println("1.수정   2.삭제   3.뒤로가기   4.댓글작성   5.댓글수정");
        PrintUtil.bar();
        System.out.print("\n【 선택 】");
        System.out.println();
        
        switch (ScanUtil.nextInt()) {
            case 1:
                return update(reqNo);
            case 2:
                return delete(reqNo);
            case 3:
                return View.ADMIN_BOARD;
            case 4:
            	return createComment(reqNo);
            case 5:
            	return updateComment(reqNo);
            default:
                PrintUtil.bar3();
                PrintUtil.centerAlignment("잘못된 입력입니다.");
                PrintUtil.bar3();
                break;
        }
        return View.ADMIN_BOARD;
    }

    // 수정
    public int update(int reqNo) {
        Map<String, Object> board = adminBoardDAO.selectBoard(reqNo);
        if (board == null) {
            PrintUtil.bar3();
            PrintUtil.centerAlignment("존재하지 않는 게시물입니다.");
            PrintUtil.bar3();
            return View.ADMIN_BOARD;
        }

        PrintUtil.bar();
        PrintUtil.centerAlignment("【 게시물 수정 】");
        PrintUtil.bar2();
        System.out.print("수정할 제목 입력: ");
        String newTitle = ScanUtil.nextLine();
        System.out.print("수정할 내용 입력: ");
        String newContent = ScanUtil.nextLine();
        System.out.print("수정할 작성자 입력: ");
        String newWriter = ScanUtil.nextLine();
        PrintUtil.bar2();

        System.out.println("정말로 수정하시겠습니까? (y / n)");
        System.out.print("\n【 선택 】");
        String confirm = ScanUtil.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            board.put("REQ_TITLE", newTitle);
            board.put("REQ_DETAIL", newContent);
            board.put("REQ_WRITER", newWriter);

            int result = adminBoardDAO.updateBoard(board);
            if (result > 0) {
                PrintUtil.bar3();
                PrintUtil.centerAlignment("게시글이 수정되었습니다.");
                PrintUtil.bar3();
            } else {
                PrintUtil.bar3();
                PrintUtil.centerAlignment("게시글 수정에 실패했습니다.");
                PrintUtil.bar3();
            }
        } else {
            PrintUtil.bar3();
            PrintUtil.centerAlignment("게시글 수정을 취소하였습니다.");
            PrintUtil.bar3();
        }
        return View.ADMIN_BOARD;
    }
    // 삭제
    public int delete(int reqNo) {
        Map<String, Object> board = adminBoardDAO.selectBoard(reqNo);
        if (board == null) {
            PrintUtil.bar3();
            PrintUtil.centerAlignment("존재하지 않는 게시물입니다.");
            PrintUtil.bar3();
            return View.ADMIN_BOARD;
        }

        System.out.println("정말로 삭제하시겠습니까? (y / n)");
        System.out.print("\n【 선택 】");
        String confirm = ScanUtil.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            int result = adminBoardDAO.deleteBoard(reqNo);
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
            PrintUtil.centerAlignment("게시글 삭제가 취소되었습니다.");
            PrintUtil.bar3();
        }
        return View.ADMIN_BOARD;
    }
    
    // 게시글 생성
    public int create() {
        PrintUtil.bar();
        System.out.println(" 【 게시물 생성 】");
        PrintUtil.bar2();

        // 제목 입력 받기
        System.out.print("제목 입력 : ");
        String title = ScanUtil.nextLine();
        // 내용 입력 받기
        System.out.print("내용 입력 : ");
        String detail = ScanUtil.nextLine();
        // 작성자 입력 받기
        System.out.print("작성자 입력 : ");
        String writer = ScanUtil.nextLine();
        
        String userNo;
        if (SessionUtil.getCurrentUserNo() == null) {
        // 현재 로그인된 유저 정보가 없을 경우, 관리자로 게시글을 생성하므로 관리자 정보 사용
            userNo = "관리자";
        } else {
        
        	// 현재 로그인된 유저 정보가 있을 경우, 유저 번호 사용
            userNo = SessionUtil.getCurrentUserNo();
        }
        
        // 게시글 생성
        Map<String, Object> board = new HashMap<>();
        	board.put("REQ_TITLE", title);
        	board.put("REQ_DETAIL", detail);
        	board.put("REQ_WRITER", writer);
        	board.put("USER_NO", userNo);
        	int result = adminBoardDAO.createBoard(board);

        	if (result > 0) {
        		PrintUtil.bar3();
        		PrintUtil.centerAlignment("게시글이 생성되었습니다.");
        		PrintUtil.bar3();
        	} else {
        		PrintUtil.bar3();
        		PrintUtil.centerAlignment("게시글 생성에 실패했습니다.");
        		PrintUtil.bar3();

        	}
        	return View.ADMIN_BOARD;
    }
    
    // 댓글 작성
    public int createComment(int reqNo) {
    	
        Map<String, Object> board = adminBoardDAO.selectBoard(reqNo);
        
        if (board == null) {
            PrintUtil.bar3();
            PrintUtil.centerAlignment("존재하지 않는 게시물입니다.");
            PrintUtil.bar3();
            return View.ADMIN_BOARD;
        }
        
        String sql = "SELECT * FROM REQUEST"
        		   + " WHERE REQ_NO = '" + reqNo + "'";
        if (JDBCUtil.getInstance().selectOne(sql).get("REQ_RESP") != null) {
            PrintUtil.bar3();
            PrintUtil.centerAlignment("이미 댓글이 존재합니다.");
            PrintUtil.bar3();
            return View.ADMIN_BOARD;
        }

        String comment = null;
        
		do {
			// 댓글 내용 입력 받기
	        PrintUtil.bar2();
	        System.out.println("【 댓글 작성 】");
	        System.out.print("댓글 내용 입력: ");
	        comment = ScanUtil.nextLine();
	        PrintUtil.bar2();
	        
			if(comment == null || comment.trim().isEmpty()) {
				System.out.println("댓글의 내용을 입력해 주세요");
			}
		} while (comment == null || comment.trim().isEmpty());

        System.out.println("정말로 댓글을 작성하시겠습니까? (y / n)");
        System.out.print("\n【 선택 】");
        String confirm = ScanUtil.nextLine();
        if (confirm.equalsIgnoreCase("y")) {

            int result = adminBoardDAO.createComment(reqNo, comment);
            
            if (result > 0) {
                PrintUtil.bar3();
                PrintUtil.centerAlignment("댓글이 작성되었습니다.");
                PrintUtil.bar3();
            } else {
                PrintUtil.bar3();
                PrintUtil.centerAlignment("댓글 작성에 실패했습니다.");
                PrintUtil.bar3();
            }
        } else {
            PrintUtil.bar3();
            PrintUtil.centerAlignment("댓글 작성을 취소하였습니다.");
            PrintUtil.bar3();
        }
        return View.ADMIN_BOARD;
    }

    // 댓글 수정
    public int updateComment(int reqNo) {
        Map<String, Object> board = adminBoardDAO.selectBoard(reqNo);
        if (board == null) {
            PrintUtil.bar3();
            PrintUtil.centerAlignment("존재하지 않는 게시물입니다.");
            PrintUtil.bar3();
            return View.ADMIN_BOARD;
        }
        if (board.get("REQ_RESP") == null || board.get("REQ_RESP").toString().trim().isEmpty()) {
            PrintUtil.bar3();
            PrintUtil.centerAlignment("댓글이 존재하지 않아 수정할 수 없습니다.");
            PrintUtil.bar3();
            return View.ADMIN_BOARD;
        }

        PrintUtil.bar();
        System.out.println("【 댓글 수정 】");
        PrintUtil.bar2();
        System.out.print("수정할 댓글 내용 입력: ");
        String comment = ScanUtil.nextLine();
        PrintUtil.bar2();

        System.out.println("정말로 댓글을 수정하시겠습니까? (y / n)");
        System.out.print("\n【 선택 】");
        String confirm = ScanUtil.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
        	
            board.put("REQ_RESP", comment);

            int result = adminBoardDAO.updateAdminBoard(board);
            
            if (result > 0) {
                PrintUtil.bar3();
                PrintUtil.centerAlignment("댓글이 수정되었습니다.");
                PrintUtil.bar3();
            } else {
                PrintUtil.bar3();
                PrintUtil.centerAlignment("댓글 수정에 실패했습니다.");
                PrintUtil.bar3();
            }
        } else {
            PrintUtil.bar3();
            PrintUtil.centerAlignment("댓글 수정을 취소하였습니다.");
            PrintUtil.bar3();
        }
        return View.ADMIN_BOARD;
    }
    
}