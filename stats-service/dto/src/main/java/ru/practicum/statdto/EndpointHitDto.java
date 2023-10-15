package ru.practicum.statdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EndpointHitDto {
    @NotBlank(message = "App не может быть пустым или null")
    private String app;
    @NotBlank(message = "Uri не может быть пустым или null")
    private String uri;
    @NotBlank(message = "Ip не может быть пустым или null")
    private String ip;
    @NotBlank(message = "Timestamp не может быть пустым или null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String timestamp;
}
