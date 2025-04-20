//echo por pablo//
package Alojamiento_api.Alojamiento_api.model;

public class Habitacion {
    private long id;
    private String numero;
    private String tipo;
    private int capacidad;

    public Habitacion(){

    }

    public Habitacion(long id, String numero, String tipo, int capacidad) {
        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.capacidad = capacidad;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}