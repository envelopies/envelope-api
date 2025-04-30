package ru.envelope.api.exceptions

import java.util.*

class IllegalAccessToPictureException(ids: List<UUID>) : ApplicationException("Вы не имеете доступа к объявлениям: ${ids.joinToString()}")
