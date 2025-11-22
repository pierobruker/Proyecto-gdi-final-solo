package ui;
import dao.ConsultasDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame {
    private final ConsultasDAO dao = new ConsultasDAO();
    private final JTable table = new JTable();
    private final JLabel status = new JLabel("Listo");

    // Paleta actualizada (Principal, Secundario, Terciario)
    private static final Color COLOR_PRIMARY = Color.decode("#253054"); // Principal
    private static final Color COLOR_ACCENT = Color.decode("#cc2727");  // Secundario
    private static final Color COLOR_BG = Color.decode("#F4e39a");      // Terciario
    private static final Color COLOR_DARK = Color.decode("#4a4a48");

    public MainFrame() {
        setTitle("Muebleria Conceptos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100,650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_BG);

        // Barra superior
        JLabel header = new JLabel("Muebleria Conceptos",SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD,20f));
        header.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        header.setOpaque(true);
        header.setBackground(COLOR_PRIMARY);
        header.setForeground(COLOR_BG);
        add(header,BorderLayout.NORTH);

        // Panel lateral con acciones
        JPanel side = new JPanel(new GridLayout(0,1,4,4));
        side.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        side.setBackground(COLOR_PRIMARY);

        // Menú Clientes (listar + agregar)
        side.add(btn("Clientes",()-> {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem listar = new JMenuItem("Listar Clientes");
            listar.addActionListener(e -> setModel(dao.modeloClientes(),"Clientes cargados"));
            JMenuItem agregar = new JMenuItem("Agregar Cliente");
            agregar.addActionListener(e -> mostrarDialogoNuevoCliente());
            menu.add(listar);
            menu.add(agregar);
            menu.show(side,0,0);
        }));

        // Menú Muebles agrupado
        side.add(btn("Muebles",()-> {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem listar = new JMenuItem("Listar Muebles");
            listar.addActionListener(e -> setModel(dao.modeloMuebles(),"Muebles listados"));
            JMenuItem editar = new JMenuItem("Editar Mueble");
            editar.addActionListener(e -> editarMueble());
            JMenuItem nuevo = new JMenuItem("Nuevo Mueble");
            nuevo.addActionListener(e -> crearMueble());
            JMenuItem eliminar = new JMenuItem("Eliminar Mueble");
            eliminar.addActionListener(e -> eliminarMueble());
            menu.add(listar);
            menu.add(editar);
            menu.add(nuevo);
            menu.add(eliminar);
            menu.show(side,0,0);
        }));

        // Menú Contratos (listar, detalle, nuevo, eliminar)
        side.add(btn("Contratos",()-> {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem listar = new JMenuItem("Listar Contratos");
            listar.addActionListener(e -> setModel(dao.modeloContratos(),"Contratos listados"));
            JMenuItem detalle = new JMenuItem("Detalle Contrato");
            detalle.addActionListener(e -> {
                String nro = JOptionPane.showInputDialog(this,"Número de Contrato:");
                if(nro!=null && !nro.trim().isEmpty()) setModel(dao.modeloContratoDetalle(nro.trim()),"Detalle " + nro.trim());
            });
            JMenuItem nuevo = new JMenuItem("Nuevo Contrato");
            nuevo.addActionListener(e -> crearContrato());
            JMenuItem eliminar = new JMenuItem("Eliminar Contrato");
            eliminar.addActionListener(e -> eliminarContrato());
            menu.add(listar);
            menu.add(detalle);
            menu.add(nuevo);
            menu.add(eliminar);
            menu.show(side,0,0);
        }));

        // Menú Boletas (listar, nueva, editar, eliminar)
        side.add(btn("Boletas",()-> {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem listar = new JMenuItem("Listar Boletas");
            listar.addActionListener(e -> setModel(dao.modeloBoletas(),"Boletas listadas"));
            JMenuItem nueva = new JMenuItem("Nueva Boleta");
            nueva.addActionListener(e -> crearBoleta());
            JMenuItem editar = new JMenuItem("Editar Boleta");
            editar.addActionListener(e -> editarBoleta());
            JMenuItem eliminar = new JMenuItem("Eliminar Boleta");
            eliminar.addActionListener(e -> eliminarBoleta());
            menu.add(listar);
            menu.add(nueva);
            menu.add(editar);
            menu.add(eliminar);
            menu.show(side,0,0);
        }));

        // Créditos Pendientes (listar + cancelar)
        side.add(btn("Créditos",()-> {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem listar = new JMenuItem("Listar Créditos Pendientes");
            listar.addActionListener(e -> setModel(dao.modeloCreditosPendientes(),"Créditos pendientes"));
            JMenuItem cancelar = new JMenuItem("Cancelar Crédito");
            cancelar.addActionListener(e -> cancelarCredito());
            menu.add(listar);
            menu.add(cancelar);
            menu.show(side,0,0);
        }));
        side.add(btn("Historial Ventas Cliente",()-> {
            String dni = JOptionPane.showInputDialog(this,"DNI Cliente:");
            if(dni!=null && !dni.trim().isEmpty()) setModel(dao.modeloHistorialVentasCliente(dni.trim()),"Historial ventas " + dni.trim());
        }));
        // Eliminado botón Empleado Top para simplificar sección de contratos

        JScrollPane spSide = new JScrollPane(side); // por si crece
        spSide.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spSide.setPreferredSize(new Dimension(260, getHeight()));
        spSide.getViewport().setBackground(COLOR_PRIMARY);
        spSide.setBorder(BorderFactory.createMatteBorder(0,0,0,1,COLOR_DARK));
        add(spSide,BorderLayout.WEST);

        // Tabla central
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.getViewport().setBackground(COLOR_BG);
        table.setBackground(COLOR_BG);
        table.setForeground(COLOR_DARK);
        table.setGridColor(COLOR_DARK);
        table.getTableHeader().setBackground(COLOR_ACCENT);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD));
        add(tableScroll,BorderLayout.CENTER);

        // Barra inferior
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(COLOR_DARK);
        status.setBorder(BorderFactory.createEmptyBorder(4,8,4,8));
        status.setForeground(COLOR_BG);
        bottom.add(status,BorderLayout.WEST);
        add(bottom,BorderLayout.SOUTH);

        applyButtonStyling(side);
    }

    private JButton btn(String text, Runnable action){
        JButton b = new JButton(text);
        b.addActionListener(e -> {
            try { action.run(); } catch(Exception ex){ error(ex.getMessage()); }
        });
        b.setBackground(COLOR_ACCENT);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_DARK),
                BorderFactory.createEmptyBorder(6,10,6,10)));
        return b;
    }

    private void applyButtonStyling(JPanel side){
        for(Component c : side.getComponents()){
            if(c instanceof JButton){
                ((JButton)c).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }
    }

    private void setModel(DefaultTableModel model, String msg){
        table.setModel(model);
        status.setText(msg + " (" + model.getRowCount() + " filas)" );
    }

    private void error(String msg){
        JOptionPane.showMessageDialog(this,msg==null?"Error desconocido":msg,"Error",JOptionPane.ERROR_MESSAGE);
        status.setText("Error: " + msg);
    }

    // --- Clientes ---
    private void mostrarDialogoNuevoCliente(){
        JPanel panel = new JPanel(new GridLayout(0,2,4,4));
        JTextField dniField = new JTextField();
        JTextField rucField = new JTextField();
        JTextField nombreField = new JTextField();
        JTextField dirField = new JTextField();
        JTextField telField = new JTextField();
        panel.add(new JLabel("DNI:")); panel.add(dniField);
        panel.add(new JLabel("RUC:")); panel.add(rucField);
        panel.add(new JLabel("Nombre:")); panel.add(nombreField);
        panel.add(new JLabel("Dirección:")); panel.add(dirField);
        panel.add(new JLabel("Teléfono:")); panel.add(telField);
        int opt = JOptionPane.showConfirmDialog(this,panel,"Agregar Cliente",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if(opt==JOptionPane.OK_OPTION){
            String dni = dniField.getText().trim();
            String ruc = rucField.getText().trim();
            String nombre = nombreField.getText().trim();
            String dir = dirField.getText().trim();
            String tel = telField.getText().trim();
            if(dni.isEmpty() || nombre.isEmpty()){ error("DNI y Nombre requeridos"); return; }
            boolean ok = dao.crearCliente(dni,ruc,nombre,dir,tel);
            if(ok){ setModel(dao.modeloClientes(),"Cliente agregado"); }
            else error("No se pudo crear (¿duplicado?)");
        }
    }

    // --- Muebles ---
    private void editarMueble(){
        String codigo = JOptionPane.showInputDialog(this,"Código de Mueble:");
        if(codigo==null || codigo.trim().isEmpty()) return;
        model.Mueble mueble = dao.obtenerMueble(codigo.trim());
        if(mueble==null){ error("No existe código " + codigo); return; }
        JPanel panel = new JPanel(new GridLayout(0,2,4,4));
        JTextField precioField = new JTextField(String.valueOf(mueble.getPrecio()));
        JTextField stockField = new JTextField(String.valueOf(mueble.getStock()));
        panel.add(new JLabel("Descripción:")); panel.add(new JLabel(mueble.getDescripcion()));
        panel.add(new JLabel("Precio:")); panel.add(precioField);
        panel.add(new JLabel("Stock:")); panel.add(stockField);
        int opt = JOptionPane.showConfirmDialog(this,panel,"Editar Mueble " + mueble.getCodigo(),JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if(opt==JOptionPane.OK_OPTION){
            try {
                double nuevoPrecio = Double.parseDouble(precioField.getText().trim());
                int nuevoStock = Integer.parseInt(stockField.getText().trim());
                if(nuevoPrecio < 0 || nuevoStock < 0){ error("Valores negativos no permitidos"); return; }
                boolean ok = dao.actualizarMueble(mueble.getCodigo(),nuevoPrecio,nuevoStock);
                if(ok){ setModel(dao.modeloMuebles(),"Mueble actualizado"); }
                else error("No se pudo actualizar");
            } catch (NumberFormatException ex){ error("Formato inválido"); }
        }
    }

    private void crearMueble(){
        JPanel panel = new JPanel(new GridLayout(0,2,4,4));
        JTextField codigoField = new JTextField();
        JTextField descripcionField = new JTextField();
        JTextField precioField = new JTextField();
        JTextField stockField = new JTextField();
        panel.add(new JLabel("Código:")); panel.add(codigoField);
        panel.add(new JLabel("Descripción:")); panel.add(descripcionField);
        panel.add(new JLabel("Precio:")); panel.add(precioField);
        panel.add(new JLabel("Stock:")); panel.add(stockField);
        int opt = JOptionPane.showConfirmDialog(this,panel,"Crear Mueble",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if(opt==JOptionPane.OK_OPTION){
            String codigo = codigoField.getText().trim();
            String desc = descripcionField.getText().trim();
            try {
                double precio = Double.parseDouble(precioField.getText().trim());
                int stock = Integer.parseInt(stockField.getText().trim());
                if(codigo.isEmpty() || desc.isEmpty()){ error("Campos vacíos"); return; }
                if(precio < 0 || stock < 0){ error("Valores negativos"); return; }
                if(dao.obtenerMueble(codigo)!=null){ error("Código ya existe"); return; }
                boolean ok = dao.crearMueble(codigo,desc,precio,stock);
                if(ok){ setModel(dao.modeloMuebles(),"Mueble creado"); }
                else error("No se pudo crear");
            } catch (NumberFormatException ex){ error("Formato inválido"); }
        }
    }

    private void eliminarMueble(){
        String codigo = JOptionPane.showInputDialog(this,"Código a eliminar:");
        if(codigo==null || codigo.trim().isEmpty()) return;
        if(dao.obtenerMueble(codigo.trim())==null){ error("No existe código " + codigo); return; }
        int opt = JOptionPane.showConfirmDialog(this,"¿Eliminar mueble " + codigo + "?","Confirmar",JOptionPane.YES_NO_OPTION);
        if(opt==JOptionPane.YES_OPTION){
            boolean ok = dao.eliminarMueble(codigo.trim());
            if(ok){ setModel(dao.modeloMuebles(),"Mueble eliminado"); }
            else error("No se pudo eliminar");
        }
    }

    // --- Contratos ---
    private void crearContrato(){
        JPanel panel = new JPanel(new GridLayout(0,2,4,4));
        JTextField nroField = new JTextField();
        JTextField fechaField = new JTextField(java.time.LocalDate.now().toString());
        JTextField tipoField = new JTextField("Contado");
        JTextField metodoField = new JTextField("Efectivo");
        JTextField totalField = new JTextField();
        JTextField aCuentaField = new JTextField("0");
        JTextField dniField = new JTextField();
        JTextField empleadoField = new JTextField();
        JTextField codMuebleField = new JTextField();
        JTextField descField = new JTextField();
        JTextField cantField = new JTextField("1");
        JTextField precioUnitField = new JTextField();
        JTextField descPctField = new JTextField("0");
        panel.add(new JLabel("Nro:")); panel.add(nroField);
        panel.add(new JLabel("Fecha (YYYY-MM-DD):")); panel.add(fechaField);
        panel.add(new JLabel("Tipo Pago:")); panel.add(tipoField);
        panel.add(new JLabel("Método Pago:")); panel.add(metodoField);
        panel.add(new JLabel("Total:")); panel.add(totalField);
        panel.add(new JLabel("A Cuenta:")); panel.add(aCuentaField);
        panel.add(new JLabel("DNI Cliente:")); panel.add(dniField);
        panel.add(new JLabel("ID Empleado:")); panel.add(empleadoField);
        panel.add(new JLabel("Código Mueble:")); panel.add(codMuebleField);
        panel.add(new JLabel("Descripción:")); panel.add(descField);
        panel.add(new JLabel("Cantidad:")); panel.add(cantField);
        panel.add(new JLabel("Precio Unitario:")); panel.add(precioUnitField);
        panel.add(new JLabel("Descuento:")); panel.add(descPctField);
        int opt = JOptionPane.showConfirmDialog(this,panel,"Nuevo Contrato",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if(opt==JOptionPane.OK_OPTION){
            try {
                String nro = nroField.getText().trim();
                java.sql.Date fecha = java.sql.Date.valueOf(fechaField.getText().trim());
                double total = Double.parseDouble(totalField.getText().trim());
                double aCuenta = Double.parseDouble(aCuentaField.getText().trim());
                int idEmp = Integer.parseInt(empleadoField.getText().trim());
                int cantidad = Integer.parseInt(cantField.getText().trim());
                double precioUnit = Double.parseDouble(precioUnitField.getText().trim());
                double descuento = Double.parseDouble(descPctField.getText().trim());
                if(nro.isEmpty() || dniField.getText().trim().isEmpty()) { error("Nro y DNI requeridos"); return; }
                boolean ok = dao.crearContrato(nro,fecha,tipoField.getText().trim(),metodoField.getText().trim(),total,aCuenta,
                        dniField.getText().trim(),idEmp,codMuebleField.getText().trim(),descField.getText().trim(),cantidad,precioUnit,descuento);
                if(ok) setModel(dao.modeloContratos(),"Contrato creado"); else error("No se pudo crear");
            } catch(Exception ex){ error("Datos inválidos"); }
        }
    }

    private void eliminarContrato(){
        String nro = JOptionPane.showInputDialog(this,"Número de Contrato a eliminar:");
        if(nro==null || nro.trim().isEmpty()) return;
        int opt = JOptionPane.showConfirmDialog(this,"¿Eliminar contrato " + nro + "?","Confirmar",JOptionPane.YES_NO_OPTION);
        if(opt==JOptionPane.YES_OPTION){
            boolean ok = dao.eliminarContrato(nro.trim());
            if(ok) setModel(dao.modeloContratos(),"Contrato eliminado"); else error("No se pudo eliminar");
        }
    }

    // --- Boletas ---
    private void crearBoleta(){
        JPanel panel = new JPanel(new GridLayout(0,2,4,4));
        JTextField nroField = new JTextField();
        JTextField fechaField = new JTextField(java.time.LocalDate.now().toString());
        JTextField montoField = new JTextField();
        JTextField contratoField = new JTextField();
        panel.add(new JLabel("Nro Boleta:")); panel.add(nroField);
        panel.add(new JLabel("Fecha:")); panel.add(fechaField);
        panel.add(new JLabel("Monto:")); panel.add(montoField);
        panel.add(new JLabel("Nro Contrato:")); panel.add(contratoField);
        int opt = JOptionPane.showConfirmDialog(this,panel,"Nueva Boleta",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if(opt==JOptionPane.OK_OPTION){
            try {
                java.sql.Date fecha = java.sql.Date.valueOf(fechaField.getText().trim());
                double monto = Double.parseDouble(montoField.getText().trim());
                boolean ok = dao.crearBoleta(nroField.getText().trim(),fecha,monto,contratoField.getText().trim());
                if(ok) setModel(dao.modeloBoletas(),"Boleta creada"); else error("No se pudo crear");
            } catch(Exception ex){ error("Datos inválidos"); }
        }
    }

    private void editarBoleta(){
        String nro = JOptionPane.showInputDialog(this,"Nro Boleta a editar:");
        if(nro==null || nro.trim().isEmpty()) return;
        // Pedir nuevos valores
        JPanel panel = new JPanel(new GridLayout(0,2,4,4));
        JTextField fechaField = new JTextField(java.time.LocalDate.now().toString());
        JTextField montoField = new JTextField();
        JTextField contratoField = new JTextField();
        panel.add(new JLabel("Nueva Fecha:")); panel.add(fechaField);
        panel.add(new JLabel("Nuevo Monto:")); panel.add(montoField);
        panel.add(new JLabel("Contrato:")); panel.add(contratoField);
        int opt = JOptionPane.showConfirmDialog(this,panel,"Editar Boleta " + nro,JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if(opt==JOptionPane.OK_OPTION){
            try {
                java.sql.Date fecha = java.sql.Date.valueOf(fechaField.getText().trim());
                double monto = Double.parseDouble(montoField.getText().trim());
                boolean ok = dao.actualizarBoleta(nro.trim(),fecha,monto,contratoField.getText().trim());
                if(ok) setModel(dao.modeloBoletas(),"Boleta actualizada"); else error("No se pudo actualizar");
            } catch(Exception ex){ error("Datos inválidos"); }
        }
    }

    private void eliminarBoleta(){
        String nro = JOptionPane.showInputDialog(this,"Nro Boleta a eliminar:");
        if(nro==null || nro.trim().isEmpty()) return;
        int opt = JOptionPane.showConfirmDialog(this,"¿Eliminar boleta " + nro + "?","Confirmar",JOptionPane.YES_NO_OPTION);
        if(opt==JOptionPane.YES_OPTION){
            boolean ok = dao.eliminarBoleta(nro.trim());
            if(ok) setModel(dao.modeloBoletas(),"Boleta eliminada"); else error("No se pudo eliminar");
        }
    }

    // --- Créditos ---
    private void cancelarCredito(){
        String nro = JOptionPane.showInputDialog(this,"Número de Contrato a cancelar crédito:");
        if(nro==null || nro.trim().isEmpty()) return;
        int opt = JOptionPane.showConfirmDialog(this,"¿Marcar crédito como cancelado?","Confirmar",JOptionPane.YES_NO_OPTION);
        if(opt==JOptionPane.YES_OPTION){
            boolean ok = dao.cancelarCredito(nro.trim());
            if(ok) setModel(dao.modeloCreditosPendientes(),"Crédito cancelado"); else error("No se pudo cancelar (¿ya cancelado?)");
        }
    }
}