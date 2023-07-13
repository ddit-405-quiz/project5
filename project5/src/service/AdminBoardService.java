package service;

import dao.AdminBoardDAO;
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
        System.out.println("【 게시물 정보 】");
        System.out.println("번호 : " + board.get("REQ_NO"));
        System.out.println("제목 : " + board.get("REQ_TITLE"));
        System.out.println("내용 : " + board.get("REQ_DETAIL"));
        System.out.println("작성자 : " + board.get("REQ_WRITER"));
        System.out.println("작성자번호 :" + board.get("USER_NO"));
        PrintUtil.bar2();
        System.out.println("① 수정 ② 삭제 ③ 뒤로가기");
        PrintUtil.bar();
        System.out.print("\n【 선택 】");

        switch (ScanUtil.nextInt()) {
            case 1:
                return update(reqNo);
            case 2:
                return delete(reqNo);
            case 3:
                return View.ADMIN_BOARD;
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
        System.out.println("【 게시물 수정 】");
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
    
  //게시글 생성
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
        
        String userNo = SessionUtil.getCurrentUserNo();
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
}