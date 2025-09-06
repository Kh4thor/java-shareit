package ru.practicum.shareit.request;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.practicum.shareit.request.model.ItemRequest;

@Repository
public interface ItemRequestRepositoryApp extends JpaRepository<ItemRequest, Long> {

	@Query("select ir from ItemRequest ir where ir.requestor.id = :requestorId")
	List<ItemRequest> getAllItemRequestsOfOwner(@Param("requestorId") Long requestorId);
}
