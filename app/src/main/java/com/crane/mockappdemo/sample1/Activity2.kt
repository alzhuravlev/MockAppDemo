package com.crane.mockappdemo.sample1

import com.crane.mockapp.core.MockAppActivity

class Activity2 : MockAppActivity() {

    /**
     * You can override getProjectId or getProjectName to pass a Project to find a layout in
     * @see com.crane.mockapp.core.MockApp.resolveLayoutDescriptor
     */
    override fun getProjectId(): String? {
        return null
    }

    override fun getProjectName(): String? {
        return "icountries"
    }

    /**
     * You can override getLayoutId or getLayoutName to find a layout to inflate
     * @see com.crane.mockapp.core.MockApp.resolveLayoutDescriptor
     */
    override fun getLayoutId(): String? {
        return null
    }

    override fun getLayoutName(): String? {
        return "page_home"
    }

    override fun inflateDefaultLayout() {
        // if for any reason layout count not be inflated this method will be invoked
    }
}