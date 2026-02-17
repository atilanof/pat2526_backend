package edu.comillas.icai.gitt.pat.padel.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Pista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer idPista;

    @Column(nullable = false)
    @NotBlank(message = "El nombre de la pista no puede estar vacío")
    public String nombre;
    
    @Column(nullable = false)
    @NotBlank(message = "La ubicación no puede estar vacía")
    public String ubicacion;
    
    @Column(nullable = false)
    @NotNull(message = "El precio por hora no puede estar vacío")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    public Double precioHora;   
    
    @Column(nullable = false)
    @NotNull(message = "El estado activo no puede estar vacío")
    public Boolean activa;
    
    @Column(nullable = false)
    @NotNull(message = "La fecha de alta no puede estar vacía")
    public LocalDateTime fechaAlta;
}
