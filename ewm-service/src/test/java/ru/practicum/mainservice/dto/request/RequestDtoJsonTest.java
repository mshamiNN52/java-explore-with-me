package ru.practicum.mainservice.dto.request;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class RequestDtoJsonTest {

    @Autowired
    private JacksonTester<RequestDto> jsonRequestDto;

    @Test
    @SneakyThrows
    void requestDtoTest() {
        RequestDto requestDto = RequestDto.builder()
                .created(LocalDateTime.now())
                .build();

        JsonContent<RequestDto> result = jsonRequestDto.write(requestDto);

        assertThat(result).hasJsonPath("$.created");
    }

}