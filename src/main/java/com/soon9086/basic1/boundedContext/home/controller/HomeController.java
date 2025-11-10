package com.soon9086.basic1.boundedContext.home.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// HomeController.java

// ① @Controller : 이 클래스가 Spring MVC의 "컨트롤러" 역할을 한다는 것을 표시.
//     - 사용자의 요청(URL)을 받아서 처리하고, 응답을 반환함.
@Controller
public class HomeController {
    int num;

    public HomeController() {
        num = 0;
    }
    // ② @GetMapping("/home/main")
    //     - 사용자가 "/home/main" 주소로 GET 방식 요청을 보낼 때,
    //       아래 메서드(showHomeMain)를 실행하도록 지정.
    @GetMapping("/home/main")
    // ③ @ResponseBody
    //     - 메서드가 반환하는 문자열을 HTML 페이지 이름이 아니라,
    //       "그대로 HTTP 응답 본문(body)"으로 보냄.
    //       즉, 뷰 파일(.jsp, .html 등)을 찾지 않고 문자열을 직접 출력함.
    @ResponseBody
    public String showHomeMain() {
        // ④ return "반갑습니다.";
        //     - 클라이언트(웹 브라우저)에 "반갑습니다."라는 문자열을 그대로 응답으로 보냄.
        return "반갑습니다.";
    }
    @GetMapping("/home/main2")
    @ResponseBody
    public String showHomeMain2() {
        return "좋아요.";
    }
    @GetMapping("/home/main3")
    @ResponseBody
    public String showHomeMain3() {
        return "강력합니다.";
    }
    @GetMapping("/home/increase")
    @ResponseBody
    public int showIncrease() {
        return num++;
    }
    @GetMapping("/home/plus")
    @ResponseBody
    public int showPlus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b) {
        // defaultValue = "0" 기본값 지정됨.
        return a+b;
    }
    @GetMapping("/home/gugudan")
    @ResponseBody
    public String showGugudan(@RequestParam(defaultValue = "2") int dan, @RequestParam(defaultValue = "9") int limit) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= limit; i++) {
            sb.append(dan)
                    .append(" x ")
                    .append(i)
                    .append(" = ")
                    .append(dan*i)
                    .append("<br>");
        }
        return sb.toString(); // String타입으로 변환
    }
    @GetMapping("/home/gugudan/{dan}/{limit}")
    @ResponseBody
    public String showGugudanPathVariable(@PathVariable int dan, @PathVariable int limit) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= limit; i++) {
            sb.append(dan)
                    .append(" x ")
                    .append(i)
                    .append(" = ")
                    .append(dan*i)
                    .append("<br>");
        }
        return sb.toString(); // String타입으로 변환
    }
}

