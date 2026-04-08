package vallegrande.edu.pe.model;

import java.util.*;
import java.io.*;

public class InMemoryLibroRepository {

    private List<Libro> lista = new ArrayList<>();
    private final String FILE = "libros.txt";

    public InMemoryLibroRepository() {
        cargar();
    }

    public void agregar(Libro libro) {
        lista.add(libro);
        guardar();
    }

    public List<Libro> listar() {
        return lista;
    }

    public void eliminar(int index) {
        lista.remove(index);
        guardar();
    }

    public void actualizar(int index, Libro libro) {
        lista.set(index, libro);
        guardar();
    }

    private void guardar() {
        try (PrintWriter pw = new PrintWriter(FILE)) {
            for (Libro l : lista) {
                pw.println(l.getTitulo()+","+l.getAutor()+","+l.getCategoria()+","+l.getEstado());
            }
        } catch (Exception e) {}
    }

    private void cargar() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(",");
                lista.add(new Libro(d[0], d[1], d[2], d[3]));
            }
        } catch (Exception e) {}
    }
}