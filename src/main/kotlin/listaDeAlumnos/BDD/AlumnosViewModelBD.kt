package listaDeAlumnos.BDD

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import listaDeAlumnos.Interfaces.IAlumnosViewModel
import listaDeAlumnos.Interfaces.IRepo
import java.sql.Connection

class AlumnosViewModelBD(val repo: IRepo): IAlumnosViewModel {

    private var _inputTexto by mutableStateOf("")

    override val inputTexto get() = _inputTexto

    private var _lista by mutableStateOf(repo.getAllStudents())

    override val lista: List<String> get() = _lista.getOrThrow()

    private var _mensajeInfo by mutableStateOf("")

    override val mensajeInfo: String get() = _mensajeInfo

    private var _showInfo by mutableStateOf(false)

    override val showInfo: Boolean get() = _showInfo

    override fun addEstudiante() {
        if (_inputTexto.isNotBlank()) {
            var connectionDb: Connection? = null
            try {
                connectionDb = Database.getConnection()
                connectionDb.autoCommit = false
                connectionDb.prepareStatement("INSERT INTO students (name) VALUES (?)").use { ps ->
                    ps.setString(1,_inputTexto)
                    ps.executeUpdate()
                }
                _mensajeInfo = "Se añadió un alumno."
                _showInfo = true
                _lista = repo.getAllStudents()
                Result.success(Unit)
            } catch (e: Exception) {
                connectionDb?.rollback()
                Result.failure(e)
            } finally {
                connectionDb?.autoCommit = true
                connectionDb?.close()
            }
        }
    }

    override fun deleteEstudiante(index: Int) {
        var connectionDb: Connection? = null
        try {
            connectionDb = Database.getConnection()
            connectionDb.autoCommit = false
            connectionDb.prepareStatement("delete from students where (id) = (?);").use { ps ->
                ps.setString(1,(_lista.getOrThrow()[index]))
                ps.execute()
            }
            _mensajeInfo = "Alumno eliminado."
            _showInfo = true
            _lista = repo.getAllStudents()
            Result.success(Unit)
        } catch (e: Exception) {
            connectionDb?.rollback()
            Result.failure(e)
        } finally {
            connectionDb?.autoCommit = true
            connectionDb?.close()
        }
    }

    override fun limpiarLista() {
        TODO("Not yet implemented")
    }

    override fun guardado() {
        var connectionDb: Connection? = null
        try {
            connectionDb = Database.getConnection()
            connectionDb.autoCommit = false
            connectionDb.commit()
        } catch (e: Exception) {
            connectionDb?.rollback()
        } finally {
            connectionDb?.autoCommit = true
            connectionDb?.close()
        }
    }

    override fun dismissInfoMensaje() {
        _showInfo = false
    }

    override fun onInfoMensajeDismissed() {
        _mensajeInfo = ""
    }

    override fun textoCambia(estudiante: String) {
        _inputTexto = estudiante
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