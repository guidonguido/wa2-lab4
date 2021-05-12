package it.polito.wa2.lab4.services

import it.polito.wa2.lab4.dto.ProductDTO
import kotlinx.coroutines.flow.Flow
import reactor.core.publisher.Mono
import java.math.BigDecimal

interface ProductService {
    suspend fun addProduct(name: String, category: String, price: BigDecimal, quantity: Long): Mono<ProductDTO>

    suspend fun updateProductQuantity(productId: Long, quantity: Long): Mono<ProductDTO>

    suspend fun getProduct(productId: Long): Mono<ProductDTO>

    suspend fun getAllProduct(): Flow<ProductDTO>

    suspend fun getProductsByCategory(category: String): Flow<ProductDTO>

}