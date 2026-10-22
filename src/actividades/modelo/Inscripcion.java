package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Inscripcion implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDate fecha;
    private String estado;
    private Estudiante estudiante;
    private TicketDeAcceso ticket;

    public Inscripcion(Estudiante estudiante) {
        this.fecha = LocalDate.now();
        this.estado = "PENDIENTE";
        this.estudiante = estudiante;
        this.ticket = null;
    }

    public LocalDate getFecha() { return fecha; }
    public String getEstado() { return estado; }
    public Estudiante getEstudiante() { return estudiante; }
    public TicketDeAcceso getTicket() { return ticket; }

    public void confirmar() {
        this.estado = "CONFIRMADA";
        this.ticket = new TicketDeAcceso("TCK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
    }

    // Clase anidada miembro
    public class TicketDeAcceso implements Serializable {
        private static final long serialVersionUID = 1L;
        private String codigoTicket;

        public TicketDeAcceso(String codigoTicket) {
            this.codigoTicket = codigoTicket;
        }

        public String getCodigoTicket() { return codigoTicket; }

        public void enviarTicket() {
            System.out.println("  -> [Ticket Enviado] Código: " + codigoTicket + " enviado a: " + estudiante.getNombre());
        }

        @Override
        public String toString() {
            return "Ticket [" + codigoTicket + "] para " + estudiante.getNombre();
        }
    }

    @Override
    public String toString() {
        return "Inscripción [" + estudiante.getNombre() + " | Estado: " + estado + 
               (ticket != null ? " | " + ticket.getCodigoTicket() : "") + "]";
    }
}