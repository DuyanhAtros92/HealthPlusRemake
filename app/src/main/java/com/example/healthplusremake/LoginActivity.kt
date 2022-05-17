package com.example.healthplusremake

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    var layoutSignUp: Button? = null
    private var edtEmail: EditText? = null
    private var edtPassword: EditText? = null
    var btn_SignIn: Button? = null
    var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        iitUi()
        initListener()
    }

    private fun iitUi() {
        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        btn_SignIn = findViewById(R.id.btn_SignIn)
        progressDialog = ProgressDialog(this)
    }

    private fun initListener() {
        btn_SignIn!!.setOnClickListener { onClickSignIn() }
    }

    private fun onClickSignIn() {
        val email = edtEmail!!.text.toString().trim { it <= ' ' }
        val password = edtPassword!!.text.toString().trim { it <= ' ' }
        val auth = FirebaseAuth.getInstance()
        progressDialog!!.show()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this
            ) { task ->
                if (task.isSuccessful) {
                    progressDialog!!.dismiss()
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                } else {
                    progressDialog!!.dismiss()
                    Toast.makeText(this@LoginActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}