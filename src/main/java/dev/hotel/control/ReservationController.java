package dev.hotel.control;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.dto.CreerClientRequestDto;
import dev.hotel.dto.CreerClientResponseDto;
import dev.hotel.dto.CreerReservationRequestDto;
import dev.hotel.dto.CreerReservationResponseDto;
import dev.hotel.entite.Reservation;
import dev.hotel.exception.HotelException;
import dev.hotel.service.ReservationService;

@RestController
@RequestMapping("reservations")
public class ReservationController {

	private ReservationService resServ;

	/**
	 * @param resR
	 */
	public ReservationController(ReservationService resServ) {
		this.resServ = resServ;
	}

	@PostMapping
	public ResponseEntity<?> reservations(@RequestBody @Valid CreerReservationRequestDto res, BindingResult resValid) {

		if (!resValid.hasErrors()) {
			Reservation reservationCree = resServ.creerReservation(res.getDateDebut(), res.getDateFin(), res.getClientId(),
					res.getChambres());
			CreerReservationResponseDto reservationResponse = new CreerReservationResponseDto(reservationCree);

			return ResponseEntity.ok(reservationResponse);
		} else {
			return ResponseEntity.badRequest().body(" Tous les champs sont obligatoires !");
		}

	}

	@ExceptionHandler(HotelException.class)
	public ResponseEntity<List<String>> onHotelException(HotelException ex) {
		return ResponseEntity.badRequest().body(ex.getMessagesErreurs());
	}

}
