package zona_fit.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    public static Connection getConnection() {
        Connection connection = null;

        // datos de la db
        var database = "zona_fit_db"; // nombre de la base de datos creada.
        var url = "jdbc:mysql://localhost:3306/" + database;
        var user = "root";
        var password = "admin";

        try {
            // clase de coneccion a la db
            Class.forName("com.mysql.cj.jdbc.Driver");

            // conexion
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.print("Ocurrió un error" + e.getMessage());
            e.printStackTrace();
        }

        return connection;
    }

    public static void main() {
        var connection = Conexion.getConnection();
        if (connection != null) {
            System.out.println("Conexion exitosa " + connection);
        } else {
            System.out.println("Error al conectarse");
        }

    }
}
