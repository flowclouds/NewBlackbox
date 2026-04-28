package com.velora.novabox.util

import androidx.annotation.StringRes
import com.velora.novabox.app.App


fun getString(@StringRes id:Int,vararg arg:String):String{
    if(arg.isEmpty()){
        return App.getContext().getString(id)
    }
    return App.getContext().getString(id,*arg)
}

