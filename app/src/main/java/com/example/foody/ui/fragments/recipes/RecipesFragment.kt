package com.example.foody.ui.fragments.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foody.databinding.FragmentRecipesBinding

class RecipesFragment : Fragment() {
    //viewBinding variables
    private var _binding:FragmentRecipesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment with viewBinding
        _binding = FragmentRecipesBinding.inflate(inflater,container,false)
        val view = binding.root

        //recyclerView Shimmer effect(placeholder_row_layout.xml)
        binding.recyclerView.showShimmer()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}