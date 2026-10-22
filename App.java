/** 
 * Clase de arranque de la aplicación.
 *
 * Se crean varios eventos universitarios, se copia cada uno con el 
 * constructor de copia, se muestran los datos originales y copias,
 * y finalmente se informa el contador total de eventos creados.
 */

import hilos.EnvioTicketsThread;
import modelo.*;
import src.actividades.*;
import src.actividades.certificacion.Certificable;
import src.actividades.excepciones.CupoExcedidoException;

import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("       TRABAJO PRÁCTICO N° 2 - PARADIGMAS DE PROGRAMACIÓN     ");
        System.out.println("===============================================================\n");

        Estudiante est1 = new Estudiante("50101", "Ana Gómez");
        Estudiante est2 = new Estudiante("50102", "Lucas Benítez");
        Estudiante est3 = new Estudiante("50103", "Martina Rossi");

        Sala aulaMagna = new Sala(1, "Aula Magna - Edificio Central");

        EventoUniversitario evento = new EventoUniversitario("EVT-2026-01", "Jornadas de Ingeniería y Tecnología", 25000.0, false);
        evento.asignarSala(aulaMagna);

        evento.crearActividad(101, "Tendencias en IA y Machine Learning", "Charla");
        evento.crearActividad(102, "Docker y Contenedores Prácticos", "Taller");
        evento.crearActividad(103, "Arquitectura de Software Moderna", "Curso");

        List<Actividad> listaActividades = evento.getActividades();
        Actividad charlaIA = listaActividades.get(0);
        Actividad tallerDocker = listaActividades.get(1);

        // EJERCICIO 1: Tolerancia a fallos y persistencia
        System.out.println("--- EJERCICIO 1: TOLERANCIA A FALLOS Y SERIALIZACIÓN ---");
        try {
            charlaIA.inscribir(est2);
            Inscripcion insAna = tallerDocker.inscribir(est1);
            insAna.confirmar();

            Inscripcion insMartina = tallerDocker.inscribir(est3);
            insMartina.confirmar();

            // Prueba de fallo con cupo superado
            Actividad tallerMini = new Taller(999, "Taller Exprés Cupo 1", 1, false);
            tallerMini.inscribir(est1);
            tallerMini.inscribir(est2); // Fuerza CupoExcedidoException
        } catch (CupoExcedidoException e) {
            System.err.println("[CONTROL DE ERROR]: " + e.getMessage());
        } finally {
            System.out.println("[FINALLY]: Validación de inscripciones finalizada.");
        }

        evento.persistirEvento();
        EventoUniversitario recuperado = EventoUniversitario.recuperarEvento(evento.getId());
        if (recuperado != null) {
            System.out.println("Evento recuperado desde archivo: " + recuperado.getTitulo());
        }

        // EJERCICIO 2: Certificados
        System.out.println("\n--- EJERCICIO 2: CERTIFICADOS POLIMÓRFICOS ---");
        for (Actividad act : evento.getActividades()) {
            if (act instanceof Certificable) {
                Certificable c = (Certificable) act;
                System.out.println("Certificados para la actividad: " + act.getTitulo());
                for (Inscripcion ins : act.getInscripciones()) {
                    System.out.println(c.generarCertificado(ins.getEstudiante()));
                    System.out.println("----------------------------------------");
                }
            } else {
                System.out.println("La actividad '" + act.getTitulo() + "' NO emite certificados.");
            }
        }

        // EJERCICIO 3: Genéricos y Wildcards
        System.out.println("\n--- EJERCICIO 3: FILTRADO Y MÉTODOS GENÉRICOS ---");
        List<Charla> charlas = evento.filtrarActividadesPorTipo(Charla.class);
        List<Taller> talleres = evento.filtrarActividadesPorTipo(Taller.class);
        List<Curso> cursos = evento.filtrarActividadesPorTipo(Curso.class);

        System.out.println("Cantidad de Charlas: " + charlas.size());
        System.out.println("Cantidad de Talleres: " + talleres.size());
        System.out.println("Cantidad de Cursos: " + cursos.size());

        System.out.println("Costo materiales Talleres: $" + evento.calcularCostoMateriales(talleres));
        System.out.println("Costo materiales Cursos: $" + evento.calcularCostoMateriales(cursos));
        System.out.println("Costo materiales Total: $" + evento.calcularCostoMateriales(evento.getActividades()));

        // EJERCICIO 4: Hilo concurrente
        System.out.println("\n--- EJERCICIO 4: ENVÍO ASÍNCRONO DE TICKETS ---");
        EnvioTicketsThread hilo = new EnvioTicketsThread(evento);
        hilo.start();

        for (int i = 1; i <= 3; i++) {
            System.out.println("[Main] Generando reportes en paralelo... Paso " + i);
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        try {
            hilo.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n--- DATOS DEL EVENTO ---");
        evento.mostrarDatos();
        System.out.println("Total eventos creados: " + EventoUniversitario.getCantidadEventos());
    }
}