package com.example.api

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    var requestQueue : RequestQueue? = null
    var dateRange : TextView? = null
    var todayDate : TextView? = null
    var desc : TextView? = null
    var compability : TextView? = null
    var mood : TextView? = null
    var color : TextView? = null
    var lucky_number : TextView? = null
    var lucky_time : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var textView = findViewById<TextView>(R.id.desc)

        dateRange = findViewById(R.id.dateRange)
        todayDate = findViewById(R.id.todayDate)
        desc = findViewById(R.id.description)
        compability = findViewById(R.id.compability)
        mood = findViewById(R.id.mood)
        color = findViewById(R.id.color)
        lucky_number = findViewById(R.id.lucky_number)
        lucky_time = findViewById(R.id.lucky_time)

        val spinner1: Spinner = findViewById(R.id.spinner1)
            // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.star,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner1.adapter = adapter
        }

        val spinner2: Spinner = findViewById(R.id.spinner2)
            // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.days,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner2.adapter = adapter
        }

        val button = findViewById<Button>(R.id.start)

        button.setOnClickListener() {
            val sign = spinner1.getSelectedItem().toString()
            val day = spinner2.getSelectedItem().toString()
            APIkall(createURL(sign, day))
        }

    }

    override fun onStop() {
        super.onStop()
        requestQueue?.cancelAll("horoskop")
    }

    private fun APIkall(url: String) {
        requestQueue = Volley.newRequestQueue(this)
        val view = findViewById<TextView>(R.id.desc)
        val request = StringRequest(
            Request.Method.POST, url,
            { response ->
                formatResponse(response)


            },
            { error ->
                view.text = "Kunne ikke laste horoskop!"
            }      )
        request.tag = "horoskop"
        requestQueue?.add(request)
    }
    private fun createURL(sign : String, date: String) : String {
        return "https://aztro.sameerkumar.website/?sign=$sign&day=$date"
    }


    private fun formatResponse(resp : String) : List<String> {
        resp.drop(0)
        resp.dropLast(1)
        val liste = resp.split("\"")
        dateRange!!.text = liste[3]
        todayDate!!.text = liste[7]
        desc!!.text = liste[11]
        compability!!.text = liste[15]
        mood!!.text = liste[19]
        color!!.text = liste[23]
        lucky_number!!.text = liste[27]
        lucky_time!!.text = liste[31]

        return liste


    }





}

