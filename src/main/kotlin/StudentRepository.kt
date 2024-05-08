import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class StudentRepository : IStudentRepository {
    override fun getAllStudents(): Result<List<String>> {
        val students = mutableListOf<String>()
        var connection: Connection? = null
        var stmt: Statement? = null
        var rs: ResultSet? = null
        try {
            connection = Database.getConnection()
            stmt = connection.createStatement()
            rs = stmt.executeQuery("SELECT name FROM students")
            while (rs.next()) {
                students.add(rs.getString("name"))
            }
            return Result.success(students)
        } catch (e: SQLException) {
            return Result.failure(e)
        } finally {
            rs?.close()
            stmt?.close()
            connection?.close()
        }
    }

    override fun updateStudents(students: List<String>): Result<Unit> {
        var connection: Connection? = null
        try {
            connection = Database.getConnection()
            connection.autoCommit = false
            val deleteStmt = connection.createStatement()
            deleteStmt.execute("DELETE FROM students")
            deleteStmt.close()

            val ps = connection.prepareStatement("INSERT INTO students (name) VALUES (?)")
            for (student in students) {
                ps.setString(1, student)
                ps.executeUpdate()
            }
            ps.close()
            connection.commit()
            return Result.success(Unit)
        } catch (e: SQLException) {
            connection?.rollback()
            return Result.failure(e)
        } finally {
            connection?.autoCommit = true
            connection?.close()
        }
    }
}