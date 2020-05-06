package ca.cshawi.myinventory

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import ca.cshawi.myinventory.api.APIService
import ca.cshawi.myinventory.api.ActionResponse
import ca.cshawi.myinventory.api.requests.UpdateItemsRequest
import ca.cshawi.myinventory.boxes.Box
import ca.cshawi.myinventory.boxes.items.ItemAdapter
import ca.cshawi.myinventory.swipe.SwipeHelper
import ca.cshawi.myinventory.swipe.SwipeHelper.UnderlayButtonClickListener
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShowItemsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var adapter: ItemAdapter
    var boxPosition = -1
    lateinit var box: Box

    private val speedDial: SpeedDialView by lazy { findViewById<SpeedDialView>(R.id.speedDial) }
    private lateinit var swipeHelper: SwipeHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_show_items)

        speedDial.expansionMode = SpeedDialView.ExpansionMode.TOP

        speedDial.addActionItem(
            SpeedDialActionItem.Builder(
                R.id.fab_new, R.drawable
                    .baseline_add_white_36
            ).setFabBackgroundColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.red,
                    theme
                )
            ).setLabel("Ajouter un objet")
                .create()
        )

        speedDial.addActionItem(
            SpeedDialActionItem.Builder(
                R.id.fab_save, R.drawable
                    .round_save_white_36
            ).setFabBackgroundColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.green,
                    theme
                )
            ).setLabel("Enregistrer et Quitter")
                .create()
        )

        speedDial.setOnActionSelectedListener(SpeedDialView.OnActionSelectedListener { actionItem ->
            when (actionItem.id) {
                R.id.fab_new -> {
                    val intent = Intent(this, AddItemActivity::class.java)
                    intent.putExtra("position", boxPosition)
                    intent.putExtra("box", box)
                    startActivityForResult(intent, 1)

                    speedDial.close()
                    return@OnActionSelectedListener true
                }
                R.id.fab_save -> {
                    box.items.forEach {
                        it.currentAmount += it.changedQuantity
                        it.changedQuantity = 0
                        adapter.notifyDataSetChanged()
                    }

                    APIService.INSTANCE.updateItems(
                        UpdateItemsRequest(
                            box.id,
                            box.items
                        ).toString()
                    )
                        .enqueue(object :
                            Callback<ActionResponse> {
                            override fun onFailure(call: Call<ActionResponse>, t: Throwable) {
                                t.printStackTrace()
                            }

                            override fun onResponse(
                                call: Call<ActionResponse>,
                                response: Response<ActionResponse>
                            ) {

                                box.modif = 0
                                onBackPressed()
                                speedDial.close()
                            }
                        });
                    return@OnActionSelectedListener true
                }
            }
            true
        })

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

//        val swipeController = ItemSwipeController()
//        val touchHelper = ItemTouchHelper(swipeController)
//        touchHelper.attachToRecyclerView(recyclerView)

        swipeHelper = object : SwipeHelper(this, recyclerView) {
            override fun instantiateUnderlayButton(
                viewHolder: RecyclerView.ViewHolder,
                underlayButtons: MutableList<UnderlayButton>
            ) {
                underlayButtons.add(UnderlayButton(
                    "Supprimer",
                    0,
                    Color.parseColor("#FF3C30"),
                    UnderlayButtonClickListener {
                        Snackbar.make(
                            viewHolder.itemView,
                            "Ëtes-vous certain de vouloir supprimer cet objet?",
                            Snackbar.LENGTH_LONG
                        )
                            .setCallback(object : Snackbar.Callback() {
                                override fun onDismissed(
                                    transientBottomBar: Snackbar?,
                                    event: Int
                                ) {
                                    if (event != DISMISS_EVENT_TIMEOUT) return

                                    APIService.INSTANCE.delItem(
                                        "/boxes/${box.id}/item/${adapter.items[it].id}"
                                    )
                                        .enqueue(object :
                                            Callback<ActionResponse> {
                                            override fun onFailure(
                                                call: Call<ActionResponse>,
                                                t: Throwable
                                            ) {
                                                t.printStackTrace()
                                            }

                                            override fun onResponse(
                                                call: Call<ActionResponse>,
                                                response: Response<ActionResponse>
                                            ) {
                                                adapter.notifyDataSetChanged()
                                            }
                                        })
                                    adapter.notifyDataSetChanged()
                                }
                            })
                            .setAction("Annuler") {
                                adapter.notifyDataSetChanged()
                            }
                            .show()
                    }
                ))
            }
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != 1) return;

        if (data?.hasExtra("position")!! && data.hasExtra("box")) {
            this.boxPosition = data.getIntExtra("position", -1)
            this.box = data.getParcelableExtra<Box>("box")
            return
        }
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