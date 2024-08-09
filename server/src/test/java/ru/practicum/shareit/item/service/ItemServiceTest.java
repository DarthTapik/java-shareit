package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.ShareItServer;
import ru.practicum.shareit.item.dto.ItemBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;


@SpringBootTest(classes = {ShareItServer.class})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql")
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemServiceTests {
    private final UserService userService;
    private final ItemService itemService;

    @Test
    void getAllReturnOnlyUsersItems() {
        UserDto userDto1 = new UserDto();
        userDto1.setName("User1");
        userDto1.setEmail("email1@example.com");
        userDto1 = userService.addUser(userDto1);
        UserDto userDto2 = new UserDto();
        userDto2.setName("User2");
        userDto2.setEmail("email2@example.com");
        userDto2 = userService.addUser(userDto2);
        ItemDto itemDto1 = new ItemDto();
        itemDto1.setName("Item1");
        itemDto1.setDescription("desc");
        itemDto1.setAvailable(true);
        itemService.addItem(itemDto1, userDto1.getId());
        ItemDto itemDto2 = new ItemDto();
        itemDto2.setName("Item2");
        itemDto2.setDescription("desc");
        itemDto2.setAvailable(true);
        itemService.addItem(itemDto2, userDto1.getId());
        ItemDto itemDto3 = new ItemDto();
        itemDto3.setName("Item3");
        itemDto3.setDescription("desc");
        itemDto3.setAvailable(true);
        itemDto3 = itemService.addItem(itemDto3, userDto2.getId());
        Long item3id = itemDto3.getId();

        List<ItemBookingDto> itemDtoList = itemService.getAll(userDto1.getId());
        Assertions.assertEquals(2, itemDtoList.size());
        Assertions.assertTrue(itemDtoList.stream()
                .noneMatch(item -> item.getId().equals(item3id)));

    }

}