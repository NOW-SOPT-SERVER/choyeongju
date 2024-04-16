package org.sopt.week_2_remind.service;

import org.sopt.week_2_remind.domain.Member;
import org.sopt.week_2_remind.domain.Part;

public record MemberFindDto(String name, Part part, int age) {
    /*
       이 메서드는 Member 객체를 인자로 받아서
       해당 객체의 정보를 사용하여 MemberFindDto 인스턴스를 생성합
        */
    public static MemberFindDto of(Member member){
        return new MemberFindDto(member.getName(), member.getPart(), member.getAge());
    }
}
