package org.curryware.androidarchitecture.ui.sdk

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.curryware.androidarchitecture.R

class UserViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView){

    val userName = itemView.findViewById<TextView>(R.id.userName)
    val fullName = itemView.findViewById<TextView>(R.id.fullName)
}