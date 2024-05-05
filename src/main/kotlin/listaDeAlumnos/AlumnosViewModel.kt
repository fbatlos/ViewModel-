package listaDeAlumnos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import java.io.File

class AlumnosViewModel {
    val manejadorDeFicheros = File()

    private val file = File(System.getProperty("user.dir") + "\\src\\main\\kotlin\\listaDeAlumnos\\estudiantes.txt")

    private var _inputTexto by mutableStateOf("")

    val inputTexto get() = _inputTexto

    private var _lista by mutableStateOf(manejadorDeFicheros.leer(file))
    val lista: List<String> get() = _lista

    private var _mensajeInfo by mutableStateOf("")
    val mensajeInfo: String get() = _mensajeInfo

    private var _showInfo by mutableStateOf(false)
    val showInfo: Boolean get() = _showInfo

    private val newStudentFocusRequester = FocusRequester()


    fun addEstudiante() {
        if (_inputTexto.isNotBlank()) {
            _lista += _inputTexto
            file.writer()
            manejadorDeFicheros.escribir(file,_lista)
            _mensajeInfo = "Se añadió un alumno."
            _showInfo = true
        }
    }

    fun deleteEstudiante(index: Int) {
        _lista = _lista.toMutableList().apply { removeAt(index) }
        file.writer()
        manejadorDeFicheros.escribir(file,_lista)
        _mensajeInfo = "Alumno eliminado."
        _showInfo = true
    }

    fun limpiarLista() {
        _lista = emptyList()
        file.writer()
        _mensajeInfo = "Lista limpiada."
        _showInfo = true
    }

    fun guardado() {
        file.writer()
        manejadorDeFicheros.escribir(file,_lista)
        _mensajeInfo = "Se guardó la lista."
        _showInfo = true
    }

    fun dismissInfoMensaje() {
        _showInfo = false
    }

    fun onInfoMensajeDismissed() {
        _mensajeInfo = ""
    }
    fun textoCambia(estudiante:String){
        _inputTexto = estudiante
    }
}