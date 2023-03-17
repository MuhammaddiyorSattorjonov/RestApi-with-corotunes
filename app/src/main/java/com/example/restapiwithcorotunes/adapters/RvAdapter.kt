package com.example.restapiwithcorotunes.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.restapiwithcorotunes.databinding.ItemRvBinding
import com.example.restapiwithcorotunes.models.MyTodo

class RvAdapter(var list: List<MyTodo> = emptyList(), val rvClick: RvClick) :
    RecyclerView.Adapter<RvAdapter.Vh>() {

    inner class Vh(val itemRvBinding: ItemRvBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(user: MyTodo, position: Int) {

            itemRvBinding.itemTvName.text = user.sarlavha
            itemRvBinding.itemTvAbout.text = user.matn
            itemRvBinding.itemTvHolati.text = user.holat
            itemRvBinding.itemTvMuddat.text = user.oxirgi_muddat

            itemRvBinding.myPopupMenu.setOnClickListener {
                rvClick.menuCklick(itemRvBinding.myPopupMenu, user)
                Log.i("TAG", "onBind: ")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface RvClick {
        fun menuCklick(image: ImageView, myTodo: MyTodo)
    }
}