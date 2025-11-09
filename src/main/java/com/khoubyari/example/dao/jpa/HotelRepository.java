package com.khoubyari.example.dao.jpa;

import com.khoubyari.example.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    // Custom finder method example — Spring Data JPA will generate the query automatically
    Hotel findHotelByCity(String city);
    
}