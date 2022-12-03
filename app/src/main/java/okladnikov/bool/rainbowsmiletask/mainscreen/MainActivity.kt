package okladnikov.bool.rainbowsmiletask.mainscreen

import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import okladnikov.bool.rainbowsmiletask.R
import okladnikov.bool.rainbowsmiletask.model.DocumentDescription

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var selectedDocument: DocumentDescription

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageButton>(R.id.image_button_refresh).setOnClickListener {
            mainViewModel.loadDocuments()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.uiState.collect {
                    it.toastMessage?.let { toastMessage ->
                        Toast.makeText(this@MainActivity, toastMessage, Toast.LENGTH_LONG).show()
                    }

                    // Set count of add operation
                    findViewById<TextView>(R.id.text_add_count).text = it.numberOfAdds.toString()

                    // Set recycler view
                    val adapter = DocumentsRvAdapter(it.documents, this@MainActivity)
                    val documentsRecyclerView = findViewById<RecyclerView>(R.id.recycler_documents)
                    documentsRecyclerView.addItemDecoration(
                        DividerItemDecoration(
                            this@MainActivity,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                    documentsRecyclerView.adapter = adapter
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        mainViewModel.sortDocuments()
    }
}