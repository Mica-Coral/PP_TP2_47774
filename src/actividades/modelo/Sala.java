/**
 * Clase de apoyo, minimal, solo para poder invocar asignarSala(Sala).
 * No se relacionan ni se almacena dentro de EventoUniversitario,
 * ya que el enunciado indica que por ahora no se deben vincular
 * entidades adicionales.
 */
package modelo;

import java.io.Serializable;

public class Sala implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nombre;

    public Sala(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return "Sala [id=" + id + ", nombre=" + nombre + "]";
    }
}