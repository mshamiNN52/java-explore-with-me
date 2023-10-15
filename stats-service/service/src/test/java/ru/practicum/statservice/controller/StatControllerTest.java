package ru.practicum.statservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.statdto.EndpointHitDto;
import ru.practicum.statdto.ViewStatsDto;
import ru.practicum.statservice.model.StatForRequest;
import ru.practicum.statservice.service.StatService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatController.class)
class StatControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatService statService;

    @Test
    @SneakyThrows
    void whenAddHit() {
        EndpointHitDto endpointHitDto = EndpointHitDto.builder()
                .uri("uri")
                .ip("ip")
                .timestamp("yyyy-MM-dd HH:mm:ss")
                .app("myApp")
                .build();

        mockMvc.perform(post("/hit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(endpointHitDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    void whenGetStats() {
        ViewStatsDto statsDto1 = ViewStatsDto.builder()
                .uri("uri")
                .app("myApp")
                .hits(2L)
                .build();
        ViewStatsDto statsDto2 = ViewStatsDto.builder()
                .uri("uri1")
                .app("myApp1")
                .hits(2L)
                .build();
        StatForRequest statForRequest = StatForRequest.builder()
                .uris(List.of("uri"))
                .unique(true)
                .end(LocalDateTime.now().plusHours(1))
                .start(LocalDateTime.now())
                .build();

        when(statService.getStats(any())).thenReturn(List.of(statsDto1, statsDto2));

        mockMvc.perform(get("/stats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statForRequest)))
                .andExpect(status().isOk());
    }

}