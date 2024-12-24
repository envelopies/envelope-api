package ru.envelope.api.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.envelope.api.dto.AuthRequestDto
import ru.envelope.api.models.TelegramDataStatus
import java.security.MessageDigest
import java.time.Instant
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class TelegramService(
    @Value("\${telegram.secret}")
    val botSecret: String
) {
    fun checkTelegramAuthData(telegramAuthData: AuthRequestDto): TelegramDataStatus {
        val now = Instant.now().epochSecond
        if ((now - telegramAuthData.authDate) > MAX_TOKEN_LIFETIME_SEC) {
            return TelegramDataStatus.EXPIRED
        }

        val digest = MessageDigest.getInstance("SHA-256")
        val encodedKeyBytes = digest.digest(botSecret.toByteArray())
        val encodedKey = SecretKeySpec(encodedKeyBytes, "HmacSHA256")
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(encodedKey)
        val computedHash = mac.doFinal(telegramAuthData.encodingString)
        return if (computedHash.contentEquals(telegramAuthData.hashBytes)) {
            TelegramDataStatus.GOOD
        } else {
            TelegramDataStatus.BAD
        }
    }

    companion object {
        const val MAX_TOKEN_LIFETIME_SEC = 86400L
    }
}
