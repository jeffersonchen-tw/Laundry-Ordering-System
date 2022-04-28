package com.jefferson.laundryorderingsystem.entities.reservation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    ReservationRepo repo;

    private class ReservationResponse {
    	int machineId;
    	String response;
    }

	public List<Reservation> getReservationsOfSpecificTime(LocalDateTime time) {
		return repo.findAllByTime(time);
	}

	public int getMachineNum(LocalDateTime time) {
		List<Reservation> occupiedTReservations = getReservationsOfSpecificTime(time);
	}

}