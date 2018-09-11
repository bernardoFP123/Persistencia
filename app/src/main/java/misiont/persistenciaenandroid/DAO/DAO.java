package misiont.persistenciaenandroid.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bernardo_NoAdmin on 24/02/2018.
 */

public interface DAO<T> {

    void insertar(T entidad);

    void eliminar(Integer id);

    ArrayList<T> listar();

    T buscar(Integer id);



    void actualizar(T entidad);

    boolean validate(T entidad);
}
