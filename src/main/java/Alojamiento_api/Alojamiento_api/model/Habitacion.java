//echo por pablo//
package Alojamiento_api.Alojamiento_api.model;

public class Habitacion {
    private Long id;
    private String numeroHabitacion;
    private String tipo;
    private int capacidad;
    private double precioPorNoche;
    private boolean disponible;

    public Habitacion() {
    }
//constructor
    public Habitacion(Long id, String numeroHabitacion, String tipo, int capacidad, double precioPorNoche, boolean disponible) {
        this.id = id;
        this.numeroHabitacion = numeroHabitacion;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.precioPorNoche = precioPorNoche;
        this.disponible = disponible;
    }
 //getter y setter//
    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getNumeroHabitacion()
    {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(String numeroHabitacion) {

        this.numeroHabitacion = numeroHabitacion;
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

    public double getPrecioPorNoche() {

        return precioPorNoche;
    }

    public void setPrecioPorNoche(double precioPorNoche) {

        this.precioPorNoche = precioPorNoche;
    }

    public boolean isDisponible()
    {
        return disponible;
    }

    public void setDisponible(boolean disponible) {

        this.disponible = disponible;
    }
    //metodo para mostrar los datos de la habitacion//
    @Override
    public String toString() {
        return "Habitacion{" +
                "id=" + id +
                ", numeroHabitacion='" + numeroHabitacion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", capacidad=" + capacidad +
                ", precioPorNoche=" + precioPorNoche +
                ", disponible=" + disponible +
                '}';
    }
}