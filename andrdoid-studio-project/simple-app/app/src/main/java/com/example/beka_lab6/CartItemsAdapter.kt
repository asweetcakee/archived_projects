package com.example.beka_lab6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartItemsAdapter(private val cartItems: List<Item>) :
    RecyclerView.Adapter<CartItemsAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.cart_item_image)
        val title: TextView = view.findViewById(R.id.cart_item_title)
        val description: TextView = view.findViewById(R.id.cart_item_description)
        val price: TextView = view.findViewById(R.id.cart_item_price)
        val quantity: TextView = view.findViewById(R.id.cart_item_quantity)
        val totalPrice: TextView = view.findViewById(R.id.cart_item_total_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.title.text = item.title
        holder.description.text = item.description
        holder.price.text = "$" + item.price.toString()
        holder.quantity.text = "Quantity: ${item.quantityInCart}"

        // Calculates the total price for the same items
        val totalItemPrice = item.price * item.quantityInCart

        holder.totalPrice.text = "Total: $${"%.2f".format(totalItemPrice)}"

        val imageID = holder.itemView.context.resources.getIdentifier(
            item.image,
            "drawable",
            holder.itemView.context.packageName
        )
        holder.image.setImageResource(imageID)
    }
}

