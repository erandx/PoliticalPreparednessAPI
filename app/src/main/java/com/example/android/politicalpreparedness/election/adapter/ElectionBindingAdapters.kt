package com.example.android.politicalpreparedness.election.adapter

import android.text.format.DateUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.network.models.Election
import java.util.*

@BindingAdapter("listData")
fun recyclerBinding(recyclerView: RecyclerView, data: List<Election>?){
val adapter = recyclerView.adapter as ElectionListAdapter
    adapter.submitList(data)
}

@BindingAdapter ("electionDate")
fun formatDate(textView: TextView, value: Date?){
    var date = ""
    if (value != null){
        date = DateUtils.formatDateTime(textView.context, value.time, DateUtils.FORMAT_SHOW_DATE
        or DateUtils.FORMAT_SHOW_YEAR)
    }
    textView.text = date
}