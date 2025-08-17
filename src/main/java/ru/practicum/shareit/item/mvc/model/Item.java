package ru.practicum.shareit.item.mvc.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.mvc.model.User;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "items")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Long id;

	@Column(name = "item_name")
	private String name;

	@Column(name = "item_description")
	private String description;

	@Column(name = "item_available")
	private Boolean available;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User owner;

	@OneToOne
	@JoinColumn(name = "request_id")
	private ItemRequest itemRequest;

	@OneToMany(mappedBy = "item")
	private List<Comment> coments;

	@Override
	public String toString() {
		return "Item ["
				+ "id=" + id 
				+ ", name=" + name 
				+ ", description=" + description 
				+ ", available=" + available
				+ ", ownerId=" + owner.getId() 
				+ ", itemRequest=" + itemRequest 
				+ "]";
	}

}
