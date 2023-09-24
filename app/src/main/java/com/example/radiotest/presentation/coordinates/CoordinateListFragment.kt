package com.example.radiotest.presentation.coordinates

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.radiotest.databinding.FragmentCoordinatesBinding
import com.example.radiotest.domain.Coordinate
import com.example.radiotest.presentation.App
import javax.inject.Inject


class CoordinateListFragment : Fragment() {
    private var _binding: FragmentCoordinatesBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var viewModelFactory: CoordinateListViewModel.Factory
    private val viewModel by viewModels<CoordinateListViewModel>{
        viewModelFactory
    }
    private var adapter: CoordinatesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.getCoordinateListComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCoordinatesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()

        viewModel.listCoordinate.observe(viewLifecycleOwner){
            updateList(it)
        }
    }

    private fun initRecycler(){
        adapter = CoordinatesAdapter()
        binding.recyclerCoordinates.adapter = adapter
    }

    private fun updateList(listCoordinates: List<Coordinate>){
        adapter?.addCoordinates(listCoordinates)
    }

}