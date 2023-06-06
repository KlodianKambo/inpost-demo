package pl.inpost.recruitmenttask.ui.shipmentList

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.recruitmenttask.ui.R
import pl.inpost.recruitmenttask.ui.databinding.FragmentShipmentListBinding
import pl.inpost.recruitmenttask.ui.databinding.ShipmentItemBinding
import pl.inpost.recruitmenttask.ui.shipmentList.entities.UiShipmentNetwork

@AndroidEntryPoint
class ShipmentListFragment : Fragment() {

    private val viewModel: ShipmentListViewModel by viewModels()
    private var binding: FragmentShipmentListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shipment_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShipmentListBinding.inflate(inflater, container, false)
        return requireNotNull(binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refreshData()

        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { shipments ->

                binding?.scrollViewContent?.removeAllViews()


                shipments.forEach(::bind)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isLoading.collect {
                binding?.loading?.isVisible = it
                binding?.swipeRefresh?.isRefreshing = it
            }
        }

        binding?.swipeRefresh?.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun bind(uiShipmentNetwork: UiShipmentNetwork) {
        val shipmentItemBinding = ShipmentItemBinding.inflate(layoutInflater).apply {
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
                viewModel.archive(uiShipmentNetwork.number)
            }
        }
        binding?.scrollViewContent?.addView(shipmentItemBinding.root)
    }

    companion object {
        fun newInstance() = ShipmentListFragment()
    }
}
