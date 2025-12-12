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
import com.example.mediaplayer.activity.AudioPlayer
import com.example.mediaplayer.items.Audio

class AudioAdapter(private var audios: MutableList<Audio>, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class AudioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.nombre_audio)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        R.layout.item_audio
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_audio, parent, false)
        return AudioViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is AudioViewHolder) {
            val audio = audios[position]
            holder.nombre.text = audio.nombre

            holder.nombre.setOnClickListener {
                Toast.makeText(
                    this.context,
                    "Reproduciendo: ${audio.nombre}",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this.context, AudioPlayer::class.java)
                intent.putExtra("audioId", audio.id)
                intent.putExtra("audioNombre", audio.nombre)
                intent.putExtra("audioUri", audio.uri.toString())
                intent.putExtra("audioDuracion", audio.duracionMs)
                this.context.startActivity(intent)
            }

        }
    }

    override fun getItemCount(): Int {
        val count = audios.size
        return count
    }

    fun update(newAudios: MutableList<Audio>) {
        audios = newAudios
        notifyDataSetChanged()
    }

}