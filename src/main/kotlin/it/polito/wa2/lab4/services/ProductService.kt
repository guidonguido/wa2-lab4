package it.polito.wa2.lab4.services

import it.polito.wa2.lab4.dto.ProductDTO
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface ProductService {
    suspend fun addProduct(name: String, category: String, price: BigDecimal, quantity: Long): ProductDTO

    suspend fun updateProductQuantity(productId: Long, quantity: Long): ProductDTO

    suspend fun getProductById(productId: Long): ProductDTO

    suspend fun getAllProducts(): Flow<ProductDTO>

    suspend fun getProductsByCategory(category: String): Flow<ProductDTO>
}
