package ru.practicum.shareit.item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query("select c from Comment c where c.item.id = :itemId")
	List<Comment> findCommentsByItemId(@Param("itemId") Long itemId);

	@Query ("select c from Comment c where c.item.id in :itemIdList")
	List<Comment> findByItemIn(@Param("itemIdList") List<Long> itemIdList);
}
