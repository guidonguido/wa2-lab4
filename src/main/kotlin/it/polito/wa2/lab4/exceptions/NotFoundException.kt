package it.polito.wa2.lab4.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class NotFoundException(override val message: String?): RuntimeException(message) {
    @ResponseBody
    override fun getLocalizedMessage(): String {
        return super.getLocalizedMessage()
    }
}