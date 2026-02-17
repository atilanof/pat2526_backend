package edu.comillas.icai.gitt.pat.spring.mvc;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public record Reserva(
        String idReserva,
        String idUsuario,
        String idPista,
        LocalDate fechaReserva,
        LocalTime horaInicio,
        Integer duracionMinutos,
        LocalTime horaFin,
        EstadoReserva estado,
        LocalDateTime fechaCreacion
) {}

enum EstadoReserva {
    ACTIVA,
    CANCELADA
}
