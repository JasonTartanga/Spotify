package modelo;

import clases.Usuario;

/**
 *
 * @author xDoble_Jx
 */
public interface DAO {

    // ************************ USUARIO *************************
    // ************ INSERTS *************
    public void registrarUsuario(Usuario usu);

    // ************ SELECTS *************
    public Usuario iniciarSesion(String usuario, String contrasenia);

    public String calcularIdUsuario();
}
