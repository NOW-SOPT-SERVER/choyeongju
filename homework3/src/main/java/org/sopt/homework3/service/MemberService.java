package org.sopt.homework3.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.homework3.common.dto.ErrorMessage;
import org.sopt.homework3.domain.Member;
import org.sopt.homework3.exception.NotFoundException;
import org.sopt.homework3.repository.MemberRepository;
import org.sopt.homework3.service.dto.MemberCreateDto;
import org.sopt.homework3.service.dto.MemberFindDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService { //Service 에는 비즈니스 로직을 구현한다.

    private final MemberRepository memberRepository;

    @Transactional
    public String createMember(
            MemberCreateDto memberCreate
    ) { //포스트맨에서 POST로 멤버 생성 가능
        Member member = memberRepository.save(Member.create(memberCreate.name(), memberCreate.part(), memberCreate.age()));
        return member.getId().toString();
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.MEMBER_NOT_FOUND)
        );
    }

    public MemberFindDto findMemberById(Long memberId){ //ID를 기준으로 Member 찾기
        Member member = memberRepository.findById(memberId).orElseThrow(
                ()->new EntityNotFoundException("ID에 해당하는 사용자가 존재하지 않습니다."));
        return MemberFindDto.of(member);
    }

    @Transactional
    public void deleteMemberById(Long memberId){ //ID를 기준으로 Member 를 삭제
        Member member=memberRepository.findById(memberId).orElseThrow(
                ()->new EntityNotFoundException("ID에 해당하는 사용자가 존재하지 않습니다."));
        memberRepository.delete(member);
    }
}
