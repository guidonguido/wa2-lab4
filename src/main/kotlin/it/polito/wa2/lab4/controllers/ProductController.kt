package it.polito.wa2.lab4.controllers

import it.polito.wa2.lab4.dto.ProductDTO
import it.polito.wa2.lab4.services.ProductService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/warehouse")
@Validated
class ProductController(val productService: ProductService) {

    @PostMapping("/products")
    suspend fun addProduct(
            @RequestBody
            @Valid
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

    @GetMapping("/products/{productId}")
    suspend fun getProduct(
        @PathVariable
        @Positive(message = "Insert a valid productId")
        productId: Long
    ): ResponseEntity<Any> {
        return try{
            val product = productService.getProduct(productId)
            ResponseEntity(product, HttpStatus.OK)
        } catch(e: Exception){
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    /**
     * The returned content will have headers:
     *  >> transfer-encoding: chunked
     *  >> Content-Type: application/stream+json
     *
     *  There will be a streamed version of the result
     */
    @GetMapping("/products", produces = ["application/stream+json"])
    suspend fun getProducts(): ResponseEntity<Flow<ProductDTO>> {
        // Flow of products
        val products = productService.getProducts()
            // .onEach { delay(2000) }
        return ResponseEntity(products, HttpStatus.OK)
    }

    @GetMapping("/productsByCategory", produces = ["application/stream+json"])
    suspend fun getProductsByCategory(@RequestParam
                                      @NotNull(message = "'category' string is required")
                                      category: String? = null,): ResponseEntity<Flow<ProductDTO>> {
        val products =  productService.getProductsByCategory(category!!)
                                      // .onEach { delay(2000) }

        return ResponseEntity(products, HttpStatus.OK)


    }

    /**
     * Test with
     * >> telnet localhost 8080
     * >> GET /warehouse/products HTTP/1.1
          Connection:Close
     */







}