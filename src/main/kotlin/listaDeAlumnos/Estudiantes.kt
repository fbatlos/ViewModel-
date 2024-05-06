package listaDeAlumnos

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import listaDeAlumnos.Interfaces.IAlumnosViewModel


@Composable
@Preview
fun listado(viewModel: IAlumnosViewModel) {


    val newStudentFocusRequester = remember { FocusRequester()}



    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            aniadirAlumno(
                inputTexto = viewModel.inputTexto,
                oncambioTexto = { viewModel.textoCambia(it)  },
                onAniadirTexto = {
                    viewModel.addEstudiante()
                    viewModel.textoCambia("")
                    newStudentFocusRequester.requestFocus()
                },
                focusRequester = newStudentFocusRequester
            )

            Column {
                scroll(items = viewModel.lista, onItemClick = {
                    viewModel.deleteEstudiante(it)
                }, requester = newStudentFocusRequester)
                limpiarListado (
                    onDelete = {
                        viewModel.limpiarLista()
                        newStudentFocusRequester.requestFocus()
                    },
                    requester = newStudentFocusRequester

                )

            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        guardarCambios (onSave = {
            viewModel.guardado()
            newStudentFocusRequester.requestFocus()
        }, requester = newStudentFocusRequester)


    }

    if (viewModel.showInfo) {
        InfoMessage(
            mensaje = viewModel.mensajeInfo,
            onDismmis = {
                viewModel.dismissInfoMensaje()
            }
        )
    }

    LaunchedEffect(viewModel.showInfo) {
        if (viewModel.showInfo) {
            delay(2000)
            viewModel.dismissInfoMensaje()
            viewModel.onInfoMensajeDismissed()
        }
    }
}

@Composable
@Preview
fun aniadirAlumno(
    inputTexto: String,
    oncambioTexto: (String) -> Unit,
    onAniadirTexto: () -> Unit,
    focusRequester: FocusRequester
) {

    Column (
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        TextField(
            modifier = Modifier
                .onKeyEvent {
                    if (it.type == KeyEventType.KeyDown && it.key == Key.Enter){
                        onAniadirTexto()
                        true
                    }else{
                        false
                    }
                }
                .focusRequester(focusRequester)

            ,
            value = inputTexto,
            onValueChange = oncambioTexto,
            label = { Text("Nuevo Elemento") },


        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAniadirTexto
        ) {
            Text("Agregar a la lista")
        }
    }
}

@Composable
@Preview
fun guardarCambios(onSave:() -> Unit,requester: FocusRequester){

    Button(
        onClick = onSave
    ){
        Text("Guardar cambios")
    }
}

@Composable
@Preview
fun limpiarListado(onDelete:() -> Unit , requester: FocusRequester){

    Button(
        onClick = onDelete
    ){
        Text("Borrar listados.")
    }
}

//Hacer con lazy
@Composable
@Preview
fun scroll(items: List<String>, onItemClick: (Int) -> Unit,requester: FocusRequester){
    Text("Estudiantes : ${items.size}")
    Column (
        modifier = Modifier.size(250.dp,400.dp)
            .border(3.dp, Color.Black)
            .padding(10.dp)
    ) {

        val state = rememberLazyListState()


        //Creará el scroll asignado y aunque cambie las dimensiones no cambia el espacio
        LazyColumn(Modifier.fillMaxSize().padding(end = 10.dp), state) {
            items(items.size) { x ->
                TextBox(items[x] , onClick = { onItemClick(x) })

                Spacer(modifier = Modifier.height(5.dp))
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.End).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(
                scrollState = state
            )
        )
    }
}


@Composable
fun TextBox(text: String, onClick: () -> Unit) {
    OutlinedTextField(
        value = text,
        onValueChange = { /* No se necesita hacer nada aquí */ },
        enabled = false,
        trailingIcon = {
            IconButton(
                enabled = true,
                onClick = onClick // Solo pasa la función onClick, no la llames
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar estudiantes.")
            }
        }
    )
}

@Composable
fun InfoMessage(mensaje: String,onDismmis:() -> Unit){
    Dialog(
        icon = painterResource("sample.png"),
        title = "hola",
        resizable = false,
        onCloseRequest = onDismmis
    ){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ){
            Text(mensaje)
        }
    }
}

