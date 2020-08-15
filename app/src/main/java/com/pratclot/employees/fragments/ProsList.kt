package com.pratclot.employees.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pratclot.employees.R
import com.pratclot.employees.data.MainViewModel
import com.pratclot.employees.data.adapters.ClickListener
import com.pratclot.employees.data.adapters.SpecsAdapter
import com.pratclot.employees.databinding.ProsListFragmentBinding
import com.pratclot.employees.domain.DataItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProsList : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ProsListFragmentBinding>(
            inflater,
            R.layout.pros_list_fragment,
            container,
            false
        )

        val layoutManager = LinearLayoutManager(context)
        val adapter = SpecsAdapter(ClickListener {
            findNavController().navigate(
                ProsListDirections.actionProsListToProInfo(it as DataItem.Employee)
            )
        })

        binding.apply {
            rcv.layoutManager = layoutManager
            rcv.adapter = adapter
            lifecycleOwner = viewLifecycleOwner
        }

        viewModel.pros.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        val spec = requireArguments().getParcelable<DataItem.Specialty>("spec")
        viewModel.getProsBySpec(spec!!)

        return binding.root
    }
}