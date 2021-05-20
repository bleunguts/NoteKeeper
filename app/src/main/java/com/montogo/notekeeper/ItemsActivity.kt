package com.montogo.notekeeper

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.montogo.notekeeper.databinding.ActivityItemsBinding
import com.montogo.notekeeper.ui.NotesFragment

class ItemsActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityItemsBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[ItemsActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarItems.toolbar)

        handleDisplaySelection(viewModel.navDrawerDisplaySelection)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_items)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_notes, R.id.nav_courses, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        /* new way of doing things
        navView.setNavigationItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.nav_notes, R.id.nav_courses -> {
                    handleDisplaySelection(menu.itemId)
                    // navigate to new fragment here
                    viewModel.navDrawerDisplaySelection = menu.itemId
                }
                R.id.nav_share -> {
                    System.out.println("share called")
                    navView.menu.findItem(R.id.nav_share).isChecked = true
                }
                R.id.nav_send -> {
                    System.out.println("send called")
                    navView.menu.findItem(R.id.nav_send).isChecked = true
                }
            }
            val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        */

        navController.addOnDestinationChangedListener { controller: NavController, destination: NavDestination, bundle: Bundle? ->
            when (destination.id) {
                R.id.nav_notes, R.id.nav_courses -> {
                    handleDisplaySelection(destination.id)
                    viewModel.navDrawerDisplaySelection = destination.id
                }
                R.id.nav_share -> {
                    System.out.println("share called")
                }
                R.id.nav_send -> {
                    System.out.println("send called")
                }
            }
            val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    private fun handleDisplaySelection(itemId: Number) {
        when (itemId) {
            R.id.nav_notes -> {
                System.out.println("notes called")
            }
            R.id.nav_courses -> {
                System.out.println("courses called")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.items, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_items)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}