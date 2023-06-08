package pl.inpost.recruitmenttask.ui.shipmentList.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import pl.inpost.recruitmenttask.ui.R
import pl.inpost.recruitmenttask.ui.shipmentList.entities.UiSortingOption


class SortingOptionAdapter(context: Context, sortingOptions: List<UiSortingOption>) :
    ArrayAdapter<UiSortingOption>(context, 0, sortingOptions) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_sort, parent, false)

        getItem(position)?.let {
            view.findViewById<TextView>(R.id.status_value).text = context.getString(it.titleResId)
        }

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_sort, parent, false)

        getItem(position)?.let {
            view.findViewById<TextView>(R.id.status_value).text = context.getString(it.titleResId)
        }

        return view
    }
}