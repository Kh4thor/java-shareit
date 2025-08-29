package ru.practicum.shareit.item.mvc.controller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.practicum.shareit.item.mvc.model.Comment;

public interface CommentRepositoryApp extends JpaRepository<Comment, Long> {

	@Query(value = ""
			+ "SELECT c.* "
			+ "FROM comments c "
			+ "WHERE c.user_id = :userId", nativeQuery = true)
	List<Comment> findCommentsByUserId(@Param("userId") Long userId);

	@Query(value = ""
			+ "SELECT c.* "
			+ "FROM comments c "
			+ "WHERE c.item_id = :itemId", nativeQuery = true)
	List<Comment> findCommentsByItemId(Long itemId);

	@Query(value = ""
			+ "SELECT c.* "
			+ "FROM comments c "
			+ "WHERE c.item_id IN :itemIdList", nativeQuery = true)
	List<Comment> findByItemIn(@Param("itemIdList") List<Long> itemIdList);
}
