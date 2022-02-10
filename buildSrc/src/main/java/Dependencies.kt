object ApplicationId {
    const val id = "br.com.rodrigolmti.password_keeper"
}

object Versions {
    const val kotlin = "1.5.31"
    const val googleServices = "4.3.8"
    const val crashlytics = "2.7.1"
    const val gradle = "4.0.0"
    const val gradleTools = "7.0.0"
    const val buildTools = "30.0.2"
    const val compileSdk = 31
    const val minSdk = 23
    const val targetSdk = 31
}

object Releases {
    const val versionCode = 2
    const val versionName = "1.0.1"
}

object Modules {
    const val app = ":app"
    const val coreAndroid = ":core-android"
    const val dashboard = ":dashboard"
    const val injector = ":injector"
    const val navigator = ":navigator"
    const val uiKit = ":ui-kit"
    const val database = ":database"
    const val userPreferences = ":user-preferences"
    const val security = ":security"
    const val authentication = ":authentication"
}

object Kotlin {
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val junit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
}

object Lifecycle {
    object Versions {
        const val lifecycle = "2.3.1"
    }

    const val common = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
}

object Dagger {
    object Versions {
        const val dagger = "2.38.1"
    }

    const val core = "com.google.dagger:dagger:${Versions.dagger}"
    const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val android = "com.google.dagger:dagger-android-support:${Versions.dagger}"
}

object Compose {
    object Versions {
        const val viewModelCompose = "1.0.0-alpha07"
        const val materialCompose = "1.0.0-alpha01"
        const val activityCompose = "1.3.1"
        const val compose = "1.0.5"
    }

    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val material3Compose = "androidx.compose.material3:material3:${Versions.materialCompose}"
    const val materialCompose = "androidx.compose.material:material:${Versions.compose}"
    const val animationCompose = "androidx.compose.animation:animation:${Versions.compose}"
    const val toolsCompose = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val viewModelCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.viewModelCompose}"
}

object Coroutines {
    object Versions {
        const val coroutines = "1.5.1"
    }

    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
}

object Navigation {
    object Versions {
        const val navigationVersion = "2.3.5"
    }

    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"
    const val navigationArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationVersion}"
}

object Firebase {
    object Versions {
        const val analytics = "19.0.0"
        const val crashlytics = "18.2.0"
    }

    const val analytics = "com.google.firebase:firebase-core:${Versions.analytics}"
    const val crashlytics = "com.google.firebase:firebase-crashlytics:${Versions.crashlytics}"
}

object AndroidX {
    object Versions {
        const val ktx = "1.6.0"
        const val appcompat = "1.3.1"
        const val support = "1.4.0"
        const val fragment = "1.3.6"
        const val room = "2.3.0"
        const val baseline = "1.2.0-alpha01"
        const val benchmark = "1.1.0-beta01"
        const val test = "1.1.3"
        const val espresso = "3.4.0"
        const val uiautomator = "2.2.0"
    }

    const val core = "androidx.core:core-ktx:${Versions.ktx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val design = "com.google.android.material:material:${Versions.support}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomExt = "androidx.room:room-ktx:${Versions.room}"
    const val baseline = "androidx.profileinstaller:profileinstaller:${Versions.baseline}"
    const val benchmark = "androidx.benchmark:benchmark-macro-junit4:${Versions.benchmark}"

    const val test = "androidx.test.ext:junit:${Versions.test}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val uiautomator = "androidx.test.uiautomator:uiautomator:${Versions.uiautomator}"
}

object JUnit {
    object Versions {
        const val junit = "4.12"
    }

    const val core = "junit:junit:${Versions.junit}"
}

object MockK {
    object Versions {
        const val mockk = "1.12.0"
    }

    const val core = "io.mockk:mockk:${Versions.mockk}"
    const val android = "io.mockk:mockk-android:${Versions.mockk}"
}

object Libraries {
    object Versions {
        const val lottie = "3.4.0"
    }

    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
}

object Layout {
    object Versions {
        const val constraintLayout = "2.0.4"
    }

    const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}