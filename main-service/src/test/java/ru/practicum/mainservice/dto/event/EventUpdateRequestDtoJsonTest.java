package java.ru.practicum.mainservice.dto.event;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.mainservice.dto.event.EventUpdateRequestDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class EventUpdateRequestDtoJsonTest {

    @Autowired
    private JacksonTester<EventUpdateRequestDto> jsonEventUpdateRequestDto;

    @Test
    @SneakyThrows
    void eventUpdateRequestDtoTest() {
        EventUpdateRequestDto eventUpdateRequestDto = EventUpdateRequestDto.builder()
                .eventDate(LocalDateTime.now())
                .build();

        JsonContent<EventUpdateRequestDto> result = jsonEventUpdateRequestDto.write(eventUpdateRequestDto);

        assertThat(result).hasJsonPath("$.eventDate");
    }

}