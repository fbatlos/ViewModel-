package listaDeAlumnos





import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import listaDeAlumnos.BDD.AlumnosViewModelBD
import listaDeAlumnos.BDD.Database
import listaDeAlumnos.BDD.EstudianteRepo
import listaDeAlumnos.File.File


fun main() = application {
    val windowState = rememberWindowState(size = DpSize(1200.dp,800.dp))
    Window(
        onCloseRequest = ::exitApplication,
        title = "Diego me mata.",
        state = windowState
    ){
        val eleccion = RadioButton()

        if (eleccion == tipoVM.File) {
            val manejoFichero = File()

            val ViewModelfile = AlumnosViewModelFile(manejoFichero)

            listado(ViewModelfile)

        }else{

            val manejobd = EstudianteRepo()

            Database.getConnection()

            val ViewModelbd = AlumnosViewModelBD(manejobd)

            listado(ViewModelbd)



        }

    }
}
