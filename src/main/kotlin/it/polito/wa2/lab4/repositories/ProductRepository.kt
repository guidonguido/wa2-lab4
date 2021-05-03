package it.polito.wa2.lab4.repositories

import it.polito.wa2.lab4.domain.Product
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ProductRepository: CoroutineCrudRepository<Product, Long>{

}