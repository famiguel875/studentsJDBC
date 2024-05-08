import java.io.File


class FileManagement() : IFiles {

    override fun existeDir(ruta: String): String {
        try {
            if (File(ruta).isDirectory) {
                return ""
            }
        } catch (e: SecurityException) {
            return "Error al comprobar si existe el directorio $ruta: ${e.message}"
        }
        return "No existe el fichero"
    }


    override fun existeFic(ruta: String): String {
        try {
            if (File(ruta).isFile) {
                return ""
            }
        } catch (e: SecurityException) {
            return "Error al comprobar si existe el fichero $ruta: ${e.message}"
        }
        return "No existe el fichero"
    }


    override fun escribir(fichero: File, info: String): String {
        try {
            fichero.appendText(info)
        } catch (e: Exception) {
            return "Error al escribir en el archivo: ${e.message}"
        }
        return ""
    }


    override fun leer(fichero: File): List<String>? {
        val lista : List<String>
        try {
            lista = fichero.readLines()
        } catch (e: Exception) {
            return null
        }
        return lista
    }


    override fun crearDir(ruta: String): String {
        val dirRuta = File(ruta)
        try {
            if (!dirRuta.isDirectory) {
                if (!dirRuta.mkdirs()) {
                    return "No fue posible crear la ruta de directorios"
                }
            }
        } catch (e: Exception) {
            return "Error al crear el directorio $ruta: ${e.message}"
        }
        return ""
    }


    override fun crearFic(ruta: String, info: String, sobreescribir: Boolean): File? {
        val fichero = File(ruta)
        crearDir(fichero.parent)
        try {
            if (sobreescribir) {
                fichero.writeText(info)
            } else {
                fichero.createNewFile()
                if (info.isNotEmpty()) {
                    fichero.appendText(info)
                }
            }
        } catch (e: Exception) {
            return null
        }
        return fichero
    }


    override fun buscarUltimoFicheroEmpiezaPor(directorio: File, nombreFicheroInicio: String): File? {
        val ficheros = directorio.listFiles { fichero ->
            fichero.isFile && fichero.name.startsWith(nombreFicheroInicio)
        }

        if (ficheros != null && ficheros.isNotEmpty()) {
            val ultimoModificado = ficheros.maxByOrNull { it.lastModified() }
            if (ultimoModificado != null) {
                return ultimoModificado
            }
        }

        return null
    }


    override fun buscarUltimoFicheroEmpiezaFinalizaPor(directorio: File, empiezaPor: String, terminaPor: String): File? {
        val ficheros = directorio.listFiles { fichero ->
            fichero.isFile && fichero.name.startsWith(empiezaPor) && fichero.name.endsWith(terminaPor)
        }

        if (ficheros != null && ficheros.isNotEmpty()) {
            val ultimoModificado = ficheros.maxByOrNull { it.lastModified() }
            if (ultimoModificado != null) {
                return ultimoModificado
            }
        }

        return null
    }

    override fun buscarInfoFichero(fichero: File, info: String) : String? {
        val lineas = leer(fichero)
        if (lineas != null) {
            for (linea in lineas) {
                if (linea == info.dropLast(1)) {
                    return ""
                }
            }
        }
        return null
    }

}