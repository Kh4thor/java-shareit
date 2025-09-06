package ru.practicum.shareit;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.Gson;

import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.ResponseUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShareItTests {

	private static final String PATH = "/users";
	@Autowired
	private MockMvc mockMvc;
	private Gson gson = new Gson();

	CreateUserDto createUserDto =	CreateUserDto.builder()
									.name("createName")
									.email("create@email.com")
									.build();

	ResponseUserDto responseCreateUserDto = ResponseUserDto.builder()
											.id(1L)
											.name("createName")
											.email("create@email.com")
											.build();

	UpdateUserDto updateUserDto =	UpdateUserDto.builder()
									.userId(1L)
									.name("updateUser")
									.email("update@email.com")
									.build();

	String createUserDtoJson = gson.toJson(createUserDto);
	String responseCreateUserDtoJson = gson.toJson(responseCreateUserDto);

	String updateUserDtoJson = gson.toJson(updateUserDto);

	@Test
	@Order(1)
	void createUserSuccessful() throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.post(PATH)
	            .contentType(MediaType.APPLICATION_JSON)
				.content(createUserDtoJson))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(responseCreateUserDtoJson));
	}

	@Test
	@Order(2)
	void getItemSuccessful() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/" + responseCreateUserDto.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(responseCreateUserDtoJson));

		System.out.println(updateUserDtoJson);
	}

//	@Test
//	@Order(3)
//	void updateUserSuccessful() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.patch(PATH + "/1")
//			    .contentType(MediaType.APPLICATION_JSON)
//				.content(updateUserDtoJson))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.content().json(updateUserDtoJson));
//	}
}
