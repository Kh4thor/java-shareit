package ru.practicum.shareit.item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i " +
           "WHERE i.owner.id = :ownerId " +
           "AND i.available = true " +
           "AND (LOWER(i.name) LIKE LOWER(CONCAT('%', :text, '%')) " +
           "OR LOWER(i.description) LIKE LOWER(CONCAT('%', :text, '%')))")
    List<Item> findByOwnerIdAndText(@Param("ownerId") Long ownerId, @Param("text") String text);

    @Query("SELECT i FROM Item i " +
           "WHERE i.available = true " +
           "AND (LOWER(i.name) LIKE LOWER(CONCAT('%', :text, '%')) " +
           "OR LOWER(i.description) LIKE LOWER(CONCAT('%', :text, '%')))")
    List<Item> searchByText(@Param("text") String text);

    @Query("SELECT i FROM Item i " +
           "WHERE i.owner.id = :ownerId " +
           "ORDER BY i.id")
    List<Item> findByOwnerId(@Param("ownerId") Long ownerId);

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END " +
           "FROM Item i WHERE i.id = :itemId AND i.owner.id = :userId")
    boolean existsByIdAndOwnerId(@Param("itemId") Long itemId, @Param("userId") Long userId);

    @Query("SELECT i FROM Item i WHERE i.itemRequest.id = :itemRequestId")
    List<Item> findItemByRequestId(@Param("itemRequestId") Long itemRequestId);
}