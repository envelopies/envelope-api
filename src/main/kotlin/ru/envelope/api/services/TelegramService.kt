package ru.envelope.api.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.envelope.api.dto.TokenRequestDto
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class TelegramService(
    @Value("\${telegram.secret}")
    val botSecret: String
) {
    fun checkTelegramHash(token: TokenRequestDto): Boolean {
        val digest = MessageDigest.getInstance("SHA-256")
        val encodedKeyBytes = digest.digest(botSecret.toByteArray())
        val encodedKey = SecretKeySpec(encodedKeyBytes, "HmacSHA256")
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(encodedKey)
        val computedHash = mac.doFinal(token.encodingString)
        return computedHash.contentEquals(token.hashBytes)
    }
}