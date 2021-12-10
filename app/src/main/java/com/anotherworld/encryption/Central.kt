package com.anotherworld.encryption

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.anotherworld.encryption.databinding.ActivityCentralBinding
import java.util.*

import android.os.Build


class Central : AppCompatActivity() {
    private val PERMISSION_REQUEST_CODE = 123

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityCentralBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissionWithRationale()
        binding = ActivityCentralBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarCentral.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_central)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_text, R.id.nav_image, R.id.nav_password, R.id.nav_directory, R.id.nav_scale, R.id.nav_chat), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.central, menu)
        var str = ""
        str = if (Locale.getDefault().language == "ru"){
            "Настройки"
        } else "Settings"
        val s: SpannableString = SpannableString(str)
        s.setSpan(ForegroundColorSpan(Color.parseColor("#a85d65")), 0, s.length, 0)
        menu[0].title = s
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_central)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_settings -> {
                val intent = Intent(this, Settings::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun requestPermissionWithRationale() {
        requestPerms()
    }
    private fun requestPerms() {
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE)
        }
    }
}