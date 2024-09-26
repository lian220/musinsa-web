package habuma.musinsaweb.controller

import habuma.musinsaweb.domain.Product
import habuma.musinsaweb.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

  @GetMapping("/prices")
  fun getPricesByCategory(@RequestParam category: String): ResponseEntity<Any> {
    return try {
      val response = productService.getPriceByCategory(category)
      ResponseEntity.ok(response)
    } catch (e: IllegalArgumentException) {
      ResponseEntity.badRequest().body(mapOf("error" to e.message))
    }
  }

  @PostMapping
  fun addProduct(@RequestBody product: Product): ResponseEntity<String> {
    return try {
      productService.addProduct(product)
      ResponseEntity.status(HttpStatus.CREATED).body("상품이 성공적으로 추가되었습니다.")
    } catch (e: Exception) {
      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 추가에 실패했습니다: ${e.message}")
    }
  }

  @PutMapping("/{id}")
  fun updateProduct(@PathVariable id: Long, @RequestBody product: Product): ResponseEntity<String> {
    return try {
      productService.updateProduct(id, product)
      ResponseEntity.ok("상품이 성공적으로 업데이트되었습니다.")
    } catch (e: Exception) {
      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 업데이트에 실패했습니다: ${e.message}")
    }
  }

  @DeleteMapping("/{id}")
  fun deleteProduct(@PathVariable id: Long): ResponseEntity<String> {
    return try {
      productService.deleteProduct(id)
      ResponseEntity.ok("상품이 성공적으로 삭제되었습니다.")
    } catch (e: Exception) {
      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 삭제에 실패했습니다: ${e.message}")
    }
  }
}