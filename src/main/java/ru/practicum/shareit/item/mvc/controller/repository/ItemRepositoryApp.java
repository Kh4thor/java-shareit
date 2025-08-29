package ru.practicum.shareit.item.mvc.controller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.practicum.shareit.item.mvc.model.Item;

public interface ItemRepositoryApp extends JpaRepository<Item, Long> {

	@Query(value = ""
			+ "SELECT i.* FROM items i "
			+ "WHERE i.user_id = :ownerId "
			+ "AND i.item_available = true "
			+ "AND (LOWER(i.item_name) LIKE LOWER(CONCAT('%', :text, '%')) "
			+ "OR LOWER(i.item_description) LIKE LOWER(CONCAT('%', :text, '%')))", nativeQuery = true)
	List<Item> findByOwnerIdAndText(@Param("ownerId") Long ownerId, @Param("text") String text);

    @Query(value = ""
			+ "SELECT i.* FROM items i "
			+ "WHERE i.item_available = true "
    		+ "AND (LOWER(i.item_name) LIKE LOWER(CONCAT('%', :text, '%')) "
    		+ "OR LOWER(i.item_description) LIKE LOWER(CONCAT('%', :text, '%')))",
           nativeQuery = true)
	List<Item> searchByText(@Param("text") String text);

	@Query(value = ""
			+ "SELECT i.* "
			+ "FROM items i "
			+ "WHERE i.user_id = :ownerId "
			+ "ORDER BY i.item_id",
           nativeQuery = true)
	List<Item> findByOwnerId(@Param("ownerId") Long ownerId);

	@Query(value = "SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END "
			+ "FROM items i WHERE i.item_id = :itemId AND i.user_id = :userId", nativeQuery = true)
    boolean existsByIdAndOwnerId(@Param("itemId") Long itemId, @Param("userId") Long userId);
}