package com.target.core

import android.app.Application

interface ResourceProvider {
    fun getString(id: Int): String
}

class ResourceProviderImpl(
    private val context: Application
) : ResourceProvider {

    override fun getString(id: Int): String {
        return context.getString(id)
    }

}