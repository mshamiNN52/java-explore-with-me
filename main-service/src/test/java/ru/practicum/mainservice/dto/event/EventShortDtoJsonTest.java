package java.ru.practicum.mainservice.dto.event;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.mainservice.dto.event.EventShortDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class EventShortDtoJsonTest {

    @Autowired
    private JacksonTester<EventShortDto> jsonBookingEventShortDto;

    @Test
    @SneakyThrows
    void eventShortDtoTest() {
        EventShortDto eventShortDto = EventShortDto.builder()
                .eventDate(LocalDateTime.now())
                .build();

        JsonContent<EventShortDto> result = jsonBookingEventShortDto.write(eventShortDto);

        assertThat(result).hasJsonPath("$.eventDate");
    }

}