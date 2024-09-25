package edu.pe.cibertec.demo_front_mautino.dto;

public record VehiculoResponse
        (String codigo,
         String mensaje,
         String VehiculoMarca,
         String VehiculoModelo,
         String VehiculoNroAsientos,
         String VehiculoPrecio,
         String VehiculoColor) {
}
