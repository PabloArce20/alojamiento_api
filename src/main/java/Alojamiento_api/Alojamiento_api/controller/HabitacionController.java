//echo por los 3//
package Alojamiento_api.Alojamiento_api.controller;

import Alojamiento_api.Alojamiento_api.model.Habitacion;
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
@RequestMapping("/habitaciones")
public class HabitacionController {

    private static final Logger logger = LoggerFactory.getLogger(HabitacionController.class);
    private List<Habitacion> habitaciones = new ArrayList<>();
    // ID autoincremental para nuevas habitaciones
    private Long nextId = 2L;

    public HabitacionController() {
        habitaciones.add(new Habitacion(1L, "1", "individual", 1, 20.0, true));
    }

    @GetMapping
    public List<Habitacion> listarHabitaciones() {
        return habitaciones; // Devuelve todas las habitaciones
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> obtenerHabitacion(@PathVariable Long id) {
        logger.info("Obtener habitación con ID: {}", id);
        Optional<Habitacion> habitacion = habitaciones.stream().filter(h -> h.getId().equals(id)).findFirst();
        return habitacion.map(h -> new ResponseEntity<>(h, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Si no la encuentra, devuelve 404

    }

    @PostMapping
    public ResponseEntity<Habitacion> crearHabitacion(@RequestBody Habitacion habitacion) {
        // Validación: número de habitación no puede ser nulo o vacío
        if (habitacion.getNumeroHabitacion() == null || habitacion.getNumeroHabitacion().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Validación: se evita el uso de guiones en el número
        if (habitacion.getNumeroHabitacion().contains("-")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Validación: el número de habitación debe ser único
        if (habitaciones.stream().anyMatch(h -> h.getNumeroHabitacion().equalsIgnoreCase(habitacion.getNumeroHabitacion()))) {
            logger.warn("Intento de crear habitación con número duplicado: {}", habitacion.getNumeroHabitacion());
            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409: conflicto por duplicación
        }
        // Validación: capacidad debe ser positiva
        if (habitacion.getCapacidad() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            // Validación: precio por noche debe ser mayor a cero
        }
        if (habitacion.getPrecioPorNoche() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Si pasa las validaciones, se asigna un ID nuevo y se guarda en la lista
        habitacion.setId(nextId++);
        habitaciones.add(habitacion);
        logger.info("Habitación creada con ID: {}", habitacion.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(habitacion); // 201: creado
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habitacion> actualizarHabitacion(@PathVariable Long id, @RequestBody Habitacion habitacionActualizada) {
        logger.info("Actualizar habitación con ID: {}", id);
        Optional<Habitacion> habitacionExistenteOptional = habitaciones.stream().filter(h -> h.getId().equals(id)).findFirst();
        if (!habitacionExistenteOptional.isPresent()) {
            logger.warn("Habitación con ID {} no encontrada para actualizar", id);
            return ResponseEntity.notFound().build(); // 404 si no existe
        }

        if (habitacionActualizada.getNumeroHabitacion() == null || habitacionActualizada.getNumeroHabitacion().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (habitacionActualizada.getNumeroHabitacion().contains("-")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (habitacionActualizada.getCapacidad() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (habitacionActualizada.getPrecioPorNoche() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Se actualizan los datos de la habitación existente
        Habitacion habitacionExistente = habitacionExistenteOptional.get();
        habitacionExistente.setNumeroHabitacion(habitacionActualizada.getNumeroHabitacion());
        habitacionExistente.setTipo(habitacionActualizada.getTipo());
        habitacionExistente.setCapacidad(habitacionActualizada.getCapacidad());
        habitacionExistente.setPrecioPorNoche(habitacionActualizada.getPrecioPorNoche());
        habitacionExistente.setDisponible(habitacionActualizada.isDisponible());
        logger.info("Habitación con ID {} actualizada", id); // Devuelve habitación actualizada
        return ResponseEntity.ok(habitacionExistente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarHabitacion(@PathVariable Long id) {
        logger.info("Eliminar habitación con ID: {}", id);
        // Elimina la habitación si existe
        boolean removed = habitaciones.removeIf(h -> h.getId().equals(id));
        if (removed) {
            logger.info("Habitación con ID {} eliminada", id);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("La habitación fue eliminada");
        } else {
            logger.warn("Habitación con ID {} no encontrada para eliminar", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}