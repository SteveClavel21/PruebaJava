/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesoadatos;

import entidades.Estudiante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author MINEDUCYT
 */
public class EstudianteDAL {
    
    
    
    
    
     public static int crear(Estudiante estudiante) {
        try (Connection conn = ComunDB.obtenerConexion()) {

            String sql = "INSERT INTO Estudiantes (Nombre, Apellido, Correo, Carrera) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, estudiante.getNombre());
                statement.setString(2, estudiante.getApellido());
                statement.setString(3, estudiante.getCorreo());
                statement.setString(4, estudiante.getCarrera());
                int rowsAffected = statement.executeUpdate();
                return rowsAffected;
            } catch (SQLException e) {
                throw new RuntimeException("Error al crear el estudiante", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la conexión a la base de datos", e);
        }
    }

    public static int modificar(Estudiante estudiante) {
        try (Connection conn = ComunDB.obtenerConexion()) {

            String sql = "UPDATE Estudiantes SET Nombre=?, Apellido=?, Correo=?, Carrera=?, WHERE EstudianteID=?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, estudiante.getNombre());
                statement.setString(2, estudiante.getApellido());
                statement.setString(3, estudiante.getCorreo());
                statement.setString(4, estudiante.getCarrera());
                statement.setInt(5, estudiante.getEstudianteId());
                int rowsAffected = statement.executeUpdate();
                return rowsAffected;
            } catch (SQLException e) {
                throw new RuntimeException("Error al crear el estudiante", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la conexión a la base de datos", e);
        }
    }

    public static int eliminar(Estudiante estudiante) {
        try (Connection conn = ComunDB.obtenerConexion()) {

            String sql = "DELETE FROM Estudiantes WHERE EstudianteID=?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, estudiante.getEstudianteId());
                int rowsAffected = statement.executeUpdate();
                return rowsAffected;
            } catch (SQLException e) {
                throw new RuntimeException("Error al crear el estudiante", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la conexión a la base de datos", e);
        }
    }

    public static ArrayList<Estudiante> buscar(Estudiante estudianteSearch) {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion()) {
            String sql = "SELECT e.EstudianteID, e.Nombre, e.Apellido, e.Correo, e.Carrera FROM Estudiantes e";
            
            sql+=" WHERE e.Nombre LIKE ? ";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, "%" + estudianteSearch.getNombre() + "%");
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Estudiante estudiante = new Estudiante();
                        estudiante.setEstudianteId(resultSet.getInt("EstudianteID"));
                        estudiante.setNombre(resultSet.getString("Nombre"));
                        estudiante.setApellido(resultSet.getString("Apellido"));
                        estudiante.setCorreo(resultSet.getString("Correo"));
                       estudiante.setCarrera(resultSet.getString("Carrera"));
                       estudiantes.add(estudiante);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al buscar estudiantes", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la conexión a la base de datos", e);
        }
        return estudiantes; 
    } 
    
   public static ArrayList<Estudiante> obtenerTodos() {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion()) {
            String sql = "select EstudianteID, Nombre, Apellido, Correo, Carrera from Estudiantes";           
            try (PreparedStatement statement = conn.prepareStatement(sql)) {                              
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int estudianteId = resultSet.getInt("EstudianteID");
                        String nombre = resultSet.getString("Nombre");
                        String apellido = resultSet.getString("Apellido");   
                        String correo = resultSet.getString("Correo");
                          String carrera = resultSet.getString("Carrera");
                        Estudiante estudiante = new Estudiante(estudianteId,nombre,apellido,correo,carrera );
                        estudiantes.add(estudiante);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al obtener los estudiantes", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la conexión a la base de datos", e);
        }
        return estudiantes;
   }
}
