package pl.inpost.recruitmenttask.ui.shipmentList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.inpost.recruitmenttask.ui.databinding.ShipmentItemBinding
import pl.inpost.recruitmenttask.ui.shipmentList.entities.UiShipmentNetwork

class ShipmentAdapter(private val action: (uiShipmentNetwork: UiShipmentNetwork) -> Unit) : ListAdapter<UiShipmentNetwork, ShipmentAdapter.ShipmentViewHolder>(
    ShipmentDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipmentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShipmentItemBinding.inflate(inflater, parent, false)
        return ShipmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShipmentViewHolder, position: Int) {
        val shipment = getItem(position)
        holder.bind(shipment, action)
    }

    inner class ShipmentViewHolder(private val binding: ShipmentItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(uiShipmentNetwork: UiShipmentNetwork, action: (uiShipmentNetwork: UiShipmentNetwork) -> Unit) {
            with(binding)
            {
                senderValue.text = uiShipmentNetwork.sender?.email
                deliveryStatusIv.setImageResource(uiShipmentNetwork.shipmentTypeIconRes)

                uiShipmentNetwork.receivedFormattedDate?.let {
                    receivedDateValue.text = it
                    // TODO this is a semi-mocked logic as we are missing some
                    //  specifications here
                    receivedValue.setText(uiShipmentNetwork.status)
                }

                packageNumberValue.text = uiShipmentNetwork.number
                statusValue.setText(uiShipmentNetwork.status)

                archive.setOnClickListener {
                    action(uiShipmentNetwork)
                }
            }
        }
    }

    class ShipmentDiffCallback : DiffUtil.ItemCallback<UiShipmentNetwork>() {
        override fun areItemsTheSame(oldItem: UiShipmentNetwork, newItem: UiShipmentNetwork): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: UiShipmentNetwork, newItem: UiShipmentNetwork): Boolean {
            return oldItem == newItem
        }
    }
}
