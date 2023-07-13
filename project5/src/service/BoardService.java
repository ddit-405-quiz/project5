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
       
    // ��ü ������ �� ���
    public int getTotalPage() {
        int totalCount = BoardService.getInstance().getTotalBoardCount();
        int pageSize = 5; // �������� �Խñ� ��
        return (int) Math.ceil((double) totalCount / pageSize);
    }     
    
    // ���������� �Խñ� ����Ʈ ��������
    public List<Map<String, Object>> getBoardListByPage(int page) {
        int pageSize = 5; // �������� �Խñ� ��
        int start = (page - 1) * pageSize + 1;
        int end = start + pageSize - 1;
        return boardDAO.getBoardListByPage(start, end);
    }

    // ��ü �Խñ� �� ��������
    public int getTotalBoardCount() {
        return boardDAO.getTotalBoardCount();
    }
    
    //�Խñ� ����ȸ
    public int read(int reqNo) {
        Map<String, Object> board = boardDAO.selectBoard(reqNo);
        if (board == null) {
            System.out.println("�������� �ʽ��ϴ�.");
            return View.BOARD;
        }

        PrintUtil.bar();
        System.out.println("[�Խù� ����]");
        System.out.println("��ȣ: " + board.get("REQ_NO"));
        System.out.println("����: " + board.get("REQ_TITLE"));
        System.out.println("����: " + board.get("REQ_DETAIL"));
        System.out.println("�ۼ���: " + board.get("REQ_WRITER"));
        PrintUtil.bar2();
        System.out.println("�� ���� �� ���� �� �ڷΰ���");
        System.out.print("���� : ");

        switch (ScanUtil.nextInt()) {
            case 1:
                if (currentUser(reqNo)) {
                    return update(reqNo);
                } else {
                    System.out.println("������ �ۼ��� �Խù��� �ƴմϴ�.");
                }
                break;
            case 2:
                if (currentUser(reqNo)) {
                    return delete(reqNo);
                } else {
                    System.out.println("������ �ۼ��� �Խù��� �ƴմϴ�.");
                }
                break;
            case 3:
                return View.BOARD;
            default:
                System.out.println("�߸��� �Է��Դϴ�.");
                break;
        }
        return View.BOARD;
    }
    
    // �Խñ� ����
    public int update(int reqNo) {
        Map<String, Object> board = boardDAO.selectBoard(reqNo);
        	if (board == null) {
        		System.out.println("�������� �ʴ� �Խñ��Դϴ�.");
        		return View.BOARD;
        	}

        PrintUtil.bar();
        System.out.println("[�Խù� ����]");
        PrintUtil.bar2();
        System.out.print("������ ���� �Է�: ");
        String newTitle = ScanUtil.nextLine();
        System.out.print("������ ���� �Է�: ");
        String newContent = ScanUtil.nextLine();
        System.out.print("������ �ۼ��� �Է�: ");
        String newWriter = ScanUtil.nextLine();
        PrintUtil.bar2();
        
        System.out.println("������ �����Ͻðڽ��ϱ�? (y / n)");
    	System.out.print("�Է� : ");
    	String confirm=ScanUtil.nextLine();
        if(confirm.equalsIgnoreCase("y")) {
        	board.put("REQ_TITLE", newTitle);
        	board.put("REQ_DETAIL", newContent);
        	board.put("REQ_WRITER", newWriter);
        	
        	int result = boardDAO.updateBoard(board);
        	if (result > 0) {
        		System.out.println("�Խñ��� �����Ǿ����ϴ�.");
        	} else {
        		System.out.println("�Խñ� ������ �����߽��ϴ�.");
        	}
        	}else {
        		System.out.println("�Խñ� ������ ����Ͽ����ϴ�.");
        	}
        	return View.BOARD;
    }
    // �Խñ� ����
    public int delete(int reqNo) {
    	System.out.println("������ �����Ͻðڽ��ϱ�? (y / n)");
    	System.out.print("�Է� : ");
    	String confirm=ScanUtil.nextLine();
    	if(confirm.equalsIgnoreCase("y")) {
    		int result = boardDAO.deleteBoard(reqNo);
    		if (result > 0) {
    			System.out.println("�Խñ��� �����Ǿ����ϴ�.");
    		} else {
    			System.out.println("�Խñ� ������ �����߽��ϴ�.");
    		}
    		} else {
    			System.out.println("�Խñ� ������ ��ҵǾ����ϴ�");
    		}
    		return View.BOARD;
    }
    // �Խñ� ����
    public int create() {
        PrintUtil.bar();
        System.out.println("[�Խù� ����]");
        PrintUtil.bar2();

        // ���� �α��ε� ȸ���� USER_NO ��������
        String userNo = SessionUtil.getCurrentUserNo();

        // ���� �Է� �ޱ�
        System.out.print("���� �Է� : ");
        String title = ScanUtil.nextLine();
        // ���� �Է� �ޱ�
        System.out.print("���� �Է� : ");
        String detail = ScanUtil.nextLine();
        // �ۼ��� �Է� �ޱ�
        System.out.print("�ۼ��� �Է� : ");
        String writer = ScanUtil.nextLine();
        
        // �Խñ� ����
        Map<String, Object> board = new HashMap<>();
        	board.put("REQ_TITLE", title);
        	board.put("REQ_DETAIL", detail);
        	board.put("REQ_WRITER", writer);
        	board.put("USER_NO", userNo);
        	int result = boardDAO.createBoard(board);

        	if (result > 0) {
        		System.out.println("�Խñ��� �����Ǿ����ϴ�.");
        	} else {
        		System.out.println("�Խñ� ������ �����߽��ϴ�.");
        	}
        	return View.BOARD;
    }
        
    // �Խñ� �ۼ��ڰ� ���� �������� Ȯ��
    public boolean currentUser(int reqNo) {
        String currentUserNo = SessionUtil.getCurrentUserNo();
        Map<String, Object> board = boardDAO.selectBoard(reqNo);
        if (board == null) {
            return false;
        }
        // �ۼ����� ȸ����ȣ ��������
        String writerUserNo = board.get("REQ_WRITER").toString();
        // ��
        return currentUserNo.equals(writerUserNo);
    }
}