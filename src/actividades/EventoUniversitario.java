package src.actividades;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import src.actividades.*;

public class EventoUniversitario implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;
    private static int cantidadEventos = 0;

    private Sala sala;
    private List<Actividad> actividades;

    public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito) {
        this.id = id;
        this.titulo = titulo;
        this.costoBase = costoBase;
        this.gratuito = gratuito;
        this.actividades = new ArrayList<>();
        cantidadEventos++;
    }

    public EventoUniversitario(EventoUniversitario otro) {
        this(otro.id + "_copia", otro.titulo + " (Copia)", otro.costoBase, otro.gratuito);
        this.sala = otro.sala;
        this.actividades = new ArrayList<>(otro.actividades);
    }

    public static int getCantidadEventos() { return cantidadEventos; }
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public List<Actividad> getActividades() { return actividades; }

    public void asignarSala(Sala sala) { this.sala = sala; }
    public Sala getSala() { return sala; }

    public void crearActividad(int id, String titulo, String tipo) {
        switch (tipo.toLowerCase().trim()) {
            case "charla":
                actividades.add(new Charla(id, titulo, 50, "Disertante Invitado"));
                break;
            case "taller":
                actividades.add(new Taller(id, titulo, 25, true));
                break;
            case "curso":
                actividades.add(new Curso(id, titulo, 30, 40));
                break;
            default:
                throw new IllegalArgumentException("Tipo de actividad no reconocido: " + tipo);
        }
    }

    public double calcularCostoEstimado() {
        if (gratuito) return 0.0;
        double sumaMateriales = 0.0;
        for (Actividad act : actividades) {
            sumaMateriales += act.calcularCostoMateriales();
        }
        return (costoBase + sumaMateriales) * 1.21;
    }

    // Ejercicio 3: Métodos Genéricos con Bound y Wildcard
    public <T extends Actividad> List<T> filtrarActividadesPorTipo(Class<T> tipo) {
        List<T> filtradas = new ArrayList<>();
        for (Actividad act : actividades) {
            if (tipo.isInstance(act)) {
                filtradas.add(tipo.cast(act));
            }
        }
        return filtradas;
    }

    public double calcularCostoMateriales(List<? extends Actividad> listaActividades) {
        double total = 0.0;
        for (Actividad act : listaActividades) {
            total += act.calcularCostoMateriales();
        }
        return total;
    }

    public void mostrarDatos() {
        System.out.println("==================================================");
        System.out.println("EVENTO: " + titulo + " [ID: " + id + "]");
        System.out.println("Gratuito: " + (gratuito ? "Sí" : "No") + " | Costo estimado: $" + String.format("%.2f", calcularCostoEstimado()));
        System.out.println("Sala asignada: " + (sala != null ? sala.getNombre() : "Sin asignar"));
        System.out.println("Actividades vinculadas (" + actividades.size() + "):");
        for (Actividad a : actividades) {
            a.mostrarIdentificacion();
            a.mostrarInscripciones();
        }
        System.out.println("==================================================");
    }

    // Persistencia
    public boolean persistirEvento() {
        String filename = "evento_" + this.id + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
            return true;
        } catch (IOException e) {
            System.err.println("Error al persistir el evento " + id + ": " + e.getMessage());
            return false;
        }
    }

    public static EventoUniversitario recuperarEvento(String id) {
        String filename = "evento_" + id + ".dat";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (EventoUniversitario) ois.readObject();
        } catch (Exception e) {
            System.err.println("Error al recuperar el evento: " + e.getMessage());
            return null;
        }
    }
}