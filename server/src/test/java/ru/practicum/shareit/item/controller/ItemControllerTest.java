package ru.practicum.shareit.item.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static ru.practicum.shareit.constants.HeaderConstants.USER_ID_HEADER;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.item.dto.ItemBookingDto;

import ru.practicum.shareit.item.service.ItemService;

import java.util.Collections;
import java.util.List;

class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    void getItemShouldReturnItemBookingDto() throws Exception {
        Long itemId = 1L;
        ItemBookingDto itemDto = new ItemBookingDto();
        itemDto.setName("Item1");
        itemDto.setDescription("desc");
        itemDto.setAvailable(true);

        when(itemService.getItem(itemId)).thenReturn(itemDto);

        mockMvc.perform(get("/items/{id}", itemId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(itemDto.getName()))
                .andExpect(jsonPath("$.description").value(itemDto.getDescription()))
                .andExpect(jsonPath("$.available").value(itemDto.getAvailable()));

    }

    @Test
    void getAllItemShouldReturnListOfItemBookingDto() throws Exception {
        Long userId = 1L;
        ItemBookingDto itemDto = new ItemBookingDto();
        itemDto.setName("Item1");
        itemDto.setDescription("desc");
        itemDto.setAvailable(true);
        List<ItemBookingDto> itemDtos = Collections.singletonList(itemDto);

        when(itemService.getAll(userId)).thenReturn(itemDtos);

        mockMvc.perform(get("/items")
                        .header(USER_ID_HEADER, userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(itemDto.getName()))
                .andExpect(jsonPath("$[0].description").value(itemDto.getDescription()))
                .andExpect(jsonPath("$[0].available").value(itemDto.getAvailable()));
    }

}
