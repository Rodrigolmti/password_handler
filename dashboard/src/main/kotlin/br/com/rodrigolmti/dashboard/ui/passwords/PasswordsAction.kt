package br.com.rodrigolmti.dashboard.ui.passwords

import android.util.Base64
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

internal sealed class PasswordsAction {
    object Init : PasswordsAction()
    data class FilterPasswords(val query: String) : PasswordsAction()
}

internal class Encryption {

    @Throws(NoSuchAlgorithmException::class)
    fun generateKey(): SecretKey? {
        val outputKeyLength = 256
        val secureRandom = SecureRandom()
        val keyGenerator: KeyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(outputKeyLength, secureRandom)
        return keyGenerator.generateKey()
    }

    fun generateInitVector(): IvParameterSpec {
        val ivRandom = SecureRandom()
        val iv = ByteArray(16)
        ivRandom.nextBytes(iv)
        return IvParameterSpec(iv)
    }

    fun encrypt(value: String): String? {
        val key = generateKey()?.encoded // TODO: Get generated key
        val initVector = "encryptionIntVec" // TODO: Get generated IV
        return try {
            val iv = IvParameterSpec(initVector.toByteArray(charset("UTF-8")))
            val skeySpec = SecretKeySpec(key, "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
            val encrypted = cipher.doFinal(value.toByteArray())
            Base64.encodeToString(encrypted, Base64.DEFAULT)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            null
        }
    }

    fun decrypt(value: String?): String? {
        val key = generateKey()?.encoded // TODO: Get generated key
        val initVector = "encryptionIntVec" // TODO: Get generated IV
        return try {
            val iv = IvParameterSpec(initVector.toByteArray(charset("UTF-8")))
            val skeySpec = SecretKeySpec(key, "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
            val original = cipher.doFinal(Base64.decode(value, Base64.DEFAULT))
            String(original)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            null
        }
    }
}