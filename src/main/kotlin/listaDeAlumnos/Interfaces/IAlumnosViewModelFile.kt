package listaDeAlumnos.Interfaces

interface IAlumnosViewModelFile {
    val inputTexto:String

    val lista: List<String>

    val mensajeInfo: String

    val showInfo: Boolean

    fun addEstudiante()

    fun deleteEstudiante(index: Int)

    fun limpiarLista()

    fun guardado()

    fun dismissInfoMensaje()

    fun onInfoMensajeDismissed()

    fun textoCambia(estudiante:String)
}