package it.polito.wa2.lab4.services

import it.polito.wa2.lab4.domain.Product
import it.polito.wa2.lab4.dto.ProductDTO
import it.polito.wa2.lab4.dto.toProductDTO
import it.polito.wa2.lab4.exceptions.NotFoundException
import it.polito.wa2.lab4.exceptions.ProductQuantityUnavailableException
import it.polito.wa2.lab4.repositories.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
@Transactional
class ProductServiceImpl(private val productRepository: ProductRepository): ProductService {

    override suspend fun addProduct(name: String,
                                    category: String,
                                    price: BigDecimal,
                                    quantity: Long): ProductDTO {
        val product = Product(null, name, category, price, quantity)

        val savedProd = productRepository.save(product)
        print("Saved product: {id: ${savedProd.id}}")

        return savedProd.toProductDTO()
    }

    override suspend fun updateProductQuantity(productId: Long, quantity: Long): ProductDTO {
        val product = productRepository.findById(productId)
            ?: throw NotFoundException("Requested productId not found on DB")

        if( quantity < 0 && product.quantity < -quantity )
            throw ProductQuantityUnavailableException("Required quantity is not available")

        val newProduct = Product(product.id,
            product.name,
            product.category,
            product.price,
        product.quantity + quantity)

        return productRepository.save(newProduct).toProductDTO()
    }

    override suspend fun getProduct(productId: Long): ProductDTO {
        val product = productRepository.findById(productId)
            ?: throw NotFoundException("Requested productId not found on DB")

        return product.toProductDTO()
    }

    override fun getProducts(): Flow<ProductDTO> {

        return productRepository.findAll()
            .map { it.toProductDTO() }
    }

    override fun getProductsByCategory(category: String): Flow<ProductDTO> {
        return productRepository.findByCategory(category)
            .map { it.toProductDTO() }
    }

}
