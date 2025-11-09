package com.khoubyari.example.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khoubyari.example.api.rest.HotelController;
import com.khoubyari.example.domain.Hotel;
import com.khoubyari.example.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelController.class)
@AutoConfigureMockMvc(addFilters = false)
public class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @Autowired
    private ObjectMapper objectMapper;

    private Hotel sampleHotel;

    @BeforeEach
    void setUp() {
        sampleHotel = new Hotel();
        sampleHotel.setId(1L);
        sampleHotel.setName("Test Hotel");
        sampleHotel.setCity("Test City");
        sampleHotel.setDescription("Nice hotel");
        sampleHotel.setRating(5);
    }

    @Test
    void testCreateHotel() throws Exception {
        Mockito.when(hotelService.createHotel(any(Hotel.class))).thenReturn(sampleHotel);

        mockMvc.perform(post("/example/v1/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleHotel)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetAllHotels() throws Exception {
        Page<Hotel> page = new PageImpl<>(List.of(sampleHotel));
        Mockito.when(hotelService.getAllHotels(eq(0), eq(10))).thenReturn(page);

        mockMvc.perform(get("/example/v1/hotels?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Test Hotel"));
    }

    @Test
    void testGetHotelById() throws Exception {
        Mockito.when(hotelService.getHotel(1L)).thenReturn(sampleHotel);

        mockMvc.perform(get("/example/v1/hotels/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Hotel"));
    }

    @Test
    void testUpdateHotel() throws Exception {
        Mockito.when(hotelService.getHotel(1L)).thenReturn(sampleHotel);

        mockMvc.perform(put("/example/v1/hotels/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleHotel)))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteHotel() throws Exception {
        Mockito.when(hotelService.getHotel(1L)).thenReturn(sampleHotel);

        mockMvc.perform(delete("/example/v1/hotels/1"))
                .andExpect(status().isNoContent());
    }
}
