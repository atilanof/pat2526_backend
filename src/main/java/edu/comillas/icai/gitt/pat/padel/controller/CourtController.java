package edu.comillas.icai.gitt.pat.padel.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import edu.comillas.icai.gitt.pat.padel.BaseController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/courts")
public class CourtController extends BaseController {
    private final Map<Long, String> pistas = new ConcurrentHashMap<Long, String>() {
    };

    //metemos el get /pistaPadel/availability
    @GetMapping("/availability") //veo la disponibilidad de la pista
    public String disponibilidadTotal(@RequestParam(required = false) Long courtId,  //false -> implica no obligatorio
                                      @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE) LocalDate date) { //para ahorrar métodos de comprobación de fecha
        if (courtId == null){
            return "Disponibilidad de fechas " +date;
        }
                if (!pistas.containsKey(courtId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La pista" + courtId + "no existe");
        }
        //en el caso de que quiera día y pista
        return "La pista " + pistas.get(courtId) + " está disponible para el día " + date;
    }


    @GetMapping("/{courtId}/availability")
    public String disponibilidadPorPista(@PathVariable Long courtId,
                                         @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE) LocalDate date){

        if (!pistas.containsKey(courtId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La pista no ha sido encontrada");
        }

        return "La pista " +pistas.get(courtId)+ " está disponible para el " + date;

    }

    @GetMapping("/admin/reservations")
    public String verReservasAdmin(
            @RequestHeader(value = "Autorización", required = false) String claveAcceso,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Long courtId,
            @RequestParam(required = false) String userId) {

        // Simulación de 401 y 403
        if (claveAcceso == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        };
        if (!claveAcceso.contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return "Lista de reservas para el admin";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}