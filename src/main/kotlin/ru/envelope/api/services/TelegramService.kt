package ru.envelope.api.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.envelope.api.dto.AuthRequestDto
import java.security.MessageDigest
import java.time.Instant
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class TelegramService(
    @Value("\${telegram.secret}")
    val botSecret: String
) {
    fun checkTelegramAuthData(telegramAuthData: AuthRequestDto): Boolean {
        val now = Instant.now().epochSecond
        if ((now - telegramAuthData.authDate) > 86400) {
            return false
        }

        val digest = MessageDigest.getInstance("SHA-256")
        val encodedKeyBytes = digest.digest(botSecret.toByteArray())
        val encodedKey = SecretKeySpec(encodedKeyBytes, "HmacSHA256")
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(encodedKey)
        val computedHash = mac.doFinal(telegramAuthData.encodingString)
        return computedHash.contentEquals(telegramAuthData.hashBytes)
    }
}