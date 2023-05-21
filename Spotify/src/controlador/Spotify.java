package controlador;

import modelo.DAO;
import modelo.DAOImplementacion;
import vista.VMain;

/**
 *
 * @author xDoble_Jx
 */
public class Spotify {

    public static void main(String[] args) {
        DAO dao = new DAOImplementacion();
        VMain VMain = new VMain(dao);
        VMain.setVisible(true);
    }
}
