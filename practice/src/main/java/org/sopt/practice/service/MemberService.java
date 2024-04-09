package org.sopt.practice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.service.dto.MemberCreateDto;
import org.sopt.practice.domain.Member;
import org.sopt.practice.repository.MemberRepository;
import org.sopt.practice.service.dto.MemberFindDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public String createMember(
            MemberCreateDto memberCreate
    ) {
        Member member = memberRepository.save(Member.create(memberCreate.name(), memberCreate.part(), memberCreate.age()));
        return member.getId().toString();
    }

    public MemberFindDto findMemberById(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(
                ()->new EntityNotFoundException("ID에 해당하는 사용자가 존재하지 않습니다."));
        return MemberFindDto.of(member);
    }

    @org.springframework.transaction.annotation.Transactional
    public void deleteMemberById(Long memberId){
        Member member=memberRepository.findById(memberId).orElseThrow(
                ()->new EntityNotFoundException("ID에 해당하는 사용자가 존재하지 않습니다."));
        memberRepository.delete(member);
    }
}
