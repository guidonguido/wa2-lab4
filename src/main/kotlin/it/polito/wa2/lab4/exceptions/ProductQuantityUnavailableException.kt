package it.polito.wa2.lab4.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value = HttpStatus.NO_CONTENT)
class ProductQuantityUnavailableException(override val message: String?): RuntimeException(message) {
}