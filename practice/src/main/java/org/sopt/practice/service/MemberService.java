package org.sopt.practice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.sopt.practice.auth.UserAuthentication;
import org.sopt.practice.auth.redis.domain.Token;
import org.sopt.practice.auth.redis.repository.RedisTokenRepository;
import org.sopt.practice.common.dto.ErrorMessage;
import org.sopt.practice.common.jwt.JwtTokenProvider;
import org.sopt.practice.exception.NotFoundException;
import org.sopt.practice.service.dto.MemberCreateDto;
import org.sopt.practice.domain.Member;
import org.sopt.practice.repository.MemberRepository;
import org.sopt.practice.service.dto.MemberFindDto;
import org.sopt.practice.service.dto.UserJoinResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MemberService { //Service 에는 비즈니스 로직을 구현한다.

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTokenRepository redisTokenRepository;

//    @Transactional
//    public String createMember(
//            MemberCreateDto memberCreate
//    ) { //포스트맨에서 POST로 멤버 생성 가능
//        Member member = memberRepository.save(Member.create(memberCreate.name(), memberCreate.part(), memberCreate.age()));
//        return member.getId().toString();
//    }

    // 5.18 세미나 부분
    @Transactional
    public UserJoinResponse createMember(
            MemberCreateDto memberCreate
    ) {
        Member member = memberRepository.save(
                Member.create(memberCreate.name(), memberCreate.part(), memberCreate.age())
        );
        Long memberId = member.getId();
        String accessToken = jwtTokenProvider.issueAccessToken( //사용자의 인증 정보를 이용하여 토큰을 발급
                UserAuthentication.createUserAuthentication(memberId)
        );
        String refreshToken = jwtTokenProvider.issueRefreshToken(
                UserAuthentication.createUserAuthentication(memberId)
        );
        redisTokenRepository.save(Token.of(memberId,refreshToken));
        return UserJoinResponse.of(accessToken, refreshToken, memberId.toString());
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
