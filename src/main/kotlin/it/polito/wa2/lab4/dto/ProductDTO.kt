package it.polito.wa2.lab4.dto

import it.polito.wa2.lab4.domain.Product
import org.springframework.data.annotation.Id
import java.math.BigDecimal

data class ProductDTO(
    val id: Long?,
    val name: String,
    val category: String,
    val price: BigDecimal,
    val quantity: Long
)

fun Product.toProductDTO(): ProductDTO = ProductDTO(id,
                                                    name,
                                                    category,
                                                    price,
                                                    quantity)