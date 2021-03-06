package com.example.eko8757.footballclubfinal.ui.myfragment.schedulematch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
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


class FragmentPrev : Fragment(), MatchView {

    private var events: MutableList<Events> = mutableListOf()
    private lateinit var presenter: MatchPresenter
    private lateinit var eventsList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var adapter: AdapterEventList
    private lateinit var leagueName: String
    private lateinit var leagueId: String

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
        val view: View = inflater.inflate(R.layout.fragment_prev, container, false)
        spinner = view.findViewById(R.id.spinner_prev)
        eventsList = view.findViewById(R.id.recycler_prev)
        progressBar = view.findViewById(R.id.progress_prev)
        swipeRefreshLayout = view.findViewById(R.id.swiperRefresh_prev)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setQueryHint("Find Event")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty() or query.isNullOrBlank()) {
                    presenter.getPrevEventsList(leagueId)
                } else {
                    presenter.getSearchEventList(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrEmpty() or query.isNullOrBlank()) {
                    presenter.getPrevEventsList(leagueId)
                } else {
                    presenter.getSearchEventList(query)
                }
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
       fun prevInstance(): FragmentPrev = FragmentPrev()
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
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                if (leagueName.equals("English Premier League", true)) {
                    leagueId = "4328"
                } else if (leagueName.equals("English League Championship", true)) {
                    leagueId = "4329"
                } else if (leagueName.equals("German Bundesliga", true)) {
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
                presenter.getPrevEventsList(leagueId)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        eventsList.layoutManager = LinearLayoutManager(activity)
        adapter = AdapterEventList(events) {
            activity?.startActivity<DetailActivityEvent>("idEvent" to "${it.eventId}")
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        eventsList.adapter = adapter
        swipeRefreshLayout.onRefresh {
            presenter.getPrevEventsList(leagueId)
        }
    }
}
