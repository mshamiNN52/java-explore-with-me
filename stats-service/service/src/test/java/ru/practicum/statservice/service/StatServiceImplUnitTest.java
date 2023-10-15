package ru.practicum.statservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.statdto.EndpointHitDto;
import ru.practicum.statdto.ViewStatsDto;
import ru.practicum.statservice.model.EndpointHit;
import ru.practicum.statservice.model.StatForRequest;
import ru.practicum.statservice.repository.StatRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatServiceImplUnitTest {
    @Mock
    private StatRepository statRepository;

    @InjectMocks
    private StatServiceImpl statService;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private ViewStatsDto viewStatsDto1;
    private ViewStatsDto viewStatsDto2;

    @BeforeEach
    void init() {
        viewStatsDto1 = ViewStatsDto.builder()
                .uri("uri")
                .app("myApp")
                .hits(2L)
                .build();
        viewStatsDto2 = ViewStatsDto.builder()
                .uri("uri1")
                .app("myApp1")
                .hits(2L)
                .build();
    }

    @Test
    void whenAddHit() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String timestamp = localDateTime.format(dateTimeFormatter);
        EndpointHitDto endpointHitDto = EndpointHitDto.builder()
                .uri("uri")
                .app("myApp")
                .timestamp(timestamp)
                .ip("ip")
                .build();
        EndpointHit endpointHit = EndpointHit.builder()
                .id(1L)
                .uri("uri")
                .ip("ip")
                .created(localDateTime)
                .app("myApp")
                .build();
        when(statRepository.save(any())).thenReturn(endpointHit);

        statService.addHit(endpointHitDto);

        verify(statRepository, times(1)).save(any());
    }

    @Test
    void whenGetStatsByUniqueStatForRequestIsTrueAndUrisIsEmpty() {
        StatForRequest statForRequest = StatForRequest.builder()
                .uris(new ArrayList<>())
                .unique(true)
                .end(LocalDateTime.now().plusHours(1))
                .start(LocalDateTime.now())
                .build();
        List<ViewStatsDto> expected = List.of(viewStatsDto1, viewStatsDto2);

        when(statRepository.getAllStatsByDistinctIp(any(), any())).thenReturn(expected);

        List<ViewStatsDto> actual = statService.getStats(statForRequest);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void whenGetStatsByUniqueStatForRequestIsTrueAndUrisIsNotEmpty() {
        StatForRequest statForRequest = StatForRequest.builder()
                .uris(List.of("uri"))
                .unique(true)
                .end(LocalDateTime.now().plusHours(1))
                .start(LocalDateTime.now())
                .build();
        List<ViewStatsDto> expected = List.of(viewStatsDto1, viewStatsDto2);

        when(statRepository.getAllStatsInUrisByDistinctIp(any(), any(), any())).thenReturn(expected);

        List<ViewStatsDto> actual = statService.getStats(statForRequest);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void whenGetStatsByUniqueStatForRequestIsFalseAndUrisIsEmpty() {
        StatForRequest statForRequest = StatForRequest.builder()
                .uris(new ArrayList<>())
                .unique(false)
                .end(LocalDateTime.now().plusHours(1))
                .start(LocalDateTime.now())
                .build();
        List<ViewStatsDto> expected = List.of(viewStatsDto1, viewStatsDto2);

        when(statRepository.getAllStats(any(), any())).thenReturn(expected);

        List<ViewStatsDto> actual = statService.getStats(statForRequest);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void whenGetStatsByUniqueStatForRequestIsFalseAndUrisIsNotEmpty() {
        StatForRequest statForRequest = StatForRequest.builder()
                .uris(List.of("uri"))
                .unique(false)
                .end(LocalDateTime.now().plusHours(1))
                .start(LocalDateTime.now())
                .build();
        List<ViewStatsDto> expected = List.of(viewStatsDto1, viewStatsDto2);

        when(statRepository.getAllStatsInUris(any(), any(), any())).thenReturn(expected);

        List<ViewStatsDto> actual = statService.getStats(statForRequest);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

}