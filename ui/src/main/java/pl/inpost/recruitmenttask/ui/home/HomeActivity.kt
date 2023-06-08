package pl.inpost.recruitmenttask.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.recruitmenttask.ui.shipmentList.ShipmentListFragment
import pl.inpost.recruitmenttask.ui.R

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ShipmentListFragment.newInstance())
                .commitNow()
        }
    }
}
