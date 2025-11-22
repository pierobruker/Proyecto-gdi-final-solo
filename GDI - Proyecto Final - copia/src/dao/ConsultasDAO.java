package dao;
import db.DBConnection;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import model.Mueble;
public class ConsultasDAO {

    public void listarClientes() {
        String sql = "SELECT dni, nombre, direccion, telefono FROM Cliente";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.printf("%s | %s | %s | %s%n",
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // Model for GUI
    public DefaultTableModel modeloClientes() {
        DefaultTableModel m = new DefaultTableModel(new Object[]{"DNI","Nombre","Dirección","Teléfono"},0){public boolean isCellEditable(int r,int c){return false;}};
        String sql = "SELECT dni, nombre, direccion, telefono FROM Cliente";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                m.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)});
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return m;
    }

    public void listarMuebles() {
        String sql = "SELECT codigo, descripcion, precio, stock FROM Mueble ORDER BY descripcion";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.printf("%s | %s | %.2f | %d%n",
                        rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public DefaultTableModel modeloMuebles() {
        DefaultTableModel m = new DefaultTableModel(new Object[]{"Código","Descripción","Precio","Stock"},0){public boolean isCellEditable(int r,int c){return false;}};
        String sql = "SELECT codigo, descripcion, precio, stock FROM Mueble ORDER BY descripcion";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                m.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getInt(4)});
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return m;
    }

    // Obtener un mueble por código
    public Mueble obtenerMueble(String codigo){
        String sql = "SELECT codigo, descripcion, precio, stock FROM Mueble WHERE codigo = ?";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1,codigo);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Mueble m = new Mueble();
                    m.setCodigo(rs.getString(1));
                    m.setDescripcion(rs.getString(2));
                    m.setPrecio(rs.getDouble(3));
                    m.setStock(rs.getInt(4));
                    return m;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // Actualizar precio y stock de un mueble
    public boolean actualizarMueble(String codigo, double nuevoPrecio, int nuevoStock){
        String sql = "UPDATE Mueble SET precio = ?, stock = ? WHERE codigo = ?";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setDouble(1,nuevoPrecio);
            ps.setInt(2,nuevoStock);
            ps.setString(3,codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // Crear nuevo mueble
    public boolean crearMueble(String codigo, String descripcion, double precio, int stock){
        String sql = "INSERT INTO Mueble(codigo, descripcion, precio, stock) VALUES (?,?,?,?)";
        try(Connection cn = DBConnection.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setString(1,codigo);
            ps.setString(2,descripcion);
            ps.setDouble(3,precio);
            ps.setInt(4,stock);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){ e.printStackTrace(); }
        return false;
    }

    // Eliminar mueble
    public boolean eliminarMueble(String codigo){
        String sql = "DELETE FROM Mueble WHERE codigo = ?";
        try(Connection cn = DBConnection.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setString(1,codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){ e.printStackTrace(); }
        return false;
    }

    // Crear cliente
    public boolean crearCliente(String dni, String ruc, String nombre, String direccion, String telefono){
        String sql = "INSERT INTO Cliente(dni, ruc, nombre, direccion, telefono) VALUES (?,?,?,?,?)";
        try(Connection cn = DBConnection.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setString(1,dni);
            ps.setString(2,ruc);
            ps.setString(3,nombre);
            ps.setString(4,direccion);
            ps.setString(5,telefono);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){ e.printStackTrace(); }
        return false;
    }

    public void buscarContratosPorCliente(String dni) {
        String sql = "SELECT nro, fecha, tipo_pago, total FROM Contrato WHERE dni_cliente = ?";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("%s | %s | %s | %.2f%n",
                            rs.getString("nro"),
                            rs.getDate("fecha"),
                            rs.getString("tipo_pago"),
                            rs.getDouble("total"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public DefaultTableModel modeloContratosPorCliente(String dni) {
        DefaultTableModel m = new DefaultTableModel(new Object[]{"Nro","Fecha","Tipo Pago","Total"},0){public boolean isCellEditable(int r,int c){return false;}};
        String sql = "SELECT nro, fecha, tipo_pago, total FROM Contrato WHERE dni_cliente = ?";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    m.addRow(new Object[]{rs.getString(1),rs.getDate(2),rs.getString(3),rs.getDouble(4)});
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return m;
    }

    public void contratosPorEmpleado(int idEmpleado) {
        String sql = "SELECT nro, fecha, dni_cliente, total FROM Contrato WHERE id_empleado = ?";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idEmpleado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("%s | %s | %s | %.2f%n",
                            rs.getString("nro"),
                            rs.getDate("fecha"),
                            rs.getString("dni_cliente"),
                            rs.getDouble("total"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public DefaultTableModel modeloContratosPorEmpleado(int idEmpleado) {
        DefaultTableModel m = new DefaultTableModel(new Object[]{"Nro","Fecha","DNI Cliente","Total"},0){public boolean isCellEditable(int r,int c){return false;}};
        String sql = "SELECT nro, fecha, dni_cliente, total FROM Contrato WHERE id_empleado = ?";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idEmpleado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    m.addRow(new Object[]{rs.getString(1),rs.getDate(2),rs.getString(3),rs.getDouble(4)});
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return m;
    }

    public void resumenContratos() {
        String sql = "SELECT COUNT(*) AS total_contratos, SUM(total) AS monto_total FROM Contrato";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                System.out.printf("Total: %d | Suma: %.2f%n",
                        rs.getInt("total_contratos"),
                        rs.getDouble("monto_total"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public DefaultTableModel modeloResumenContratos() {
        DefaultTableModel m = new DefaultTableModel(new Object[]{"Total Contratos","Monto Total"},0){public boolean isCellEditable(int r,int c){return false;}};
        String sql = "SELECT COUNT(*) AS total_contratos, SUM(total) AS monto_total FROM Contrato";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                m.addRow(new Object[]{rs.getInt(1),rs.getDouble(2)});
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return m;
    }

    public void listarBoletas() {
        String sql = "SELECT nro_boleta, fecha, monto, nro_contrato_fk FROM Boleta_de_Venta ORDER BY fecha";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.printf("%s | %s | %.2f | %s%n",
                        rs.getString("nro_boleta"),
                        rs.getDate("fecha"),
                        rs.getDouble("monto"),
                        rs.getString("nro_contrato_fk"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public DefaultTableModel modeloBoletas() {
        DefaultTableModel m = new DefaultTableModel(new Object[]{"Nro Boleta","Fecha","Monto","Nro Contrato"},0){public boolean isCellEditable(int r,int c){return false;}};
        String sql = "SELECT nro_boleta, fecha, monto, nro_contrato_fk FROM Boleta_de_Venta ORDER BY fecha";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                m.addRow(new Object[]{rs.getString(1),rs.getDate(2),rs.getDouble(3),rs.getString(4)});
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return m;
    }

    public void creditosPendientes() {
        String sql = "SELECT nro, nombre AS cliente, total, a_cuenta, saldo " +
                     "FROM Contrato JOIN Cliente ON Contrato.dni_cliente = Cliente.dni " +
                     "WHERE tipo_pago = 'Crédito'";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.printf("%s | %s | %.2f | %.2f | %.2f%n",
                        rs.getString("nro"),
                        rs.getString("cliente"),
                        rs.getDouble("total"),
                        rs.getDouble("a_cuenta"),
                        rs.getDouble("saldo"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public DefaultTableModel modeloCreditosPendientes() {
        DefaultTableModel m = new DefaultTableModel(new Object[]{"Nro","Cliente","Total","A Cuenta","Saldo"},0){public boolean isCellEditable(int r,int c){return false;}};
        String sql = "SELECT nro, nombre AS cliente, total, a_cuenta, saldo FROM Contrato JOIN Cliente ON Contrato.dni_cliente = Cliente.dni WHERE tipo_pago = 'Crédito'";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                m.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getDouble(4),rs.getDouble(5)});
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return m;
    }

    public void mueblesBajoStock(int limite) {
        String sql = "SELECT codigo, descripcion, stock FROM Mueble WHERE stock <= ?";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("%s | %s | %d%n",
                            rs.getString("codigo"),
                            rs.getString("descripcion"),
                            rs.getInt("stock"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public DefaultTableModel modeloMueblesBajoStock(int limite) {
        DefaultTableModel m = new DefaultTableModel(new Object[]{"Código","Descripción","Stock"},0){public boolean isCellEditable(int r,int c){return false;}};
        String sql = "SELECT codigo, descripcion, stock FROM Mueble WHERE stock <= ?";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    m.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getInt(3)});
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return m;
    }

    public void historialVentasCliente(String dni) {
        String sql = "SELECT c.nombre AS cliente,b.nro_boleta,b.fecha,b.monto " +
                     "FROM Boleta_de_Venta b " +
                     "JOIN Contrato ct ON b.nro_contrato_fk = ct.nro " +
                     "JOIN Cliente c ON ct.dni_cliente = c.dni " +
                     "WHERE c.dni = ?";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("%s | %s | %s | %.2f%n",
                            rs.getString("cliente"),
                            rs.getString("nro_boleta"),
                            rs.getDate("fecha"),
                            rs.getDouble("monto"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public DefaultTableModel modeloHistorialVentasCliente(String dni) {
        DefaultTableModel m = new DefaultTableModel(new Object[]{"Cliente","Nro Boleta","Fecha","Monto"},0){public boolean isCellEditable(int r,int c){return false;}};
        String sql = "SELECT c.nombre AS cliente,b.nro_boleta,b.fecha,b.monto FROM Boleta_de_Venta b JOIN Contrato ct ON b.nro_contrato_fk = ct.nro JOIN Cliente c ON ct.dni_cliente = c.dni WHERE c.dni = ?";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    m.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getDate(3),rs.getDouble(4)});
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return m;
    }

    public void empleadoMasContratos() {
        String sql = "SELECT e.nombre AS empleado, COUNT(c.nro) AS total_contratos " +
                     "FROM Contrato c JOIN Empleado e ON c.id_empleado = e.id_empleado " +
                     "GROUP BY e.nombre ORDER BY total_contratos DESC LIMIT 1";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                System.out.printf("%s | %d%n",
                        rs.getString("empleado"),
                        rs.getInt("total_contratos"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public DefaultTableModel modeloEmpleadoMasContratos() {
        DefaultTableModel m = new DefaultTableModel(new Object[]{"Empleado","Total Contratos"},0){public boolean isCellEditable(int r,int c){return false;}};
        String sql = "SELECT e.nombre AS empleado, COUNT(c.nro) AS total_contratos FROM Contrato c JOIN Empleado e ON c.id_empleado = e.id_empleado GROUP BY e.nombre ORDER BY total_contratos DESC LIMIT 1";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                m.addRow(new Object[]{rs.getString(1),rs.getInt(2)});
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return m;
    }

    // Detalle completo de un contrato por número
    public DefaultTableModel modeloContratoDetalle(String nro) {
        DefaultTableModel m = new DefaultTableModel(new Object[]{
                "Nro","Fecha","Tipo Pago","Método","Total","A Cuenta","Saldo",
                "DNI Cliente","ID Empleado","Código Mueble","Descripción","Cantidad",
                "Precio Unitario","Descuento"
        },0){public boolean isCellEditable(int r,int c){return false;}};
        String sql = "SELECT nro, fecha, tipo_pago, metodo_pago, total, a_cuenta, saldo, dni_cliente, id_empleado, codigo_mueble, descripcion, cantidad, precio_unitario, descuento FROM Contrato WHERE nro = ?";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nro);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    m.addRow(new Object[]{
                            rs.getString("nro"), rs.getDate("fecha"), rs.getString("tipo_pago"), rs.getString("metodo_pago"),
                            rs.getDouble("total"), rs.getDouble("a_cuenta"), rs.getDouble("saldo"),
                            rs.getString("dni_cliente"), rs.getInt("id_empleado"), rs.getString("codigo_mueble"), rs.getString("descripcion"), rs.getInt("cantidad"),
                            rs.getDouble("precio_unitario"), rs.getDouble("descuento")
                    });
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return m;
    }

    // Listado general de contratos
    public DefaultTableModel modeloContratos() {
        DefaultTableModel m = new DefaultTableModel(new Object[]{
                "Nro","Fecha","Tipo Pago","Método","Total","A Cuenta","Saldo","DNI Cliente","ID Empleado"
        },0){public boolean isCellEditable(int r,int c){return false;}};
        String sql = "SELECT nro, fecha, tipo_pago, metodo_pago, total, a_cuenta, saldo, dni_cliente, id_empleado FROM Contrato ORDER BY fecha DESC";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                m.addRow(new Object[]{
                        rs.getString(1), rs.getDate(2), rs.getString(3), rs.getString(4),
                        rs.getDouble(5), rs.getDouble(6), rs.getDouble(7), rs.getString(8), rs.getInt(9)
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return m;
    }

    // Crear contrato (saldo calculado: total - a_cuenta)
    public boolean crearContrato(String nro, java.sql.Date fecha, String tipoPago, String metodoPago,
                                 double total, double aCuenta, String dniCliente, int idEmpleado,
                                 String codigoMueble, String descripcion, int cantidad, double precioUnitario, double descuento){
        double saldo = total - aCuenta;
        String sql = "INSERT INTO Contrato(nro, fecha, tipo_pago, metodo_pago, total, a_cuenta, saldo, dni_cliente, id_empleado, codigo_mueble, descripcion, cantidad, precio_unitario, descuento) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try(Connection cn = DBConnection.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setString(1,nro);
            ps.setDate(2,fecha);
            ps.setString(3,tipoPago);
            ps.setString(4,metodoPago);
            ps.setDouble(5,total);
            ps.setDouble(6,aCuenta);
            ps.setDouble(7,saldo);
            ps.setString(8,dniCliente);
            ps.setInt(9,idEmpleado);
            ps.setString(10,codigoMueble);
            ps.setString(11,descripcion);
            ps.setInt(12,cantidad);
            ps.setDouble(13,precioUnitario);
            ps.setDouble(14,descuento);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){ e.printStackTrace(); }
        return false;
    }

    public boolean eliminarContrato(String nro){
        String sql = "DELETE FROM Contrato WHERE nro = ?";
        try(Connection cn = DBConnection.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setString(1,nro);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){ e.printStackTrace(); }
        return false;
    }

    // Cancelar crédito: saldo = 0, a_cuenta = total
    public boolean cancelarCredito(String nro){
        String sql = "UPDATE Contrato SET a_cuenta = total, saldo = 0 WHERE nro = ? AND saldo > 0";
        try(Connection cn = DBConnection.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setString(1,nro);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){ e.printStackTrace(); }
        return false;
    }

    // CRUD Boletas
    public boolean crearBoleta(String nroBoleta, java.sql.Date fecha, double monto, String nroContrato){
        String sql = "INSERT INTO Boleta_de_Venta(nro_boleta, fecha, monto, nro_contrato_fk) VALUES (?,?,?,?)";
        try(Connection cn = DBConnection.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setString(1,nroBoleta);
            ps.setDate(2,fecha);
            ps.setDouble(3,monto);
            ps.setString(4,nroContrato);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){ e.printStackTrace(); }
        return false;
    }

    public boolean actualizarBoleta(String nroBoleta, java.sql.Date fecha, double monto, String nroContrato){
        String sql = "UPDATE Boleta_de_Venta SET fecha = ?, monto = ?, nro_contrato_fk = ? WHERE nro_boleta = ?";
        try(Connection cn = DBConnection.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setDate(1,fecha);
            ps.setDouble(2,monto);
            ps.setString(3,nroContrato);
            ps.setString(4,nroBoleta);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){ e.printStackTrace(); }
        return false;
    }

    public boolean eliminarBoleta(String nroBoleta){
        String sql = "DELETE FROM Boleta_de_Venta WHERE nro_boleta = ?";
        try(Connection cn = DBConnection.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setString(1,nroBoleta);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){ e.printStackTrace(); }
        return false;
    }
}
