package ru.practicum.shareit.item.mvc.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.practicum.shareit.item.mvc.model.Comment;

public interface CommentRepositoryApp extends JpaRepository<Comment, Long> {

}
