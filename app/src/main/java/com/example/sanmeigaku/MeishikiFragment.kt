package com.example.sanmeigaku

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sanmeigaku.databinding.FragmentMeishikiBinding

class MeishikiFragment : Fragment() {
    private var _binding: FragmentMeishikiBinding? = null
    private val binding get() = _binding!!

    /**
     * Create meishiki fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * Create meishiki fragment view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeishikiBinding.inflate(inflater, container, false)
        return binding.root
    }
}