package modelo.ejercicio.actividad.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Usuario {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;

}
