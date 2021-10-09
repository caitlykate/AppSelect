package com.caitlykate.appselect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caitlykate.appselect.adapter.MoviesRcAdapter
import com.caitlykate.appselect.api.ApiRequest
import com.caitlykate.appselect.api.BASE_URL
import com.caitlykate.appselect.api.data.Movie
import com.caitlykate.appselect.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val countOnPage = 20

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val adapter = MoviesRcAdapter()
    private var list = ArrayList<Movie>()
    private var offset = 1
    private var hasMore = true

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.Theme_AppSelect)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        scrollListener()
        initRecyclerView()
        makeApiRequest()

    }

    private fun initRecyclerView(){
        binding.mainContent.apply {
            rcView.layoutManager = LinearLayoutManager(this@MainActivity)
            rcView.adapter = adapter
        }
    }

    private fun makeApiRequest() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)  //Базовая часть адреса
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            list.clear()
            try {
                val response = api.getMovieList(offset, BuildConfig.API_KEY)
                hasMore = response.has_more
                offset += countOnPage
                for (i in response.results){
                    val movie = Movie(i.display_title,i.summary_short, i.multimedia?.src)
//                    Log.d("Main", "Movie: $movie")
                    list.add(movie)
                }
                withContext(Dispatchers.Main) {
                    adapter.updateAdapter(list)
                }
            } catch (e: Exception) {
                Log.e("Main", "Error: ${e.message}")
            }
        }
    }

    private fun scrollListener() = with(binding.mainContent) {
        rcView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && hasMore) {
                    makeApiRequest()
                }
            }
        })
    }
}