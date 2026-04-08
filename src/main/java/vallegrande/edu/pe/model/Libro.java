package vallegrande.edu.pe.model;

public class Libro {

    private String titulo;
    private String autor;
    private String categoria;
    private String estado;

    // CONSTRUCTOR
    public Libro(String titulo, String autor, String categoria, String estado) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.estado = estado;
    }

    // GETTERS
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getCategoria() { return categoria; }
    public String getEstado() { return estado; }

    // SETTERS
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setEstado(String estado) { this.estado = estado; }

    // 🔥 VALIDACIÓN INTERNA (EXTRA PRO)
    public boolean esValido() {
        return titulo != null && !titulo.trim().isEmpty() &&
                autor != null && !autor.trim().isEmpty() &&
                categoria != null && !categoria.trim().isEmpty() &&
                estado != null && !estado.trim().isEmpty();
    }

    // 🔥 PARA GUARDAR EN TXT
    public String toFile() {
        return titulo + "," + autor + "," + categoria + "," + estado;
    }

    // 🔥 PARA LEER DESDE TXT
    public static Libro fromFile(String linea) {
        String[] d = linea.split(",");
        if (d.length == 4) {
            return new Libro(d[0], d[1], d[2], d[3]);
        }
        return null;
    }

    // 🔥 PARA DEBUG / IMPRESIÓN
    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", categoria='" + categoria + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}