package org.sopt.week_2_remind.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.week_2_remind.service.MemberCreateDto;
import org.sopt.week_2_remind.service.MemberFindDto;
import org.sopt.week_2_remind.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member") //어디로 요청이 들어올거냐?를 보는 것.
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity postMember(@RequestBody MemberCreateDto memberCreate){
        return ResponseEntity.created(URI.create(memberService.createMember(memberCreate))).build();
        //createMember가 MemberService의 함수이다.
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberFindDto> findMemberById(@PathVariable Long memberId){
        return ResponseEntity.ok(memberService.findMemberById(memberId));
        //findMemberById가 MemberService의 함수이다.
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity deleteMember(@PathVariable Long memberId){
        memberService.deleteMemberById(memberId);  //deleteMemberById가 MemberService의 함수이다.
        return ResponseEntity.noContent().build();
          /*
        클라이언트에게 반환할 데이터가 없으므로, HTTP 상태 코드 204(No Content)를 함께 응답한다.
        이는 요청이 성공적으로 처리되었지만 응답으로 반환할 데이터가 없음을 나타낸다.
         */
    }

    //기존 세미나 코드에서 이 부분 추가
    @GetMapping("/memberList")
    public ResponseEntity<List<MemberFindDto>> findAllMembers() {
        try {
            List<MemberFindDto> members = memberService.findAllMembers();
            return ResponseEntity.ok(members);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
