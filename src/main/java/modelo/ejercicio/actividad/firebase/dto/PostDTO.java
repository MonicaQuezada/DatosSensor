package modelo.ejercicio.actividad.firebase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDTO {
    private String id;
    private String idUser;
    private String fecha;
    private String hora;
    private Double content;



}
