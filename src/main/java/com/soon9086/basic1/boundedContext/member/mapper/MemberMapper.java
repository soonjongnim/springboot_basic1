package com.soon9086.basic1.boundedContext.member.mapper;

import com.soon9086.basic1.boundedContext.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    MemberDTO findById(Long id);
    MemberDTO findByProviderId(String providerId);
    int insertMember(MemberDTO member);
    MemberDTO findByUsername(String username);
    MemberDTO findByEmail(String email);
}
