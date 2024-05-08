import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StudentViewModelDb(
    private val studentRepository: StudentRepository
) : IStudentViewModel {

    companion object {
        private const val MAXCHARACTERS = 10
        private const val MAXNUMSTUDENTSVISIBLE = 7
    }

    private var _newStudent = mutableStateOf("")
    override val newStudent: State<String> = _newStudent

    private val _students = mutableStateListOf<String>()
    override val students: List<String> = _students

    private val _infoMessage = mutableStateOf("")
    override val infoMessage: State<String> = _infoMessage

    private val _showInfoMessage = mutableStateOf(false)
    override val showInfoMessage: State<Boolean> = _showInfoMessage

    private val _selectedIndex = mutableStateOf(-1)  // -1 indicates no selection
    override val selectedIndex: State<Int> = _selectedIndex

    override fun addStudent() {
        if (_newStudent.value.isNotBlank() && _newStudent.value.length <= MAXCHARACTERS) {
            _students.add(_newStudent.value.trim())
            _newStudent.value = ""
            saveStudents()  // Save to database on adding a new student
        }
    }

    override fun removeStudent(index: Int) {
        if (index in _students.indices) {
            _students.removeAt(index)
            saveStudents()  // Save to database after removing a student
        }
    }

    override fun loadStudents() {
        val result = studentRepository.getAllStudents()
        result.onSuccess { loadedStudents ->
            _students.clear()
            _students.addAll(loadedStudents)
        }.onFailure { exception ->
            updateInfoMessage("Failed to load students from database: ${exception.message}")
        }
    }

    override fun saveStudents() {
        val result = studentRepository.updateStudents(students)
        result.onSuccess {
            updateInfoMessage("Student data saved successfully")
        }.onFailure { exception ->
            updateInfoMessage("Failed to save students to database: ${exception.message}")
        }
    }

    override fun clearStudents() {
        _students.clear()
        saveStudents()  // Clear the database when clearing the list
    }

    override fun shouldShowScrollStudentListImage() = _students.size > MAXNUMSTUDENTSVISIBLE

    override fun newStudentChange(name: String) {
        if (name.length <= MAXCHARACTERS) {
            _newStudent.value = name
        }
    }

    override fun studentSelected(index: Int) {
        _selectedIndex.value = index
    }

    private fun updateInfoMessage(message: String) {
        _infoMessage.value = message
        _showInfoMessage.value = true
        CoroutineScope(Dispatchers.Default).launch {
            delay(2000)
            _showInfoMessage.value = false
            _infoMessage.value = ""
        }
    }

    override fun showInfoMessage(show: Boolean) {
        _showInfoMessage.value = show
    }
}