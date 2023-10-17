package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ConstructorBinding
@Builder
public class EndpointHitDto {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String timestamp;

    public EndpointHitDto(String app, String uri, String ip, String formatTimeToString) {
        this.app = app;
        this.uri = uri;
        this.ip = ip;
        this.timestamp = formatTimeToString;
    }
}
