package it.polito.wa2.lab4.repositories

import it.polito.wa2.lab4.domain.Product
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ProductRepository: ReactiveCrudRepository<Product, Long> {

    suspend fun findAllByCategory(category: String): Flow<Product>
}