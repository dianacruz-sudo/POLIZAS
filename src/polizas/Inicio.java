package polizas;

import polizas.General.Controlador;
import polizas.General.Bienvenida;
import javax.swing.SwingUtilities;

public class Inicio {

    private Controlador controlador;
    private Bienvenida vista;

    public Inicio() {
        //Instanciar Bienvenida
        vista = new Bienvenida();
        controlador = new Controlador(vista);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Inicio::new);
    }
}
