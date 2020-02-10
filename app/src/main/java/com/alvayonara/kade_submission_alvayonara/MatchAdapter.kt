package com.alvayonara.kade_submission_alvayonara

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MatchAdapter(
    private val context: Context,
    private val matches: List<Match>,
    private val listener: (Match) -> Unit
) :
    RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder =
        MatchViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_row_match,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = matches.size

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) =
        holder.bindItem(matches[position], listener)

    class MatchViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val dateEvent = view.findViewById<TextView>(R.id.date_event)
        private val nameHomeTeam = view.findViewById<TextView>(R.id.name_home_team)
        private val nameAwayTeam = view.findViewById<TextView>(R.id.name_away_team)
        private val scoreHomeTeam = view.findViewById<TextView>(R.id.score_home_team)
        private val scoreAwayTeam = view.findViewById<TextView>(R.id.score_away_team)
        private val nameLeague = view.findViewById<TextView>(R.id.name_league)

        fun bindItem(matches: Match, listener: (Match) -> Unit) {
            dateEvent.text = matches.eventDate
            nameHomeTeam.text = matches.homeTeamName
            nameAwayTeam.text = matches.awayTeamName
            scoreHomeTeam.text = matches.homeScore
            scoreAwayTeam.text = matches.awayScore
            nameLeague.text = matches.leagueName
            itemView.setOnClickListener {
                listener(matches)
            }
        }

    }
}