package com.soon9086.basic1.boundedContext.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.*;

@Controller
@RequestMapping("/board")
public class BoardController {

    // 임시 저장용 리스트 (DB 연결 전 테스트용)
    private List<Map<String, String>> boardList = new ArrayList<>();

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
        Map<String, String> post = new HashMap<>();
        post.put("title", title);
        post.put("content", content);
        post.put("date", java.time.LocalDateTime.now().toString());
        boardList.add(0, post); // 최신 글 위로

        model.addAttribute("message", "게시글이 등록되었습니다!");
        model.addAttribute("boardList", boardList);
        return "/board/board_list";
    }

    // 게시글 목록 화면
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("boardList", boardList);
        return "/board/board_list";
    }
}
