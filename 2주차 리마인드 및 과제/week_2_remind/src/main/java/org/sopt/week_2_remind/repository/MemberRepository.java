package org.sopt.week_2_remind.repository;

import org.sopt.week_2_remind.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
      /*
    JPA에서 기본적으로 delete, get 이런거 제공해줌..
    DB에서 자체적으로 생성해주는 ID로 찾고, 지우고 이런거 하는듯
     */
}
