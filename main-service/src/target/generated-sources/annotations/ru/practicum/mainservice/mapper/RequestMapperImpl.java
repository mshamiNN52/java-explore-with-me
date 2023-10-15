package ru.practicum.mainservice.mapper;

import javax.annotation.processing.Generated;
import ru.practicum.mainservice.dto.request.RequestDto;
import ru.practicum.mainservice.model.Event;
import ru.practicum.mainservice.model.Request;
import ru.practicum.mainservice.model.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-15T16:17:38+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
public class RequestMapperImpl implements RequestMapper {

    @Override
    public RequestDto toDto(Request request) {
        if ( request == null ) {
            return null;
        }

        RequestDto.RequestDtoBuilder requestDto = RequestDto.builder();

        requestDto.event( requestEventId( request ) );
        requestDto.requester( requestRequesterId( request ) );
        requestDto.id( request.getId() );
        requestDto.created( request.getCreated() );
        requestDto.status( request.getStatus() );

        return requestDto.build();
    }

    private Long requestEventId(Request request) {
        if ( request == null ) {
            return null;
        }
        Event event = request.getEvent();
        if ( event == null ) {
            return null;
        }
        Long id = event.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long requestRequesterId(Request request) {
        if ( request == null ) {
            return null;
        }
        User requester = request.getRequester();
        if ( requester == null ) {
            return null;
        }
        Long id = requester.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
