package com.android.academy.avengers_movie

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.R
import kotlinx.android.synthetic.main.activity_movie.*


class MovieActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        trailer_button.setOnClickListener{
            watchYoutubeVideo(this,"6ZfuNTqbHE8")
        }
    }

    private fun watchYoutubeVideo(context: Context, id:String){
        val appIntent =  Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(Intent.ACTION_VIEW,
            Uri.parse("https://www.youtube.com/watch?v=$id"))
        try {
            context.startActivity(appIntent)
        }
        catch (e: ActivityNotFoundException){
            context.startActivity(webIntent)
        }
    }

    companion object {
        fun start(context: Context) {
            val openMovieActivity = Intent(context, MovieActivity::class.java)
            context.startActivity(openMovieActivity)
        }
    }

}
