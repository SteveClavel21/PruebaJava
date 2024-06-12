/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import accesoadatos.ComunDB;
import java.sql.CallableStatement;
import java.sql.Date;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author MINEDUCYT
 */
public class Evaluacione {

    private String evaluacionId;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;
    private double puntaje;
    private int estudianteId;

    private Estudiante estudiantes;

    public String getEvaluacionId() {
        return evaluacionId;
    }

    public void setEvaluacionId(String evaluacionId) {
        this.evaluacionId = evaluacionId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }

    public int getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(int estudianteId) {
        this.estudianteId = estudianteId;
    }

    public Estudiante getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(Estudiante estudiantes) {
        this.estudiantes = estudiantes;
    }

     public void crear(JTextField campoTitulo, JTextField campoDescripcion, JTextField campoFecha, JTextField campoPuntaje) {
        String titulo = campoTitulo.getText();
        String descripcion = campoDescripcion.getText();
        String fecha = campoFecha.getText();
        String puntaje = campoPuntaje.getText();
        
            

        String consulta = "INSERT INTO Evaluaciones (Titulo, Descripcion, Fecha, Puntaje) VALUES (?, ?, ?, ?);";
        try {
            CallableStatement cs = ComunDB.obtenerConexion().prepareCall(consulta);
            cs.setString(1, titulo);
            cs.setString(2, descripcion);
            cs.setDate(3, Date.valueOf(fecha));
            cs.setString(4, puntaje);
            cs.execute();

            JOptionPane.showMessageDialog(null, "Se insertó correctamente la Evaluacion");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar la Evaluacion: " + e.toString());
        }
    }
     
     public void mostrarEstudiantes(JTable tablaEvaluaciones) {
        DefaultTableModel modelo = new DefaultTableModel();
        TableRowSorter<TableModel> ordenarTabla = new TableRowSorter<>(modelo);
        tablaEvaluaciones.setRowSorter(ordenarTabla);

        modelo.addColumn("ID");
        modelo.addColumn("Titulo");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Fecha");
        modelo.addColumn("Puntaje");

        tablaEvaluaciones.setModel(modelo);

        String consulta = "SELECT * FROM Evaluaciones;";
        try {
            Statement st = ComunDB.obtenerConexion().createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("EvaluacionID");
                fila[1] = rs.getString("Titulo");
                fila[2] = rs.getString("Descripcion");
                fila[3] = rs.getDate("Fecha");
                fila[4] = rs.getString("Puntaje");
                modelo.addRow(fila);
            }
            tablaEvaluaciones.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los Evaluaciones: " + e.toString());
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
        int evaluacionId = Integer.parseInt(campoId.getText());
        String consulta = "DELETE FROM Evaluaciones WHERE EvaluacionID = ?;";
        try {
            CallableStatement cs = ComunDB.obtenerConexion().prepareCall(consulta);
            cs.setInt(1, evaluacionId);
            cs.execute();
            JOptionPane.showMessageDialog(null, "Evaluacion con ID " + evaluacionId + " eliminado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el Evaluacion con ID " + evaluacionId + ": " + e.toString());
        }
    }
}
