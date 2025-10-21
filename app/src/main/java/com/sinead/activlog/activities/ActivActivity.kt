package com.sinead.activlog.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.sinead.activlog.R
import com.sinead.activlog.databinding.ActivityActivBinding
import com.sinead.activlog.main.MainApp
import com.sinead.activlog.models.ActivModel
import timber.log.Timber.i

class ActivActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityActivBinding
    var activ = ActivModel()
    lateinit var app : MainApp

    private var types = arrayOf(
        "Run", "Walk", "Cycle", "Swim", "Workout", "Train"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Log activity started..")

        // Spinner
        binding.activTypeSpinner.onItemSelectedListener = this
        val arrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, types
        )
        arrayAdapter.setDropDownViewResource(
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
        )
        binding.activTypeSpinner.adapter = arrayAdapter

        // Button
        binding.btnAdd.setOnClickListener {
            // spinner is set onItemSelectedListener
            activ.duration = binding.durationEditText.text.toString()
            if (activ.duration.isNotEmpty()) {
                app.activs.add(activ.copy())
                i("add Button Pressed, duration: ${activ.duration}, type: ${activ.type}")
                for (i in app.activs.indices) {
                    i("Activ[$i]:${this.app.activs[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"Enter a duration", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
    {
        activ.type = binding.activTypeSpinner.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

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