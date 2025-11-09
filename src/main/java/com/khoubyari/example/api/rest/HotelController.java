package com.khoubyari.example.api.rest;

import com.khoubyari.example.domain.Hotel;
import com.khoubyari.example.exception.DataFormatException;
import com.khoubyari.example.service.HotelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/example/v1/hotels")
@Tag(name = "Hotels", description = "Hotel management operations")
public class HotelController extends AbstractRestHandler {

    @Autowired
    private HotelService hotelService;

    @PostMapping(consumes = {"application/json", "application/xml"},
                 produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Create a hotel resource",
        description = "Returns the URL of the new resource in the Location header."
    )
    public void createHotel(
            @RequestBody Hotel hotel,
            HttpServletRequest request,
            HttpServletResponse response) {
        Hotel createdHotel = this.hotelService.createHotel(hotel);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdHotel.getId()).toString());
    }

    @GetMapping(produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get all hotels (paginated)",
        description = "Provide a page number (default 0) and page size (default 100)."
    )
    public Page<Hotel> getAllHotel(
            @Parameter(description = "The page number (zero-based)", required = true)
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUM) Integer page,
            @Parameter(description = "The page size", required = true)
            @RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE) Integer size) {
        return this.hotelService.getAllHotels(page, size);
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a single hotel", description = "Provide a valid hotel ID.")
    public Hotel getHotel(
            @Parameter(description = "The ID of the hotel.", required = true)
            @PathVariable("id") Long id) throws Exception {
        Hotel hotel = this.hotelService.getHotel(id);
        checkResourceFound(hotel);
        return hotel;
    }

    @PutMapping(value = "/{id}", consumes = {"application/json", "application/xml"},
                produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Update a hotel resource",
        description = "Provide a valid hotel ID in the URL and payload. The ID cannot be updated."
    )
    public void updateHotel(
            @Parameter(description = "The ID of the existing hotel resource.", required = true)
            @PathVariable("id") Long id,
            @RequestBody Hotel hotel) {
        checkResourceFound(this.hotelService.getHotel(id));
        if (!id.equals(hotel.getId())) throw new DataFormatException("ID doesn't match!");
        this.hotelService.updateHotel(hotel);
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete a hotel resource",
        description = "Provide a valid hotel ID in the URL. Once deleted, the resource cannot be recovered."
    )
    public void deleteHotel(
            @Parameter(description = "The ID of the existing hotel resource.", required = true)
            @PathVariable("id") Long id) {
        checkResourceFound(this.hotelService.getHotel(id));
        this.hotelService.deleteHotel(id);
    }
}