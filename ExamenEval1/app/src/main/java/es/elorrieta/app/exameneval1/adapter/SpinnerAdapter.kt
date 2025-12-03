package es.elorrieta.app.exameneval1.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import es.elorrieta.app.exameneval1.R

class SpinnerAdapter(
    context: Context,
    private val flags: List<Int>
) : ArrayAdapter<Int>(context, R.layout.layout_spinner, flags) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_spinner, parent, false)
        view.findViewById<ImageView>(R.id.imgFlag).setImageResource(flags[position])
        return view
    }
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}