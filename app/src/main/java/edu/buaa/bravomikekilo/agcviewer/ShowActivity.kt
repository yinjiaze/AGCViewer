package edu.buaa.bravomikekilo.agcviewer

import android.app.Activity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_show.*

class ShowActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        button.setOnClickListener { view -> change(view) }
        title = "Show"
    }

    fun change(view: View){
        chart.changeValues()
    }

}
