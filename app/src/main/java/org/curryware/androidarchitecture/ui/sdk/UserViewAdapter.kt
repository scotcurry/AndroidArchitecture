package org.curryware.androidarchitecture.ui.sdk

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.curryware.androidarchitecture.R
import org.curryware.androidarchitecture.datamodels.Access.AccessRestApi.AccessUser

class UserViewAdapter(private val allUsers: List<AccessUser>): RecyclerView.Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val userView = inflater.inflate(R.layout.user_item_view, parent, false)

        return UserViewHolder(userView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val currentUser: AccessUser = allUsers.get(position)
        val textViewUserName = holder.userName
        val textViewFullName = holder.fullName
    }

    override fun getItemCount(): Int {
        return allUsers.size
    }
}