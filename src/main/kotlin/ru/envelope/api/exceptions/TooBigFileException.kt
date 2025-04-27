package ru.envelope.api.exceptions

class TooBigFileException(maxSize: String) : ApplicationException("Файл слишком большой для загрузки (не более ${maxSize})")
