package it.polito.wa2.lab4.services

import it.polito.wa2.lab4.domain.Product
import it.polito.wa2.lab4.dto.ProductDTO
import it.polito.wa2.lab4.dto.toProductDTO
import it.polito.wa2.lab4.exceptions.NotFoundException
import it.polito.wa2.lab4.exceptions.ProductQuantityUnavailableException
import it.polito.wa2.lab4.repositories.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingleOrNull
import kotlinx.coroutines.reactor.asFlux
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.lang.Math.abs
import java.math.BigDecimal
import java.util.NoSuchElementException

@Service
class ProductServiceImpl(private val productRepository: ProductRepository): ProductService {

    override suspend fun addProduct(name: String,
                                    category: String,
                                    price: BigDecimal,
                                    quantity: Long
    ): Mono<ProductDTO>{
        val product = Product(null, name, category, price, quantity)

        val savedProd = productRepository.save(product).awaitSingleOrNull()

        print("Saved product: {id: ${savedProd.id }}")

        return savedProd.toProductDTO().toMono()
    }

    override suspend fun updateProductQuantity(productId: Long, quantity: Long): Mono<ProductDTO> {
        //throw NoSuchElement exception
        val product = productRepository.findById(productId).awaitSingleOrNull()

        if( quantity < 0 && product.quantity < -quantity )
            throw ProductQuantityUnavailableException("Required quantity is not available")

        val newProduct = Product(product.id,
            product.name,
            product.category,
            product.price,
        product.quantity + quantity)

        return productRepository.save(newProduct).awaitSingleOrNull().toProductDTO().toMono()
    }

    override suspend fun getProduct(productId: Long): Mono<ProductDTO> {

        val product = productRepository.findById(productId)

        return product
                .hasElement()
                .flatMap {
                    if(it) product.map { it.toProductDTO() }
                    else throw NotFoundException("Inserted productId not found on DB")
            }
    }

    override suspend fun getAllProduct(): Flow<ProductDTO> {

        return productRepository.findAll().map { it.toProductDTO() }.asFlow()
                .onEmpty { throw NoSuchElementException("NO such product found on DB") }
    }

    override suspend fun getProductsByCategory(category: String): Flow<ProductDTO> {

        return productRepository.findAllByCategory(category)
            .map { it.toProductDTO() }
            .onEmpty { throw NotFoundException("Inserted category not found on DB") }
    }

}