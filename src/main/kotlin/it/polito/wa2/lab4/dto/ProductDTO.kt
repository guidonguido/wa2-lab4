package it.polito.wa2.lab4.dto

import it.polito.wa2.lab4.domain.Product
import org.springframework.data.annotation.Id
import java.math.BigDecimal
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class ProductDTO(
    val id: Long?,

    @field:NotNull(message="Product name can't be null")
    val name: String,

    @field:NotNull(message="Product category can't be null")
    val category: String,

    @field:NotNull(message="Product price can't be null")
    @field:Positive(message="Product price must be greater the 0")
    val price: BigDecimal,

    @field:NotNull(message="Product quantity can't be null")
    val quantity: Long
)

fun Product.toProductDTO(): ProductDTO = ProductDTO(id,
                                                    name,
                                                    category,
                                                    price,
                                                    quantity)