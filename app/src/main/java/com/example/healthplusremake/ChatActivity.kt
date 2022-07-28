package com.example.healthplusremake

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {
    var firebaseUser: FirebaseUser? = null
    var reference: DatabaseReference? = null
    lateinit var chatAdapter : ChatAdapter
    var chatList  = ArrayList<Chat>()
    var topic = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)



       chatRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
         chatAdapter = ChatAdapter(this@ChatActivity, chatList)

        chatRecyclerView.adapter = chatAdapter

        var intent = getIntent()
        var userId = intent.getStringExtra("userId")
        var userName = intent.getStringExtra("userName")



        firebaseUser = FirebaseAuth.getInstance().currentUser
       try {
           reference = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)
       }
       catch (err : Exception) {
           err.printStackTrace();
       }



        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                val user  = snapshot.getValue(User::class.java)

                tvUserName.text = user?.userName

            }
        })

        btnSendMessage.setOnClickListener {
            var message: String = etMessage.text.toString()

            if (message.isEmpty()) {
                Toast.makeText(applicationContext, "message is empty", Toast.LENGTH_SHORT).show()
                etMessage.setText("")
            } else {
                sendMessage(firebaseUser!!.uid, userId!!, message)
                etMessage.setText("")
                topic = "/topics/$userId"

            }
        }
        if (userId != null) {

            readMessage(firebaseUser!!.uid, userId)
        }
    }

    private fun sendMessage(senderId: String, receiverId: String, message: String) {
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()

        var hashMap: HashMap<String, String> = HashMap()
        hashMap.put("senderId", senderId)
        hashMap.put("receiverId", receiverId)
        hashMap.put("messag" +
                "'e", message)

        reference!!.child("Chat").push().setValue(hashMap)

    }
    fun readMessage(senderId: String, receiverId: String) {
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Chat")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val chat = dataSnapShot.getValue(Chat::class.java)


                    System.out.println("chat"+ chat)
                    if (chat != null) {
                        chatList.add(chat)
                    }

                }

                chatAdapter.notifyDataSetChanged()
            }
        })
    }

}