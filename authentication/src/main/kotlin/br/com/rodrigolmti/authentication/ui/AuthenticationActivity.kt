package br.com.rodrigolmti.authentication.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.rodrigolmti.authentication.R
import br.com.rodrigolmti.authentication.di.AuthenticationComponent
import br.com.rodrigolmti.authentication.ui.theme.AppTheme
import br.com.rodrigolmti.core_android.base.BaseActivity

class AuthenticationActivity : BaseActivity() {

    val component: AuthenticationComponent by lazy { AuthenticationComponent.inject(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Surface(
                    color = Color.Black
                ) {
                    BuildBody()
                }
            }
        }
    }

    @Composable
    fun BuildBody() {

        val passcode = remember { mutableStateOf("") }
        val passcodeLength = passcode.value.length

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                stringResource(R.string.authentication_activity_title),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            BuildLoading(passcode.value.length)

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth(),
            ) {
                for (number in 1..3) {
                    BuildKeyButton(number.toString(), passcodeLength) {
                        updatePasscode(
                            passcode,
                            it
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth(),
            ) {
                for (number in 4..6) {
                    BuildKeyButton(number.toString(), passcodeLength) {
                        updatePasscode(
                            passcode,
                            it
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth(),
            ) {
                for (number in 7..9) {
                    BuildKeyButton(number.toString(), passcodeLength) {
                        updatePasscode(
                            passcode,
                            it
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun BuildLoading(passcodeLength: Int) {
        LinearProgressIndicator(
            progress = passcodeLength * 0.16f,
        )
    }

    @Composable
    fun BuildKeyButton(
        value: String,
        passcodeLength: Int,
        onClick: (String) -> Unit
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Color.Gray, shape = CircleShape)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)

                    val currentHeight = placeable.height
                    var heightCircle = currentHeight
                    if (placeable.width > heightCircle)
                        heightCircle = placeable.width

                    layout(heightCircle, heightCircle) {
                        placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
                    }
                }
                .clickable(
                    enabled = passcodeLength < 6,
                    onClick = {
                        onClick(value)
                    },
                )
        ) {
            Text(
                text = value,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .padding(4.dp)
                    .defaultMinSize(64.dp)
            )
        }
    }

    private fun updatePasscode(
        passcode: MutableState<String>,
        value: String
    ) {
        passcode.value += value
    }
}