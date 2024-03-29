/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import entities.Inquilino;
import entities.Persona;

/**
 *
 * @author agusv
 */
public class InquilinoData {

    private Connection conn = null;
    PersonaData personaData;

    public InquilinoData(Conexion conn) {
        super();
        this.conn = conn.getConexion();
        personaData = new PersonaData(conn);
    }

    public Boolean agregarInquilino(Inquilino inquilino) {
        Boolean result = false;
        if (inquilino.getPersona().getId() != null) {
            // Editamos persona
            personaData.editarPersona(inquilino.getPersona());
        } else {
            // Agregamos persona
            personaData.agregarPersona(inquilino.getPersona());
        }

        try {
            String querySql = "INSERT INTO inquilino(id_persona,condicion,cant_renovacion) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(querySql, RETURN_GENERATED_KEYS);
            ps.setInt(1, inquilino.getPersona().getId());
            ps.setString(2, inquilino.getCondicion());
            ps.setInt(3, inquilino.getCantRenovaciones());
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();

            if (resultSet.next()) {
                inquilino.setId(resultSet.getInt(1));
                result = true;
            }
            ps.close();
            JOptionPane.showMessageDialog(null,
                    "Inquilino dni:" + inquilino.getPersona().getDni() + " agregado con exito\n",
                    "Inquilino agregado con exito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null,
                    "Error al ingresar el inquilino en la base de dato\n" + e.getMessage(),
                    "Error al insertar un dato en la BD", JOptionPane.WARNING_MESSAGE);
        }

        return result;
    }

    public ArrayList<Inquilino> obtenerInquilinos() {
        ArrayList<Inquilino> inquilinoList = new ArrayList<>();
        try {
            String querySql = "SELECT * FROM inquilino";
            PreparedStatement ps = conn.prepareStatement(querySql);
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                Inquilino inquilino = new Inquilino();

                inquilino.setId(result.getInt("id_inquilino"));
                inquilino.setPersona(personaData.obtenerPersonaXId(result.getInt("id_persona")));
                inquilino.setCondicion(result.getString("condicion"));
                inquilino.setCantRenovaciones(result.getInt("cant_renovacion"));
                inquilinoList.add(inquilino);
            }
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al conseguir lista de Inquilinos\n" + ex.getMessage());
        }

        return inquilinoList;
    }

    public Inquilino obtenerInquilinosXDni(Long dni) {
        Inquilino inquilino = new Inquilino();
        try {
            Persona persona = personaData.obtenerPersonaXDni(dni);
            if (persona.getNombre() != null) {
                inquilino.setPersona(persona);
                String querySql = "SELECT * FROM inquilino  WHERE id_persona=?";
                PreparedStatement ps = conn.prepareStatement(querySql);
                ps.setLong(1, persona.getId());
                ResultSet result = ps.executeQuery();
                while (result.next()) {
                    inquilino.setId(result.getInt("id_inquilino"));
                    inquilino.setCondicion(result.getString("condicion"));
                    inquilino.setCantRenovaciones(result.getInt("cant_renovacion"));
                }
                ps.close();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el inquilino DNI: " + dni + "\n" + e.getMessage());
        }
        return inquilino;
    }

    public Inquilino obtenerInquilinosXId(Integer id) {
        Inquilino inquilino = new Inquilino();
        try {
            String querySql = "SELECT * FROM inquilino  WHERE id_inquilino=?";
            PreparedStatement ps = conn.prepareStatement(querySql);
            ps.setLong(1, id);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                inquilino.setId(result.getInt("id_inquilino"));
                inquilino.setPersona(personaData.obtenerPersonaXId(result.getInt("id_persona")));
                inquilino.setCondicion(result.getString("condicion"));
                inquilino.setCantRenovaciones(result.getInt("cant_renovacion"));
            }
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el inquilino\n" + e.getMessage());
        }
        return inquilino;
    }

    public Boolean editarInquilino(Inquilino inquilino) {
        Boolean result = false;

        if (personaData.editarPersona(inquilino.getPersona())) {
            try {
                String querySql = "UPDATE inquilino SET condicion=?, cant_renovacion=?  WHERE id_inquilino=?";
                PreparedStatement ps = conn.prepareStatement(querySql);
                ps.setString(1, inquilino.getCondicion());
                ps.setInt(2, inquilino.getCantRenovaciones());
                ps.setInt(3, inquilino.getId());
                if (ps.executeUpdate() != 0) {
                    result = true;
                    JOptionPane.showMessageDialog(null, "El Inquilino fue editado con exito");
                }
                ps.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al editar el Inquilino: \n" + e.getMessage());
            }
        }
        return result;
    }

    public Boolean eliminarInquilino(Integer id) {
        Boolean result = false;
        try {
            String querySql = "DELETE FROM inquilino WHERE id_inquilino=?";
            PreparedStatement ps = conn.prepareStatement(querySql);
            ps.setInt(1, id);
            if (ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Inquilino eliminado");
                result = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null,
                    "Error al eliminar el inquilino desde la base de datos: \n" + e.getMessage(),
                    "Error al eliminar el Inquilino", JOptionPane.WARNING_MESSAGE);
        }
        return result;
    }
}
