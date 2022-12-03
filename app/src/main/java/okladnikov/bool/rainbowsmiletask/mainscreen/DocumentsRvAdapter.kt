package okladnikov.bool.rainbowsmiletask.mainscreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import okladnikov.bool.rainbowsmiletask.R
import okladnikov.bool.rainbowsmiletask.model.DocumentDescription

class DocumentsRvAdapter(
    private val documents: List<DocumentDescription>,
    private val context: Context
) : RecyclerView.Adapter<DocumentsRvAdapter.DocumentViewHolder>() {

    class DocumentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val constraintLayoutRootView: ConstraintLayout =
            itemView.findViewById(R.id.constraint_layout_root_view)
        val idPosTextView: TextView = itemView.findViewById(R.id.text_id_pos)
        val idRecordTextView: TextView = itemView.findViewById(R.id.text_id_record)
        val nomRouteTextView: TextView = itemView.findViewById(R.id.text_nom_route)
        val nomZakTextView: TextView = itemView.findViewById(R.id.text_nom_zak)
        val idHdRouteTextView: TextView = itemView.findViewById(R.id.text_id_hd_route)
        val nomNaklTextView: TextView = itemView.findViewById(R.id.text_nom_nakl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item_document, parent, false)

        return DocumentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        holder.idPosTextView.text =
            if (documents[position].idPos == null) "Not set" else documents[position].idPos.toString()
        holder.idRecordTextView.text =
            if (documents[position].idRecord == null) "Not set" else documents[position].idRecord.toString()
        holder.nomRouteTextView.text = documents[position].nomRoute ?: "Not set"
        holder.nomZakTextView.text = documents[position].nomZak ?: "Not set"
        holder.idHdRouteTextView.text =
            if (documents[position].idHdRoute == null) "Not set" else documents[position].idHdRoute.toString()
        holder.nomNaklTextView.text = documents[position].nomNakl ?: "Not set"

        // Make item clickable
        holder.constraintLayoutRootView.setOnClickListener {
            // TODO - implement select logic
//            Log.e(this.javaClass.simpleName, "item with idPos = ${documents[position].idPos} clicked")
            Toast.makeText(
                context,
                "item with idPos = ${documents[position].idPos} clicked",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount() = documents.size
}