package okladnikov.bool.rainbowsmiletask.model

import com.google.gson.annotations.SerializedName

data class DocumentDescription(
    @SerializedName("id_pos") val idPos: Int?,
    @SerializedName("id_record") val idRecord: Int?,
    @SerializedName("nom_route") val nomRoute: String?,
    @SerializedName("nom_zak") val nomZak: String?,
    @SerializedName("id_hd_route") val idHdRoute: Int?,
    @SerializedName("nom_nakl") val nomNakl: String?
)
