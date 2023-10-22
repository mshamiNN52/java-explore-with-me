package ru.practicum.statservice.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.statdto.EndpointHitDto;
import ru.practicum.statservice.model.EndpointHit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class HitMapper {
    public static EndpointHit toHitModel(EndpointHitDto endpointHitDto) {
        return new EndpointHit(
                null,
                endpointHitDto.getApp(),
                endpointHitDto.getUri(),
                endpointHitDto.getIp(),
                LocalDateTime.parse(endpointHitDto.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }
}
