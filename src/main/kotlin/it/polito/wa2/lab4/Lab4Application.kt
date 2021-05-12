package it.polito.wa2.lab4

import it.polito.wa2.lab4.domain.Product
import it.polito.wa2.lab4.repositories.ProductRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.math.BigDecimal

@SpringBootApplication
class Lab4Application {

    /*@Bean
    fun populateDB(
        productRepository: ProductRepository
    ): CommandLineRunner {
        return CommandLineRunner {
            suspend {
                val product = Product(
                null, "product1", category = "Food", BigDecimal(10), 1L)

                val savedProd = productRepository.save(product)
                print("Saved product: {id: ${savedProd.id}}")
            }
        }
    }
    */
}

suspend fun main(args: Array<String>) {
    val context = runApplication<Lab4Application>(*args)

   /* val productRepository = context.getBean("productRepository") as ProductRepository

    val product = Product(null, "product1", "Food", BigDecimal(10), 1L)

    val savedProd = productRepository.save(product)
    print("Saved product: {id: ${savedProd.id}}")*/
}
