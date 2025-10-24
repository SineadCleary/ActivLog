package com.sinead.activlog.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.sinead.activlog.R
import com.sinead.activlog.adapters.ActivAdapter
import com.sinead.activlog.databinding.ActivityActivBinding
import com.sinead.activlog.main.MainApp
import com.sinead.activlog.models.ActivModel
import timber.log.Timber.i

class ActivActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityActivBinding
    var activ = ActivModel()
    lateinit var app : MainApp
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Log activity started..")

        // Type Spinner
        // code based on https://developer.android.com/develop/ui/views/components/spinner#SelectListener
        binding.activTypeSpinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            binding.activTypeSpinner.adapter = adapter
        }

        // Time Spinner
        binding.timeSpinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.times,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            binding.timeSpinner.adapter = adapter
        }

        // RPE Picker
        binding.RPEPicker.setMinValue(1)
        binding.RPEPicker.setMaxValue(10)
        binding.RPEPicker.value = 5

        // Edit mode
        if (intent.hasExtra("activ_edit")) {
            edit = true
            activ = intent.extras?.getParcelable("activ_edit")!!
            val types = resources.getStringArray(R.array.types)
            val times = resources.getStringArray(R.array.times)
            binding.activTypeSpinner.setSelection(types.indexOf(activ.type))
            binding.timeSpinner.setSelection(times.indexOf(activ.time.toString()))
            binding.RPEPicker.value = activ.RPE
            binding.noteEditText.setText(activ.note)
            binding.btnAdd.setText(R.string.save_activ)
            binding.btnDelete.isVisible = true
        } else {
            binding.btnDelete.isVisible = false
        }

        // Add activity button
        binding.btnAdd.setOnClickListener {
            activ.type = binding.activTypeSpinner.selectedItem.toString()
            activ.time = binding.timeSpinner.selectedItem.toString().toInt()
            activ.RPE = binding.RPEPicker.getValue()
            activ.note = binding.noteEditText.text.toString()
            if (edit) {
                app.activs.update(activ.copy())
            } else {
                app.activs.create(activ.copy())
            }
            setResult(RESULT_OK)
            finish()
        }

        // Delete activity button
        binding.btnDelete.setOnClickListener {
            app.activs.delete(activ)
            setResult(RESULT_OK)
            finish()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) { }

    override fun onNothingSelected(parent: AdapterView<*>?) { }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activ, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}