package com.tv.shows.ui

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import com.tv.shows.R
import com.tv.shows.ui.fragment.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // Using a shared ViewModel to send the search view text to Main Fragment
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val sv = MenuItemCompat.getActionView(menu.findItem(R.id.action_search)) as SearchView
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                sv.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.setSearchText(newText)
                return false
            }
        })
        return true
    }
}
