package com.example.radiotest.presentation.coordinates

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.radiotest.R
import com.yandex.mapkit.geometry.Point

private const val ARG_LATITUDE = "latitude"
private const val ARG_LONGITUDE = "longitude"

class CoordinateDialog : DialogFragment() {
    var onOkCallback: (lat: String, lon: String) -> Unit = { _: String, _: String -> }
    private var lat: String? = null
    private var lon: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lat = it.getString(ARG_LATITUDE)
            lon = it.getString(ARG_LONGITUDE)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_coordinate_dialog, null)
            val editTextLat = view.findViewById<EditText>(R.id.edittext_latitude)
            val editTextLon = view.findViewById<EditText>(R.id.edittext_longitude)
            editTextLat.setText(lat)
            editTextLon.setText(lon)
            builder.setView(view)
                .setTitle(getString(R.string.title_dialog))
                .setPositiveButton(getString(R.string.save_button)) { _, _ ->
                    onOkCallback.invoke(editTextLat.text.toString() , editTextLon.text.toString())
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {

        fun newInstance(point: Point, onOk: (lat: String, lon: String) -> Unit): CoordinateDialog {
            return  CoordinateDialog().apply {
                onOkCallback = onOk
                arguments = Bundle().apply {
                    putString(ARG_LATITUDE, point.latitude.toString())
                    putString(ARG_LONGITUDE, point.longitude.toString())
                }
            }
        }
    }
}