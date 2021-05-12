package it.polito.wa2.lab4.services

import it.polito.wa2.lab4.domain.Product
import it.polito.wa2.lab4.dto.ProductDTO
import it.polito.wa2.lab4.dto.toProductDTO
import it.polito.wa2.lab4.exceptions.NotFoundException
import it.polito.wa2.lab4.exceptions.ProductQuantityUnavailableException
import it.polito.wa2.lab4.repositories.ProductRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ProductServiceImpl(private val productRepository: ProductRepository): ProductService {

    override suspend fun addProduct(
        name: String,
        category: String,
        price: BigDecimal,
        quantity: Long
    ): ProductDTO{
        val product = Product(null, name, category, price, quantity)

        val savedProd = productRepository.save(product)
        print("Saved product: {id: ${savedProd.id}}")

        return savedProd.toProductDTO()
    }

    override suspend fun updateProductQuantity(productId: Long, quantity: Long): ProductDTO {
        val product = productRepository.findById(productId)
            ?: throw NotFoundException("Inserted productId not found on DB")

        if( quantity < 0 && product.quantity < kotlin.math.abs(quantity))
            throw ProductQuantityUnavailableException("Required quantity is not available")

        //TODO which way is better?
        val newProduct = Product(
            product.id,
            product.name,
            product.category,
            product.price,
            product.quantity + quantity
        )

        val prod = productRepository.save(newProduct)

        //FIXME why this doesn't work?
        /*productRepository.updateProductQuantity(productId, quantity)

        val prod = productRepository.findById(productId)

        println("Prod $prod")*/

        return prod.toProductDTO()
    }

}
