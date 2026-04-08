package vallegrande.edu.pe.model;

import java.util.ArrayList;
import java.util.List;

public class InMemoryPrestamooRepository {

    private List<Prestamo> lista = new ArrayList<>();

    // AGREGAR
    public void agregar(Prestamo prestamo) {
        lista.add(prestamo);
    }

    // LISTAR
    public List<Prestamo> listar() {
        return lista;
    }

    // ELIMINAR
    public void eliminar(int index) {
        lista.remove(index);
    }

    // ACTUALIZAR (EDITAR)
    public void actualizar(int index, Prestamo prestamo) {
        lista.set(index, prestamo);
    }
}