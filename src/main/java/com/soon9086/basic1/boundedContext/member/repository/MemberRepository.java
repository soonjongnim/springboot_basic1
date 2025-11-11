package com.soon9086.basic1.boundedContext.member.repository;

import com.soon9086.basic1.boundedContext.member.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {
    private List<Member> members;

    public MemberRepository() {
        members = new ArrayList<>();

        // 초기 데이터 생성
        members.add(new Member("user1", "1234"));
        members.add(new Member("user2", "4567"));
        members.add(new Member("user3", "123456"));
        members.add(new Member("admin1", "admin123"));
        members.add(new Member("test1", "test123"));
        members.add(new Member("hong", "gildong"));
        members.add(new Member("kim", "korea123"));
        members.add(new Member("lee", "spring123"));
        members.add(new Member("park", "java456"));
        members.add(new Member("choi", "pass789"));
    }

    public Member findByUsername(String username) {
        return members.stream()
                .filter(member -> member.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public Member fineById(long id) {
        return members.stream()
                .filter(member -> member.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
