/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesoadatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author MINEDUCYT
 */
public class ComunDB {
     static String connectionUrl = "workstation id=PruebaJava06DB.mssql.somee.com;packet size=4096;user id=Steven003_SQLLogin_1;pwd=vibbucn2q5;data source=PruebaJava06DB.mssql.somee.com;persist security info=False;initial catalog=PruebaJava06DB;TrustServerCertificate=True";
     public static Connection obtenerConexion() throws SQLException {
        // Registrar el Driver de la conexion a la base de datos SQL server
        // para que lo reconozca el servidor web
        DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        Connection connection = DriverManager.getConnection(connectionUrl); // abrir la conexion a la base de datos
        return connection; // retornar la conexion a la base de datos
    }
}
