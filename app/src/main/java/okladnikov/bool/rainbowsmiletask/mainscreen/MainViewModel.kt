package okladnikov.bool.rainbowsmiletask.mainscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okladnikov.bool.rainbowsmiletask.repository.DocumentsRepository

class MainViewModel : ViewModel() {
    private val documentsRepository = DocumentsRepository

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        loadDocuments()
    }

    fun loadDocuments() {
        viewModelScope.launch {
            documentsRepository.getDocuments()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Log.e(this@MainViewModel.javaClass.simpleName, e.message.toString())
                    _uiState.update { currentState ->
                        currentState.copy(
                            numberOfAdds = 0,
                            documents = mutableListOf(),
                            selectedDocumentPosition = null,
                            toastMessage = "Случилась проблема с запросом\nНажмите кнопку refresh"
                        )
                    }
                }
                .collect { documentsFromNet ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            numberOfAdds = currentState.numberOfAdds,
                            documents = documentsFromNet.toMutableList(),
                            toastMessage = null
                        )
                    }
                }
        }
    }

    fun addSelectedToEnd() {
        _uiState.update { currentState ->
            if (currentState.selectedDocumentPosition != null)
            currentState.copy(
                numberOfAdds = currentState.numberOfAdds + 1,
                documents = currentState.documents.apply { add(currentState.documents[currentState.selectedDocumentPosition]) },
                toastMessage = null
            )
            else currentState.copy(
                toastMessage = "Не выбран элемент для копирования"
            )
        }
    }

    fun addSelectedToStart() {
        _uiState.update { currentState ->
            if (currentState.selectedDocumentPosition != null)
                currentState.copy(
                    numberOfAdds = currentState.numberOfAdds + 1,
                    documents = currentState.documents.apply { add(0, currentState.documents[currentState.selectedDocumentPosition]) },
                    toastMessage = null,
                    selectedDocumentPosition = currentState.selectedDocumentPosition + 1
                )
            else currentState.copy(
                toastMessage = "Не выбран элемент для копирования"
            )
        }
    }

    fun selectDocumentAtPosition(position: Int) {
        if (position in 0.._uiState.value.documents.size)
            _uiState.update { currentState ->
                currentState.copy(
                    selectedDocumentPosition = position,
                    toastMessage = "Выбран ${position + 1} элемент"
                )
            }
        else _uiState.update { currentState ->
            currentState.copy(
                toastMessage = "Ошибка при выборе элемента из списка." +
                        "\nНет элемента с такой позицией"
            )
        }
    }

    fun sortDocuments() {
        _uiState.update { currentState ->
            currentState.copy(
                selectedDocumentPosition = null,
                toastMessage = "Отсортировано по полю nomZak"
            ).apply { documents.sortBy { it.nomZak } }
        }
    }
}