//echo por pablo//
package Alojamiento_api.Alojamiento_api.model;

public class Persona {
    private Long id;
    private String NombreCompleto;
    private String email;

    public Persona() {
    }
//constructor
    public Persona(Long id, String NombreCompleto, String email) {
        this.id = id;
        this.NombreCompleto = NombreCompleto;
        this.email = email;
    }
//getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return NombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        NombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}