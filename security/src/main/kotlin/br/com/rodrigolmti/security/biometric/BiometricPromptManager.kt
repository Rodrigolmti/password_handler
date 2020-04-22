package br.com.rodrigolmti.security.biometric

import android.content.Context
import android.os.Handler
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.core.os.CancellationSignal
import androidx.fragment.app.FragmentManager
import br.com.rodrigolmti.security.R
import br.com.rodrigolmti.security.domain.model.BiometricEvent

class BiometricPromptManager(
    private val context: Context?,
    private val fragmentManager: FragmentManager,
    private var title: CharSequence? = null,
    private var subtitle: CharSequence? = null,
    private var description: CharSequence? = null
) {

    infix fun authenticate(biometricEventListener: BiometricEventListener) {
        context?.let {
            if (!BiometricHelper.isSdkVersionSupported) biometricEventListener.invoke(BiometricEvent.SdkVersionNotSupported)
            if (!BiometricHelper.isPermissionGranted(it)) biometricEventListener(BiometricEvent.BiometricPermissionNotGranted)
            if (!BiometricHelper.isHardwareSupported(it)) biometricEventListener(BiometricEvent.BiometricNotSupported)
            if (!BiometricHelper.isFingerprintAvailable(it)) biometricEventListener(BiometricEvent.BiometricNotAvailable)

            displayBiometricPrompt(biometricEventListener)
        }
    }

    private fun displayBiometricPrompt(biometricEventListener: BiometricEventListener) {
        val cancellationSignal = CancellationSignal()

        val biometricPromptBottomSheet =
            createBiometricPromptBottomSheet(biometricEventListener, cancellationSignal)

        val animateHandler = Handler(context?.mainLooper)
        val resetAnimate = Runnable {
            biometricPromptBottomSheet.initialStatus()
        }

        FingerprintManagerCompat.from(context!!).authenticate(
            null, 0, cancellationSignal,
            object : FingerprintManagerCompat.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    biometricPromptBottomSheet.isVisible.takeIf { it }?.run {
                        biometricPromptBottomSheet.dismiss()
                        animateHandler.removeCallbacks(resetAnimate)
                    }

                    biometricEventListener(BiometricEvent.AuthenticationSucceeded)
                }

                override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence) {
                    super.onAuthenticationHelp(helpMsgId, helpString)
                    biometricPromptBottomSheet.stateIcon = BiometricIconView.State.ON
                    biometricPromptBottomSheet.status = helpString
                    animateHandler.postDelayed(resetAnimate, 2000)
                }

                override fun onAuthenticationError(errMsgId: Int, errString: CharSequence) {
                    super.onAuthenticationError(errMsgId, errString)

                    if (errMsgId != 5) {
                        biometricPromptBottomSheet.stateIcon = BiometricIconView.State.ERROR
                        biometricPromptBottomSheet.status = errString
                        biometricPromptBottomSheet.statusColor = R.color.biometric_error
                        animateHandler.postDelayed(resetAnimate, 2000)
                    }

                    biometricPromptBottomSheet.isVisible.takeIf { it }?.run {
                        biometricPromptBottomSheet.dismiss()
                        animateHandler.removeCallbacks(resetAnimate)
                    }

                    biometricEventListener(BiometricEvent.AuthenticationFailed)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    biometricPromptBottomSheet.stateIcon = BiometricIconView.State.ERROR
                    biometricPromptBottomSheet.status = context.getString(R.string.biometric_failed)
                    biometricPromptBottomSheet.statusColor = R.color.biometric_error
                    animateHandler.postDelayed(resetAnimate, 2000)
                }
            }, null
        )
    }

    private fun createBiometricPromptBottomSheet(
        biometricEventListener: BiometricEventListener,
        cancellationSignal: CancellationSignal
    ): BiometricPromptBottomSheet {
        return BiometricPromptBottomSheet.Builder(
            fragmentManager,
            title,
            subtitle,
            description,
            biometricEventListener,
            cancellationSignal
        ).show()
    }
}