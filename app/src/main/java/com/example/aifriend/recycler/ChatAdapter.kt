package com.example.aifriend.recycler

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.aifriend.ChatRoomActivity
import com.example.aifriend.R
import com.example.aifriend.data.ChatData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import androidx.recyclerview.widget.DiffUtil as Diff


class ChatAdapter: RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    private val chatList = ArrayList<ChatData>()
    private var uid : String? = null
    private var fireStore: FirebaseFirestore? = null
    private val destinationUsers: ArrayList<String> = arrayListOf()

    init {
        uid = Firebase.auth.currentUser?.uid.toString()

        fireStore = FirebaseFirestore.getInstance()

        fireStore?.collection("ChatRoomList")
            ?.whereArrayContains("uid", uid!!)
            ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // ArrayList 비워줌
                chatList.clear()

                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject<ChatData>()
                    chatList.add(item!!)
                }
                notifyDataSetChanged()

            }

        // DiffUtil 로 갱신 해보기

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false))
    }

    //user 뷰 홀더 설정
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val chatTitleTextView: TextView = view.findViewById(R.id.chatTitleTextView)
        val chatMessageTextView: TextView = view.findViewById(R.id.chatMessageTextView)
    }



    @SuppressLint("WrongConstant")
    @RequiresApi(31)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // 계정에 따라 상대방 이름 다르게 보이기
        if(chatList[position].uid?.get(0) == uid) {
            holder.chatTitleTextView.text = chatList[position].name?.get(1)
        }
        else {
            holder.chatTitleTextView.text = chatList[position].name?.get(0)
        }
        holder.chatMessageTextView.text = chatList[position].lastChat

        //채팅창 선책 시 이동
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ChatRoomActivity::class.java)
            intent.putExtra("destinationUid", chatList[position].key)
            holder.itemView.context?.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return chatList.size
    }


}