package entidades;

import accesoadatos.ComunDB;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MINEDUCYT
 */
public class Evaluacione {

    private String evaluacionId;
    private String titulo;
    private String descripcion;
    private Date fecha;
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
        return fecha.toLocalDate();
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = Date.valueOf(fecha);
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

    // New Methods
    public void cargardatosID(JComboBox<String> comboBox) {
        comboBox.removeAllItems();
        comboBox.addItem("Seleccione una cuenta"); // Agregar ítem predeterminado
        String consulta = "SELECT EstudianteID, Nombre, Apellido, Correo, Carrera FROM Estudiantes;";
        try {
            ComunDB conexion = new ComunDB();
            CallableStatement cs = conexion.obtenerConexion().prepareCall(consulta);
            ResultSet rs = cs.executeQuery();

            // Agregar los datos al JComboBox como String
            while (rs.next()) {
                // Obtener el valor del CuentaID
                int EstudianteID = rs.getInt("EstudianteID");
                // Agregar el valor como String al JComboBox
                comboBox.addItem(EstudianteID != 0 ? String.valueOf(EstudianteID) : null);
            }

            rs.close();
            // cs.close(); // Cierra el statement
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos del JComboBox: " + e.toString());
        }
    }

    public void crear(JTextField paramTitulo, JTextField paramDescripcion, JTextField paramFecha, JTextField paramPuntaje, JComboBox<String> paramEstudianteId) {
        // Verificar que todos los campos estén llenos
        if (paramTitulo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Título es obligatorio.");
            return;
        }
        if (paramDescripcion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Descripción es obligatorio.");
            return;
        }
        if (paramFecha.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Fecha es obligatorio.");
            return;
        }
        if (paramPuntaje.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Puntaje es obligatorio.");
            return;
        }
        if (paramEstudianteId.getSelectedItem() == null || paramEstudianteId.getSelectedItem().toString().equals("Seleccione un estudiante")) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un EstudianteID.");
            return;
        }

        // Insertar la evaluación en la base de datos
        String consulta = "INSERT INTO Evaluaciones (Titulo, Descripcion, Fecha, Puntaje, EstudianteID) VALUES (?, ?, ?, ?, ?)";
        try {
            CallableStatement cs = ComunDB.obtenerConexion().prepareCall(consulta);

            // Convertir la fecha al formato adecuado para la base de datos
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date parsedDate = inputDateFormat.parse(paramFecha.getText());
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = outputDateFormat.format(parsedDate);

            // Establecer los valores de los parámetros
            cs.setString(1, paramTitulo.getText());
            cs.setString(2, paramDescripcion.getText());
            cs.setString(3, formattedDate);
            cs.setDouble(4, Double.parseDouble(paramPuntaje.getText()));
            cs.setInt(5, Integer.parseInt(paramEstudianteId.getSelectedItem().toString()));

            // Ejecutar la consulta
            cs.execute();

            JOptionPane.showMessageDialog(null, "Se insertó correctamente la evaluación");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: El puntaje debe ser un número.");
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Error al convertir la fecha: Formato de fecha incorrecto.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar la evaluación: " + e.getMessage());
        }
    }

    public void mostrarEvaluaciones(JTable tablaEvaluaciones) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("EvaluacionID");
        modelo.addColumn("Titulo");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Fecha");
        modelo.addColumn("Puntaje");
        modelo.addColumn("EstudianteID");

        tablaEvaluaciones.setModel(modelo);

        String sql = "SELECT * FROM Evaluaciones;";

        try (Connection connection = ComunDB.obtenerConexion(); Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Object[] datos = new Object[6];
                datos[0] = rs.getString("EvaluacionID");
                datos[1] = rs.getString("Titulo");
                datos[2] = rs.getString("Descripcion");
                datos[3] = rs.getDate("Fecha");
                datos[4] = rs.getDouble("Puntaje");
                datos[5] = rs.getInt("EstudianteID");

                modelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los registros de evaluaciones: " + e.getMessage());
        }
    }

    public void SeleccionarEvaluacion(JTable paramTablaEvaluacion, JTextField paramEvaluacionID, JTextField paramTitulo, JTextField paramDescripcion, JTextField paramFecha, JTextField paramPuntaje, JComboBox<String> paramEstudianteId) {
        try {
            int fila = paramTablaEvaluacion.getSelectedRow();
            if (fila >= 0) { // Verifica que se ha seleccionado una fila válida
                paramEvaluacionID.setText(paramTablaEvaluacion.getValueAt(fila, 0).toString());
                paramTitulo.setText(paramTablaEvaluacion.getValueAt(fila, 1).toString());
                paramDescripcion.setText(paramTablaEvaluacion.getValueAt(fila, 2).toString());
                paramFecha.setText(paramTablaEvaluacion.getValueAt(fila, 3).toString());
                paramPuntaje.setText(paramTablaEvaluacion.getValueAt(fila, 4).toString());

                // Seleccionar el EstudianteID correspondiente en el JComboBox
                String estudianteId = paramTablaEvaluacion.getValueAt(fila, 5).toString();
                paramEstudianteId.setSelectedItem(estudianteId);
            } else {
                JOptionPane.showMessageDialog(null, "Ninguna fila seleccionada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar la fila: " + e.toString());
        }
    }

    public void ModificarEvaluacion(JTable paramTablaEvaluacion, JTextField paramTitulo, JTextField paramDescripcion, JTextField paramFecha, JTextField paramPuntaje, JComboBox<String> paramEstudianteId) {
        try {
            // Check if a row is selected in the table
            int selectedRow = paramTablaEvaluacion.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "No row selected. Please select a row to modify.");
                return;
            }

            // Retrieve EvaluacionID from the selected row
            String evaluacionID = paramTablaEvaluacion.getValueAt(selectedRow, 0).toString();

            // Convertir cadena de fecha a java.sql.Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date parsedDate = dateFormat.parse(paramFecha.getText());
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

            // Convert other fields
            String titulo = paramTitulo.getText();
            String descripcion = paramDescripcion.getText();
            double puntaje = Double.parseDouble(paramPuntaje.getText());
            int estudianteID = Integer.parseInt(paramEstudianteId.getSelectedItem().toString());

            String consulta = "UPDATE Evaluaciones SET Titulo = ?, Descripcion = ?, Fecha = ?, Puntaje = ?, EstudianteID = ? WHERE EvaluacionID = ?;";

            try {
                CallableStatement cs = ComunDB.obtenerConexion().prepareCall(consulta);
                cs.setString(1, titulo);
                cs.setString(2, descripcion);
                cs.setDate(3, sqlDate);
                cs.setDouble(4, puntaje);
                cs.setInt(5, estudianteID);
                cs.setString(6, evaluacionID);

                cs.execute();
                JOptionPane.showMessageDialog(null, "Modificación exitosa");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al Modificar, error: " + e.toString());
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Error al convertir la fecha: Formato de fecha incorrecto.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Formato de número incorrecto.");
        }
    }

    public void EliminarEvaluacion(JTextField evaluacionIDField) {
        ComunDB conexion = new ComunDB();
        String consulta = "DELETE FROM Evaluaciones WHERE EvaluacionID = ?";
        try {
            String evaluacionID = evaluacionIDField.getText(); // Obtener el texto
            CallableStatement cs = conexion.obtenerConexion().prepareCall(consulta);
            cs.setString(1, evaluacionID); // Usar el valor aquí
            cs.execute();
            JOptionPane.showMessageDialog(null, "Eliminación exitosa de la evaluación con ID: " + evaluacionID);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la evaluación con ID " + ", error: " + e.toString());
        }
    }
}