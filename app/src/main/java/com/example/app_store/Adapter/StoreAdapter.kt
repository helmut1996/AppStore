package com.example.app_store.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_store.R
import com.example.app_store.databinding.ItemStoreBinding
import com.example.app_store.models.StoreEntity

class StoreAdapter(private var store:MutableList<StoreEntity>, private var listener:onClicklistener):
RecyclerView.Adapter<StoreAdapter.ViewHolder>(){

    private lateinit var mContext:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //TODO: Implementar la vista
        mContext = parent.context

        val view = LayoutInflater.from(mContext).inflate(R.layout.item_store,parent,false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int = store.size
    fun add(storeEntity: StoreEntity) {
        store.add(storeEntity)
        notifyDataSetChanged()
    }
    fun setStore(stores: MutableList<StoreEntity>) {
        this.store = stores
        notifyDataSetChanged()
    }
    fun update(storeEntity: StoreEntity?) {
        val index = store.indexOf(storeEntity)
        if (index != -1){
            storeEntity?.let { store.set(index, it) }
            notifyItemChanged(index)
        }
    }
    fun delete(storeEntity: StoreEntity?) {
        val index = store.indexOf(storeEntity)
        if (index != -1){
            storeEntity?.let { store.removeAt(index) }
            notifyItemRemoved(index)
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val store = store.get(position)

        with(holder){
            setListener(store)
            binding.tvName.text= store.name
            binding.cbFavorite.isChecked = store.ifFavorite
        }
    }




    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val binding = ItemStoreBinding.bind(view)
         fun setListener(storeEntity: StoreEntity) {

             with(binding.root){
                 setOnClickListener {
                     listener.onClickStore(storeEntity)
                 }
                 setOnLongClickListener {
                     listener.deleteStore(storeEntity)
                     true
                 }
             }

             binding.cbFavorite.setOnClickListener {
                 listener.onFavoriteStore(storeEntity)
             }
        }
    }

}