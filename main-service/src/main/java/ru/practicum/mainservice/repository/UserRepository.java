package ru.practicum.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
