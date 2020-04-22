package br.com.rodrigolmti.security.biometric

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat

object BiometricHelper {

    val isSdkVersionSupported = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    fun isHardwareSupported(context: Context) =
        FingerprintManagerCompat.from(context).isHardwareDetected

    fun isFingerprintAvailable(context: Context) =
        FingerprintManagerCompat.from(context).hasEnrolledFingerprints()

    fun isPermissionGranted(context: Context) = ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.USE_FINGERPRINT
    ) === PackageManager.PERMISSION_GRANTED
}