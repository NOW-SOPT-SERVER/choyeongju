package org.sopt.practice.service.dto;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.sopt.practice.domain.Part;

public record MemberCreateDto(
        String name,
        Part part,
        int age
) {
}

