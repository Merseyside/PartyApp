package com.merseyside.partyapp.presentation.view.fragment.addItem.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.item.MemberInfo
import com.merseyside.partyapp.presentation.view.view.circleView.CircleView
import com.merseyside.partyapp.utils.getCircleText

class MemberSpinnerAdapter (
    context: Context,
    private var resourceId: Int,
    list: List<MemberInfo>?
): ArrayAdapter<MemberInfo>(context, resourceId, list ?: ArrayList()) {

     private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

     override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
         return getMemberView(position, convertView, parent)
     }

     private fun getMemberView(position: Int, convertView: View?, parent: ViewGroup): View {
         val member = getItem(position)!!

         val view: View = convertView ?: inflater.inflate(resourceId, parent, false)

         val name: TextView = view.findViewById(R.id.name)
         val circle: CircleView = view.findViewById(R.id.circle)

         name.text = member.name
         circle.setText(getCircleText(member.name))

         return view
     }

     override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
         return getMemberView(position, convertView, parent)
     }
}
