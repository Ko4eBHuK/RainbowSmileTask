package okladnikov.bool.rainbowsmiletask.mainscreen

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import okladnikov.bool.rainbowsmiletask.R

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpControls()

        val adapter = DocumentsRvAdapter(emptyList(), this@MainActivity, mainViewModel)
        val documentsRecyclerView = findViewById<RecyclerView>(R.id.recycler_documents)
        documentsRecyclerView.adapter = adapter

        // Subscribe to state from viewModel
        subscribeToState(adapter)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        mainViewModel.sortDocuments()
    }

    private fun setUpControls() {
        // Set add buttons
        findViewById<ImageButton>(R.id.image_button_refresh).setOnClickListener {
            mainViewModel.loadDocuments()
        }
        findViewById<Button>(R.id.button_add_start).setOnClickListener {
            mainViewModel.addSelectedToStart()
        }
        findViewById<Button>(R.id.button_add_end).setOnClickListener {
            mainViewModel.addSelectedToEnd()
        }
    }

    private fun subscribeToState(adapter: DocumentsRvAdapter) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.uiState.collect { state ->
                    state.toastMessage?.let { toastMessage ->
                        Toast.makeText(this@MainActivity, toastMessage, Toast.LENGTH_LONG).show()
                    }

                    // Set count of add info
                    findViewById<TextView>(R.id.text_add_count).text = state.numberOfAdds.toString()

                    // Update recycler view
                    adapter.documents = state.documents
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}