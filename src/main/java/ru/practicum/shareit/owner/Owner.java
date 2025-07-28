package ru.practicum.shareit.owner;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.user.model.User;

@Getter
@Setter
@ToString
public class Owner extends User {
	public Owner(Long id, String name, String email) {
		super(id, name, email);
	}
}
