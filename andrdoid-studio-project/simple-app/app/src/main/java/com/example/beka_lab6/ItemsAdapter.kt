package com.example.beka_lab6

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(
    var items: List<Item>,
    var context: Context,
    var onAddToCartClick: (Item) -> Unit // "(Item) -> Unit" lambda function, that handles click event
    ) : RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.item_list_image)
        val title: TextView = view.findViewById(R.id.item_list_title)
        val description: TextView = view.findViewById(R.id.item_list_descritption)
        val price: TextView = view.findViewById(R.id.item_list_price)
        val addToCartButton: Button = view.findViewById(R.id.cardview_button)

        init {
            addToCartButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = items[position] // Access the items property from the outer class
                    onAddToCartClick(item) // Access the onAddToCartClick callback from the outer class
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.description.text = item.description
        holder.price.text = "$" + item.price.toString()

        val imageID = context.resources.getIdentifier(
            item.image,
            "drawable",
            context.packageName
        )
        holder.image.setImageResource(imageID)

        // Displays the quantity of items in the cart on the button, if needed
        holder.addToCartButton.text = "ADD TO CART ${item.quantityInCart}"

        // Adds an OnClickListener to the whole card view
        holder.itemView.setOnClickListener {
            // Handles the item click here
            val intent = Intent(context, ItemDetailActivity::class.java)
            intent.putExtra("item", item)
            context.startActivity(intent)
        }
    }
}