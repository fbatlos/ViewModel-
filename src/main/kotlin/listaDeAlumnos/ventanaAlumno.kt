package listaDeAlumnos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.skia.paragraph.Alignment

@Composable
fun AlumnoWindow(onClose: () -> Unit , alumnoNombre:String , onEliminar:() -> Unit , onVolver:(String) -> Unit) {
    val secondaryWindowState = rememberWindowState()
    Window(
        onCloseRequest = onClose,
        title = "Ventana Secundaria",
        state = secondaryWindowState
    ) {
        val newAlumnoName = ""
        Column (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally

        ){
            OutlinedTextField(
                value = alumnoNombre,
                onValueChange = {newAlumnoName} ,
                label = { Text(text = alumnoNombre) },
                modifier = Modifier
                    .size(200.dp, 60.dp)
            )

            Row {
                Button(
                    onClick = {onEliminar()}
                ){
                    Text("Eliminar alumno")
                }

                Button(
                    onClick = {onVolver(newAlumnoName)}
                ){
                    Text("Guardar modificacion")
                }
            }
        }
    }
}