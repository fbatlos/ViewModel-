package listaDeAlumnos.BDD

import java.sql.Connection
import java.sql.DriverManager


object Database {
    private const val URL = "jdbc:mysql://localhost:3306/studentdb"
    private const val USER = "studentuser"
    private const val PASSWORD = "password"
    init {
        try {
            // Asegurarse de que el driver JDBC de MySQL esté disponible
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (e: ClassNotFoundException) {
            e.printStackTrace();
        }
    }
    fun getConnection(): Connection =
            DriverManager.getConnection(URL, USER, PASSWORD)
}