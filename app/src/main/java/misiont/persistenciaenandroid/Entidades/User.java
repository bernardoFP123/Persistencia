package misiont.persistenciaenandroid.Entidades;

/**
 * Created by Bernardo_NoAdmin on 24/02/2018.
 */

public class User {

    private int id;
    private String nombre;
    private String contraseña;

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public boolean equals(Object obj) {
        User user =(User)obj;
        return user.getNombre().equals(getNombre());
    }
}
