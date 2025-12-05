package com.soon9086.basic1.base.rq;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;
import java.util.Enumeration;

@Component
@RequestScope   // 객체는 매 요청마다 생성된다.
@AllArgsConstructor
public class Rq {
    private HttpServletRequest req;
    private HttpServletResponse resp;

    // 인증 성공 기록 저장
    public void setSession(String name, long value) {
        HttpSession  session = req.getSession();
        session.setAttribute(name, value);
    }

    // 인증 성공 여부 가져오기
    public boolean isSessionSuccess(String name) {
        Object value = req.getSession().getAttribute(name);
        if (value instanceof Boolean) return (Boolean) value;
        return false;
    }

    public long getSessionAsLong(String name, long defaultValue) {
        Object attr = req.getSession().getAttribute(name);
        if (attr == null) return defaultValue;

        try {
            return (attr instanceof Long)
                    ? (Long) attr
                    : Long.parseLong(attr.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    // 인증 성공 기록 제거
    public boolean removeSession(String name) {
        HttpSession session = req.getSession();
        if(session.getAttribute(name) == null) return false;    // 세션을 가져왔는데 없으면 못한다.
        session.removeAttribute(name);
        return true;
    }

    // HttpServletRequest를 받아서 현재 세션의 정보를 문자열로 반환하는 메서드
    public String getSessionDebugInfo() {
        // 문자열을 효율적으로 연결하기 위한 StringBuilder 생성
        StringBuilder debug = new StringBuilder();

        // false면 세션이 없을 때 새로 만들지 않고 null을 반환함
        HttpSession session = req.getSession(false);

        // 세션이 존재하지 않을 경우 즉시 메시지를 반환하고 종료
        if (session == null) {
            return "No active session";
        }

        // 세션 디버그 정보 제목 추가
        debug.append("Session Debug Information:\n");
        debug.append("--------------------------------\n");

        // 세션 ID 출력
        debug.append("Session ID: ").append(session.getId()).append("\n");
        // 세션 생성 시간 출력 (Date 객체로 보기 쉽게 변환)
        debug.append("Creation Time: ").append(new java.util.Date(session.getCreationTime())).append("\n");
        // 세션의 마지막 접근 시간 출력
        debug.append("Last Accessed Time: ").append(new java.util.Date(session.getLastAccessedTime())).append("\n");
        // 세션의 최대 유효 시간(초 단위) 출력
        debug.append("Max Inactive Interval: ").append(session.getMaxInactiveInterval()).append(" seconds\n");
        // 세션이 새로 생성된 것인지 여부 출력
        debug.append("Is New: ").append(session.isNew()).append("\n");
        // 세션에 저장된 속성(Attribute)들을 나열하기 위한 구분선
        debug.append("\nSession Attributes:\n");
        // 세션에 저장된 모든 속성 이름을 가져옴
        Enumeration<String> attributeNames = session.getAttributeNames();

        // 만약 세션 속성이 하나도 없으면 표시
        if (!attributeNames.hasMoreElements()) {
            debug.append("  (no attributes)\n");
        } else {
            // 속성이 하나 이상 있을 때, 모든 속성 이름과 값을 출력
            while (attributeNames.hasMoreElements()) {
                String name = attributeNames.nextElement();       // 속성 이름
                Object value = session.getAttribute(name);        // 속성 값
                debug.append("  ").append(name).append(" = ").append(value).append("\n");
            }
        }

        // 출력 구분선 마무리
        debug.append("--------------------------------\n");
        // 완성된 문자열 반환
        return debug.toString();
    }

    public boolean isLogined() {
        long loginedMemberId = getSessionAsLong("loginedMemberId", 0);
        return loginedMemberId > 0;
    }

    public boolean isLogout() {
        return !isLogined();
    }

    public long getLoginedMember() {
        return getSessionAsLong("loginedMemberId", 0);
    }
}
