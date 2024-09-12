package com.example.beka_lab6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ItemDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        // Retrieves the item object from the intent
        val item = intent.getParcelableExtra<Item>("item")

        val imageView = findViewById<ImageView>(R.id.item_detail_image)
        val titleTextView = findViewById<TextView>(R.id.item_detail_title)
        val textTextView = findViewById<TextView>(R.id.item_detail_text)

        if (item != null) {
            val imageID = resources.getIdentifier(
                item.image,
                "drawable",
                packageName
            )
            imageView.setImageResource(imageID)
            titleTextView.text = item.title
            textTextView.text = item.text
        }

    }
}