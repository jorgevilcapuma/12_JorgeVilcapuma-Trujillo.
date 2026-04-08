package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.PrestamoController;
import vallegrande.edu.pe.model.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PrestamoCrudView extends JFrame {

    private PrestamoController controller = new PrestamoController();
    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtLibro, txtUsuario, txtFecha;

    public PrestamoCrudView() {
        setTitle("Gestión de Préstamos");
        setSize(800, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🔵 HEADER
        JPanel header = new JPanel();
        header.setBackground(new Color(21, 67, 96));

        JLabel titulo = new JLabel("📖 Gestión de Préstamos");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        header.add(titulo);
        add(header, BorderLayout.NORTH);

        // ⚪ FORMULARIO
        JPanel form = new JPanel(new GridLayout(2,3,15,10));
        form.setBorder(BorderFactory.createTitledBorder("Datos del Préstamo"));
        form.setBackground(Color.WHITE);

        txtLibro = new JTextField();
        txtUsuario = new JTextField();
        txtFecha = new JTextField();

        form.add(new JLabel("Libro:"));
        form.add(new JLabel("Usuario:"));
        form.add(new JLabel("Fecha:"));

        form.add(txtLibro);
        form.add(txtUsuario);
        form.add(txtFecha);

        add(form, BorderLayout.BEFORE_FIRST_LINE);

        // 📋 TABLA
        modelo = new DefaultTableModel(new String[]{"Libro","Usuario","Fecha"},0);
        tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.setSelectionBackground(new Color(174, 214, 241));

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // 🔘 BOTONES
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        botones.setBackground(Color.WHITE);

        JButton btnAgregar = crearBoton("➕ Agregar", new Color(40, 116, 166));
        JButton btnEditar = crearBoton("✏️ Editar", new Color(52, 152, 219));
        JButton btnEliminar = crearBoton("🗑️ Eliminar", new Color(192, 57, 43));
        JButton btnVolver = crearBoton("⬅ Menú", Color.GRAY);

        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnVolver);

        add(botones, BorderLayout.SOUTH);

        // 🎯 EVENTOS

        btnVolver.addActionListener(e -> {
            new MiniPaginaView().setVisible(true);
            dispose();
        });

        // AGREGAR
        btnAgregar.addActionListener(e -> {
            if(validar()){
                controller.agregar(
                        txtLibro.getText(),
                        txtUsuario.getText(),
                        txtFecha.getText()
                );
                limpiar();
                cargarTabla();
            }
        });

        // ELIMINAR
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if(fila >= 0){
                controller.eliminar(fila);
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Seleccione un registro");
            }
        });

        // ✏️ EDITAR (LO QUE TE PEDÍA LA PROFESORA)
        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();

            if(fila >= 0){
                Prestamo p = controller.listar().get(fila);

                String libro = JOptionPane.showInputDialog(this, "Libro:", p.getLibro());
                String usuario = JOptionPane.showInputDialog(this, "Usuario:", p.getUsuario());
                String fecha = JOptionPane.showInputDialog(this, "Fecha:", p.getFecha());

                if(libro == null || usuario == null || fecha == null ||
                        libro.isEmpty() || usuario.isEmpty() || fecha.isEmpty()){

                    JOptionPane.showMessageDialog(this, "⚠️ Complete todos los campos");
                    return;
                }

                // 👇 ACTUALIZA MANUAL (porque tu controller no tenía editar)
                controller.listar().set(fila, new Prestamo(libro, usuario, fecha));

                cargarTabla();

                JOptionPane.showMessageDialog(this, "✅ Préstamo actualizado");

            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Seleccione un registro");
            }
        });
    }

    // 🎨 BOTÓN MODERNO
    private JButton crearBoton(String texto, Color color){
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ✅ VALIDACIONES
    private boolean validar(){
        if(txtLibro.getText().isEmpty() ||
                txtUsuario.getText().isEmpty() ||
                txtFecha.getText().isEmpty()){

            JOptionPane.showMessageDialog(this, "⚠️ Complete todos los campos");
            return false;
        }
        return true;
    }

    private void limpiar(){
        txtLibro.setText("");
        txtUsuario.setText("");
        txtFecha.setText("");
    }

    private void cargarTabla(){
        modelo.setRowCount(0);
        for(Prestamo p : controller.listar()){
            modelo.addRow(new Object[]{
                    p.getLibro(),
                    p.getUsuario(),
                    p.getFecha()
            });
        }
    }
}