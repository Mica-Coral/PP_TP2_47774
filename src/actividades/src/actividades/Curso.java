package src.actividades.src.actividades;

import modelo.Estudiante;
import src.actividades.certificacion.Certificable;

public class Curso extends Actividad implements Certificable {
    private static final long serialVersionUID = 1L;
    private int horasCatedra;

    public Curso(int id, String titulo, int cupoMaximo, int horasCatedra) {
        super(id, titulo, cupoMaximo);
        this.horasCatedra = horasCatedra;
    }

    public int getHorasCatedra() {
        return horasCatedra;
    }

    @Override
    public double calcularCostoMateriales() {
        return horasCatedra * 250.0;
    }

    @Override
    public String getTipo() {
        return "Curso";
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "CERTIFICADO DE APROBACIÓN\n" +
               "Estudiante: " + estudiante.getNombre() + " (Legajo: " + estudiante.getLegajo() + ")\n" +
               "Por completar el Curso: " + getTitulo() + " (" + horasCatedra + " hs reloj)\n" +
               "Emite: " + ENTIDAD_EMISORA;
    }
}