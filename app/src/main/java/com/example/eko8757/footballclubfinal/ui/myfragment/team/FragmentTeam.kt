package com.example.eko8757.footballclubfinal.ui.myfragment.team

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.*

import com.example.eko8757.footballclubfinal.R
import com.example.eko8757.footballclubfinal.adapter.AdapterTeamList
import com.example.eko8757.footballclubfinal.api.ApiRepository
import com.example.eko8757.footballclubfinal.model.Team
import com.example.eko8757.footballclubfinal.presenter.TeamPresenter
import com.example.eko8757.footballclubfinal.ui.myactivity.detail_team.DetailActivityTeam
import com.example.eko8757.footballclubfinal.util.invisible
import com.example.eko8757.footballclubfinal.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class FragmentTeam : Fragment(), TeamView {

    private var teams: MutableList<Team> = mutableListOf()
    private var leagueName: String = "English%20Premier%20League"

    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: AdapterTeamList
    private lateinit var spinner: Spinner
    private lateinit var teamList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout


    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }


    companion object {
        fun teamsInstance(): FragmentTeam = FragmentTeam()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(this, request, gson)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                leagueName = leagueName.replace(" ", "%20")
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        //eventList
        teamList.layoutManager = LinearLayoutManager(activity)
        adapter = AdapterTeamList(teams){
            activity?.startActivity<DetailActivityTeam>("idTeam" to "${it.teamId}")
        }
        teamList.adapter = adapter

        swipeRefresh.onRefresh {
            presenter.getTeamList(leagueName)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_team, container, false)
        spinner = v.findViewById(R.id.spinner_team)
        teamList = v.findViewById(R.id.recycler_team)
        progressBar = v.findViewById(R.id.progress_team)
        swipeRefresh = v.findViewById(R.id.swipeRefresh_team)

        return v
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setQueryHint("Find Team")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty() or query.isNullOrBlank()) {
                    presenter.getTeamList(leagueName)
                } else {
                    presenter.getSearchTeam(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrEmpty() or query.isNullOrBlank()) {
                    presenter.getTeamList(leagueName)
                } else {
                    presenter.getSearchTeam(query)
                }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}
