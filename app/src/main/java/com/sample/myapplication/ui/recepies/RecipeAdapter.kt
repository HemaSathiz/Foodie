package com.sample.myapplication.ui.recepies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sample.myapplication.R
import com.sample.myapplication.model.network.Recipe
import kotlinx.android.synthetic.main.list_item_movie.view.*

class RecipeAdapter(private val context: Context, private val list: ArrayList<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.MovieViewHolder>() {

    class MovieViewHolder(private val context: Context, itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Recipe) {
            itemView.setOnClickListener {
                /* val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.EXTRAS_MOVIE_ID, movie.id)
                context.startActivity(intent)*/
            }
            itemView.tvTitle.text = movie.title
            Glide.with(context).load(movie.imageUrl)
                .apply(
                    RequestOptions().override(400, 400).centerInside()
                        .placeholder(R.drawable.ic_launcher_background)
                ).into(itemView.ivPoster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_movie, parent, false)
        return MovieViewHolder(context, view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun updateData(newList: List<Recipe>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}
