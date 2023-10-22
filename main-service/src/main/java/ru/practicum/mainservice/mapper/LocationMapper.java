package ru.practicum.mainservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.practicum.mainservice.dto.location.LocationDto;
import ru.practicum.mainservice.model.Location;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    Location toModel(LocationDto locationDto);

    LocationDto toDto(Location location);

}