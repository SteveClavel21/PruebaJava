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
     public static ArrayList<Estudiante> obtenerTodos() {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion()) {
            String sql = "SELECT EstudianteID, Nombre, Apellido, Correo, Carrera  FROM Estudiantes";           
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
