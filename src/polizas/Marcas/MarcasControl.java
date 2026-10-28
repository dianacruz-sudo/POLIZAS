
package polizas.Marcas;

import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JOptionPane;

public class MarcasControl {
    
    private MarcasVista mv;
    private MarcasModelo mm;
    private List<Marca> lista;

    public MarcasControl (MarcasVista marcasVista) {
        this.mv = marcasVista;
        this.mm = new MarcasModelo();
        
        marcasEventos();
    }

    public void marcasEventos() {
        mv.btn1.addActionListener( e -> {
            lista = mm.read();
            mv.actualizarVista (lista);
        });

        mv.btn_a.addActionListener( e -> {
            Object marca = new Marca(0, mv.txt1.getText());
            JOptionPane.showMessageDialog(null,"Agregado correctamente");
             mm.create(marca);
            mv.actualizarVista(mm.read());
        });
        
        mv.btn_c.addActionListener( e -> {
            int fila = mv.tabla.getSelectedRow();
            if (fila != -1) {
                Marca marca = mv.marcasModelo.getMarca(fila);
                mm.delete(marca);
                JOptionPane.showMessageDialog(null, "Eliminado correctamente");
                mv.actualizarVista(mm.read());
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un registro en la tabla");
            }
        });
        mv.btn_b.addActionListener( e -> {
            int fila = mv.tabla.getSelectedRow();
            if (fila != -1) {
                int idSeleccionado = mv.marcasModelo.getMarca(fila).getId(); 
                Object marca = new Marca(idSeleccionado, mv.txt1.getText()); 
                mm.update(marca);
                JOptionPane.showMessageDialog(null, "Actualizado correctamente");
                mv.actualizarVista(mm.read());
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un registro en la tabla");
            }
        });

        mv.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked (java.awt.event.MouseEvent e) {
                System.out.println("Registro:" + mv.marcasModelo.getValueAt(mv.tabla.getSelectedRow(), 1));
                mv.txt1.setText((String) mv.marcasModelo.getValueAt(mv.tabla.getSelectedRow(), 1));
            }
        });

        mv.tabla.getSelectionModel().addListSelectionListener(
            e -> {
                if (!e.getValueIsAdjusting()) {
                    int fila = mv.tabla.getSelectedRow();
                    System.out.println("Registro: " + fila + " Valor: " + mv.tabla.getValueAt(fila, 1));
                }
            }
        );

        mv.cmbMarcas.addActionListener( e -> {
            Marca marca = (Marca) mv.cmbMarcas.getSelectedItem();
        });
    }
}