package com.pratclot.employees.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pratclot.employees.R
import com.pratclot.employees.data.MainViewModel
import com.pratclot.employees.databinding.ProInfoFragmentBinding
import com.pratclot.employees.domain.DataItem
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProInfo : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var picasso: Picasso

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ProInfoFragmentBinding>(
            inflater,
            R.layout.pro_info_fragment,
            container,
            false
        )

        binding.apply {
            employee = requireArguments().getParcelable("employee")
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }
}