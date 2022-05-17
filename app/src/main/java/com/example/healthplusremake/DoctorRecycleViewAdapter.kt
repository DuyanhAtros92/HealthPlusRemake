package com.example.healthplusremake

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class DoctorRecycleViewAdapter  : RecyclerView.Adapter<DoctorRecycleViewAdapter.ViewHoder>() {

    private var doctors : ArrayList<Doctor> = ArrayList()
    private lateinit var contextApp : Context


    fun setList(list : ArrayList<Doctor>) {
        this.doctors = list
    }

    fun setContext (context: Context) {
        this.contextApp = context
    }

    class ViewHoder constructor(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val doctorName = itemView.findViewById<TextView>(R.id.tv_doctor_name)
        val doctorImage = itemView.findViewById<ImageView>(R.id.img_doctor)
        fun bind(get: Doctor) {
             doctorName.setText(get.doctorName)
            Picasso.get().load(get.doctorImage).into(doctorImage);
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoder {
        return ViewHoder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_sub_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHoder, position: Int) {

        when(holder) {

            is ViewHoder -> {
                holder.bind(doctors.get(position))
                holder.itemView.setOnClickListener{
                    var intent = Intent(this.contextApp,DetailActivity::class.java)
                    intent.putExtra("name", doctors.get(position).doctorName)
                    intent.putExtra("image", doctors.get(position).doctorImage)
                    intent.putExtra("phone", doctors.get(position).phone)
                    this.contextApp.startActivity(intent)
                }
            }

        }
    }

    override fun getItemCount(): Int {
      return  doctors.size
    }
}