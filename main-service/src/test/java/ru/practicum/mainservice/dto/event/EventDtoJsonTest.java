package java.ru.practicum.mainservice.dto.event;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.mainservice.dto.event.EventDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class EventDtoJsonTest {

    @Autowired
    private JacksonTester<EventDto> jsonEventDto;

    @Test
    @SneakyThrows
    void eventDtoTest() {
        EventDto eventDto = EventDto.builder()
                .eventDate(LocalDateTime.now())
                .createdOn(LocalDateTime.now().minusHours(1))
                .publishedOn(LocalDateTime.now().plusHours(2))
                .build();

        JsonContent<EventDto> result = jsonEventDto.write(eventDto);

        assertThat(result).hasJsonPath("$.eventDate");
        assertThat(result).hasJsonPath("$.createdOn");
        assertThat(result).hasJsonPath("$.publishedOn");
    }

}