package com.example.imran_47129083_job3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapterlist(private val userD:List<UserD>, private val itemClickListener: ItemClickListener):
    RecyclerView.Adapter<Adapterlist.ViewHolder>() {
    class ViewHolder (itemView: View):RecyclerView.ViewHolder(itemView) {
        val sname = itemView.findViewById<TextView>(R.id.tname)
        val semail = itemView.findViewById<TextView>(R.id.temail)
        val sphone = itemView.findViewById<TextView>(R.id.tphone)
        val saddress = itemView.findViewById<TextView>(R.id.taddress)
        val sedit = itemView.findViewById<ImageButton>(R.id.editBtn)
        val sdelete = itemView.findViewById<ImageButton>(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Adapterlist.ViewHolder, position: Int) {
        val userD : UserD= userD[position]
        holder.sname.text = userD.name
        holder.semail.text = userD.email
        holder.sphone.text = userD.phone
        holder.saddress.text = userD.address

        holder.sedit.setOnClickListener {
            itemClickListener.onEditItemClick(userD)
        }
        holder.sdelete.setOnClickListener {
            itemClickListener.onDeleteItemClick(userD)
        }



    }

    override fun getItemCount(): Int {
        return userD.size
    }

}

interface ItemClickListener{
    fun onEditItemClick(userD:UserD)
    fun onDeleteItemClick(userD:UserD)
}
