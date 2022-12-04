package okladnikov.bool.rainbowsmiletask.mainscreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import okladnikov.bool.rainbowsmiletask.R
import okladnikov.bool.rainbowsmiletask.model.DocumentDescription

class DocumentsRvAdapter(
    var documents: List<DocumentDescription>,
    private val context: Context,
    private val viewModel: MainViewModel
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
        // Display selected item
        if (position == viewModel.uiState.value.selectedDocumentPosition)
            applySelectedStyle(holder)
        else applyDefaultStyle(holder)

        // Add space between items
        if (position > 0) holder.constraintLayoutRootView.setPadding(
            0,
            context.resources.getDimensionPixelSize(R.dimen.padding_default),
            0,
            0
        ) else holder.constraintLayoutRootView.setPadding(
            0,
            0,
            0,
            0
        )

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
            viewModel.selectDocumentAtPosition(position)
        }
    }

    override fun getItemCount() = documents.size

    private fun applySelectedStyle(holder: DocumentViewHolder) {
        holder.constraintLayoutRootView.children.forEach {
            it.setBackgroundColor(context.resources.getColor(R.color.transparent))
        }
    }

    private fun applyDefaultStyle(holder: DocumentViewHolder) {
        holder.constraintLayoutRootView.children.forEach {
            it.setBackgroundColor(context.resources.getColor(R.color.white))
        }
    }
}