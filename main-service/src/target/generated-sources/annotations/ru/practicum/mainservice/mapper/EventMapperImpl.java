package ru.practicum.mainservice.mapper;

import javax.annotation.processing.Generated;
import ru.practicum.mainservice.dto.category.CategoryDto;
import ru.practicum.mainservice.dto.event.EventDto;
import ru.practicum.mainservice.dto.event.EventShortDto;
import ru.practicum.mainservice.dto.event.EventUpdateRequestDto;
import ru.practicum.mainservice.dto.event.NewEventDto;
import ru.practicum.mainservice.dto.location.LocationDto;
import ru.practicum.mainservice.dto.user.UserShortDto;
import ru.practicum.mainservice.model.Category;
import ru.practicum.mainservice.model.Event;
import ru.practicum.mainservice.model.Location;
import ru.practicum.mainservice.model.User;
import ru.practicum.mainservice.model.enums.EventState;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-15T16:17:38+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
public class EventMapperImpl implements EventMapper {

    @Override
    public Event toModel(NewEventDto newEventDto, User user, Category category) {
        if ( newEventDto == null && user == null && category == null ) {
            return null;
        }

        Event.EventBuilder event = Event.builder();

        if ( newEventDto != null ) {
            event.annotation( newEventDto.getAnnotation() );
            event.description( newEventDto.getDescription() );
            event.eventDate( newEventDto.getEventDate() );
            event.location( locationDtoToLocation( newEventDto.getLocation() ) );
            if ( newEventDto.getPaid() != null ) {
                event.paid( newEventDto.getPaid() );
            }
            event.participantLimit( newEventDto.getParticipantLimit() );
            if ( newEventDto.getRequestModeration() != null ) {
                event.requestModeration( newEventDto.getRequestModeration() );
            }
            event.title( newEventDto.getTitle() );
        }
        event.initiator( user );
        event.category( category );

        return event.build();
    }

    @Override
    public EventDto toDto(Event event, Long confirmedRequests, Long views) {
        if ( event == null && confirmedRequests == null && views == null ) {
            return null;
        }

        EventDto.EventDtoBuilder eventDto = EventDto.builder();

        if ( event != null ) {
            eventDto.id( event.getId() );
            eventDto.annotation( event.getAnnotation() );
            eventDto.category( categoryToCategoryDto( event.getCategory() ) );
            eventDto.createdOn( event.getCreatedOn() );
            eventDto.description( event.getDescription() );
            eventDto.eventDate( event.getEventDate() );
            eventDto.initiator( userToUserShortDto( event.getInitiator() ) );
            eventDto.location( locationToLocationDto( event.getLocation() ) );
            eventDto.paid( event.isPaid() );
            eventDto.participantLimit( event.getParticipantLimit() );
            eventDto.publishedOn( event.getPublishedOn() );
            eventDto.requestModeration( event.isRequestModeration() );
            eventDto.state( event.getState() );
            eventDto.title( event.getTitle() );
        }
        eventDto.confirmedRequests( confirmedRequests );
        eventDto.views( views );

        return eventDto.build();
    }

    @Override
    public EventShortDto toShortDto(Event event, Long confirmedRequests, Long views) {
        if ( event == null && confirmedRequests == null && views == null ) {
            return null;
        }

        EventShortDto.EventShortDtoBuilder eventShortDto = EventShortDto.builder();

        if ( event != null ) {
            eventShortDto.id( event.getId() );
            eventShortDto.annotation( event.getAnnotation() );
            eventShortDto.category( categoryToCategoryDto( event.getCategory() ) );
            eventShortDto.eventDate( event.getEventDate() );
            eventShortDto.initiator( userToUserShortDto( event.getInitiator() ) );
            eventShortDto.paid( event.isPaid() );
            eventShortDto.title( event.getTitle() );
        }
        eventShortDto.confirmedRequests( confirmedRequests );
        eventShortDto.views( views );

        return eventShortDto.build();
    }

    @Override
    public Event forUpdate(EventUpdateRequestDto updateEventDto, Category newCategory, EventState newState, Event event) {
        if ( updateEventDto == null && newCategory == null && newState == null ) {
            return event;
        }

        if ( updateEventDto != null ) {
            if ( updateEventDto.getAnnotation() != null ) {
                event.setAnnotation( updateEventDto.getAnnotation() );
            }
            if ( updateEventDto.getDescription() != null ) {
                event.setDescription( updateEventDto.getDescription() );
            }
            if ( updateEventDto.getEventDate() != null ) {
                event.setEventDate( updateEventDto.getEventDate() );
            }
            if ( updateEventDto.getLocation() != null ) {
                if ( event.getLocation() == null ) {
                    event.setLocation( Location.builder().build() );
                }
                locationDtoToLocation1( updateEventDto.getLocation(), event.getLocation() );
            }
            if ( updateEventDto.getPaid() != null ) {
                event.setPaid( updateEventDto.getPaid() );
            }
            if ( updateEventDto.getParticipantLimit() != null ) {
                event.setParticipantLimit( updateEventDto.getParticipantLimit() );
            }
            if ( updateEventDto.getRequestModeration() != null ) {
                event.setRequestModeration( updateEventDto.getRequestModeration() );
            }
            if ( updateEventDto.getTitle() != null ) {
                event.setTitle( updateEventDto.getTitle() );
            }
        }
        if ( newCategory != null ) {
            event.setCategory( newCategory );
        }
        if ( newState != null ) {
            event.setState( newState );
        }

        return event;
    }

    protected Location locationDtoToLocation(LocationDto locationDto) {
        if ( locationDto == null ) {
            return null;
        }

        Location.LocationBuilder location = Location.builder();

        location.lat( locationDto.getLat() );
        location.lon( locationDto.getLon() );

        return location.build();
    }

    protected CategoryDto categoryToCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto.CategoryDtoBuilder categoryDto = CategoryDto.builder();

        categoryDto.id( category.getId() );
        categoryDto.name( category.getName() );

        return categoryDto.build();
    }

    protected UserShortDto userToUserShortDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserShortDto.UserShortDtoBuilder userShortDto = UserShortDto.builder();

        userShortDto.name( user.getName() );
        userShortDto.email( user.getEmail() );

        return userShortDto.build();
    }

    protected LocationDto locationToLocationDto(Location location) {
        if ( location == null ) {
            return null;
        }

        LocationDto.LocationDtoBuilder locationDto = LocationDto.builder();

        locationDto.lat( location.getLat() );
        locationDto.lon( location.getLon() );

        return locationDto.build();
    }

    protected void locationDtoToLocation1(LocationDto locationDto, Location mappingTarget) {
        if ( locationDto == null ) {
            return;
        }

        if ( locationDto.getLat() != null ) {
            mappingTarget.setLat( locationDto.getLat() );
        }
        if ( locationDto.getLon() != null ) {
            mappingTarget.setLon( locationDto.getLon() );
        }
    }
}
