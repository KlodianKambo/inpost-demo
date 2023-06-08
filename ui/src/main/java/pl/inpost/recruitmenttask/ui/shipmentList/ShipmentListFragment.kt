package pl.inpost.recruitmenttask.ui.shipmentList

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.recruitmenttask.ui.R
import pl.inpost.recruitmenttask.ui.databinding.FragmentShipmentListBinding
import pl.inpost.recruitmenttask.ui.shipmentList.adapter.ShipmentAdapter
import pl.inpost.recruitmenttask.ui.shipmentList.adapter.ShipmentItemDecorator
import pl.inpost.recruitmenttask.ui.shipmentList.adapter.SortingOptionAdapter

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


        val adapter = ShipmentAdapter { viewModel.archive(it.number) }

        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { shipments ->
                adapter.submitList(shipments)
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

        binding?.recyclerView?.run {
            addItemDecoration(
                ShipmentItemDecorator(
                    20,
                    ContextCompat.getColor(requireContext(), R.color.separator_bg)
                )
            )
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }

        binding?.sortingSpinner?.adapter =
            SortingOptionAdapter(requireContext(), viewModel.sortingOptions)
        binding?.sortingSpinner?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position in 0 until viewModel.sortingOptions.size) {
                        viewModel.sort(viewModel.sortingOptions[position])
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Do nothing
                }
            }

        viewModel.refreshData()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    companion object {
        fun newInstance() = ShipmentListFragment()
    }
}