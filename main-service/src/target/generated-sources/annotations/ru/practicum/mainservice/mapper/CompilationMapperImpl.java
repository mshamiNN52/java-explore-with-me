package ru.practicum.mainservice.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import ru.practicum.mainservice.dto.compilation.CompilationDto;
import ru.practicum.mainservice.dto.compilation.CompilationUpdateDto;
import ru.practicum.mainservice.dto.compilation.NewCompilationDto;
import ru.practicum.mainservice.dto.event.EventShortDto;
import ru.practicum.mainservice.model.Compilation;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-15T16:17:38+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
public class CompilationMapperImpl implements CompilationMapper {

    @Override
    public Compilation toModel(NewCompilationDto newCompilationDto) {
        if ( newCompilationDto == null ) {
            return null;
        }

        Compilation compilation = new Compilation();

        compilation.setPinned( newCompilationDto.isPinned() );
        compilation.setTitle( newCompilationDto.getTitle() );

        return compilation;
    }

    @Override
    public CompilationDto toDto(Compilation compilation, List<EventShortDto> events) {
        if ( compilation == null && events == null ) {
            return null;
        }

        Long id = null;
        Boolean pinned = null;
        String title = null;
        if ( compilation != null ) {
            id = compilation.getId();
            pinned = compilation.isPinned();
            title = compilation.getTitle();
        }
        List<EventShortDto> events1 = null;
        List<EventShortDto> list = events;
        if ( list != null ) {
            events1 = new ArrayList<EventShortDto>( list );
        }

        CompilationDto compilationDto = new CompilationDto( id, pinned, title, events1 );

        return compilationDto;
    }

    @Override
    public Compilation forUpdate(CompilationUpdateDto updateCompilationDto, Compilation compilation) {
        if ( updateCompilationDto == null ) {
            return compilation;
        }

        if ( updateCompilationDto.getPinned() != null ) {
            compilation.setPinned( updateCompilationDto.getPinned() );
        }
        if ( updateCompilationDto.getTitle() != null ) {
            compilation.setTitle( updateCompilationDto.getTitle() );
        }

        return compilation;
    }
}
