package okladnikov.bool.rainbowsmiletask.model

import com.google.gson.annotations.SerializedName

data class DocumentResponse(
    @SerializedName("data") val data: List<DocumentDescription?> = emptyList()
)