package com.example.app_store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.app_store.Adapter.StoreAdapter
import com.example.app_store.Adapter.onClicklistener
import com.example.app_store.databinding.ActivityMainBinding
import com.example.app_store.models.StoreEntity
import java.util.concurrent.LinkedBlockingQueue

class MainActivity : AppCompatActivity(),onClicklistener,MainAux {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: StoreAdapter
    private lateinit var mGridLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

       /* mBinding.btnSave.setOnClickListener {
            val store = StoreEntity(name = mBinding.textName.text.toString().trim())

          Thread{
              StoreApplication.database.storeDao().addStore(store)
          }.start()

            mAdapter.add(store)
            mBinding.textName.setText("")
            Toast.makeText(this,"Tienda Guardada!!",Toast.LENGTH_SHORT).show()
        }*/

        mBinding.btnAdd.setOnClickListener {
            LunchFragmentEdit()
        }

        setupRecyclerView()
    }

    private fun LunchFragmentEdit() {
        val fragment = EditStoreFragment()

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.container_main,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        hideFab()
    }

    private fun setupRecyclerView() {
        mAdapter = StoreAdapter(mutableListOf(),this)
        mGridLayout = GridLayoutManager(this,2)
        getStore()

        mBinding.listItemsStore.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }
    }

    private fun getStore() {
        val queue=LinkedBlockingQueue<MutableList<StoreEntity>>()
        Thread{
            val stores = StoreApplication.database.storeDao().getAllStore()
            queue.add(stores)
        }.start()

        mAdapter.setStore(queue.take())
    }

    /*
    * FUNTION ONCLICKLISTENER
    * */
    override fun onClickStore(storeEntity: StoreEntity) {

    }

    override fun onFavoriteStore(storeEntity: StoreEntity) {
        storeEntity.ifFavorite = !storeEntity.ifFavorite
        val queue = LinkedBlockingQueue<StoreEntity>()
        Thread{
            StoreApplication.database.storeDao().upadateStore(storeEntity)
            queue.add(storeEntity)
        }.start()
        mAdapter.update(queue.take())
    }

    override fun deleteStore(storeEntity: StoreEntity) {
        val queue = LinkedBlockingQueue<StoreEntity>()
        Thread{
            StoreApplication.database.storeDao().deleteStore(storeEntity)
            queue.add(storeEntity)
        }.start()
        mAdapter.delete(queue.take())
    }

    /*
    * MainAux
    * */
    override fun hideFab(isVisible: Boolean) {
        if (isVisible) mBinding.btnAdd.show() else mBinding.btnAdd.hide()
    }
}