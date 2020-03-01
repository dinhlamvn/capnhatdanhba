package com.adomino.ddsdb.ui.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adomino.ddsdb.R
import com.adomino.ddsdb.recyclerview.XAdapter

class SampleActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_sample)

    val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

    recyclerView.layoutManager = LinearLayoutManager(this)
    val adapter = XAdapter.create()
        .setItemClickListener { view, model, position ->
          view?.setOnClickListener {
            val sample = model as SampleModel
            Toast.makeText(this@SampleActivity, "Hello ${sample.name}", Toast.LENGTH_LONG)
                .show()
          }
        }
        .setViewHolderFactory { viewGroup, _ ->
          SampleViewHolder(
              LayoutInflater.from(this@SampleActivity)
                  .inflate(R.layout.text_view_1, viewGroup, false)
          )
        }
    recyclerView.adapter = adapter

    for (i in 0..100) {
      adapter.addModel(SampleModel("$i", "Dinh $i"))
    }
  }
}