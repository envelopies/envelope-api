package ru.envelope.api.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import jakarta.xml.bind.DatatypeConverter

data class AuthRequestDto(
    /**
     * Время аутентификации в телеграмме.
     * Является Unix Timestamp.
     */
    @Schema(description = "время логина в телеграмм; мс")
    val authDate: Long,

    /**
     * ID пользователя в телеграмме.
     */
    @Schema(description = "ID пользователя телеграмм")
    val id: Long,

    /**
     * Имя пользователя в телеграмме.
     */
    @Schema(description = "имя пользователя; может быть пустым значением (см. профиль телеграмм)")
    val username: String,

    /**
     * First Name пользователя.
     */
    @Schema(description = "First Name пользователя; никогда не бывает пустым (см. профиль телеграмм)")
    @NotBlank(message = "First Name должен быть заполнен")
    val firstName: String,

    /**
     * Last Name пользователя.
     */
    @Schema(description = "Last Name пользователя; может быть пустым (см. профиль телеграмм)")
    val lastName: String?,

    /**
     * Хэш этих данных.
     */
    @Schema(description = "хэш данных, проверяет в правильном ли боте был залогинен")
    @NotBlank(message = "хэш данных должен быть заполнен, иначе невозможно проверить запрос от того ли бота")
    @Size(min = 64, max = 64, message = "хэш является размером 64")
    val hash: String,
) {
    /**
     * Данные пользователя, представленные в правильном формате для проверки.
     */
    val encodingString: ByteArray
        get() = "auth_date=$authDate\nfirst_name=$firstName\nid=$id\nlast_name=$lastName\nusername=$username".toByteArray()

    /**
     * Массив байтов для проверки.
     */
    val hashBytes: ByteArray
        get() = DatatypeConverter.parseHexBinary(hash)
}
