package com.example.eko8757.footballclubfinal.ui.myfragment.schedulematch

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.view.MenuItemCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import android.util.Log
import android.view.*
import android.widget.*

import com.example.eko8757.footballclubfinal.R
import com.example.eko8757.footballclubfinal.adapter.AdapterEventList
import com.example.eko8757.footballclubfinal.api.ApiRepository
import com.example.eko8757.footballclubfinal.model.Events
import com.example.eko8757.footballclubfinal.presenter.MatchPresenter
import com.example.eko8757.footballclubfinal.ui.myactivity.detail_event.DetailActivityEvent
import com.example.eko8757.footballclubfinal.util.invisible
import com.example.eko8757.footballclubfinal.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh


class FragmentNext : Fragment(), MatchView {

    private var events: MutableList<Events> = mutableListOf()
    private var leagueId: String = "4328"
    private lateinit var presenter: MatchPresenter
    private lateinit var eventList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var adapter: AdapterEventList
    private lateinit var leagueName: String

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPrevEvents(data: List<Events>) {
        swipeRefreshLayout.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showNextEvents(data: List<Events>) {
        swipeRefreshLayout.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_next, container, false)
        spinner = view.findViewById(R.id.spinner_next)
        eventList = view.findViewById(R.id.recycler_next)
        progressBar = view.findViewById(R.id.progress_next)
        swipeRefreshLayout = view.findViewById(R.id.swiperRefresh_next)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setQueryHint("Find Event")
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty() or query.isNullOrBlank()) {
                    presenter.getNextEventsList(leagueId)
                } else {
                    if (query != null) {
                        presenter.getSearchEventList(query)
                    }
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrEmpty() or query.isNullOrBlank()) {
                    presenter.getNextEventsList(leagueId)
                } else {
                    if (query != null) {
                        presenter.getSearchEventList(query)
                    }
                }
                return true
            }
        } )
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        fun nextIntance(): FragmentNext = FragmentNext()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchPresenter(this, request, gson)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               leagueName = spinner.selectedItem.toString()
                if (leagueName.equals("English Premier League", true)) {
                    leagueId = "4328"
                } else if (leagueName.equals("English League Championship", true)) {
                    leagueId = "4329"
                } else if (leagueName.equals("German Bundesliga", true)){
                    leagueId = "4331"
                } else if (leagueName.equals("Italian Serie A", true)) {
                    leagueId = "4332"
                } else if (leagueName.equals("French Ligue 1", true)) {
                    leagueId = "4334"
                } else if (leagueName.equals("Spanish La Liga", true)) {
                    leagueId = "4335"
                }

                Log.d("league", leagueName)
                Log.d("league", leagueId)
                presenter.getNextEventsList(leagueId)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        eventList.layoutManager = LinearLayoutManager(activity)
        adapter = AdapterEventList(events) {
            activity?.startActivity<DetailActivityEvent>("idEvent" to "${it.eventId}")
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        eventList.adapter = adapter
        swipeRefreshLayout.onRefresh {
            presenter.getNextEventsList(leagueId)
        }
    }
}
