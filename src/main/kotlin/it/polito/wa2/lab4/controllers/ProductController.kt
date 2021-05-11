package it.polito.wa2.lab4.controllers

import it.polito.wa2.lab4.dto.ProductDTO
import it.polito.wa2.lab4.services.ProductService
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.lang.RuntimeException
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/warehouse")
@Validated
class ProductController(val productService: ProductService) {

    @PostMapping("/products")
    suspend fun addProduct(
            @RequestBody
            // @Valid
            bodyDTO: ProductDTO): ResponseEntity<Mono<ProductDTO>> {

        val newProduct = productService.addProduct(bodyDTO.name,
                                                   bodyDTO.category,
                                                   bodyDTO.price,
                                                   bodyDTO.quantity)

        return ResponseEntity(newProduct, HttpStatus.CREATED)
    }

    @PatchMapping("/products/{productId}")
    suspend fun updateQuantity(
        @PathVariable
        @Positive(message = "Insert a valid productId")
        productId: Long,

        @RequestBody
        @NotNull(message = "Insert a valid quantity")
        bodyVal: Long? = null
    ): ResponseEntity<Any> {
        return try{
            val newProduct = productService.updateProductQuantity(productId, bodyVal!!)
            ResponseEntity(newProduct, HttpStatus.OK)
        } catch(e: Exception){
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/products/{productId}")
    suspend fun getProduct(@PathVariable
                           @Positive(message = "Insert a valid productId")
                           productId: Long
    ): ResponseEntity<Mono<ProductDTO>> {

        val returnPrd = productService.getProduct(productId)

        return ResponseEntity<Mono<ProductDTO>>(returnPrd, HttpStatus.OK)
    }

    @GetMapping("/products")
    suspend fun getAllProducts(): ResponseEntity<Flow<ProductDTO>> {

        return ResponseEntity<Flow<ProductDTO>>(
                                                productService.getAllProduct(),
                                                HttpStatus.OK
        )
    }

    @GetMapping("/productsByCategory")
    suspend fun getproductsByCategory(@RequestParam(name = "category", required = true)
                                      @NotNull(message = "A category is required")
                                      category: String? = null
    ): ResponseEntity<Flow<ProductDTO>> {

        return ResponseEntity<Flow<ProductDTO>>(productService.getProductsByCategory(category!!) ,HttpStatus.OK)
    }





}