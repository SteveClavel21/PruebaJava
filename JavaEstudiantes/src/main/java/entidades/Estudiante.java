package entidades;

import accesoadatos.ComunDB;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Estudiante {

    private int estudianteId;
    private String nombre;
    private String apellido;
    private String correo;
    private String carrera;

    public int getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(int estudianteId) {
        this.estudianteId = estudianteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public void crear(JTextField campoNombre, JTextField campoApellido, JTextField campoCorreo, JTextField campoCarrera) {
        String nombre = campoNombre.getText();
        String apellido = campoApellido.getText();
        String correo = campoCorreo.getText();
        String carrera = campoCarrera.getText();

        String consulta = "INSERT INTO Estudiantes (Nombre, Apellido, Correo, Carrera) VALUES (?, ?, ?, ?);";
        try {
            CallableStatement cs = ComunDB.obtenerConexion().prepareCall(consulta);
            cs.setString(1, nombre);
            cs.setString(2, apellido);
            cs.setString(3, correo);
            cs.setString(4, carrera);
            cs.execute();

            JOptionPane.showMessageDialog(null, "Se insertó correctamente el Estudiante");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar el Estudiante: " + e.toString());
        }
    }

    public void mostrarEstudiantes(JTable tablaEstudiantes) {
        DefaultTableModel modelo = new DefaultTableModel();
        TableRowSorter<TableModel> ordenarTabla = new TableRowSorter<>(modelo);
        tablaEstudiantes.setRowSorter(ordenarTabla);

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Correo");
        modelo.addColumn("Carrera");

        tablaEstudiantes.setModel(modelo);

        String consulta = "SELECT * FROM Estudiantes;";
        try {
            Statement st = ComunDB.obtenerConexion().createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("EstudianteID");
                fila[1] = rs.getString("Nombre");
                fila[2] = rs.getString("Apellido");
                fila[3] = rs.getString("Correo");
                fila[4] = rs.getString("Carrera");
                modelo.addRow(fila);
            }
            tablaEstudiantes.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los Estudiantes: " + e.toString());
        }
    }

    public void seleccionarEstudiante(JTable tablaEstudiantes, JTextField campoId, JTextField campoNombre, JTextField campoApellido, JTextField campoCorreo, JTextField campoCarrera) {
        try {
            int fila = tablaEstudiantes.getSelectedRow();
            if (fila >= 0) {
                campoId.setText(tablaEstudiantes.getValueAt(fila, 0).toString());
                campoNombre.setText(tablaEstudiantes.getValueAt(fila, 1).toString());
                campoApellido.setText(tablaEstudiantes.getValueAt(fila, 2).toString());
                campoCorreo.setText(tablaEstudiantes.getValueAt(fila, 3).toString());
                campoCarrera.setText(tablaEstudiantes.getValueAt(fila, 4).toString());
            } else {
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de selección: " + e.getMessage());
        }
    }

    public void modificarEstudiante(JTextField campoId, JTextField campoNombre, JTextField campoApellido, JTextField campoCorreo, JTextField campoCarrera) {
        int estudianteId = Integer.parseInt(campoId.getText());

        String consulta = "UPDATE Estudiantes SET Nombre = ?, Apellido = ?, Correo = ?, Carrera = ? WHERE EstudianteID = ?;";
        try {
            CallableStatement cs = ComunDB.obtenerConexion().prepareCall(consulta);
            cs.setString(1, campoNombre.getText());
            cs.setString(2, campoApellido.getText());
            cs.setString(3, campoCorreo.getText());
            cs.setString(4, campoCarrera.getText());
            cs.setInt(5, estudianteId);
            cs.execute();
            JOptionPane.showMessageDialog(null, "Modificación exitosa");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Modificar el Estudiante, error: " + e.toString());
        }
    }

    public void eliminarEstudiante(JTextField campoId) {
        int estudianteId = Integer.parseInt(campoId.getText());
        String consulta = "DELETE FROM Estudiantes WHERE EstudianteID = ?;";
        try {
            CallableStatement cs = ComunDB.obtenerConexion().prepareCall(consulta);
            cs.setInt(1, estudianteId);
            cs.execute();
            JOptionPane.showMessageDialog(null, "Estudiante con ID " + estudianteId + " eliminado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el Estudiante con ID " + estudianteId + ": " + e.toString());
        }
    }
}