package com.example.animecontagious.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import com.example.animecontagious.R
import com.example.animecontagious.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {
    companion object {
        private val startDate: String = "Start Date- "
        private val details: String = "You can find details at ->"
    }

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetailsFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)
        binding.apply {
            Glide.with(requireContext())
                .load(args.Anime.imageUrl)
                .centerCrop()
                .fitCenter()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .override(Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.image_not_available)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.image_not_available)
                .into(detailAnimeImage)
            detailAnimeTitle.text = args.Anime.title
            if (args.Anime.startDate == null) {
                detailAnimeStartDate.isVisible = false
            } else {
                detailAnimeStartDate.text = startDate.plus(args.Anime.startDate)
            }
            val uri = Uri.parse(args.Anime.url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            detailAnimeUrl.apply {
                text = details.plus(args.Anime.imageUrl)
                setOnClickListener {
                    context.startActivity(intent)
                }
                paint.isUnderlineText = true
            }
        }
    }
}