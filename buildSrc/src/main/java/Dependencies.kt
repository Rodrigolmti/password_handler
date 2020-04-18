object ApplicationId {
    const val id = "br.com.rodrigolmti.password_keeper"
    const val demo = "br.com.rodrigolmti.password_keeper.demo"
}

object Versions {
    const val kotlin = "1.3.71"
    const val gradle = "3.6.1"
    const val buildTools = "29.0.3"
    const val compileSdk = 29
    const val minSdk = 21
    const val targetSdk = 29
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Modules {
    const val app = ":app"
    const val coreAndroid = ":core-android"
    const val dashboard = ":dashboard"
    const val injector = ":injector"
    const val navigator = ":navigator"
    const val uiKit = ":ui-kit"
}

object Kotlin {
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
}

object Lifecycle {
    object Versions {
        const val lifecycle = "2.2.0"
    }

    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
}

object Dagger {
    object Versions {
        const val dagger = "2.25.4"
    }

    const val core = "com.google.dagger:dagger:${Versions.dagger}"
    const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val android = "com.google.dagger:dagger-android-support:${Versions.dagger}"
}

object Navigation {
    object Versions {
        const val navigationVersion = "2.3.0-alpha04"
    }

    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"
}

object AndroidX {
    object Versions {
        const val ktx = "1.2.0"
        const val appcompat = "1.1.0"
        const val support = "1.0.0"
        const val transition = "1.0.1"
        const val annotation = "1.0.2"
        const val fragment = "1.2.2"
    }

    const val ktx = "androidx.core:core-ktx:${Versions.ktx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val design = "com.google.android.material:material:${Versions.support}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
}

object Layout {
    object Versions {
        const val constraintLayout = "1.1.3"
    }

    const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}