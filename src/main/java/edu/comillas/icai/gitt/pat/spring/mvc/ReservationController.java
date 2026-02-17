package edu.comillas.icai.gitt.pat.spring.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pistaPadel")
public class ReservationController {

    // Creamos el "Logger" para cumplir con el requisito de las trazas
    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);

    // 1. GET /pistaPadel/reservations
    @GetMapping("/reservations")
    public ResponseEntity<List<Reserva>> getReservations(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        log.info("Petición GET recibida para listar reservas con filtros from: {}, to: {}", from, to);
        List<Reserva> reservas = new ArrayList<>();
        return ResponseEntity.ok(reservas); // Devuelve 200 OK
    }

    // 2. GET /pistaPadel/reservations/{reservationId}
    @GetMapping("/reservations/{reservationId}")
    public ResponseEntity<Reserva> getReservation(@PathVariable String reservationId) {
        log.info("Petición GET recibida para consultar la reserva ID: {}", reservationId);
        // Simulamos devolver un 200 OK (más adelante pondremos la lógica real)
        return ResponseEntity.ok(null);
    }

    // 3. DELETE /pistaPadel/reservations/{reservationId}
    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable String reservationId) {
        log.info("Petición DELETE recibida para cancelar la reserva ID: {}", reservationId);
        // Devuelve un 204 No Content, que es el código de éxito esperado al borrar
        return ResponseEntity.noContent().build();
    }

    // 4. PATCH /pistaPadel/reservations/{reservationId}
    @PatchMapping("/reservations/{reservationId}")
    public ResponseEntity<Reserva> updateReservation(@PathVariable String reservationId) {
        log.info("Petición PATCH recibida para reprogramar la reserva ID: {}", reservationId);
        // Devuelve un 200 OK
        return ResponseEntity.ok(null);
    }
}