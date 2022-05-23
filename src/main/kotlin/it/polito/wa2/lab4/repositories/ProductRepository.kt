package it.polito.wa2.lab4.repositories

import it.polito.wa2.lab4.domain.Product
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ProductRepository: CoroutineCrudRepository<Product, Long>{
    @Query("UPDATE product SET quantity = quantity + :quantity WHERE id = :productId")
    @Modifying
    fun updateProductQuantity(productId: Long, quantity: Long)

    fun findAllByCategory(category: String): Flow<Product>
}
