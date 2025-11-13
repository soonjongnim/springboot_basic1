package com.soon9086.basic1.boundedContext.home.controller;

import com.soon9086.basic1.boundedContext.member.entity.Member;
import com.soon9086.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

// HomeController.java

// ① @Controller : 이 클래스가 Spring MVC의 "컨트롤러" 역할을 한다는 것을 표시.
//     - 사용자의 요청(URL)을 받아서 처리하고, 응답을 반환함.
@Controller
public class HomeController {

    private final MemberService memberService;

    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 메인 페이지로 이동
    @GetMapping("/")
    public String showMain(HttpSession session, Model model) {
        Long loginedMemberId = (Long) session.getAttribute("loginedMemberId");

        if(loginedMemberId != null) {
            // 로그인 되어있으면 Member 정보 가져오기
            Member member = memberService.findById(loginedMemberId);
            System.out.println(member);
            model.addAttribute("member", member);
        }

        return "main"; // --> /WEB-INF/views/main.jsp
    }

}

