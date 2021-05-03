package it.polito.wa2.lab4.controllers

import it.polito.wa2.lab4.dto.ProductDTO
import it.polito.wa2.lab4.services.ProductService
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
            bodyDTO: ProductDTO): ResponseEntity<ProductDTO> {

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




}