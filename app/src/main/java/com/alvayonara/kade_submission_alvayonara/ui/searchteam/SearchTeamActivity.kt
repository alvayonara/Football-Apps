package com.alvayonara.kade_submission_alvayonara.ui.searchteam

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvayonara.kade_submission_alvayonara.R
import com.alvayonara.kade_submission_alvayonara.adapter.TeamAdapter
import com.alvayonara.kade_submission_alvayonara.api.ApiRepository
import com.alvayonara.kade_submission_alvayonara.model.Team
import com.alvayonara.kade_submission_alvayonara.presenter.SearchTeamPresenter
import com.alvayonara.kade_submission_alvayonara.ui.team.TeamActivity
import com.alvayonara.kade_submission_alvayonara.ui.team.TeamActivity.Companion.EXTRA_TEAM_DETAIL
import com.alvayonara.kade_submission_alvayonara.utils.invisible
import com.alvayonara.kade_submission_alvayonara.utils.visible
import com.alvayonara.kade_submission_alvayonara.view.SearchTeamView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search_team.*
import org.jetbrains.anko.startActivity

class SearchTeamActivity : AppCompatActivity(), SearchTeamView {

    private val teams: MutableList<Team> = mutableListOf()
    private lateinit var adapter: TeamAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: SearchTeamPresenter
    private lateinit var layoutSearch: LinearLayout
    private lateinit var layoutSearchResult: LinearLayout
    private lateinit var layoutSearchNotFound: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_team)

        initToolbar()
        initView()
        initData()
    }

    private fun initToolbar() {
        supportActionBar?.title = "Search Team"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initView() {
        progressBar = findViewById(R.id.progress_bar)
        layoutSearch = findViewById(R.id.layout_search)
        layoutSearchResult = findViewById(R.id.layout_search_result)
        layoutSearchNotFound = findViewById(R.id.layout_search_not_found)

        team_list.layoutManager = LinearLayoutManager(this)
        adapter =
            TeamAdapter(
                this,
                teams
            ) {
                startActivity<TeamActivity>(EXTRA_TEAM_DETAIL to it)
            }
        team_list.adapter = adapter
    }

    private fun initData() {
        val request = ApiRepository()
        val gson = Gson()
        presenter = SearchTeamPresenter(
            this, request, gson
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_team_menu, menu)

        val searchView = menu?.findItem(R.id.search_team)?.actionView as SearchView

        initSearchView(searchView)

        return true
    }

    private fun initSearchView(searchView: SearchView) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_team_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.getSearchTeamList(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showSearch() {
        layoutSearch.visible()
    }

    override fun hideSearch() {
        layoutSearch.invisible()
    }

    override fun showSearchResult() {
        layoutSearchResult.visible()
    }

    override fun hideSearchResult() {
        layoutSearchResult.invisible()
    }

    override fun showSearchNotFound() {
        layoutSearchNotFound.visible()
    }

    override fun hideSearchNotFound() {
        layoutSearchNotFound.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
