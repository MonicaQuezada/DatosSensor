package modelo.ejercicio.actividad.firebase.controller;

import modelo.ejercicio.actividad.firebase.dto.PostDTO;
import modelo.ejercicio.actividad.firebase.service.PostActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostActividadService service;

    @GetMapping(value = "/great")
    public String great(){
        return "Hello World";
    }

    @PostMapping(value = "/add")
    public ResponseEntity add(@RequestBody PostDTO post){
        return new ResponseEntity(service.add(post), HttpStatus.OK);
    }

}
