package src.actividades;

import modelo.Estudiante;
import modelo.Inscripcion;
import src.actividades.excepciones.CupoExcedidoException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Actividad implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String titulo;
    private int cupoMaximo;
    public static final int CUPO_MINIMO = 5;

    private List<Inscripcion> inscripciones;

    public Actividad(int id, String titulo, int cupoMaximo) {
        this.id = id;
        this.titulo = titulo;
        this.cupoMaximo = cupoMaximo;
        this.inscripciones = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public int getCupoMaximo() { return cupoMaximo; }
    public List<Inscripcion> getInscripciones() { return inscripciones; }

    public Inscripcion inscribir(Estudiante estudiante) throws CupoExcedidoException {
        if (inscripciones.size() >= cupoMaximo) {
            throw new CupoExcedidoException("No hay cupo disponible en '" + titulo + "' (Capacidad máxima: " + cupoMaximo + ").");
        }
        Inscripcion inscripcion = new Inscripcion(estudiante);
        inscripciones.add(inscripcion);
        return inscripcion;
    }

    public void mostrarInscripciones() {
        if (inscripciones.isEmpty()) {
            System.out.println("    [Inscripciones]: Sin inscriptos aún.");
            return;
        }
        System.out.println("    [Inscripciones (" + inscripciones.size() + "/" + cupoMaximo + ")]:");
        for (Inscripcion ins : inscripciones) {
            System.out.println("      - " + ins);
        }
    }

    public final void mostrarIdentificacion() {
        System.out.println("  * [" + getTipo().toUpperCase() + "] #" + id + " - " + titulo + 
                           " | Costo materiales: $" + calcularCostoMateriales());
    }

    public abstract double calcularCostoMateriales();
    public abstract String getTipo();
} 