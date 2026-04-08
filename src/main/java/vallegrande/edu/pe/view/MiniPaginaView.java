package vallegrande.edu.pe.view;

import javax.swing.*;
import java.awt.*;

public class MiniPaginaView extends JFrame {

    public MiniPaginaView() {
        setTitle("Sistema de Biblioteca - Portal Principal");
        setSize(850, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // 🔵 HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(21, 67, 96));
        header.setPreferredSize(new Dimension(850, 100));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // 🔥 LOGO DESDE INTERNET
        JLabel lblLogo = new JLabel();

        try {
            java.net.URL url = new java.net.URL("https://tse4.mm.bing.net/th/id/OIP.1ZtJtFBauUgPabtG5mg-PQHaHT?pid=Api&P=0&h=180");
            ImageIcon icono = new ImageIcon(url);
            Image img = icono.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblLogo.setText("📚");
            lblLogo.setFont(new Font("Arial", Font.BOLD, 28));
            lblLogo.setForeground(Color.WHITE);
        }

        // TITULO
        JLabel titulo = new JLabel("Sistema de Biblioteca");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));

        JLabel subtitulo = new JLabel("Instituto Valle Grande");
        subtitulo.setForeground(new Color(200, 220, 240));
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel textoHeader = new JPanel();
        textoHeader.setBackground(new Color(21, 67, 96));
        textoHeader.setLayout(new BoxLayout(textoHeader, BoxLayout.Y_AXIS));
        textoHeader.add(titulo);
        textoHeader.add(subtitulo);

        header.add(lblLogo, BorderLayout.WEST);
        header.add(textoHeader, BorderLayout.CENTER);

        panel.add(header, BorderLayout.NORTH);

        // ⚪ CENTRO
        JPanel centro = new JPanel();
        centro.setBackground(new Color(245, 247, 250));
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));

        JLabel bienvenida = new JLabel("Bienvenido al sistema");
        bienvenida.setFont(new Font("Segoe UI", Font.BOLD, 20));
        bienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descripcion = new JLabel("Gestione libros, préstamos y usuarios fácilmente");
        descripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descripcion.setAlignmentX(Component.CENTER_ALIGNMENT);

        centro.add(Box.createVerticalStrut(30));
        centro.add(bienvenida);
        centro.add(Box.createVerticalStrut(10));
        centro.add(descripcion);
        centro.add(Box.createVerticalStrut(40));

        // 🔘 BOTONES
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 25, 0));
        panelBotones.setBackground(new Color(245, 247, 250));
        panelBotones.setMaximumSize(new Dimension(700, 120));

        JButton btnLibros = crearBotonCard("📚 Libros", new Color(40, 116, 166));
        JButton btnPrestamos = crearBotonCard("📖 Préstamos", new Color(52, 152, 219));
        JButton btnUsuarios = crearBotonCard("👤 Usuarios", new Color(39, 174, 96));

        panelBotones.add(btnLibros);
        panelBotones.add(btnPrestamos);
        panelBotones.add(btnUsuarios);

        centro.add(panelBotones);

        panel.add(centro, BorderLayout.CENTER);

        // 🔽 FOOTER
        JPanel footer = new JPanel();
        footer.setBackground(new Color(230, 230, 230));
        footer.setPreferredSize(new Dimension(850, 40));

        JLabel copy = new JLabel("© 2026 Sistema de Biblioteca | Valle Grande");
        copy.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        footer.add(copy);
        panel.add(footer, BorderLayout.SOUTH);

        add(panel);

        // 🎯 EVENTOS
        btnLibros.addActionListener(e -> {
            new LibroCrudView().setVisible(true);
            dispose();
        });

        btnPrestamos.addActionListener(e -> {
            new PrestamoCrudView().setVisible(true);
            dispose();
        });

        btnUsuarios.addActionListener(e -> {
            new UsuarioCrudView().setVisible(true);
            dispose();
        });
    }

    // 🔥 BOTONES ESTILO PRO
    private JButton crearBotonCard(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        return btn;
    }
}