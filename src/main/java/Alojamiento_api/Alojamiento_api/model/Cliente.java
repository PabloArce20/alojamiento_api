//echo por pablo//
package Alojamiento_api.Alojamiento_api.model;

public class Cliente extends Persona {
    private String estado;
    private String idCliente;

    public Cliente() {
        super(); // Llama al constructor de la clase padre (Persona)
    }
//constructor
    public Cliente(Persona persona, String estado, String idCliente) {
        super(persona.getId(), persona.getNombreCompleto(), persona.getEmail());
        this.estado = estado;
        this.idCliente = idCliente;
    }


    // Getters y setters para estado e idCliente

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
}