package br.com.rodrigolmti.dashboard.ui.passwords

internal sealed class PasswordsAction {
    object Init : PasswordsAction()
    data class FilterPasswords(val query: String) : PasswordsAction()
}


//internal class Encryption {
//
//    fun encrypt(value: String): String? {
//        val key = "aesEncryptionKey"
//        val initVector = "encryptionIntVec"
//        try {
//            val iv =
//                IvParameterSpec(initVector.toByteArray(charset("UTF-8")))
//            val skeySpec =
//                SecretKeySpec(key.toByteArray(charset("UTF-8")), "AES")
//            val cipher =
//                Cipher.getInstance("AES/CBC/PKCS5PADDING")
//            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
//            val encrypted = cipher.doFinal(value.toByteArray())
//            return Base64.getEncoder(encrypted)
//        } catch (ex: java.lang.Exception) {
//            ex.printStackTrace()
//        }
//        return null
//    }
//
//    fun decrypt(value: String?): String? {
//        val key = "aesEncryptionKey"
//        val initVector = "encryptionIntVec"
//        try {
//            val iv =
//                IvParameterSpec(initVector.toByteArray(charset("UTF-8")))
//            val skeySpec =
//                SecretKeySpec(key.toByteArray(charset("UTF-8")), "AES")
//            val cipher =
//                Cipher.getInstance("AES/CBC/PKCS5PADDING")
//            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
//            val original = cipher.doFinal(Base64.decode(value))
//            return String(original)
//        } catch (ex: java.lang.Exception) {
//            ex.printStackTrace()
//        }
//        return null
//    }
//
//    fun encrypt(
//        dataToEncrypt: ByteArray,
//        password: CharArray
//    ): HashMap<String, ByteArray> {
//        val map = HashMap<String, ByteArray>()
//
//        try {
//            // 1
//            //Random salt for next step
//            val random = SecureRandom()
//            val salt = ByteArray(256)
//            random.nextBytes(salt)
//
//            // 2
//            //PBKDF2 - derive the key from the password, don't use passwords directly
//            val pbKeySpec = PBEKeySpec(password, salt, 1324, 256)
//            val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
//            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
//            val keySpec = SecretKeySpec(keyBytes, "AES")
//
//            // 3
//            //Create initialization vector for AES
//            val ivRandom = SecureRandom() //not caching previous seeded instance of SecureRandom
//            val iv = ByteArray(16)
//            ivRandom.nextBytes(iv)
//            val ivSpec = IvParameterSpec(iv)
//
//            // 4
//            //Encrypt
//            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
//            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
//            val encrypted = cipher.doFinal(dataToEncrypt)
//
//            // 5
//            map["salt"] = salt
//            map["iv"] = iv
//            map["encrypted"] = encrypted
//        } catch (e: Exception) {
//            Log.e("MYAPP", "encryption exception", e)
//        }
//
//        return map
//
//    }
//
//    fun decrypt(map: HashMap<String, ByteArray>, password: CharArray): ByteArray? {
//        var decrypted: ByteArray? = null
//        try {
//            // 1
//            val salt = map["salt"]
//            val iv = map["iv"]
//            val encrypted = map["encrypted"]
//
//            // 2
//            //regenerate key from password
//            val pbKeySpec = PBEKeySpec(password, salt, 1324, 256)
//            val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
//            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
//            val keySpec = SecretKeySpec(keyBytes, "AES")
//
//            // 3
//            //Decrypt
//            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
//            val ivSpec = IvParameterSpec(iv)
//            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
//            decrypted = cipher.doFinal(encrypted)
//        } catch (e: Exception) {
//            Log.e("MYAPP", "decryption exception", e)
//        }
//
//        return decrypted
//    }
//
//    fun keystoreEncrypt(dataToEncrypt: ByteArray): HashMap<String, ByteArray> {
//        val map = HashMap<String, ByteArray>()
//        try {
//
//            // 1
//            //Get the key
//            val keyStore = KeyStore.getInstance("AndroidKeyStore")
//            keyStore.load(null)
//
//            val secretKeyEntry =
//                keyStore.getEntry("MyKeyAlias", null) as KeyStore.SecretKeyEntry
//            val secretKey = secretKeyEntry.secretKey
//
//            // 2
//            //Encrypt data
//            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
//            val ivBytes = cipher.iv
//            val encryptedBytes = cipher.doFinal(dataToEncrypt)
//
//            // 3
//            map["iv"] = ivBytes
//            map["encrypted"] = encryptedBytes
//        } catch (e: Throwable) {
//            e.printStackTrace()
//        }
//
//        return map
//    }
//
//    fun keystoreDecrypt(map: HashMap<String, ByteArray>): ByteArray? {
//        var decrypted: ByteArray? = null
//        try {
//            // 1
//            //Get the key
//            val keyStore = KeyStore.getInstance("AndroidKeyStore")
//            keyStore.load(null)
//
//            val secretKeyEntry =
//                keyStore.getEntry("MyKeyAlias", null) as KeyStore.SecretKeyEntry
//            val secretKey = secretKeyEntry.secretKey
//
//            // 2
//            //Extract info from map
//            val encryptedBytes = map["encrypted"]
//            val ivBytes = map["iv"]
//
//            // 3
//            //Decrypt data
//            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
//            val spec = GCMParameterSpec(128, ivBytes)
//            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
//            decrypted = cipher.doFinal(encryptedBytes)
//        } catch (e: Throwable) {
//            e.printStackTrace()
//        }
//
//        return decrypted
//    }
//
//    @TargetApi(23)
//    fun keystoreTest() {
//
//        //TODO - Add Test code here
//        val keyGenerator =
//            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
//        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
//            "MyKeyAlias",
//            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
//        )
//            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
//            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
//            //.setUserAuthenticationRequired(true) // 2 requires lock screen, invalidated if lock screen is disabled
//            //.setUserAuthenticationValidityDurationSeconds(120) // 3 only available x seconds from password authentication. -1 requires finger print - every time
//            .setRandomizedEncryptionRequired(true) // 4 different ciphertext for same plaintext on each call
//            .build()
//        keyGenerator.init(keyGenParameterSpec)
//        keyGenerator.generateKey()
//
//        val map = keystoreEncrypt("My very sensitive string!".toByteArray(Charsets.UTF_8))
//        val decryptedBytes = keystoreDecrypt(map)
//        decryptedBytes?.let {
//            val decryptedString = String(it, Charsets.UTF_8)
//            Log.e("MyApp", "The decrypted string is: $decryptedString")
//        }
//    }
//}