package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.LibroController;
import vallegrande.edu.pe.model.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LibroCrudView extends JFrame {

    private LibroController controller = new LibroController();
    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtTitulo, txtAutor, txtCategoria, txtEstado, txtBuscar;
    private JButton btnAgregar, btnEditar, btnEliminar, btnGuardar, btnCancelar, btnMenu;
    private int filaSeleccionada = -1;

    public LibroCrudView() {
        setTitle("📚 Gestión de Libros");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        // ================= HEADER + BUSCADOR =================
        JPanel topPanel = new JPanel(new BorderLayout(5,5));
        topPanel.setBackground(new Color(21, 67, 96));

        JLabel tituloLabel = new JLabel("📚 Gestión de Libros");
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        topPanel.add(tituloLabel, BorderLayout.WEST);

        btnMenu = new JButton("⬅ Menú");
        btnMenu.setBackground(new Color(100,100,100));
        btnMenu.setForeground(Color.WHITE);
        btnMenu.setFocusPainted(false);
        topPanel.add(btnMenu, BorderLayout.EAST);

        // BUSCADOR
        JPanel buscador = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buscador.setBackground(Color.WHITE);
        buscador.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        buscador.add(new JLabel("🔍 Buscar:"));
        txtBuscar = new JTextField(20);
        buscador.add(txtBuscar);

        // Combinar header + buscador
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(topPanel, BorderLayout.NORTH);
        northPanel.add(buscador, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);

        // ================= FORMULARIO =================
        JPanel form = new JPanel(new GridLayout(4,2,10,10));
        form.setBorder(BorderFactory.createTitledBorder("Datos del Libro"));
        form.setBackground(Color.WHITE);
        form.setPreferredSize(new Dimension(300,0));

        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtCategoria = new JTextField();
        txtEstado = new JTextField();

        form.add(new JLabel("Título:")); form.add(txtTitulo);
        form.add(new JLabel("Autor:")); form.add(txtAutor);
        form.add(new JLabel("Categoría:")); form.add(txtCategoria);
        form.add(new JLabel("Estado:")); form.add(txtEstado);

        add(form, BorderLayout.WEST);

        // ================= TABLA =================
        modelo = new DefaultTableModel(new String[]{"Título","Autor","Categoría","Estado"},0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // Desactivar edición directa
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setReorderingAllowed(false);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ================= BOTONES =================
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        botones.setBackground(Color.WHITE);

        btnAgregar = crearBoton("➕ Nuevo", new Color(40, 116, 166));
        btnEditar = crearBoton("✏️ Editar", new Color(52, 152, 219));
        btnEliminar = crearBoton("🗑️ Eliminar", new Color(192, 57, 43));
        btnGuardar = crearBoton("💾 Guardar", new Color(39, 174, 96));
        btnCancelar = crearBoton("❌ Cancelar", new Color(100,100,100));

        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnGuardar);
        botones.add(btnCancelar);

        add(botones, BorderLayout.SOUTH);

        // ================= EVENTOS =================
        btnMenu.addActionListener(e -> {
            new MiniPaginaView().setVisible(true);
            dispose();
        });

        btnAgregar.addActionListener(e -> limpiar());

        btnGuardar.addActionListener(e -> {
            if(validar()){
                if(filaSeleccionada == -1) { // Nuevo
                    controller.agregar(txtTitulo.getText(), txtAutor.getText(), txtCategoria.getText(), txtEstado.getText());
                } else { // Editar
                    controller.editar(filaSeleccionada, txtTitulo.getText(), txtAutor.getText(), txtCategoria.getText(), txtEstado.getText());
                }
                limpiar();
                cargarTabla();
            }
        });

        btnCancelar.addActionListener(e -> limpiar());

        btnEditar.addActionListener(e -> {
            filaSeleccionada = tabla.getSelectedRow();
            if(filaSeleccionada >= 0){
                Libro l = controller.listar().get(filaSeleccionada);
                txtTitulo.setText(l.getTitulo());
                txtAutor.setText(l.getAutor());
                txtCategoria.setText(l.getCategoria());
                txtEstado.setText(l.getEstado());
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un registro");
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if(fila >= 0){
                int opcion = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if(opcion == JOptionPane.YES_OPTION){
                    controller.eliminar(fila);
                    cargarTabla();
                    limpiar();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un registro");
            }
        });

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) { filtrar(); }
        });

        cargarTabla();
        setVisible(true);
    }

    // ================= MÉTODOS AUXILIARES =================
    private JButton crearBoton(String texto, Color color){
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    private void filtrar(){
        String texto = txtBuscar.getText().toLowerCase();
        modelo.setRowCount(0);
        for(Libro l : controller.listar()){
            if(l.getTitulo().toLowerCase().contains(texto) ||
                    l.getAutor().toLowerCase().contains(texto)){
                modelo.addRow(new Object[]{l.getTitulo(), l.getAutor(), l.getCategoria(), l.getEstado()});
            }
        }
    }

    private boolean validar(){
        if(txtTitulo.getText().isEmpty() || txtAutor.getText().isEmpty() ||
                txtCategoria.getText().isEmpty() || txtEstado.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Complete todos los campos");
            return false;
        }
        return true;
    }

    private void limpiar(){
        txtTitulo.setText(""); txtAutor.setText(""); txtCategoria.setText(""); txtEstado.setText("");
        filaSeleccionada = -1;
        tabla.clearSelection();
    }

    private void cargarTabla(){
        modelo.setRowCount(0);
        for(Libro l : controller.listar()){
            modelo.addRow(new Object[]{l.getTitulo(), l.getAutor(), l.getCategoria(), l.getEstado()});
        }
    }
}