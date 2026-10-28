package polizas.Marcas;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import polizas.General.Conexion;
import polizas.General.DAO;

public class MarcasModelo extends Conexion implements DAO {

    MarcasModelo() {
        super();
    }

    @Override
    public List read() {
        List<Marca> lista = new ArrayList<>();
        try {
            this.conectar();
            ps = cnx.prepareStatement("SELECT * FROM marcas");
            rs = ps.executeQuery();
            lista.clear();
            while (rs.next()) {
                lista.add(new Marca(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try {
                cnx.close();
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        return lista;
    }

    @Override
    public int create(Object obj) {
        Marca marca = (Marca) obj;
        String nombre = marca.getNombre();

        //  No permitir campos vacíos
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre de la marca no puede estar vacío");
            return 0;
        }

        try {
            this.conectar();

            //  No valores duplicados
            ps = cnx.prepareStatement("SELECT COUNT(*) FROM marcas WHERE nombre = ?");
            ps.setString(1, nombre.trim());
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("La marca ya existe en la base de datos");
                return 0; 
            }
            
            ps = cnx.prepareStatement("insert into marcas (nombre) values (?) ");
            ps.setString(1, nombre.trim());
            return ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try {
                cnx.close();
            } catch (SQLException ex) {
                System.out.println("Error:" + ex.getMessage());
            }
        }
        return 0;
    }

    @Override
    public int update(Object lista) {
        Marca marca = (Marca) lista;
        String nombre = marca.getNombre();
        int id = marca.getId();
        
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre de la marca no puede estar vacío");
            return 0;
        }

        try {
            this.conectar();
            
            ps = cnx.prepareStatement("SELECT COUNT(*) FROM marcas WHERE nombre = ?, id = ?");
            ps.setString(1, nombre.trim());
            ps.setInt(2, id);
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Ya existe otra marca con ese nombre");
                return 0;
            }
            
            ps = cnx.prepareStatement("UPDATE marcas SET nombre = ? WHERE id = ?");
            ps.setString(1, nombre.trim());
            ps.setInt(2, id);
            return ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try {
                cnx.close();
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        return 0;
    }

    @Override
    public int delete(Object obj) {
        Marca marca = (Marca) obj;

        try {
            this.conectar();

            ps = cnx.prepareStatement("DELETE FROM marcas WHERE id = ?");
            ps.setInt(1, marca.getId());
            return ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try {
                cnx.close();
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        return 0;
    }
}
