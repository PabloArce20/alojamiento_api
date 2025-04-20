package Alojamiento_api.Alojamiento_api.controller;

import Alojamiento_api.Alojamiento_api.model.Persona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    private static final Logger logger = LoggerFactory.getLogger(PersonaController.class);
    private List<Persona> personas = new ArrayList<>();
    private Long nextId = 2L;

    public PersonaController() {
        personas.add(new Persona(1L, "Juan Pérez", "juan.perez@example.com"));
    }

    @GetMapping
    public List<Persona> listarPersonas() {
        return personas;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtenerPersona(@PathVariable Long id) {
        logger.info("Obtener persona con ID: {}", id);
        Optional<Persona> persona = personas.stream().filter(p -> p.getId().equals(id)).findFirst();
        return persona.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Persona> crearPersona(@RequestBody Persona persona) {
        if (persona.getNombre() == null || persona.getNombre().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (persona.getEmail() == null || persona.getEmail().trim().isEmpty() || !persona.getEmail().contains("@")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Validación de email básica
        }
        persona.setId(nextId++);
        personas.add(persona);
        logger.info("Persona creada con ID: {}", persona.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(persona);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizarPersona(@PathVariable Long id, @RequestBody Persona personaActualizada) {
        logger.info("Actualizar persona con ID: {}", id);
        Optional<Persona> personaExistenteOptional = personas.stream().filter(p -> p.getId().equals(id)).findFirst();
        if (!personaExistenteOptional.isPresent()) {
            logger.warn("Persona con ID {} no encontrada para actualizar", id);
            return ResponseEntity.notFound().build();
        }
        if (personaActualizada.getNombre() == null || personaActualizada.getNombre().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (personaActualizada.getEmail() == null || personaActualizada.getEmail().trim().isEmpty() || !personaActualizada.getEmail().contains("@")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Validación de email básica
        }
        personaActualizada.setId(id);
        int index = personas.indexOf(personaExistenteOptional.get());
        personas.set(index, personaActualizada);
        return ResponseEntity.ok(personaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPersona(@PathVariable Long id) {
        logger.info("Eliminar persona con ID: {}", id);
        boolean removed = personas.removeIf(p -> p.getId().equals(id));
        if (removed) {
            logger.info("Persona con ID {} eliminada", id);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("La persona fue eliminada");
        } else {
            logger.warn("Persona con ID {} no encontrada para eliminar", id);
            return new ResponseEntity<>("Error al eliminar la persona", HttpStatus.NOT_FOUND);
        }
    }
}