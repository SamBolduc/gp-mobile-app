package ca.cshawi.myinventory

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import ca.cshawi.myinventory.boxes.items.Item
import ca.cshawi.myinventory.items.items.ItemAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class ShowItemsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var adapter: ItemAdapter
    var boxPosition = -1
    lateinit var items: MutableList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_show_items)

        fab.setImageResource(R.drawable.round_save_white_36)
        fab.setOnClickListener { view ->
            items.forEach {
                it.currentAmount += it.changedQuantity
                it.changedQuantity = 0
                adapter.notifyDataSetChanged()
            }

            Snackbar.make(
                view,
                "Les modifications ont été sauvegardé avec succès",
                Snackbar.LENGTH_LONG
            )
                .setAction("Quitter") { onBackPressed() }.show()
        }


        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        boxPosition = intent.getIntExtra("position", -1)
        items = intent.getParcelableArrayListExtra("items")
        adapter = ItemAdapter(items)

        val recyclerView = findViewById<RecyclerView>(R.id.item_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
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
        intent.putParcelableArrayListExtra("items", ArrayList(items))
        setResult(1, intent)
        finish()
    }
}