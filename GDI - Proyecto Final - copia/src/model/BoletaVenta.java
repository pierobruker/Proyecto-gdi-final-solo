package model;
import java.time.LocalDate;
public class BoletaVenta {
    private String nroBoleta;
    private LocalDate fecha;
    private double monto;
    private String nroContrato;
    // Getters/Setters
    public String getNroBoleta() { return nroBoleta; }
    public void setNroBoleta(String nroBoleta) { this.nroBoleta = nroBoleta; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getNroContrato() { return nroContrato; }
    public void setNroContrato(String nroContrato) { this.nroContrato = nroContrato; }

    @Override
    public String toString() {
        return "BoletaVenta{" +
                "nroBoleta='" + nroBoleta + '\'' +
                ", fecha=" + fecha +
                ", monto=" + monto +
                ", nroContrato='" + nroContrato + '\'' +
                '}';
    }
}