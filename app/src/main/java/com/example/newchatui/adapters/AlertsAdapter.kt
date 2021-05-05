package com.example.newchatui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newchatui.R
import com.example.newchatui.model.AlertList
import kotlinx.android.synthetic.main.alert_item_list.view.*

class AlertsAdapter(val alertList :List<AlertList> , val context: Context) :RecyclerView.Adapter<AlertsAdapter.AlertsViewHolder>(){

    class AlertsViewHolder(view: View) :RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertsViewHolder {
        return  AlertsViewHolder(LayoutInflater.from(context).inflate(R.layout.alert_item_list,parent,false))
    }

    override fun getItemCount(): Int {
        return alertList.size
    }

    override fun onBindViewHolder(holder: AlertsViewHolder, position: Int) {
        holder.itemView.txtTitleRecyclerView.text = alertList.get(position).title
        holder.itemView.txtDescriptionRecyclerView.text = alertList.get(position).alertMsg
    }
}