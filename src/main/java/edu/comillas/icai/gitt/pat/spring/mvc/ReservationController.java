package edu.comillas.icai.gitt.pat.spring.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pistaPadel")
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);

    // BASE DE DATOS SIMULADA
    private final List<Reserva> reservas = new ArrayList<>();

    public ReservationController() {
        // Metemos una reserva de prueba automáticamente para poder probar el código
        reservas.add(new Reserva(
                "reserva-1", "user-1", "pista-1",
                LocalDate.now(), LocalTime.of(10, 0), 90, LocalTime.of(11, 30),
                EstadoReserva.ACTIVA, LocalDateTime.now()
        ));
    }

    // 1. GET: Listar reservas
    @GetMapping("/reservations")
    public ResponseEntity<List<Reserva>> getReservations(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        log.info("Petición GET recibida para listar reservas con filtros from: {}, to: {}", from, to);
        return ResponseEntity.ok(reservas);
    }

    // 2. GET: Ver una reserva concreta
    @GetMapping("/reservations/{reservationId}")
    public ResponseEntity<Reserva> getReservation(@PathVariable String reservationId) {
        log.info("Petición GET recibida para consultar la reserva ID: {}", reservationId);

        for (Reserva r : reservas) {
            if (r.idReserva().equals(reservationId)) {
                return ResponseEntity.ok(r); // Si la encuentra, devuelve 200 OK
            }
        }
        log.warn("Reserva {} no encontrada", reservationId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
    }

    // 3. DELETE: Cancelar reserva
    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable String reservationId) {
        log.info("Petición DELETE recibida para cancelar la reserva ID: {}", reservationId);

        // Simulamos la regla de negocio: si es "reserva-intocable", da conflicto
        if ("reserva-intocable".equals(reservationId)) {
            log.error("Conflicto: No se puede cancelar la reserva por política de empresa");
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        }

        boolean borrado = reservas.removeIf(r -> r.idReserva().equals(reservationId));
        if (borrado) {
            return ResponseEntity.noContent().build(); // 204 No Content (Borrado con éxito)
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

    // 4. PATCH: Modificar reserva
    @PatchMapping("/reservations/{reservationId}")
    public ResponseEntity<Reserva> updateReservation(@PathVariable String reservationId) {
        log.info("Petición PATCH recibida para reprogramar la reserva ID: {}", reservationId);

        // Simulamos la regla de negocio: si el slot está ocupado, da conflicto
        if ("reserva-ocupada".equals(reservationId)) {
            log.error("Conflicto: El nuevo horario ya está ocupado");
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        }

        for (Reserva r : reservas) {
            if (r.idReserva().equals(reservationId)) {
                return ResponseEntity.ok(r); // 200 OK
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
    }
}