package com.caitlykate.appselect.adapter



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caitlykate.appselect.R
import com.caitlykate.appselect.api.data.Movie
import com.caitlykate.appselect.databinding.MovieListItemBinding
import com.squareup.picasso.Picasso


class MoviesRcAdapter (): RecyclerView.Adapter<MoviesRcAdapter.MoviesHolder>() {
    private val movieArray = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val binding =
            MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        holder.setData(movieArray[position])
    }

    override fun getItemCount(): Int {
        return movieArray.size
    }

    fun updateAdapter(newList: List<Movie>) {
        //movieArray.clear()
        movieArray.addAll(newList)
        notifyDataSetChanged()
    }


    class MoviesHolder(private val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(movie: Movie) = with(binding) {

            if (movie.title == null || movie.title.isEmpty()) tvTitle.setText(R.string.no_name) else tvTitle.text = movie.title
            tvDescription.text = movie.description
            if (movie.img != null) Picasso.get().load(movie.img).into(mainImage)
            else mainImage.setImageResource(R.drawable.ic_baseline_image)

        }
    }

}