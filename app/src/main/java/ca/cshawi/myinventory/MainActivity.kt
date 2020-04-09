package ca.cshawi.myinventory

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import ca.cshawi.myinventory.boxes.Box
import ca.cshawi.myinventory.boxes.BoxAdapter
import ca.cshawi.myinventory.boxes.items.Item
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {

    lateinit var boxes: MutableList<Box>
    lateinit var adapter: BoxAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

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

        boxes = mutableListOf();

        val itemTitles: ArrayList<String> =
            arrayListOf("Seringue", "Masque N95", "Pancement", "Gant", "Tylenol", "Advil")
        val descriptions: ArrayList<String> =
            arrayListOf("Anti-Inflamatoire", "Masque", "Pancements", "Gants", "Seringues")

        for (i in 1..15) {
            val box = Box();
            box.id = i;
            box.title = descriptions.random()

            for (itemId in 1..3) {
                val item = Item(
                    itemId,
                    itemTitles.random(),
                    "Une description de cette armoire",
                    Random.nextInt(1, 25),
                    Random.nextInt(25, 45),
                    Random.nextInt(600000, 900000)
                )
                box.items.add(item)
            }

            box.open = Random.nextBoolean()
            boxes.add(box)
        }


        adapter = BoxAdapter(boxes, this)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START))
            drawer_layout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_box -> {
                //TODO: Armoire nav
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onClick(view: View) {
        if (view.tag == null) return
        Log.i("MainActivity", "Click sur une armoire de la liste")
    }
}