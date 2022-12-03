package okladnikov.bool.rainbowsmiletask.mainscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okladnikov.bool.rainbowsmiletask.model.DocumentDescription
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

    fun addToEnd(document: DocumentDescription) {
        _uiState.update { currentState ->
            currentState.copy(
                numberOfAdds = currentState.numberOfAdds + 1,
                documents = currentState.documents.apply { add(document) },
                toastMessage = null
            )
        }
    }

    fun addToStart(document: DocumentDescription) {
        _uiState.update { currentState ->
            currentState.copy(
                numberOfAdds = currentState.numberOfAdds + 1,
                documents = currentState.documents.apply { add(0, document) },
                toastMessage = null
            )
        }
    }

    fun sortDocuments() {
        _uiState.update { currentState ->
            currentState.copy(
                toastMessage = "Отсортировано по полю nomZak"
            ).apply { documents.sortBy { it.nomZak } }
        }
    }
}