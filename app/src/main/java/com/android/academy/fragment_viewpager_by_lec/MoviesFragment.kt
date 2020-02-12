package com.android.academy.fragment_viewpager_by_lec

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.OnMovieClickListener
import com.android.academy.R
import com.android.academy.models.MovieModel
import com.android.academy.recyclerview_adapters_by_lec.MoviesViewAdapter

class MoviesFragment : Fragment(), OnMovieClickListener {
	
	private var movies: MutableList<MovieModel> = ArrayList()
	private lateinit var moviesRcv: RecyclerView
	private lateinit var moviesAdapter: MoviesViewAdapter
	private var listener: OnMovieClickListener? = null
	
	override fun onAttach(context: Context) {
		super.onAttach(context)
		if (context is OnMovieClickListener) {
			listener = context // <-Smart casting
		}
	}
	
	override fun onDetach() {
		super.onDetach()
		listener = null
	}
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view: RecyclerView = inflater.inflate(R.layout.fragment_movie_list, container, false) as RecyclerView
		moviesRcv = view.findViewById(R.id.movies_fragment_rcv)
		loadMovies()
		initRecycleView()
		return view
	}
	
	private fun initRecycleView() {
		
		context?.let {
			moviesRcv.layoutManager = LinearLayoutManager(it)
			//Create Movies Adapter
			moviesAdapter =
				MoviesViewAdapter(
					context = it,
					movieClickListener = this@MoviesFragment
				)
			
			//Attach Adapter To RecyclerView
			moviesRcv.adapter = moviesAdapter
			//Populate Adapter with data
			moviesAdapter.setData(movies)
		}
	}
	
	fun loadMovies(): MutableList<MovieModel> {
//		movies.addAll(
//			arrayOf(
//				MovieModel(
//					"Jurassic World - Fallen Kingdom",
//					R.drawable.jurassic_world_fallen_kingdom,
//					"Three years after the demise of Jurassic World, a volcanic eruption threatens the remaining dinosaurs on the isla Nublar, so Claire Dearing, the former park manager, recruits Owen Grady to help prevent the extinction of the dinosaurs once again"
//				),
//				MovieModel(
//					"The Meg",
//					R.drawable.the_meg,
//					"A deep sea submersible pilot revisits his past fears in the Mariana Trench, and accidentally unleashes the seventy foot ancestor of the Great White Shark believed to be extinct"
//				),
//				MovieModel(
//					"The First Purge",
//					R.drawable.the_first_purge,
//					"To push the crime rate below one percent for the rest of the year, the New Founding Fathers of America test a sociological theory that vents aggression for one night in one isolated community. But when the violence of oppressors meets the rage of the others, the contagion will explode from the trial-city borders and spread across the nation"
//				),
//				MovieModel(
//					"Deadpool 2",
//					R.drawable.deadpool_2,
//					"Wisecracking mercenary Deadpool battles the evil and powerful Cable and other bad guys to save a boy's life"
//				),
//				MovieModel(
//					"Black Panther",
//					R.drawable.black_panther,
//					"King T'Challa returns home from America to the reclusive, technologically advanced African nation of Wakanda to serve as his country's new leader. However, T'Challa soon finds that he is challenged for the throne by factions within his own country as well as without. Using powers reserved to Wakandan kings, T'Challa assumes the Black Panther mantel to join with girlfriend Nakia, the queen-mother, his princess-kid sister, members of the Dora Milaje (the Wakandan 'special forces') and an American secret agent, to prevent Wakanda from being dragged into a world war"
//				),
//				MovieModel(
//					"Ocean's Eight",
//					R.drawable.ocean_eight,
//					"Debbie Ocean, a criminal mastermind, gathers a crew of female thieves to pull off the heist of the century at New York's annual Met Gala"
//				),
//				MovieModel(
//					"Interstellar",
//					R.drawable.interstellar,
//					"Interstellar chronicles the adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage"
//				),
//				MovieModel(
//					"Thor - Ragnarok",
//					R.drawable.thor_ragnarok,
//					"Thor is on the other side of the universe and finds himself in a race against time to get back to Asgard to stop Ragnarok, the prophecy of destruction to his homeworld and the end of Asgardian civilization, at the hands of an all-powerful new threat, the ruthless Hela"
//				),
//				MovieModel(
//					"Guardians of the Galaxy",
//					R.drawable.guardians_of_the_galaxy,
//					"Light years from Earth, 26 years after being abducted, Peter Quill finds himself the prime target of a manhunt after discovering an orb wanted by Ronan the Accuser"
//				)
//			)
//		)
		return movies
	}
	
	override fun onMovieClicked(movieModel: MovieModel) {
		listener?.onMovieClicked(movieModel)
	}
	companion object{
		val Tag = MoviesFragment::class.simpleName
	}
}