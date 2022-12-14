package com.example.aifriend

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aifriend.data.ChatData
import com.example.aifriend.recycler.AiChatAdapter
import com.example.aifriend.recycler.ChatAdapter
import com.google.android.material.internal.ContextUtils

/**
 * 채팅방 리스트 프래그먼트
 */
class ChatFragment : Fragment() {
    val mContext = context

    private val chatList = ArrayList<ChatData>()
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        count ++
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        val aiChatRecyclerView = view.findViewById<RecyclerView>(R.id.aiChatRecyclerView)
        val chatRecyclerView = view.findViewById<RecyclerView>(R.id.chatFragmentRecyclerView)

        val chatItemView = inflater.inflate(R.layout.item_chat, container, false)
        val receivedChatNotificationIcon: ImageView = chatItemView.findViewById(R.id.receivedChatNotificationIcon)
        var notify = FCMService()
        // AI 채팅방, 상대 채팅방 띄우기
        aiChatRecyclerView.layoutManager = LinearLayoutManager(context)
        chatRecyclerView.layoutManager = LinearLayoutManager(context)

        aiChatRecyclerView.adapter = AiChatAdapter()
        aiChatRecyclerView.itemAnimator = null
        chatRecyclerView.adapter = ChatAdapter()


        return view
    }

}