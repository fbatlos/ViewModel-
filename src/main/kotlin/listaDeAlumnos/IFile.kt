package listaDeAlumnos

import java.io.File

interface IFile {
    fun escribir(fichero: File, info: List<String>)
    fun leer(file: File): List<String>
}

