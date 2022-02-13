package br.com.rodrigolmti.authentication.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import br.com.rodrigolmti.authentication.R
import br.com.rodrigolmti.authentication.di.AuthenticationComponent
import br.com.rodrigolmti.authentication.ui.theme.*
import br.com.rodrigolmti.core_android.base.BaseActivity
import br.com.rodrigolmti.navigator.AuthenticationOrigin
import br.com.rodrigolmti.navigator.Navigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthenticationActivity : BaseActivity() {

    val component: AuthenticationComponent by lazy { AuthenticationComponent.inject(this) }

    @Inject
    internal lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AuthenticationViewModel> { viewModelProviderFactory }

    private val utm by lazy {
        intent.extras?.getParcelable(Navigator.AuthenticationOriginKey) ?: AuthenticationOrigin.NONE
    }

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val modalBottomSheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden
            )

            val scope = rememberCoroutineScope()

            utm.takeIf { utm == AuthenticationOrigin.SETTINGS }
                ?.run { scope.launch { modalBottomSheetState.show() } }

            AppTheme {
                Surface(
                    color = dark
                ) {

                    BuildModalBottomSheet(modalBottomSheetState, scope)
                }
            }
        }
    }

    @Composable
    @ExperimentalMaterialApi
    private fun BuildModalBottomSheet(
        modalBottomSheetState: ModalBottomSheetState,
        scope: CoroutineScope
    ) {
        ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
            sheetBackgroundColor = gray,
            sheetContent = {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(tradeWind)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            getString(R.string.authentication_activity_create_pin),
                            color = white
                        )

                        Spacer(modifier = Modifier.height(ceriseRed))

                        BuildOkButton(scope, modalBottomSheetState)

                        Spacer(modifier = Modifier.height(cottonCandy))
                    }
                }
            }
        ) {
            BuildBody()
        }
    }

    @Composable
    @ExperimentalMaterialApi
    private fun BuildOkButton(
        scope: CoroutineScope,
        modalBottomSheetState: ModalBottomSheetState
    ) {
        Button(
            onClick = {

                scope.launch { modalBottomSheetState.hide() }

            },
            shape = RoundedCornerShape(malibu),
            modifier = Modifier
                .fillMaxWidth()
                .height(violetEggplant)
                .padding(all = malibu),
            enabled = true,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = red,
            )
        )
        {
            Text(
                text = stringResource(R.string.authentication_activity_ok_action),
                color = Color.White
            )
        }
    }

    @Composable
    fun BuildBody() {

        val passcode = remember { mutableStateOf("") }
        val passcodeLength = passcode.value.length
        val buttonEnabled = passcodeLength < 6

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(violetEggplant))

            Text(
                stringResource(R.string.authentication_activity_title),
                color = Color.White,
                fontSize = 22.sp,
            )

            Spacer(modifier = Modifier.height(tradeWind))

            BuildPasscodeLengthIndicator(passcode.value.length)

            Spacer(modifier = Modifier.height(violetEggplant))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth(),
            ) {
                for (number in 1..3) {
                    BuildKeyButton(number.toString(), buttonEnabled) {
                        addPasscode(
                            passcode,
                            it
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(ceriseRed))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth(),
            ) {
                for (number in 4..6) {
                    BuildKeyButton(number.toString(), buttonEnabled) {
                        addPasscode(
                            passcode,
                            it
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(ceriseRed))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth(),
            ) {
                for (number in 7..9) {
                    BuildKeyButton(number.toString(), buttonEnabled) {
                        addPasscode(
                            passcode,
                            it
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(ceriseRed))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth(),
            ) {
                BuildKeyButton(
                    "",
                    buttonEnabled,
                    backgroundColor = dark,
                    labelColor = dark,
                )
                BuildKeyButton(
                    "0",
                    buttonEnabled,
                ) {
                    addPasscode(
                        passcode,
                        it
                    )
                }
                BuildKeyButton(
                    stringResource(R.string.authentication_activity_delete_action),
                    backgroundColor = dark,
                    enabled = true,
                ) {
                    removePasscode(passcode)
                }
            }
        }
    }

    @Composable
    fun BuildPasscodeLengthIndicator(passcodeLength: Int) {
        LinearProgressIndicator(
            progress = passcodeLength * 0.17f,
            backgroundColor = lightRed,
            color = red,
        )
    }

    @Composable
    fun BuildKeyButton(
        value: String,
        enabled: Boolean,
        backgroundColor: Color = gray,
        labelColor: Color = Color.White,
        onClick: (String) -> Unit = {}
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(backgroundColor, shape = CircleShape)
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
                    enabled = enabled,
                    onClick = {
                        onClick(value)
                    },
                )
        ) {
            Text(
                text = value,
                textAlign = TextAlign.Center,
                color = labelColor,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(malibu)
                    .defaultMinSize(violetEggplant)
            )
        }
    }

    private fun addPasscode(
        passcode: MutableState<String>,
        value: String
    ) {
        passcode.value += value
    }

    private fun removePasscode(
        passcode: MutableState<String>
    ) {
        passcode.value = passcode.value.dropLast(1)
    }
}