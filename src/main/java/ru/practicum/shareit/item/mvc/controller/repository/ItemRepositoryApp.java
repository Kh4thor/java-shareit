package ru.practicum.shareit.item.mvc.controller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.practicum.shareit.item.mvc.model.Item;

public interface ItemRepositoryApp extends JpaRepository<Item, Long> {

    String searchItemByTextSql = ""
    		+ "SELECT * "
    		+ "FROM items i "
			+ "WHERE i.item_available = true AND i.user_id = :ownerId "
			+ "AND (LOWER(i.item_name) LIKE LOWER(CONCAT('%', :text, '%')) "
			+ "OR LOWER(i.item_description) LIKE LOWER(CONCAT('%', :text, '%')))";

    String isItemExistsSql = ""
            + "SELECT EXISTS (SELECT 1 "
				            + "FROM items "
				            + "WHERE id = :itemId)";

	@Query(value = searchItemByTextSql, nativeQuery = true)
    List<Item> searchItemByText(@Param("ownerId") Long ownerId, @Param("text") String text);

    @Query(value = isItemExistsSql, nativeQuery = true)
    Boolean isItemExists(@Param("itemId") Long itemId);
}