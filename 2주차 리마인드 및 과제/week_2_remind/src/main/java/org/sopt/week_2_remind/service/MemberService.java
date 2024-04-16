package org.sopt.week_2_remind.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.sopt.week_2_remind.domain.Member;
import org.sopt.week_2_remind.repository.MemberRepository;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService { //Service 에는 비즈니스 로직을 구현한다.

    private final MemberRepository memberRepository;

    @Transactional
    public String createMember(MemberCreateDto memberCreate){ //포스트맨에서 POST로 멤버 생성 가능
        Member member = memberRepository.save(Member.create(memberCreate.name(), memberCreate.part(), memberCreate.age()));
        return member.getId().toString();
    }

    public MemberFindDto findMemberById(Long memberId){ //ID를 기준으로 Member 찾기
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new EntityNotFoundException("ID에 해당하는 사용자가 존재하지 않습니다."));
        return MemberFindDto.of(member);
    }

    @Transactional
    public void deleteMemberById(Long memberId){ //ID를 기준으로 Member 를 삭제
        Member member = memberRepository.findById(memberId).orElseThrow(
                ()->new EntityNotFoundException("ID에 해당하는 사용자가 존재하지 않습니다."));
        memberRepository.delete(member);
    }

    //기존 세미나 코드에서 이 부분 추가
    public List<MemberFindDto> findAllMembers() {
        try {
            return memberRepository.findAll().stream()
                    .map(MemberFindDto::of)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
