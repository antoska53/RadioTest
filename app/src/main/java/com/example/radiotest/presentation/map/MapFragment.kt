package com.example.radiotest.presentation.map

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.radiotest.R
import com.example.radiotest.databinding.FragmentMapBinding
import com.example.radiotest.presentation.App
import com.example.radiotest.presentation.TcpService
import com.example.radiotest.presentation.coordinates.CoordinateDialog
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.ScreenPoint
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.runtime.image.ImageProvider
import javax.inject.Inject


class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var viewModelFactory: MapViewModel.Factory
    private val viewModel by viewModels<MapViewModel>{
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.getMapComponent().inject(this)
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(requireContext())
        startTcpService()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenu()
        viewModel.listCoordinate.observe(viewLifecycleOwner){listCoordinate ->
            listCoordinate.forEach {
                setPinToMap(it.latitude, it.longitude)
            }
        }
        binding.mapView.map.move(
            CameraPosition(Point(LATITUDE, LONGITUDE), ZOOM, AZIMUTH, TILT),
            Animation(Animation.Type.SMOOTH, DURATION),
            null
        )
    }

    private fun initMenu(){
        requireActivity().addMenuProvider(object: MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_map, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.save_coordinate -> {
                        val worldPoint = getCoordinateFromMap()
                        val dialog =
                            CoordinateDialog.newInstance(worldPoint) { lat: String, lon: String ->
                                saveCoordinate(lat, lon)
                                setPinToMap(lat, lon)
                            }
                        dialog.show(childFragmentManager, "dialog")
                        true
                    }
                    R.id.list_coordinates -> {
                        navigateToListCoordinate()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }

    private fun startTcpService(){
        val intent = Intent(requireContext(), TcpService::class.java)
        intent.putExtra(SERVER_ADDRESS_KEY, SERVER_ADDRESS)
        intent.putExtra(SERVER_PORT_KEY, SERVER_PORT)
        ContextCompat.startForegroundService(requireContext(), intent)
    }

    private fun getCoordinateFromMap(): Point{
        val centerX = binding.mapView.mapWindow.width() / 2f
        val centerY = binding.mapView.mapWindow.height() / 2f
        val centerPoint = ScreenPoint(centerX, centerY)
        val worldPoint = binding.mapView.mapWindow.screenToWorld(centerPoint)
        return worldPoint ?: Point(0.0, 0.0)
    }

    private fun setPinToMap(lat: String, lon: String){
        binding.mapView.map.mapObjects.addPlacemark(
            Point(lat.toDouble(), lon.toDouble()),
            ImageProvider.fromResource(requireContext(), R.drawable.ic_location)
        )
    }

    private fun saveCoordinate(lat: String, lon: String) {
        viewModel.saveCoordinate(lat, lon)
    }

    private fun navigateToListCoordinate(){
        findNavController().navigate(R.id.action_mapFragment_to_coordinatesFragment)
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    companion object {
        const val LATITUDE = 59.945933
        const val LONGITUDE = 30.320045
        const val ZOOM = 14.0f
        const val AZIMUTH = 0.0f
        const val TILT = 0.0f
        const val DURATION = 5.0f
        const val SERVER_ADDRESS = "192.168.1.96"
        const val SERVER_PORT = 5555
        const val SERVER_ADDRESS_KEY = "SERVER_ADDRESS"
        const val SERVER_PORT_KEY = "SERVER_PORT"
    }
}