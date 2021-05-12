package it.polito.wa2.lab4

import it.polito.wa2.lab4.domain.Product
import it.polito.wa2.lab4.repositories.ProductRepository
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingleOrNull
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import reactor.core.publisher.Flux
import java.math.BigDecimal
import kotlin.random.Random

@SpringBootApplication
class Lab4Application(private val productRepository: ProductRepository) {

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

    fun randomName(len: Int): String {
        val random = java.util.Random()
        val vowels = listOf("a", "e", "i", "o", "u", "y")
        val consonants = listOf(
            "b", "c", "d", "f", "g", "h", "j", "k",
            "l", "m", "n", "p", "q", "r", "s", "t", "v", "w", "x", "z"
        )
        val l = mutableListOf<String>()
        for (i in 0 until len) {
            if (i % 2 == 0)
                l.add(vowels[random.nextInt(vowels.size)])
            else
                l.add(consonants[random.nextInt(consonants.size)])
        }
        return l.joinToString("")
    }

    @Bean
    fun populateDB(): CommandLineRunner {
        return CommandLineRunner {

            /* println("+++ Execute populate DB +++")

             val random = java.util.Random()
             val products = Flux.range(1, 150)
                 .map { Product(null,
                        randomName(10),
                        randomName(7),
                        BigDecimal.valueOf(120),
                        500) }
                 .doOnNext{ println(it) }

             productRepository.saveAll(products)
                 .then(productRepository.count())
                 .subscribe {
                     println("Count is $it")
                 }
         }*/
        }

    }

}

fun main(args: Array<String>) {
    runApplication<Lab4Application>(*args)

    /*val productRepository = context.getBean("productRepository") as ProductRepository

    val product = Product(
        null, "product2", category = "Food", BigDecimal(10), 2L)

    val savedProd = productRepository.save(product).block()
    print("Saved product: {id: ${savedProd!!.id}}")*/
}


