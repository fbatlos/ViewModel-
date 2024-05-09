package listaDeAlumnos





import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
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
    val icon = BitmapPainter(useResource("sample.png", ::loadImageBitmap))
    var showMainWindow by remember { mutableStateOf(true) }
    var showSecondWindow by remember { mutableStateOf(false) }
    var nombre =""
    when {
        showMainWindow -> {
            showMainWindow(onClose ={showMainWindow=false},
                onSecondWindow = {
                showSecondWindow=true
                showMainWindow=false
                    nombre=it
                                 },

            )
        }
        showSecondWindow ->{
            AlumnoWindow(onClose = {showSecondWindow=false},
                onVolver = {
                showMainWindow=true
                showSecondWindow=false
                nombre=it },
                onEliminar = {nombre = it
                    showMainWindow=true
                    showSecondWindow=false
                             },
                alumnoNombre = nombre)


        }
    }

    if (!showMainWindow && !showSecondWindow) {
        exitApplication()
    }
}

fun showMainWindow(onClose: () -> Unit,onSecondWindow:(String) ->  Unit) = application {
    val windowState = rememberWindowState(size = DpSize(1200.dp,800.dp))
    Window(
        onCloseRequest = onClose,
        title = "Diego me mata.",
        state = windowState
    ){
        val eleccion = RadioButton()

        if (eleccion == tipoVM.File) {
            val manejoFichero = File()

            val ViewModelfile = AlumnosViewModelFile(manejoFichero)

                listado(ViewModelfile , onNombre = {onSecondWindow(it)})

        }else{

            val manejobd = EstudianteRepo()

            Database.getConnection()

            val ViewModelbd = AlumnosViewModelBD(manejobd)

            listado(ViewModelbd, onNombre = {onSecondWindow(it)})



        }

    }
}


