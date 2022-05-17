package com.example.healthplusremake

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
     //  val intent = Intent()
        val name = intent.getStringExtra("name")
        val image = intent.getStringExtra("image")
        val phone = intent.getStringExtra("phone");

        val imgView = findViewById<RoundedImageView>(R.id.imgItem)
        val nameView = findViewById<TextView>(R.id.tvCategory)
        val phoneView = findViewById<TextView>(R.id.tvExperience)
        Picasso.get().load(image).into(imgView);
        nameView.setText(name)
        phoneView.setText(phone)
    }
}