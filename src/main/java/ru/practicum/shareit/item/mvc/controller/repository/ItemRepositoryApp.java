package ru.practicum.shareit.item.mvc.controller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.practicum.shareit.item.mvc.model.Item;

public interface ItemRepositoryApp extends JpaRepository<Item, Long> {

    String searchItemByTextSql = ""
			+ "SELECT i.* "
			+ "FROM items i "
			+ "WHERE (LOWER(i.item_name) LIKE LOWER(:text) OR LOWER(i.item_description) LIKE LOWER(:text)) "
			+ "AND i.owner_id = :ownerId "
			+ "AND i.available = true";

    String isItemExistsSql = ""
            + "SELECT EXISTS (SELECT 1 "
				            + "FROM items "
				            + "WHERE id = :itemId)";

    @Query(value = searchItemByTextSql, nativeQuery = true)
    List<Item> searchItemByText(@Param("ownerId") Long ownerId, @Param("text") String text);

    @Query(value = isItemExistsSql, nativeQuery = true)
    Boolean isItemExists(@Param("itemId") Long itemId);
}