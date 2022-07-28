package com.example.healthplusremake

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

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

        btnBook.setOnClickListener{
        val i = Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.158.44:3000/schedule?fbclid=IwAR3fD8bexI10RoLp-WKkmw3P4e99NH4ZWy1QsNxued4QKI07eU8wobhCwGQ"))
        startActivity(i)}


        btnChat.setOnClickListener{
            val intent: Intent = Intent (this@DetailActivity, ChatActivity::class.java)
            intent.putExtra("userId", phone);
            intent.putExtra("userName", name);
            startActivity(intent)
        }
    }
}