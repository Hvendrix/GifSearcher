package com.example.gifsearcher

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.*
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.gifsearcher.databinding.ActivityMainBinding
import com.example.gifsearcher.fragments.home.presentation.fragment.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private var toast: Toast? = null

    var newDelay = 0L
    var oldDelay = 0L

    var searchMenu: SearchView? = null

    val viewModel: MainActivityViewModel by viewModels()

    private var _navController: NavController? = null
    private val navController get() = _navController!!

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        _navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        Timber.e("create menu")

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchMenu = (menu.findItem(R.id.app_bar_search).actionView as SearchView)
        searchMenu?.apply {

            this.setOnSearchClickListener {
                Timber.e("fragmetn ${supportFragmentManager.primaryNavigationFragment!!::class}  ${supportFragmentManager.primaryNavigationFragment?.id}${navController.currentDestination?.id} != ${R.layout.fragment_home}")
                Timber.e("back ${navController.backQueue.size} ${supportFragmentManager.backStackEntryCount}")
                if(navController.backQueue.size > 2){
                   val action =  HomeFragmentDirections.actionGlobalHomeFragment()
                    navController.navigate(action)
                    viewModel.searchPressed = true
                }
            }

            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isIconifiedByDefault = true
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    viewModel.updateSearchQuery(p0)
                    return false
                }

            })
            setOnCloseListener {
                viewModel.needReloadGifs(true)
                return@setOnCloseListener false
            }
            this.queryHint = "Поиск"
        }

        if(viewModel.searchPressed){
            searchMenu?.apply{
                setQuery("", false);
                requestFocus()
                isIconified = false
                viewModel.searchPressed = false
            }
        }

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (navController.backQueue.size <= 2) {
            newDelay = SystemClock.currentThreadTimeMillis()
            if (newDelay - oldDelay <= 100) {
                finish()
            } else {
                lifecycleScope.launchWhenResumed {
                    delay(700)
                    showToast("Нажмите два раза для выхода", Toast.LENGTH_SHORT)
                }

                oldDelay = newDelay
            }
        } else {
            super.onBackPressed()
        }
    }

    private fun showToast(message: String?, duration: Int) {
        if (message == null) return
        toast?.cancel()
        toast = Toast.makeText(this, message, duration)
        toast?.show()
    }
}