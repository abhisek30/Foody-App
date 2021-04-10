package com.example.foody

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foody.databinding.FragmentRecipesBinding

class RecipesFragment : Fragment() {
    private var _binding:FragmentRecipesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment with viewBinding
        _binding = FragmentRecipesBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.recyclerView.showShimmer()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}