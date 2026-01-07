package co.devskills.springbootboilerplate.dto;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record TodoResponse(
        Long id,
        String name,
        String description,
        ActionStatus status,
        LocalDate createdAt
) {}