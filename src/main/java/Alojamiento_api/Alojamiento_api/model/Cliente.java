//echo por pablo//
package Alojamiento_api.Alojamiento_api.model;

import java.util.List;
import java.util.ArrayList;

public class Cliente extends Persona {
    private String estado;
    private List<Reserva> reservas = new ArrayList<>();
    private String idCliente; // Nuevo identificador específico para el cliente

    public Cliente() {
        super();
    }

    public Cliente(Long id, String nombre, String email, String estado, String idCliente) {
        super(id, nombre, email);
        this.estado = estado;
        this.reservas = new ArrayList<>();
        this.idCliente = idCliente;
    }

    // Getters y setters para estado
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Getters y setters para reservas
    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    // Getters y setters para idCliente
    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + getId() +
                ", nombre='" + getNombre() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", estado='" + estado + '\'' +
                ", reservas=" + reservas +
                ", idCliente='" + idCliente + '\'' +
                '}';
    }
}