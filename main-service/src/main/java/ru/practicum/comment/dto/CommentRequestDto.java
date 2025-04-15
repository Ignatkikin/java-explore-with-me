package ru.practicum.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {

    @NotBlank(message = "пустой text")
    @Size(min = 2, max = 2000, message = "поле text должно содержать от 2 до 2000 символом")
    private String text;
}
