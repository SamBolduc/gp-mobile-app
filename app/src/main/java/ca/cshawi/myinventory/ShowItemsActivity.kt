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
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MenuItem
import ca.cshawi.myinventory.api.APIService
import ca.cshawi.myinventory.api.ActionResponse
import ca.cshawi.myinventory.api.requests.UpdateItemsRequest
import ca.cshawi.myinventory.boxes.Box
import ca.cshawi.myinventory.boxes.items.ItemAdapter
import ca.cshawi.myinventory.boxes.items.ItemSwipeController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShowItemsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var adapter: ItemAdapter
    var boxPosition = -1
    lateinit var box: Box

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_show_items)

        fab.setImageResource(R.drawable.round_save_white_36)
        fab.setOnClickListener { view ->

            box.items.forEach {
                it.currentAmount += it.changedQuantity
                it.changedQuantity = 0
                adapter.notifyDataSetChanged()
            }
            
            APIService.INSTANCE.updateItems(UpdateItemsRequest(box.id, box.items).toString())
                .enqueue(object :
                    Callback<ActionResponse> {
                    override fun onFailure(call: Call<ActionResponse>, t: Throwable) {
                        t.printStackTrace()
                    }

                    override fun onResponse(
                        call: Call<ActionResponse>,
                        response: Response<ActionResponse>
                    ) {

                        Snackbar.make(
                            view,
                            "Les modifications ont été sauvegardé avec succès",
                            Snackbar.LENGTH_LONG
                        ).setAction("Quitter") { onBackPressed() }.show()
                    }
                });
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
        box = intent.getParcelableExtra("box")
        adapter = ItemAdapter(box.items)

        val recyclerView = findViewById<RecyclerView>(R.id.item_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val swipeController = ItemSwipeController()
        val touchHelper = ItemTouchHelper(swipeController)
        touchHelper.attachToRecyclerView(recyclerView)
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
        box.items.forEach {
            it.changedQuantity = 0
        }

        val intent = Intent()
        intent.putExtra("position", boxPosition)
        intent.putExtra("box", box)
        setResult(1, intent)
        finish()
    }
}