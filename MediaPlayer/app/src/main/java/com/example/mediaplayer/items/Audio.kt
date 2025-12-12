package com.example.mediaplayer.items

import android.net.Uri

data class Audio(
    val id: Long,
    val nombre: String,
    val uri: Uri, // Uri no es serializable, en el intent se pasa como String
    val duracionMs: Int,
)
