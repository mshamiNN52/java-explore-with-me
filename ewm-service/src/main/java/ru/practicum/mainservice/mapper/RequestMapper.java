package ru.practicum.mainservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.practicum.mainservice.dto.request.RequestDto;
import ru.practicum.mainservice.model.Request;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RequestMapper {

    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);

    @Mapping(target = "event", source = "request.event.id")
    @Mapping(target = "requester", source = "request.requester.id")
    RequestDto toDto(Request request);

}
