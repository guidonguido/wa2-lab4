package it.polito.wa2.lab4.services

import it.polito.wa2.lab4.dto.ProductDTO
import java.math.BigDecimal

interface ProductService {
    suspend fun addProduct(name: String, category: String, price: BigDecimal, quantity: Long): ProductDTO

    suspend fun updateProductQuantity(productId: Long, quantity: Long): ProductDTO
}