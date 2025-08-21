package ru.practicum.shareit.item.utills;

import ru.practicum.shareit.item.dto.CreateCommentDto;
import ru.practicum.shareit.item.dto.ResponseCommentDto;
import ru.practicum.shareit.item.mvc.model.Comment;

public class CommentMapper {

	public static Comment createCommentDtoToComment(CreateCommentDto createCommentDto) {
		return Comment.builder()
				.text(createCommentDto.getText())
				.build();
	}

	public static ResponseCommentDto commentToResponseCommentDto(Comment comment) {
		return ResponseCommentDto.builder()
				.id(comment.getId())
				.itemId(comment.getItem().getId())
				.authorId(comment.getCommentator().getId())
				.authorName(comment.getCommentator().getName())
				.text(comment.getText())
				.created(comment.getCreated())
				.build();
	}
}