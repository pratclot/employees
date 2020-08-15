package com.pratclot.employees

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.navigation.NavAction
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.pratclot.employees.data.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_content) as NavHostFragment
        val navC = navHostFragment.navController

        nav_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_menu_update -> {
                    viewModel.updateDb()
                    true
                }
                R.id.bottom_menu_specs -> {
                    navC.navigate(
                        NavDeepLinkRequest.Builder.fromUri("com.pratclot.employees://specsList".toUri())
                            .build()
                    )
                    true
                }
                else -> false
            }
        }
    }
}