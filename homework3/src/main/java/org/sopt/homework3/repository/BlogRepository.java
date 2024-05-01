package org.sopt.homework3.repository;

import org.sopt.homework3.common.dto.ErrorMessage;
import org.sopt.homework3.domain.Blog;
import org.sopt.homework3.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
