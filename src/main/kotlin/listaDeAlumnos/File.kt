package listaDeAlumnos

import java.io.File

class File:IFile {
    override fun escribir(fichero: File, info: List<String>) {
        try {
            info?.forEach {fichero.appendText("$it \n")}
        } catch (e: Exception) {

        }
    }

    override fun leer(file: File): List<String> {
        return if (file.exists()) {
            file.readLines()
        } else {
            emptyList()
        }
    }
}

