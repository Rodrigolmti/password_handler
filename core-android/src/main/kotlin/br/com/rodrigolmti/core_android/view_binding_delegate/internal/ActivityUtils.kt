package br.com.rodrigolmti.core_android.view_binding_delegate.internal

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.RestrictTo
import androidx.core.app.ActivityCompat

/*
* Copyright 2020 Kirill Rozov
* Licensed under the Apache License, Version 2.0 (the "License");
* You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
*
* https://github.com/kirich1409/ViewBindingPropertyDelegate (search for tag 1.0.0)
* */

@RestrictTo(RestrictTo.Scope.LIBRARY)
@PublishedApi
internal fun <V : View> Activity.requireViewByIdCompat(@IdRes viewId: Int): V {
    return ActivityCompat.requireViewById(this, viewId)
}
