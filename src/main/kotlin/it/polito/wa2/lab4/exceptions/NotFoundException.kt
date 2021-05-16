package it.polito.wa2.lab4.exceptions

import java.lang.RuntimeException

class NotFoundException(override val message: String?): RuntimeException(message)
