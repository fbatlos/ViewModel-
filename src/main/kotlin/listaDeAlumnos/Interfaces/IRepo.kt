package listaDeAlumnos.Interfaces

interface IRepo {

    fun getAllStudents(): Result<List<String>>

}