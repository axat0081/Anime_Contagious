package com.example.animecontagious.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import com.example.animecontagious.R
import com.example.animecontagious.data.AnimeResponse
import com.example.animecontagious.databinding.DisplayLayoutBinding

class UpcomingAnimeAdapter(
    var context: OnItemClickListener,
    var list: List<AnimeResponse.Anime>
) :
    RecyclerView.Adapter<UpcomingAnimeAdapter.AnimeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding =
            DisplayLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = list[position]
        holder.bind(anime)
    }

    interface OnItemClickListener {
        fun onItemCLick(anime: AnimeResponse.Anime)
    }

    inner class AnimeViewHolder(private val binding: DisplayLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item: AnimeResponse.Anime? = list[position]
                    if (item != null) {
                        context.onItemCLick(item)
                    }
                }
            }
        }

        fun bind(anime: AnimeResponse.Anime) {
            binding.apply {
                if (anime.imageUrl == null) {
                    Glide.with(itemView)
                        .load(R.drawable.image_not_available)
                        .centerCrop()
                        .fitCenter()
                        .placeholder(R.drawable.image_not_available)
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .override(Target.SIZE_ORIGINAL)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView)
                } else {
                    Glide.with(itemView)
                        .load(anime.imageUrl)
                        .centerCrop()
                        .fitCenter()
                        .placeholder(R.drawable.image_not_available)
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .override(Target.SIZE_ORIGINAL)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView)
                }
                if (anime.title?.length!! <= 45)
                    textView.text = anime.title
                else
                    textView.text = anime.title.substring(0, 45).plus("......")
            }
        }
    }
}