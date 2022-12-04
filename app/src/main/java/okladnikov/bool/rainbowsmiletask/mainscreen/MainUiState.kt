package okladnikov.bool.rainbowsmiletask.mainscreen

import okladnikov.bool.rainbowsmiletask.model.DocumentDescription

data class MainUiState(
    val numberOfAdds: Int = 0,
    val documents: MutableList<DocumentDescription> = mutableListOf(),
    val selectedDocumentPosition: Int? = null,
    val toastMessage: String? = null
)