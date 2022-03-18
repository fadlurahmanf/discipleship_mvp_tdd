package com.fadlurahmanf.starter_app_mvp.ui.home

import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

object BaseDrawer {
    fun openCloseDrawer(drawerLayout: DrawerLayout?) {
        if (drawerLayout?.isDrawerOpen(GravityCompat.START) == true){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            drawerLayout?.openDrawer(GravityCompat.START)
        }
    }
}