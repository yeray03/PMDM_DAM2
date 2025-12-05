package com.example.mediaplayer.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaplayer.R
import com.example.mediaplayer.activity.VideoPlayer
import com.example.mediaplayer.items.Video
import kotlin.toString

class VideoAdapter(private var videos: MutableList<Video>, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.nombreVideo)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        R.layout.item_video
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is VideoViewHolder) {
            val video = videos[position]
            holder.nombre.text = video.nombre

            holder.nombre.setOnClickListener {
                Toast.makeText(
                    this.context,
                    "Reproduciendo: ${video.nombre}",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this.context, VideoPlayer::class.java)
                intent.putExtra("videoId", video.id)
                intent.putExtra("videoNombre", video.nombre)
                intent.putExtra("videoUri", video.uri.toString())
                intent.putExtra("videoDuracion", video.duracionMs)
                this.context.startActivity(intent)
            }

        }
    }

    override fun getItemCount(): Int {
        val count = videos.size
        return count
    }

    fun update(newVideos: MutableList<Video>) {
        videos = newVideos
        notifyDataSetChanged()
    }

}