package hilos;

import actividades.Actividad;
import modelo.EventoUniversitario;
import modelo.Inscripcion;

public class EnvioTicketsThread extends Thread {
    private EventoUniversitario evento;

    public EnvioTicketsThread(EventoUniversitario evento) {
        super("HiloEnvioTickets");
        this.evento = evento;
    }

    @Override
    public void run() {
        System.out.println("\n>>> [HILO ASÍNCRONO] Iniciando envío de tickets...");
        for (Actividad act : evento.getActividades()) {
            for (Inscripcion ins : act.getInscripciones()) {
                if ("CONFIRMADA".equalsIgnoreCase(ins.getEstado()) && ins.getTicket() != null) {
                    try {
                        Thread.sleep(700); // Pausa para que se intercalen los hilos en consola
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    ins.getTicket().enviarTicket();
                }
            }
        }
        System.out.println(">>> [HILO ASÍNCRONO] Envíos finalizados.\n");
    }
}
