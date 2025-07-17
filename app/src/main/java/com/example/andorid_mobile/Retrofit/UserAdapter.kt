package com.example.andorid_mobile.Retrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.andorid_mobile.R
import com.example.andorid_mobile.databinding.ItemUserBinding
import com.squareup.picasso.Picasso

class UserAdapter(
    private val list: ArrayList<Data>,
    private val onUserClickListener: OnUserClickListener
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userResponse: Data) {
            // get all data user
            val firstName = userResponse.first_name
            val lastName = userResponse.last_name
            val email = userResponse.email
            val avatar = userResponse.avatar

            // fill the layout item
            binding.name.text = "$firstName $lastName"
            binding.emailUser.text = email
            Picasso.get()
                .load(avatar)
                .resize(140, 140)
                .into(binding.avatarUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onUserClickListener.onUserItemClicked(position)
        }
    }

    override fun getItemCount(): Int = list.size

    fun addList(items: ArrayList<Data>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}

