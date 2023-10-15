package ru.practicum.mainservice.mapper;

import javax.annotation.processing.Generated;
import ru.practicum.mainservice.dto.location.LocationDto;
import ru.practicum.mainservice.model.Location;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-15T16:17:38+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
public class LocationMapperImpl implements LocationMapper {

    @Override
    public Location toModel(LocationDto locationDto) {
        if ( locationDto == null ) {
            return null;
        }

        Location.LocationBuilder location = Location.builder();

        location.lat( locationDto.getLat() );
        location.lon( locationDto.getLon() );

        return location.build();
    }

    @Override
    public LocationDto toDto(Location location) {
        if ( location == null ) {
            return null;
        }

        LocationDto.LocationDtoBuilder locationDto = LocationDto.builder();

        locationDto.lat( location.getLat() );
        locationDto.lon( location.getLon() );

        return locationDto.build();
    }
}
