package br.com.rodrigolmti.core_android.view_binding_delegate.internal

import android.os.Looper
import androidx.annotation.RestrictTo

/*
* Copyright 2020 Kirill Rozov
* Licensed under the Apache License, Version 2.0 (the "License");
* You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
*
* https://github.com/kirich1409/ViewBindingPropertyDelegate (search for tag 1.0.0)
* */

@get:RestrictTo(RestrictTo.Scope.LIBRARY)
internal inline val isMainThread: Boolean
    get() = Looper.myLooper() == Looper.getMainLooper()

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal fun checkIsMainThread() = check(isMainThread)
