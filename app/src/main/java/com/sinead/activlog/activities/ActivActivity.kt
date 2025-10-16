package com.sinead.activlog.activities

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.sinead.activlog.databinding.ActivityActivBinding
import com.sinead.activlog.models.ActivModel
import timber.log.Timber
import timber.log.Timber.i

class ActivActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityActivBinding
    var activ = ActivModel()

    private var types = arrayOf(
        "Run", "Walk", "Cycle", "Swim", "Workout", "Train"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityActivBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())
        Timber.i("Log activity started..")

        // Spinner
        binding.activTypeSpinner.onItemSelectedListener = this
        val arrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            R.layout.simple_spinner_item, types
        )
        arrayAdapter.setDropDownViewResource(
            R.layout.simple_spinner_dropdown_item
        )
        binding.activTypeSpinner.adapter = arrayAdapter

        // Button
        binding.btnAdd.setOnClickListener {
            activ.duration = binding.durationEditText.text.toString()
            if (activ.duration.isNotEmpty()) {
                i("add Button Pressed, duration: $activ.duration")
            }
            else {
                Snackbar
                    .make(it,"Enter a duration", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View,
                                position: Int, id: Long)
    {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}