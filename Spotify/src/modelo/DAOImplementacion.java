package modelo;

import clases.Usuario;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Properties;

/**
 *
 * @author xDoble_Jx
 */
public class DAOImplementacion implements DAO {

    private Connection con = null;
    private PreparedStatement stmt;

    //******************** USUARIO ********************/
    final private String REGISTRAR_USUARIO = "INSERT INTO USUARIO VALUES(?, ?, ?, ?, ?, ?)";
    final private String INICIAR_SESION = "SELECT * FROM usuario WHERE nombre = ? and contrasenia = ?";
    final private String CALCULAR_ID_USUARIO = "SELECT id_usuario FROM usuario ORDER BY id_usuario desc LIMIT 1";

    public void abrirConexion() {

        try {
            Properties configBDA = new Properties();
            String rutaProyecto = System.getProperty("user.dir");
            FileInputStream fis = new FileInputStream(rutaProyecto + "\\src\\modelo\\ConfigBD.properties");
            configBDA.load(fis);

            final String URL = configBDA.getProperty("url");
            final String USER = configBDA.getProperty("user");
            final String PASSWORD = configBDA.getProperty("password");

            con = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void cerrarConexion() {
        try {
            if (con != null) {
                con.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement prepararUsuario(Usuario usu) {
        try {
            stmt.setString(1, usu.getId_usuario());
            stmt.setString(2, usu.getNombre());
            stmt.setString(3, usu.getContrasenia());
            stmt.setString(4, usu.getCorreo());
            stmt.setDate(5, Date.valueOf(usu.getFecha_nac()));
            stmt.setString(6, usu.getIcono());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return stmt;
    }

    public Usuario getUsuario(ResultSet rs) {
        Usuario usu = new Usuario();

        try {
            usu.setId_usuario(rs.getString("id_usuario"));
            usu.setId_usuario(rs.getString("nombre"));
            usu.setId_usuario(rs.getString("contrasenia"));
            usu.setId_usuario(rs.getString("correo"));
            usu.setId_usuario(rs.getString("fecha_nac"));
            usu.setId_usuario(rs.getString("icono"));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return usu;
    }

    @Override
    public void registrarUsuario(Usuario usu) {

        this.abrirConexion();

        try {
            stmt = con.prepareStatement(REGISTRAR_USUARIO);
            stmt = this.prepararUsuario(usu);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.cerrarConexion();
    }

    @Override
    public Usuario iniciarSesion(String usuario, String contrasenia) {
        Usuario usu = null;
        this.abrirConexion();

        try {
            stmt = con.prepareStatement(INICIAR_SESION);
            stmt.setString(1, usuario);
            stmt.setString(2, contrasenia);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usu = new Usuario();
                usu = this.getUsuario(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usu;
    }

    @Override
    public String calcularIdUsuario() {
        String id_usuario = null;
        this.abrirConexion();

        try {
            stmt = con.prepareStatement(CALCULAR_ID_USUARIO);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                id_usuario = "U-" + String.format("%03d", Integer.parseInt(rs.getString("id_usuario").substring(2, 5)) + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.cerrarConexion();
        return id_usuario;
    }

}
