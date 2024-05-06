package listaDeAlumnos.BDD

import listaDeAlumnos.Interfaces.IRepo
import java.sql.Connection

class EstudianteRepo: IRepo {
    override fun getAllStudents(): Result<List<String>> {
        return try {
            val connectionDb = Database.getConnection()
            val students = mutableListOf<String>()
            connectionDb.use { conn ->
                conn.createStatement().use { stmt ->
                    stmt.executeQuery("SELECT name FROM students").use { rs ->
                        while (rs.next()) {
                            students.add(rs.getString("name"))
                        }
                    }
                }
            }
            Result.success(students)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}