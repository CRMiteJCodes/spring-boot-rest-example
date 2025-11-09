package com.khoubyari.example.service;

import com.khoubyari.example.domain.Hotel;
import com.khoubyari.example.dao.jpa.HotelRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final Counter largePayloadCounter;

    public HotelService(HotelRepository hotelRepository, MeterRegistry meterRegistry) {
        this.hotelRepository = hotelRepository;
        this.largePayloadCounter = meterRegistry.counter("Khoubyari.HotelService.getAll.largePayload");
    }

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel getHotel(long id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        return hotel.orElse(null);
    }

    public void updateHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    //http://goo.gl/7fxvVf
    public Page<Hotel> getAllHotels(Integer page, Integer size) {
        Page<Hotel> pageOfHotels = hotelRepository.findAll(PageRequest.of(page, size));
        // example of adding to the /metrics
        if (size > 50) {
            largePayloadCounter.increment();
        }

        return pageOfHotels;
    }
}
