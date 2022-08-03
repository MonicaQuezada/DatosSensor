package modelo.ejercicio.actividad.firebase.service;

import modelo.ejercicio.actividad.firebase.dto.PostDTO;
import modelo.ejercicio.actividad.firebase.firebaze.FirebaseInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostActividadService {


    List<PostDTO> list();

    Boolean add(PostDTO post);

    Boolean edit(String id, PostDTO post);

    Boolean delete(String id);
}
