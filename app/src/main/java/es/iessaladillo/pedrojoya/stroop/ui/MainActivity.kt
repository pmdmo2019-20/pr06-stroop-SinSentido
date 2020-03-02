package es.iessaladillo.pedrojoya.stroop.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener


class MainActivity : AppCompatActivity(), OnToolbarAvailableListener {

    private val navController: NavController by lazy{
        findNavController(R.id.navHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onToolbarCreated(toolbar: Toolbar) {
        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(navController.graph).build();
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onToolbarDestroyed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
