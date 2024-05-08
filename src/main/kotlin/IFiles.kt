import java.io.File

interface IFiles {

    fun existeDir(ruta: String): String


    fun existeFic(ruta: String): String


    fun escribir(fichero: File, info: String): String


    fun leer(fichero: File): List<String>?


    fun crearDir(ruta: String): String


    fun crearFic(ruta: String, info: String = "", sobreescribir: Boolean = true): File?


    fun buscarUltimoFicheroEmpiezaPor(directorio: File, nombreFicheroInicio: String): File?


    fun buscarUltimoFicheroEmpiezaFinalizaPor(directorio: File, empiezaPor: String, terminaPor: String): File?


    fun buscarInfoFichero(fichero: File, info: String) : String?
}