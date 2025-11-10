package com.soon9086.basic1.boundedContext.home.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
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
    private int num;
    private List<Person> personList;

    public HomeController() {
        num = 0;
        personList = new ArrayList<>();
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
    @GetMapping("/home/returnMap")
    @ResponseBody
    public Map<String, Object> showReturnMap() {
//        Map<String, Object> map = Map.of(
//                "id", 1,
//                "subject", "제목1",
//                "content", "내용1",
//                "writerName", "홍길순",
//                "articleNo", new ArrayList<>() {{
//                    add(1);
//                    add(2);
//                    add(3);
//                }}
//        );
        Map<String, Object> map = new LinkedHashMap<>() {{
            put("id", 1);
            put("subject", "제목1");
            put("content", "내용1");
            put("writerName", "홍길순");
            put("articleNo", new ArrayList<>() {{
                add(1);
                add(2);
                add(3);
            }});
        }};
        return map;
    }
    @GetMapping("/home/returnArticle")
    @ResponseBody
    public Article2 showReturnArticle() {
        Article2 article2 = new Article2(1, "제목1", "내용1", "김철수", new ArrayList<>() {{
            add(1);
            add(2);
            add(3);
        }});
        return article2;
    }
    @GetMapping("/home/returnArticleList")
    @ResponseBody
    public List<Article2> showReturnArticleList() {
        Article2 article1 = new Article2(1, "제목1", "내용1", "김철수", new ArrayList<>() {{
            add(1);
            add(2);
            add(3);
        }});
        Article2 article2 = new Article2(2, "제목2", "내용2", "김영희", new ArrayList<>() {{
            add(4);
            add(5);
            add(6);
        }});
        Article2 article3 = new Article2(3, "제목3", "내용4", "최수영", new ArrayList<>() {{
            add(7);
            add(8);
            add(9);
        }});

        List<Article2> list = new ArrayList<>();
        list.add(article1);
        list.add(article2);
        list.add(article3);
        return list;
    }
    @GetMapping("/home/addPerson")
    @ResponseBody
    public String addPerson(String name, int age) {
        Person person = new Person(name, age);
        System.out.println("person: " + person);
        personList.add(person);
        System.out.println("personList: " + personList);
        return "name : %s, age : %d".formatted(name, age);
    }
    @GetMapping("/home/showPerson")
    @ResponseBody
    public List<Person> showPerson() {
        return personList;
    }
    @GetMapping("/home/removePerson")
    @ResponseBody
    public String removePerson(int id) {
//        Person target = null;
//        for (Person person : personList) {
//            if (person.getId() == id) {
//                target = person;
//                break;
//            }
//        }
//        if  (target != null) {
//            personList.remove(target);
//        } else {
//            return "%d번 사람이 존재하지 않습니다.".formatted(id);
//        }
        // V2
        boolean removed = personList.removeIf(person -> person.getId() == id);
        if (!removed) {
            return "%d번 사람이 존재하지 않습니다.".formatted(id);
        }
        return "%d번 삭제 처리하였습니다.".formatted(id);
    }

}

class Article {
    public int id;
    public String subject;
    public String content;
    public String writerName;
    public List<Integer> articleNo;
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class Article2 {
    private int id;
    private String subject;
    private String content;
    private String writerName;
    private List<Integer> articleNo;
}

@AllArgsConstructor
@Data
class Person {
    private static int lastId;
    private final int id;
    private final String name;
    private final int age;

    static {
        lastId = 0;
    }

    public Person(String name, int age) {
        this(++lastId, name, age);
    }

}

