package com.adomino.ddsdb.ui.sample

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adomino.ddsdb.R
import com.adomino.ddsdb.recyclerview.XAdapter
import com.adomino.ddsdb.recyclerview.XModel

class SampleActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_sample)

    val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

    recyclerView.layoutManager = LinearLayoutManager(this)
    val adapter = XAdapter.create { viewGroup, _ ->
      SampleViewHolder(
          LayoutInflater.from(this@SampleActivity)
              .inflate(R.layout.text_view_1, viewGroup, false)
      )
    }
    recyclerView.adapter = adapter

    val models = mutableListOf<XModel>()

    for (i in 0..100) {
      models.add(SampleModel("$i", "Dinh $i"))
    }

    adapter.submitChange(models)
  }
}