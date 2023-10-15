package ru.practicum.statservice.mapper;

import org.junit.jupiter.api.Test;
import ru.practicum.statdto.EndpointHitDto;
import ru.practicum.statservice.model.EndpointHit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

class HitMapperTest {

    @Test
    void whenToHitModel() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(dateTimeFormatter);
        LocalDateTime localDateTime = LocalDateTime.parse(timestamp, dateTimeFormatter);
        EndpointHitDto endpointHitDto = EndpointHitDto.builder()
                .uri("uri")
                .app("myApp")
                .timestamp(timestamp)
                .ip("ip")
                .build();
        EndpointHit expected = EndpointHit.builder()
                .uri("uri")
                .created(localDateTime)
                .ip("ip")
                .app("myApp")
                .build();

        EndpointHit actual = HitMapper.toHitModel(endpointHitDto);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

}