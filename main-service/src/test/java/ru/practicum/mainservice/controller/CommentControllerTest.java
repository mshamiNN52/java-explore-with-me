package ru.practicum.mainservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.mainservice.dto.comment.CommentRequestDto;
import ru.practicum.mainservice.model.*;
import ru.practicum.mainservice.model.enums.EventState;
import ru.practicum.mainservice.repository.*;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class CommentControllerTest {

    private static final String COMMENT_ENDPOINT = "/users/{userId}/comments";
    private static final String COMMENT_ID_PATH = "/{commentId}";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    LocationRepository locationRepository;

    private User user1;
    private User user2;
    private Comment comment;
    private Category category;
    private Location location;
    private Event event;
    private CommentRequestDto commentRequestDto;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .email("test@mail.com")
                .name("username")
                .build();

        user2 = User.builder()
                .email("test2@mail.com")
                .name("username2")
                .build();

        category = Category.builder()
                .name("category")
                .build();

        location = Location.builder()
                .lat(12313.1231)
                .lon(412414.1231)
                .build();

        event = Event.builder()
                .eventDate(LocalDateTime.now().plusDays(1))
                .annotation("annotation")
                .category(category)
                .createdOn(LocalDateTime.now())
                .description("description")
                .paid(true)
                .location(location)
                .title("title")
                .initiator(user1)
                .participantLimit(2)
                .requestModeration(true)
                .publishedOn(LocalDateTime.now().plusHours(1))
                .state(EventState.PUBLISHED)
                .build();

        comment = Comment.builder()
                .text("My comment is pretty")
                .createdOn(LocalDateTime.now().minusDays(1))
                .event(event)
                .author(user1)
                .build();

        commentRequestDto = CommentRequestDto.builder()
                .text("My test comment so bad")
                .build();
    }

    @AfterEach
    void clear() {
        commentRepository.deleteAll();
        eventRepository.deleteAll();
        locationRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void whenAddCommentIsSuccess() {
        userRepository.save(user1);
        categoryRepository.save(category);
        locationRepository.save(location);
        eventRepository.save(event);

        String jsonRequestDto = objectMapper.writeValueAsString(commentRequestDto);

        mockMvc.perform(post(COMMENT_ENDPOINT, user1.getId())
                        .param("eventId", event.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestDto))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("My test comment so bad"))
                .andExpect(jsonPath("$.author").value(user1.getId()))
                .andExpect(jsonPath("$.event").value(event.getId()));
    }

    @Test
    @SneakyThrows
    void whenAddCommentByNotExistUserIsNotSuccess() {
        String jsonRequestDto = objectMapper.writeValueAsString(commentRequestDto);

        mockMvc.perform(post(COMMENT_ENDPOINT, 999)
                        .param("eventId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestDto))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Пользователь не найден"));
    }

    @Test
    @SneakyThrows
    void whenAddCommentByNotExistEventIsNotSuccess() {
        userRepository.save(user1);

        String jsonRequestDto = objectMapper.writeValueAsString(commentRequestDto);

        mockMvc.perform(post(COMMENT_ENDPOINT, user1.getId())
                        .param("eventId", "999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestDto))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Событие не найдено"));

    }

    @Test
    @SneakyThrows
    void whenAddCommentByNotPublishedEventIsNotSuccess() {
        event.setState(EventState.CANCELED);
        userRepository.save(user1);
        categoryRepository.save(category);
        locationRepository.save(location);
        eventRepository.save(event);

        String jsonRequestDto = objectMapper.writeValueAsString(commentRequestDto);

        mockMvc.perform(post(COMMENT_ENDPOINT, user1.getId())
                        .param("eventId", event.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestDto))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Событие еще не опубликовано"));
    }

    @Test
    @SneakyThrows
    void whenAddCommentByInvalidAuthorEventIsNotSuccess() {
        event.setInitiator(user2);
        userRepository.save(user1);
        userRepository.save(user2);
        categoryRepository.save(category);
        locationRepository.save(location);
        eventRepository.save(event);

        String jsonRequestDto = objectMapper.writeValueAsString(commentRequestDto);

        mockMvc.perform(post(COMMENT_ENDPOINT, user1.getId())
                        .param("eventId", event.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestDto))
                .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void whenAddCommentByUserCommentAlreadyExistIsNotSuccess() {
        userRepository.save(user1);
        categoryRepository.save(category);
        locationRepository.save(location);
        eventRepository.save(event);
        commentRepository.save(comment);

        String jsonRequestDto = objectMapper.writeValueAsString(commentRequestDto);

        mockMvc.perform(post(COMMENT_ENDPOINT, user1.getId())
                        .param("eventId", event.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestDto))
                .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void whenDeleteCommentByIdIsSuccess() {
        userRepository.save(user1);
        categoryRepository.save(category);
        locationRepository.save(location);
        eventRepository.save(event);
        commentRepository.save(comment);

        mockMvc.perform(delete(COMMENT_ENDPOINT + COMMENT_ID_PATH, user1.getId(), comment.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void whenDeleteCommentByInvalidCommentIdIsNotSuccess() {
        userRepository.save(user1);
        categoryRepository.save(category);
        locationRepository.save(location);
        eventRepository.save(event);
        commentRepository.save(comment);

        mockMvc.perform(delete(COMMENT_ENDPOINT + COMMENT_ID_PATH, user1.getId(), 999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Комментарий не найден"));
    }

    @Test
    @SneakyThrows
    void whenDeleteCommentByInvalidUserIdIsNotSuccess() {
        userRepository.save(user1);
        userRepository.save(user2);
        categoryRepository.save(category);
        locationRepository.save(location);
        eventRepository.save(event);
        comment.setAuthor(user2);
        commentRepository.save(comment);

        mockMvc.perform(delete(COMMENT_ENDPOINT + COMMENT_ID_PATH, user1.getId(), comment.getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void whenUpdateCommentIsSuccess() {
        userRepository.save(user1);
        categoryRepository.save(category);
        locationRepository.save(location);
        eventRepository.save(event);
        commentRepository.save(comment);

        String jsonRequestDto = objectMapper.writeValueAsString(commentRequestDto);

        mockMvc.perform(patch(COMMENT_ENDPOINT + COMMENT_ID_PATH, user1.getId(), comment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("My test comment so bad"))
                .andExpect(jsonPath("$.author").value(user1.getId()))
                .andExpect(jsonPath("$.event").value(event.getId()));
    }

    @Test
    @SneakyThrows
    void whenUpdateNotExistCommentIsNotSuccess() {
        userRepository.save(user1);
        categoryRepository.save(category);
        locationRepository.save(location);
        eventRepository.save(event);
        commentRepository.save(comment);

        String jsonRequestDto = objectMapper.writeValueAsString(commentRequestDto);

        mockMvc.perform(patch(COMMENT_ENDPOINT + COMMENT_ID_PATH, user1.getId(), 999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestDto))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Комментарий не найден"));
    }

    @Test
    @SneakyThrows
    void whenUpdateCommentByInvalidUserIdIsNotSuccess() {
        userRepository.save(user1);
        userRepository.save(user2);
        categoryRepository.save(category);
        locationRepository.save(location);
        eventRepository.save(event);
        commentRepository.save(comment);

        String jsonRequestDto = objectMapper.writeValueAsString(commentRequestDto);

        mockMvc.perform(patch(COMMENT_ENDPOINT + COMMENT_ID_PATH, user2.getId(), comment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestDto))
                .andExpect(status().isForbidden());
    }

}