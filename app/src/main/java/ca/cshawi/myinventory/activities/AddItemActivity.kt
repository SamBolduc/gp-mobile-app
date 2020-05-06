package ca.cshawi.myinventory.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.EditText
import ca.cshawi.myinventory.R
import ca.cshawi.myinventory.api.APIService
import ca.cshawi.myinventory.api.ActionResponse
import ca.cshawi.myinventory.api.requests.AddItemRequest
import ca.cshawi.myinventory.boxes.Box
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddItemActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var boxPosition = -1
    lateinit var box: Box

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        boxPosition = intent.getIntExtra("position", -1)
        box = intent.getParcelableExtra("box")

        setContentView(R.layout.activity_new_item)
        fab.setImageResource(R.drawable.round_save_white_36)
        fab.setOnClickListener { view ->

            val name = findViewById<EditText>(R.id.new_item_name).text.toString()
            val description = findViewById<EditText>(R.id.new_item_description).text.toString()
            val currentAmount = findViewById<EditText>(R.id.new_item_quantity).text.toString()
            val maxAmount = findViewById<EditText>(R.id.new_item_max_quantity).text.toString()
            val barCode = findViewById<EditText>(R.id.new_item_barCode).text.toString()

            APIService.INSTANCE.addItem("/boxes/${box.id}/newItem", AddItemRequest(name, description, currentAmount.toInt(), maxAmount.toInt(), barCode.toInt()).toString()).enqueue(object : Callback<ActionResponse> {
                override fun onFailure(call: Call<ActionResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<ActionResponse>, response: Response<ActionResponse>) {
                    Snackbar.make(view, "L'objet a été ajouté avec succès.", Snackbar.LENGTH_LONG).setAction("Quitter") { onBackPressed() }.show()
                }
            });
        }


        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_box -> {
                onBackPressed()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("position", boxPosition)
        intent.putExtra("box", box)
        setResult(1, intent)
        finish()
    }
}