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

   /* public static ArrayList<Producto> buscar(Producto productoSearch) {
        ArrayList<Producto> productos = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion()) {
            String sql = "SELECT p.ProductoID, p.Nombre, p.Descripcion, p.Precio, p.CategoriaID, c.Nombre AS NombreCat FROM Productos p";
             sql+=" INNER JOIN Categorias c ON c.CategoriaID= p.CategoriaID  ";
            sql+=" WHERE p.Nombre LIKE ? ";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, "%" + productoSearch.getNombre() + "%");
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Producto producto = new Producto();
                        producto.setProductoId(resultSet.getInt("ProductoID"));
                        producto.setNombre(resultSet.getString("Nombre"));
                        producto.setDescripcion(resultSet.getString("Descripcion"));
                        producto.setPrecio(resultSet.getDouble("Precio"));
                        producto.setCategoriaId(resultSet.getInt("CategoriaID"));
                        Categoria categoria= new Categoria();
                        categoria.setNombre(resultSet.getString("NombreCat"));
                        producto.setCategoria(categoria);
                        productos.add(producto);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al buscar productos", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la conexión a la base de datos", e);
        }
        return productos; 
    } */
}
