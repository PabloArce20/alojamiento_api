package Alojamiento_api.Alojamiento_api.controller;

import Alojamiento_api.Alojamiento_api.model.Cliente;
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
@RequestMapping("/clientes")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    private List<Cliente> clientes = new ArrayList<>();
    private Long nextId = 2L;
    private int nextClienteId = 1;

    public ClienteController() {
        clientes.add(new Cliente(1L, "Ana Gómez", "ana.gomez@example.com", "Activo", "CLI001"));
    }

    @GetMapping
    public List<Cliente> listarClientes() {
        return clientes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable Long id) {
        logger.info("Obtener cliente con ID: {}", id);
        Optional<Cliente> cliente = clientes.stream().filter(c -> c.getId().equals(id)).findFirst();
        return cliente.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (cliente.getEstado() == null || cliente.getEstado().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        cliente.setId(nextId++);
        cliente.setIdCliente("CLI" + String.format("%03d", nextClienteId++));
        clientes.add(cliente);
        logger.info("Cliente creado con ID: {}", cliente.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
        logger.info("Actualizar cliente con ID: {}", id);
        Optional<Cliente> clienteExistenteOptional = clientes.stream().filter(c -> c.getId().equals(id)).findFirst();
        if (!clienteExistenteOptional.isPresent()) {
            logger.warn("Cliente con ID {} no encontrado para actualizar", id);
            return ResponseEntity.notFound().build();
        }
        if (clienteActualizado.getNombre() == null || clienteActualizado.getNombre().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (clienteActualizado.getEstado() == null || clienteActualizado.getEstado().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        clienteActualizado.setId(id);
        int index = clientes.indexOf(clienteExistenteOptional.get());
        clientes.set(index, clienteActualizado);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id) {
        logger.info("Eliminar cliente con ID: {}", id);
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