package edu.comillas.icai.gitt.pat.spring.mvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.annotation.DirtiesContext;

@WebMvcTest(ReservationController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc(addFilters = false) // Mantenemos la magia para saltarnos el error 401
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // --- TESTS PARA EL GET (Listar) ---
    @Test
    void getReservations_DebeDevolver200() throws Exception {
        mockMvc.perform(get("/pistaPadel/reservations"))
                .andExpect(status().isOk());
    }

    // --- TESTS PARA EL GET INDIVIDUAL (Ver una) ---
    @Test
    void getReservation_Existe_DebeDevolver200() throws Exception {
        // Buscamos "reserva-1", que es la que metimos "a mano" en el Controlador
        mockMvc.perform(get("/pistaPadel/reservations/reserva-1"))
                .andExpect(status().isOk());
    }

    @Test
    void getReservation_NoExiste_DebeDevolver404() throws Exception {
        // Buscamos un ID que no existe
        mockMvc.perform(get("/pistaPadel/reservations/id-inventado"))
                .andExpect(status().isNotFound());
    }

    // --- TESTS PARA EL DELETE (Borrar) ---
    @Test
    void deleteReservation_Exito_DebeDevolver204() throws Exception {
        // Borramos "reserva-1" (esperamos un 204 No Content)
        mockMvc.perform(delete("/pistaPadel/reservations/reserva-1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteReservation_Conflicto_DebeDevolver409() throws Exception {
        // Probamos la regla de que si es "reserva-intocable" da error
        mockMvc.perform(delete("/pistaPadel/reservations/reserva-intocable"))
                .andExpect(status().isConflict());
    }

    // --- TESTS PARA EL PATCH (Modificar) ---
    @Test
    void updateReservation_Ocupado_DebeDevolver409() throws Exception {
        // Probamos la regla de que si está ocupada da error
        mockMvc.perform(patch("/pistaPadel/reservations/reserva-ocupada"))
                .andExpect(status().isConflict());
    }
}