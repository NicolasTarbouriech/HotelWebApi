package dev.hotel.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.hotel.dto.CreerReservationRequestDto;
import dev.hotel.entite.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, UUID>{

	

}
