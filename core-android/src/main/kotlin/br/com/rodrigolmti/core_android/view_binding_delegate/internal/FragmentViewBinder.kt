package br.com.rodrigolmti.core_android.view_binding_delegate.internal

import android.view.View
import androidx.annotation.RestrictTo
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/*
* Copyright 2020 Kirill Rozov
* Licensed under the Apache License, Version 2.0 (the "License");
* You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
*
* https://github.com/kirich1409/ViewBindingPropertyDelegate (search for tag 1.0.0)
* */

@RestrictTo(RestrictTo.Scope.LIBRARY)
@PublishedApi
internal class FragmentViewBinder<T : ViewBinding>(private val viewBindingClass: Class<T>) {

    /**
     * Cache static method `ViewBinding.bind(View)`
     */
    private val bindViewMethod by lazy(LazyThreadSafetyMode.NONE) {
        viewBindingClass.getMethod("bind", View::class.java)
    }

    /**
     * Create new [ViewBinding] instance
     */
    @Suppress("UNCHECKED_CAST")
    fun bind(fragment: Fragment): T {
        return bindViewMethod(null, fragment.requireView()) as T
    }
}
