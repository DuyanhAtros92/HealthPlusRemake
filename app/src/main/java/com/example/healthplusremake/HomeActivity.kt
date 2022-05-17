package com.example.healthplusremake


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.healthplusremake.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONException
import org.json.JSONObject


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    lateinit var doctorsRec : RecyclerView
    lateinit var  doctorAdapter : DoctorRecycleViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        doctorsRec = findViewById(R.id.rv_sub_category)
        doctorAdapter = DoctorRecycleViewAdapter()
        doctorAdapter.setContext(this)
        getData()
        initRecyclerView()



    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }else{
            val email = firebaseUser.email
            binding.tvCategory.text = email
        }
    }

    private fun getData() {
        // creating a new variable for our request queue
        val queue = Volley.newRequestQueue(this)
        val url = "https://server-production-2a21.up.railway.app/api/doctor"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val doctors =  ArrayList<Doctor>()
                for (i in 0..response.length()) {

                    try {
                        // we are getting each json object.
                        val responseObj = response.getJSONObject(i)

                        val name = responseObj.getString("name")
                        val image = responseObj.getString("img")
                        val phone = responseObj.getString("phone")

                      //  Log.i("Log" , "Doctor : " +   Doctor(name,image,phone).toString())
                        doctors.add(Doctor(name,image, phone))

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }



                doctorAdapter.setList(doctors)
                doctorAdapter.notifyDataSetChanged()

            }
        ) { }
        queue.add(jsonArrayRequest)
    }


    private fun initRecyclerView(){

        doctorsRec.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = doctorAdapter
        }



    }




}
