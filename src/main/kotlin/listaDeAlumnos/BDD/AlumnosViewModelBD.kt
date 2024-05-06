package listaDeAlumnos.BDD

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import listaDeAlumnos.Interfaces.IAlumnosViewModel
import listaDeAlumnos.Interfaces.IRepo
import java.sql.Connection

class AlumnosViewModelBD( repo: IRepo): IAlumnosViewModel {

    private var _inputTexto by mutableStateOf("")

    override val inputTexto get() = _inputTexto

    private var _lista by mutableStateOf(repo.getAllStudents())

    override val lista: List<String> get() = _lista.getOrThrow()

    private var _mensajeInfo by mutableStateOf("")

    override val mensajeInfo: String get() = _mensajeInfo

    private var _showInfo by mutableStateOf(false)

    override val showInfo: Boolean get() = _showInfo

    override fun addEstudiante() {
        updateStudents(_lista.getOrThrow())
    }

    override fun deleteEstudiante(index: Int) {
        TODO("Not yet implemented")
    }

    override fun limpiarLista() {
        TODO("Not yet implemented")
    }

    override fun guardado() {
        TODO("Not yet implemented")
    }

    override fun dismissInfoMensaje() {
        TODO("Not yet implemented")
    }

    override fun onInfoMensajeDismissed() {
        TODO("Not yet implemented")
    }

    override fun textoCambia(estudiante: String) {
        TODO("Not yet implemented")
    }
}


fun updateStudents(students: List<String>): Result<Unit> {
    var connectionDb : Connection? = null
    return try {
        connectionDb = Database.getConnection()
        connectionDb.autoCommit = false
        connectionDb.createStatement().use { stmt ->
            stmt.execute("DELETE FROM students")
        }
        connectionDb.prepareStatement("INSERT INTO students (name) VALUES (?)").use { ps ->
            for (student in students) {
                ps.setString(1, student)
                ps.executeUpdate()
            }
        }
        connectionDb.commit()
        Result.success(Unit)
    } catch (e: Exception) {
        connectionDb?.rollback()
        Result.failure(e)
    } finally {
        connectionDb?.autoCommit = true
        connectionDb?.close()
    }
}