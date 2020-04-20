package br.com.rodrigolmti.dashboard.domain.use_cases

import javax.inject.Inject

private const val PASSWORD_MIN_SECURE_LENGTH = 6

interface PasswordStrengthUseCase {
    suspend operator fun invoke(
        password: String
    ): Int
}

class PasswordStrength @Inject constructor() : PasswordStrengthUseCase {

    override suspend fun invoke(password: String): Int {
        return calculate(password) + 1
    }

    private fun calculate(
        password: String
    ): Int {
        var uppercase = 0
        var lowercase = 0
        var digits = 0
        var symbols = 0
        var digitsOrSymbolsMiddle = 0
        var requirements = 0

        var onlyLetters = 0
        var onlyDigits = 0
        var repeatedCharacters = 0
        var consecutiveDigits = 0
        var consecutiveUppercase = 0
        var consecutiveLowercase = 0
        var sequenceLetters = 0
        var sequenceDigits = 0

        var score = 0

        val listAux = arrayListOf<Char>()

        password.forEachIndexed { index, char ->
            if (!char.isLetterOrDigit()) {
                symbols++

                if (index > 0 && index < password.length - 1) {
                    digitsOrSymbolsMiddle++
                }
            } else if (char.isDigit()) {
                digits++

                if (index > 0 && index < password.length - 1) {
                    digitsOrSymbolsMiddle++
                }

                if (index > 0 && password[index - 1].isDigit()) {
                    consecutiveDigits++

                    if (index > 1 &&
                        password[index - 2].isDigit() &&
                        sequence(password[index - 2], password[index - 1], char)
                    ) {
                        sequenceDigits++
                    }
                }
            } else {
                if (index > 1 &&
                    !password[index - 2].isDigit() &&
                    !password[index - 1].isDigit() &&
                    sequence(
                        password[index - 2].toLowerCase(),
                        password[index - 1].toLowerCase(),
                        char.toLowerCase()
                    )
                ) {
                    sequenceLetters++
                }

                if (char.isUpperCase()) {
                    uppercase++

                    if (index > 0 && password[index - 1].isUpperCase()) {
                        consecutiveUppercase++
                    }

                } else {
                    lowercase++

                    if (index > 0 && password[index - 1].isLowerCase()) {
                        consecutiveLowercase++
                    }
                }
            }

            val lowerCharacter = char.toLowerCase()

            if (!listAux.contains(lowerCharacter)) {
                listAux.add(lowerCharacter)

                var repeated = 0

                for (i in index until password.length) {
                    if (password[i].toLowerCase() == lowerCharacter) {
                        repeated++
                    }
                }

                if (repeated > 1) {
                    repeatedCharacters += repeated * (repeated - 1)
                }
            }
        }

        if (password.length >= PASSWORD_MIN_SECURE_LENGTH) {
            requirements++

            if (uppercase > 0) {
                requirements++
            }

            if (lowercase > 0) {
                requirements++
            }

            if (digits > 0) {
                requirements++
            }

            if (symbols > 0) {
                requirements++
            }

            if (requirements < 4) {
                requirements = 0
            }
        }

        if (digits == password.length) {
            onlyDigits = password.length
        }

        if (uppercase + lowercase == password.length) {
            onlyLetters = password.length
        }

        if (password.isNotEmpty()) score += password.length * 4
        if (uppercase > 0) score += (password.length - uppercase) * 2
        if (lowercase > 0) score += (password.length - lowercase) * 2
        if (digits > 0 && (uppercase > 0 || lowercase > 0 || symbols > 0)) score += digits * 4
        if (symbols > 0) score += symbols * 6
        if (digitsOrSymbolsMiddle > 0) score += digitsOrSymbolsMiddle * 2
        if (requirements > 0) score += requirements * 2

        if (onlyLetters > 0) score -= onlyLetters
        if (onlyDigits > 0) score -= onlyDigits
        if (repeatedCharacters > 0) score -= repeatedCharacters
        if (consecutiveUppercase > 0) score -= consecutiveUppercase * 2
        if (consecutiveLowercase > 0) score -= consecutiveLowercase * 2
        if (consecutiveDigits > 0) score -= consecutiveDigits * 2
        if (sequenceDigits > 0) score -= sequenceDigits * 3
        if (sequenceLetters > 0) score -= sequenceLetters * 3

        return when {
            score <= 25 -> 0
            score <= 50 -> 1
            score <= 75 -> 2
            else -> 3
        }
    }

    private fun sequence(one: Char, two: Char, actual: Char): Boolean {
        return two == actual - 1 && one == actual - 2
    }
}