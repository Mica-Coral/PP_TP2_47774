/**
 * Modela un evento universitario simple (charla, jornada, taller,
 * hackathon, competencia, etc.).
 *
 * Por el momento la clase solo registra los datos propios de cada
 * evento: no mantiene listas de participantes ni referencias a otras
 * entidades (Sala, Actividad), tal como lo pide el enunciado. Los
 * métodos asignarSala(...) y crearActividad(...) están declarados
 * porque forman parte del contrato de la clase, pero no almacenan
 * ninguna relación: solo informan la acción realizada.
 */

import java.util.ArrayList;
import java.util.List;

import src.actividades.Actividad;
import src.actividades.Charla;
import src.actividades.Taller;

public class EventoUniversitario {

    // ------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------

    private final String id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;

    // Atributo de clase: cuenta cuántos eventos se han creado en total.
    private static int cantidadEventos = 0;

    // ------------------------------------------------------------------
    // Constructores
    // ------------------------------------------------------------------

    /**
     * Constructor principal: crea un evento correctamente inicializado.
     */
    public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito) {
        this.id = id;
        this.titulo = titulo;
        this.costoBase = costoBase;
        this.gratuito = gratuito;
        cantidadEventos++;
    }

    /**
     * Constructor de copia: crea un nuevo evento a partir de otro ya
     * existente, duplicando sus valores.
     */
    public EventoUniversitario(EventoUniversitario otro) {
        this.id = otro.id;
        this.titulo = otro.titulo;
        this.costoBase = otro.costoBase;
        this.gratuito = otro.gratuito;
        cantidadEventos++;
    }

    // ------------------------------------------------------------------
    // Métodos de negocio
    // ------------------------------------------------------------------

    /**
     * Calcula el costo estimado del evento: 0 si es gratuito,
     * o el costo base en caso contrario.
     */
    public double calcularCostoEstimado() {
        return gratuito ? 0.0 : costoBase;
    }

    /**
     * Registra la asignación de una sala para el evento.
     * No se guarda la referencia a la sala (ver nota de clase).
     */
    public void asignarSala(Sala sala) {
        this.sala = sala;
    }

    /**
     * Registra la creación de una actividad dentro del evento.
     * No se guarda ninguna lista de actividades (ver nota de clase).
     */
    public void crearActividad(int id, String titulo, int cupo, String tipo) {
        if ("Charla".equalsIgnoreCase(tipo)) {
            actividades.add(new Charla(id, titulo, cupo, "A confirmar"));
        } else if ("Taller".equalsIgnoreCase(tipo)) {
            actividades.add(new Taller(id, titulo, cupo, false));
        } else {
            throw new IllegalArgumentException("Tipo de actividad no reconocido: " + tipo);
        }
    }
    
    public List<Actividad> getActividades() {
        return actividades;
    }

    /**
     * Muestra por consola los datos del evento.
     */
    public void mostrarDatos() {
        System.out.println("----- Evento Universitario -----");
        System.out.println("Id            : " + id);
        System.out.println("Titulo        : " + titulo);
        System.out.println("Costo base    : " + costoBase);
        System.out.println("Gratuito      : " + (gratuito ? "Si" : "No"));
        System.out.println("Costo estimado: " + calcularCostoEstimado());
        System.out.println("---------------------------------");
        System.out.println("Sala asignada : " + (sala != null ? sala : "sin asignar"));
        System.out.println("Actividades : " + actividades.size());
        for (Actividad actividad : actividades) {
           actividad.mostrarIdentificacion();
           actividad.mostrarInscripciones();
        }
        
    }

    // ------------------------------------------------------------------
    // Getters (encapsulamiento)
    // ------------------------------------------------------------------
    private Sala sala; // Atributo para almacenar la sala asignada
    
    private List<Actividad> actividades = new ArrayList<>(); // Lista para almacenar las actividades creadas
    
    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getCostoBase() {
        return costoBase;
    }

    public void setCostoBase(double costoBase) {
        this.costoBase = costoBase;
    }

    public boolean isGratuito() {
        return gratuito;
    }

    public void setGratuito(boolean gratuito) {
        this.gratuito = gratuito;
    }

    /**
     * Método de clase (static): informa el total de eventos creados
     * hasta el momento (incluyendo copias).
     */
    public static int getCantidadEventos() {
        return cantidadEventos;
    }
}
