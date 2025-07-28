package ru.practicum.shareit.booker.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.user.model.User;

@Getter
@Setter
@ToString
public class Booker extends User {
	public Booker(Long id, String name, String email) {
		super(id, name, email);
	}
}
