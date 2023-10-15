package ru.practicum.mainservice.mapper;

import javax.annotation.processing.Generated;
import ru.practicum.mainservice.dto.user.NewUserDto;
import ru.practicum.mainservice.dto.user.UserDto;
import ru.practicum.mainservice.dto.user.UserShortDto;
import ru.practicum.mainservice.model.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-15T16:17:38+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserShortDto toShortDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserShortDto.UserShortDtoBuilder userShortDto = UserShortDto.builder();

        userShortDto.name( user.getName() );
        userShortDto.email( user.getEmail() );

        return userShortDto.build();
    }

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.id( user.getId() );
        userDto.email( user.getEmail() );
        userDto.name( user.getName() );

        return userDto.build();
    }

    @Override
    public User toModel(NewUserDto newUserRequestDto) {
        if ( newUserRequestDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.name( newUserRequestDto.getName() );
        user.email( newUserRequestDto.getEmail() );

        return user.build();
    }
}
