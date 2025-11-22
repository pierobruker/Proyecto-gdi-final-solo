package model;
import java.time.LocalDate;
public class Contrato {
    private String nro;
    private LocalDate fecha;
    private String tipoPago;
    private String metodoPago;
    private double total;
    private double aCuenta;
    private double saldo;
    private String dniCliente;
    private int idEmpleado;
    private String codigoMueble;
    private String descripcion;
    private int cantidad;
    private double precioUnitario;
    private double descuento;
    // Getters/Setters
    public String getNro() { return nro; }
    public void setNro(String nro) { this.nro = nro; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getTipoPago() { return tipoPago; }
    public void setTipoPago(String tipoPago) { this.tipoPago = tipoPago; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public double getACuenta() { return aCuenta; }
    public void setACuenta(double aCuenta) { this.aCuenta = aCuenta; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public String getDniCliente() { return dniCliente; }
    public void setDniCliente(String dniCliente) { this.dniCliente = dniCliente; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getCodigoMueble() { return codigoMueble; }
    public void setCodigoMueble(String codigoMueble) { this.codigoMueble = codigoMueble; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { this.descuento = descuento; }

    @Override
    public String toString() {
        return "Contrato{" +
                "nro='" + nro + '\'' +
                ", fecha=" + fecha +
                ", total=" + total +
                ", dniCliente='" + dniCliente + '\'' +
                ", idEmpleado=" + idEmpleado +
                '}';
    }
}