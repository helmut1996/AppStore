package com.example.app_store.Adapter

import com.example.app_store.models.StoreEntity

interface onClicklistener {
    fun onClickStore(storeEntity:StoreEntity)
    fun onFavoriteStore(storeEntity: StoreEntity)
    fun deleteStore(storeEntity: StoreEntity)
}