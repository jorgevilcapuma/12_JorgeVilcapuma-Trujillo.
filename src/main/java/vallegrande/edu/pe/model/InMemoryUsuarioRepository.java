package vallegrande.edu.pe.model;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUsuarioRepository {

    private List<Usuario> lista = new ArrayList<>();

    public void agregar(Usuario u){
        lista.add(u);
    }

    public List<Usuario> listar(){
        return lista;
    }

    public void eliminar(int index){
        if(index >= 0 && index < lista.size()){
            lista.remove(index);
        }
    }

    public void actualizar(int index, Usuario u){
        if(index >= 0 && index < lista.size()){
            lista.set(index, u);
        }
    }
}