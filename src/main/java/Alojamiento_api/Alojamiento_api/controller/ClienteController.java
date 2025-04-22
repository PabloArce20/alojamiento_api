//echo por los 3//
package Alojamiento_api.Alojamiento_api.controller;

import Alojamiento_api.Alojamiento_api.model.Cliente;
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
@RequestMapping("/clientes") // Este controlador responderá a todas las rutas que empiecen con /clientes
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    // Lista en memoria que simula una base de datos
    private List<Cliente> clientes = new ArrayList<>();

    // IDs automáticos para nuevos clientes
    private Long nextId = 2L;
    private int nextClienteId = 1;

    public ClienteController() {
        // Carga inicial de un cliente de prueba
        Persona persona = new Persona(1L, "Pablo Leonardo Monasterios Arce", "parce4981@gmail.com");
        clientes.add(new Cliente(persona, "Cuenta activa", "CLI000"));
    }

    @GetMapping
    public List<Cliente> listarClientes() {
        return clientes; // Devuelve todos los clientes registrados
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable Long id) {
        logger.info("Obtener cliente con ID: {}", id);
        // Busca al cliente por ID, si no existe devuelve 404
        Optional<Cliente> cliente = clientes.stream().filter(c -> c.getId().equals(id)).findFirst();
        return cliente.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        // Validaciones básicas de campos requeridos
        if (cliente.getNombreCompleto() == null || cliente.getNombreCompleto().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty() || !cliente.getEmail().contains("@")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (cliente.getEstado() == null || cliente.getEstado().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Asignar ID y código único (CLI001, CLI002...)
        cliente.setId(nextId++);
        cliente.setIdCliente("CLI" + String.format("%03d", nextClienteId++));
        clientes.add(cliente);

        logger.info("Cliente creado con ID: {}", cliente.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente); // Devuelve el cliente creado con status 201
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
        logger.info("Actualizar cliente con ID: {}", id);
        Optional<Cliente> clienteExistenteOptional = clientes.stream().filter(c -> c.getId().equals(id)).findFirst();

        if (!clienteExistenteOptional.isPresent()) {
            logger.warn("Cliente con ID {} no encontrado para actualizar", id);
            return ResponseEntity.notFound().build();
        }

        // Validaciones iguales a las del POST
        if (clienteActualizado.getNombreCompleto() == null || clienteActualizado.getNombreCompleto().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (clienteActualizado.getEmail() == null || clienteActualizado.getEmail().trim().isEmpty() || !clienteActualizado.getEmail().contains("@")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (clienteActualizado.getEstado() == null || clienteActualizado.getEstado().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Mantener el mismo ID y código del cliente original
        clienteActualizado.setId(id);
        clienteActualizado.setIdCliente(clienteExistenteOptional.get().getIdCliente());

        // Reemplazar el cliente antiguo por el actualizado
        int index = clientes.indexOf(clienteExistenteOptional.get());
        clientes.set(index, clienteActualizado);

        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id) {
        logger.info("Eliminar cliente con ID: {}", id);
        // Elimina el cliente si existe
        boolean removed = clientes.removeIf(c -> c.getId().equals(id));

        if (removed) {
            logger.info("Cliente con ID {} eliminado", id);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("El cliente fue eliminado");
        } else {
            logger.warn("Cliente con ID {} no encontrado para eliminar", id);
            return new ResponseEntity<>("Error al eliminar el cliente", HttpStatus.NOT_FOUND);
        }
    }
}
