package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.UsuarioController;
import vallegrande.edu.pe.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UsuarioCrudView extends JFrame {

    private UsuarioController controller = new UsuarioController();
    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtNombre, txtEmail;
    private JButton btnAgregar, btnEditar, btnEliminar, btnVolver;
    private int filaSeleccionada = -1;

    public UsuarioCrudView() {
        setTitle("👤 Gestión de Usuarios");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        // ================= HEADER =================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(21, 67, 96));
        header.setBorder(BorderFactory.createEmptyBorder(15,25,15,25));

        JLabel logo = new JLabel("👤");
        logo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        logo.setForeground(Color.WHITE);
        logo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        JLabel titulo = new JLabel("GESTIÓN DE USUARIOS");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));

        JPanel logoTituloPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        logoTituloPanel.setBackground(new Color(21, 67, 96));
        logoTituloPanel.add(logo);
        logoTituloPanel.add(titulo);

        header.add(logoTituloPanel, BorderLayout.WEST);

        btnVolver = new JButton("⬅ Menú");
        btnVolver.setBackground(new Color(120,120,120));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));

        header.add(btnVolver, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // ================= FORMULARIO =================
        JPanel form = new JPanel(new GridLayout(2,2,10,10));
        form.setBorder(BorderFactory.createTitledBorder("Datos del Usuario"));
        form.setBackground(Color.WHITE);
        form.setPreferredSize(new Dimension(300,0));

        txtNombre = new JTextField();
        txtEmail = new JTextField();

        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Email:")); form.add(txtEmail);

        add(form, BorderLayout.WEST);

        // ================= TABLA =================
        modelo = new DefaultTableModel(new String[]{"Nombre","Email"},0){
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.getTableHeader().setReorderingAllowed(false);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ================= BOTONES =================
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER,15,10));
        botones.setBackground(Color.WHITE);

        btnAgregar = crearBoton("➕ Agregar", new Color(40, 116, 166));
        btnEditar = crearBoton("✏️ Editar", new Color(52, 152, 219));
        btnEliminar = crearBoton("🗑️ Eliminar", new Color(192, 57, 43));

        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);

        add(botones, BorderLayout.SOUTH);

        // ================= EVENTOS =================

        // Volver
        btnVolver.addActionListener(e -> {
            new MiniPaginaView().setVisible(true);
            dispose();
        });

        // Seleccionar fila -> cargar datos
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                filaSeleccionada = tabla.getSelectedRow();
                Usuario u = controller.listar().get(filaSeleccionada);
                txtNombre.setText(u.getNombre());
                txtEmail.setText(u.getEmail());
            }
        });

        // Agregar
        btnAgregar.addActionListener(e -> {
            if(validar()){
                controller.agregar(txtNombre.getText(), txtEmail.getText());
                JOptionPane.showMessageDialog(this, "Usuario agregado");
                limpiar();
                cargarTabla();
            }
        });

        // Editar (usando inputs)
        btnEditar.addActionListener(e -> {
            if(filaSeleccionada >= 0){
                if(validar()){
                    controller.editar(filaSeleccionada, txtNombre.getText(), txtEmail.getText());
                    JOptionPane.showMessageDialog(this, "Usuario actualizado");
                    limpiar();
                    cargarTabla();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un registro");
            }
        });

        // Eliminar
        btnEliminar.addActionListener(e -> {
            if(filaSeleccionada >= 0){
                int opcion = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if(opcion == JOptionPane.YES_OPTION){
                    controller.eliminar(filaSeleccionada);
                    JOptionPane.showMessageDialog(this, "Usuario eliminado");
                    limpiar();
                    cargarTabla();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un registro");
            }
        });

        cargarTabla();
        setVisible(true);
    }

    // ================= MÉTODOS =================

    private JButton crearBoton(String texto, Color color){
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private boolean validar(){
        if(txtNombre.getText().isEmpty() || txtEmail.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Complete todos los campos");
            return false;
        }

        if(!txtEmail.getText().contains("@")){
            JOptionPane.showMessageDialog(this, "Email inválido");
            return false;
        }

        return true;
    }

    private void limpiar(){
        txtNombre.setText("");
        txtEmail.setText("");
        tabla.clearSelection();
        filaSeleccionada = -1;
    }

    private void cargarTabla(){
        modelo.setRowCount(0);
        for(Usuario u : controller.listar()){
            modelo.addRow(new Object[]{u.getNombre(), u.getEmail()});
        }
    }
}