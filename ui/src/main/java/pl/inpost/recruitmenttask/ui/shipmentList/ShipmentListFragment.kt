package pl.inpost.recruitmenttask.ui.shipmentList

import android.os.Bundle
import android.view.*
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
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { shipments ->
                shipments.forEach(::bind)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun bind(uiShipmentNetwork: UiShipmentNetwork) {
        val shipmentItemBinding = ShipmentItemBinding.inflate(layoutInflater).apply {
            shipmentNumber.text = uiShipmentNetwork.number
            status.setText(uiShipmentNetwork.status)
        }
        binding?.scrollViewContent?.addView(shipmentItemBinding.root)
    }

    companion object {
        fun newInstance() = ShipmentListFragment()
    }
}
