/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import entities.Inmueble;
import entities.Persona;
import entities.Propietario;

/**
 *
 * @author agusv
 */
public class InmuebleData {

    private Connection conexion = null;
    private PropietarioData propietarioData;

    public InmuebleData(Conexion conexion) {
        super();
        this.conexion = conexion.getConexion();
        propietarioData = new PropietarioData(conexion);
    }

    public Boolean agregarInmueble(Inmueble inmueble) {
        Boolean result = false;


        try {
            if(inmueble.getPropietario().getId()==null)            {
                Propietario propietario = new Propietario();
                
            }
            String querySql = "INSERT INTO inmueble(id_propietario,tipo_inmueble,estado_inmueble,zona,direccion,localidad,provincia,caracteristicas,monto_inicial,estado) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(querySql, RETURN_GENERATED_KEYS);
            ps.setInt(1, inmueble.getPropietario().getId());
            ps.setString(2, inmueble.getTipoInmueble());
            ps.setString(3, inmueble.getEstadoInmueble());
            ps.setString(4, inmueble.getZona());
            ps.setString(5, inmueble.getDireccion());
            ps.setString(6, inmueble.getLocalidad());
            ps.setString(7, inmueble.getProvincia());
            ps.setString(8, inmueble.getCaracteristicas());
            ps.setLong(9, inmueble.getMontoInicial());
            ps.setBoolean(10, inmueble.getEstado());
            ps.executeQuery();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                inmueble.setId(resultSet.getInt(1));
                result = true;
            }
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "No se ah podido guardar el inmueble\n" + e.getMessage(),
                    "Error al guardar el inmueble", JOptionPane.WARNING_MESSAGE);
        }
        return result;
    }

    public ArrayList<Inmueble> obtenerInmuebles() {
        ArrayList<Inmueble> inmuebleList = new ArrayList<>();
        try {
            String querySql = "SELECT * FROM inmueble";
            PreparedStatement ps = conexion.prepareStatement(querySql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Inmueble inmueble = new Inmueble();
                inmueble.setId(resultSet.getInt("id_inmueble"));
                // id_propietario,tipo_inmueble,estado_inmueble,zona,direccion,localidad,provincia,caracteristicas,monto_inicial,estado
                inmueble.setPropietario(propietarioData.obtenerPropietariosXId(resultSet.getInt("id_propietario")));
                inmueble.setTipoInmueble(resultSet.getString("tipo_inmueble"));
                inmueble.setEstadoInmueble(resultSet.getString("estado_inmueble"));
                inmueble.setZona(resultSet.getString("zona"));
                inmueble.setDireccion(resultSet.getString("direccion"));
                inmueble.setLocalidad(resultSet.getString("localidad"));
                inmueble.setProvincia(resultSet.getString("provincia"));
                inmueble.setCaracteristicas(resultSet.getString("caracteristicas"));
                inmueble.setMontoInicial(Long.parseLong(resultSet.getString("monto_inicial")));
                inmueble.setEstado(resultSet.getBoolean("estado"));
                inmuebleList.add(inmueble);
            }
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conseguir lista de propietarios" + e.getMessage());
        }
        return inmuebleList;
    }

    public Inmueble obtenerInmueblesXID(Integer id) {
        Inmueble inmueble = new Inmueble();
        try {
            String querySql = "SELECT * FROM inmueble WHERE id_inmueble=?";
            PreparedStatement ps = conexion.prepareStatement(querySql);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                inmueble.setId(resultSet.getInt("id_inmueble"));
                // id_propietario,tipo_inmueble,estado_inmueble,zona,direccion,localidad,provincia,caracteristicas,monto_inicial,estado
                inmueble.setPropietario(propietarioData.obtenerPropietariosXId(resultSet.getInt("id_propietario")));
                inmueble.setTipoInmueble(resultSet.getString("tipo_inmueble"));
                inmueble.setEstadoInmueble(resultSet.getString("estado_inmueble"));
                inmueble.setZona(resultSet.getString("zona"));
                inmueble.setDireccion(resultSet.getString("direccion"));
                inmueble.setLocalidad(resultSet.getString("localidad"));
                inmueble.setProvincia(resultSet.getString("provincia"));
                inmueble.setCaracteristicas(resultSet.getString("caracteristicas"));
                inmueble.setMontoInicial(Long.parseLong(resultSet.getString("monto_inicial")));
                inmueble.setEstado(resultSet.getBoolean("estado"));

            } else {
                inmueble = null;
            }
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conseguir lista de propietarios" + e.getMessage());
        }
        return inmueble;
    }

    public ArrayList<Inmueble> obtenerInmueblesNoAlquilados() {
        ArrayList<Inmueble> inmuebleList = new ArrayList<>();
        try {
            String querySql = "SELECT inmueble.* FROM inmueble LEFT JOIN contrato ON inmueble.id_inmueble=contrato.id_inmueble WHERE contrato.id_inmueble is Null";
            PreparedStatement ps = conexion.prepareStatement(querySql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Inmueble inmueble = new Inmueble();
                inmueble.setId(resultSet.getInt("id_inmueble"));
                // id_propietario,tipo_inmueble,estado_inmueble,zona,direccion,localidad,provincia,caracteristicas,monto_inicial,estado
                inmueble.setPropietario(propietarioData.obtenerPropietariosXId(resultSet.getInt("id_propietario")));
                inmueble.setTipoInmueble(resultSet.getString("tipo_inmueble"));
                inmueble.setEstadoInmueble(resultSet.getString("estado_inmueble"));
                inmueble.setZona(resultSet.getString("zona"));
                inmueble.setDireccion(resultSet.getString("direccion"));
                inmueble.setLocalidad(resultSet.getString("localidad"));
                inmueble.setProvincia(resultSet.getString("provincia"));
                inmueble.setCaracteristicas(resultSet.getString("caracteristicas"));
                inmueble.setMontoInicial(Long.parseLong(resultSet.getString("monto_inicial")));
                inmueble.setEstado(resultSet.getBoolean("estado"));
                inmuebleList.add(inmueble);
            }
            ps.close();
        } catch (Exception e) {
            System.out.println(
                    "Error al conseguir lista de inmuebles No alquilados\n" + e.getMessage() + "\n" + e.toString());
        }
        return inmuebleList;
    }

    public ArrayList<Inmueble> obtenerInmueblesAlquilados() {
        ArrayList<Inmueble> inmuebleList = new ArrayList<>();
        try {
            String querySql = "SELECT inmueble.* FROM inmueble INNER JOIN contrato ON inmueble.id_inmueble=contrato.id_inmueble";
            PreparedStatement ps = conexion.prepareStatement(querySql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Inmueble inmueble = new Inmueble();
                inmueble.setId(resultSet.getInt("id_inmueble"));
                // id_propietario,tipo_inmueble,estado_inmueble,zona,direccion,localidad,provincia,caracteristicas,monto_inicial,estado
                inmueble.setPropietario(propietarioData.obtenerPropietariosXId(resultSet.getInt("id_propietario")));
                inmueble.setTipoInmueble(resultSet.getString("tipo_inmueble"));
                inmueble.setEstadoInmueble(resultSet.getString("estado_inmueble"));
                inmueble.setZona(resultSet.getString("zona"));
                inmueble.setDireccion(resultSet.getString("direccion"));
                inmueble.setLocalidad(resultSet.getString("localidad"));
                inmueble.setProvincia(resultSet.getString("provincia"));
                inmueble.setCaracteristicas(resultSet.getString("caracteristicas"));
                inmueble.setMontoInicial(Long.parseLong(resultSet.getString("monto_inicial")));
                inmueble.setEstado(resultSet.getBoolean("estado"));
                inmuebleList.add(inmueble);
            }
            ps.close();
        } catch (Exception e) {
            System.out.println(
                    "Error al conseguir lista de inmuebles No alquilados\n" + e.getMessage() + "\n" + e.toString());
        }
        return inmuebleList;
    }

    public ArrayList<Inmueble> obtenerInmueblesNoAlquiladosXTipoYZona(String tipo, String zona) {
        ArrayList<Inmueble> inmuebleList = new ArrayList<>();
        try {
            String querySql = "SELECT inmueble.* FROM inmueble LEFT JOIN contrato ON contrato.id_inmueble=inmueble.id_inmueble WHERE  inmueble.zona=? AND inmueble.tipo_inmueble=? AND contrato.id_inmueble is Null";                    ;            
            PreparedStatement ps = conexion.prepareStatement(querySql);            
            ps.setString(1, zona);
            ps.setString(2, tipo);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Inmueble inmueble = new Inmueble();
                inmueble.setId(resultSet.getInt("id_inmueble"));
                // id_propietario,tipo_inmueble,estado_inmueble,zona,direccion,localidad,provincia,caracteristicas,monto_inicial,estado
                inmueble.setPropietario(propietarioData.obtenerPropietariosXId(resultSet.getInt("id_propietario")));
                inmueble.setTipoInmueble(resultSet.getString("tipo_inmueble"));
                inmueble.setEstadoInmueble(resultSet.getString("estado_inmueble"));
                inmueble.setZona(resultSet.getString("zona"));
                inmueble.setDireccion(resultSet.getString("direccion"));
                inmueble.setLocalidad(resultSet.getString("localidad"));
                inmueble.setProvincia(resultSet.getString("provincia"));
                inmueble.setCaracteristicas(resultSet.getString("caracteristicas"));
                inmueble.setMontoInicial(Long.parseLong(resultSet.getString("monto_inicial")));
                inmueble.setEstado(resultSet.getBoolean("estado"));
                inmuebleList.add(inmueble);
            }
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conseguir lista de propietarios" + e.getMessage());
        }
        return inmuebleList;
    }

    public ArrayList<Inmueble> buscarInmueblesXPropietario(Integer id) {
        ArrayList<Inmueble> inmuebleList = new ArrayList<>();

        try {
            String querySql = "SELECT * FROM inmueble WHERE id_propietario=? ";
            PreparedStatement ps = conexion.prepareStatement(querySql);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Inmueble inmueble = new Inmueble();
                inmueble.setId(resultSet.getInt("id_inmueble"));
                // id_propietario,tipo_inmueble,estado_inmueble,zona,direccion,localidad,provincia,caracteristicas,monto_inicial,estado
                inmueble.setPropietario(propietarioData.obtenerPropietariosXId(resultSet.getInt("id_propietario")));
                inmueble.setTipoInmueble(resultSet.getString("tipo_inmueble"));
                inmueble.setEstadoInmueble(resultSet.getString("estado_inmueble"));
                inmueble.setZona(resultSet.getString("zona"));
                inmueble.setDireccion(resultSet.getString("direccion"));
                inmueble.setLocalidad(resultSet.getString("localidad"));
                inmueble.setProvincia(resultSet.getString("provincia"));
                inmueble.setCaracteristicas(resultSet.getString("caracteristicas"));
                inmueble.setMontoInicial(Long.parseLong(resultSet.getString("monto_inicial")));
                inmueble.setEstado(resultSet.getBoolean("estado"));
                inmuebleList.add(inmueble);
            }
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conseguir lista de propietarios" + e.getMessage());
        }
        return inmuebleList;
    }

    public Boolean editarInmueble(Inmueble inmueble) {
        Boolean result = false;
        try {
            String querySql = "UPDATE inmueble SET id_propietario=?,tipo_inmueble=?,estado_inmueble=?,zona=?,direccion=?,localidad=?,provincia=?,caracteristicas=?,monto_inicial=?,estado=?  WHERE id_inmueble=?";
            PreparedStatement ps = conexion.prepareStatement(querySql);
            ps.setInt(1, inmueble.getPropietario().getId());
            ps.setString(2, inmueble.getTipoInmueble());
            ps.setString(3, inmueble.getEstadoInmueble());
            ps.setString(4, inmueble.getZona());
            ps.setString(5, inmueble.getDireccion());
            ps.setString(6, inmueble.getLocalidad());
            ps.setString(7, inmueble.getProvincia());
            ps.setString(8, inmueble.getCaracteristicas());
            ps.setLong(9, inmueble.getMontoInicial());
            ps.setBoolean(10, inmueble.getEstado());
            ps.setInt(11, inmueble.getId());
            if (ps.executeUpdate() != 0) {
                System.out.println("La persona fue modificada con exito");
                result = true;
            }

        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, "Error al actualizar el inmueble\n" + e.getMessage());
        }

        return result;
    }

    public Boolean eliminarInmueble(Integer id) {
        Boolean result = false;
        try {
            String querySql = "DELETE FROM inmueble WHERE id_inmueble=?";
            PreparedStatement ps = conexion.prepareStatement(querySql);
            ps.setInt(1, id);
            if (ps.executeUpdate() != 0) {
                System.out.println("El inmueble fue eliminada con exito");
                result = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error al eliminar el inmueble desde la base de datos: \n" + e.getMessage());
        }
        return result;
    }

}
