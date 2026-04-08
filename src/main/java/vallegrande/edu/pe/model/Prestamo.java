package vallegrande.edu.pe.model;

public class Prestamo {

    private String libro;
    private String usuario;
    private String fecha;

    // CONSTRUCTOR
    public Prestamo(String libro, String usuario, String fecha) {
        this.libro = libro;
        this.usuario = usuario;
        this.fecha = fecha;
    }

    // GETTERS
    public String getLibro() { return libro; }
    public String getUsuario() { return usuario; }
    public String getFecha() { return fecha; }

    // SETTERS (🔥 importante para editar)
    public void setLibro(String libro) { this.libro = libro; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    // 🔥 VALIDACIÓN (para formularios)
    public boolean esValido() {
        return libro != null && !libro.trim().isEmpty() &&
                usuario != null && !usuario.trim().isEmpty() &&
                fecha != null && !fecha.trim().isEmpty();
    }

    // 🔥 PARA GUARDAR EN TXT (persistencia)
    public String toFile() {
        return libro + "," + usuario + "," + fecha;
    }

    // 🔥 PARA LEER DESDE TXT
    public static Prestamo fromFile(String linea) {
        String[] d = linea.split(",");
        if (d.length == 3) {
            return new Prestamo(d[0], d[1], d[2]);
        }
        return null;
    }

    // 🔥 PARA DEBUG / IMPRESIÓN
    @Override
    public String toString() {
        return "Prestamo{" +
                "libro='" + libro + '\'' +
                ", usuario='" + usuario + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}