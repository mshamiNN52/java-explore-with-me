package java.ru.practicum.mainservice.dto.event;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.mainservice.dto.event.NewEventDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class NewEventDtoJsonTest {

    @Autowired
    private JacksonTester<NewEventDto> jsonNewEventDto;

    @Test
    @SneakyThrows
    void newEventDtoTest() {
        NewEventDto newEventDto = NewEventDto.builder()
                .eventDate(LocalDateTime.now())
                .build();

        JsonContent<NewEventDto> result = jsonNewEventDto.write(newEventDto);

        assertThat(result).hasJsonPath("$.eventDate");
    }

}