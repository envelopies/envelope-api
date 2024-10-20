package ru.envelope.api.dto

import jakarta.xml.bind.DatatypeConverter

data class AuthRequestDto(
    /**
     * Время аутентификации в телеграмме.
     * Является Unix Timestamp.
     */
    val authDate: Long,

    /**
     * ID пользователя в телеграмме.
     */
    val id: Long,

    /**
     * Имя пользователя в телеграмме.
     */
    val username: String,

    /**
     * First Name пользователя.
     */
    val firstName: String,

    /**
     * Last Name пользователя.
     */
    val lastName: String?,

    /**
     * Хэш этих данных.
     */
    val hash: String,
) {
    val encodingString: ByteArray
        get() = "auth_date=$authDate\nfirst_name=$firstName\nid=$id\nlast_name=$lastName\nusername=$username".toByteArray()

    val hashBytes: ByteArray
        get() = DatatypeConverter.parseHexBinary(hash)
}
