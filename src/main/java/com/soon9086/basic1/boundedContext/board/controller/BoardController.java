package com.soon9086.basic1.boundedContext.board.controller;

import com.soon9086.basic1.boundedContext.board.model.Board;
import com.soon9086.basic1.boundedContext.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.*;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 게시글 등록 화면
    @GetMapping("/write")
    public String writeForm() {
        return "/board/board_write";
    }

    // 게시글 저장
    @PostMapping("/save")
    public String save(@RequestParam String title,
                       @RequestParam String content,
                       Model model) {
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);

        boardService.save(board); // ← MySQL DB 저장

        model.addAttribute("message", "게시글이 DB에 등록되었습니다!");
        model.addAttribute("boardList", boardService.list());

        return "redirect:/board/list"; // ★ 새로고침해도 insert 재실행 안됨
    }

    // 게시글 목록 화면
    @GetMapping("/list")
    public String list(Model model) {
        List<Board> boardList = boardService.list();
        model.addAttribute("boardList", boardList);
        return "/board/board_list";
    }

    // 상세 페이지
    @PostMapping("/detail")
    public String detail(@RequestParam Long id, Model model) {
        Board post = boardService.getBoardDetail(id);
        model.addAttribute("post", post);
        return "/board/board_detail";
    }

    // 수정 페이지
    @PostMapping("/edit")
    public String editForm(@RequestParam Long id, Model model) {
        Board post = boardService.getBoardDetail(id);
        model.addAttribute("post", post);
        return "board/board_edit";
    }

    // 수정 처리
    @PostMapping("/update")
    public String update(Board dto) {
        boardService.update(dto);
        return "redirect:/board/list";
    }

    // 삭제 처리
    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        boardService.delete(id);
        return "redirect:/board/list";
    }


}
