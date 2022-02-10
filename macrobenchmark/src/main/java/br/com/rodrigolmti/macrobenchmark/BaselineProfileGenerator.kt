package br.com.rodrigolmti.macrobenchmark

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalBaselineProfilesApi
@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun startup() =
        baselineProfileRule.collectBaselineProfile(packageName = "br.com.rodrigolmti.password_keeper") {
            pressHome()

            startActivityAndWait()
        }
}
