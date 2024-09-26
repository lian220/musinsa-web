package habuma.musinsaweb.service

import habuma.musinsaweb.domain.Brand
import habuma.musinsaweb.domain.Product
import habuma.musinsaweb.repository.ProductRepository
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class ProductServiceTest {

  private lateinit var productRepository: ProductRepository
  private lateinit var productService: ProductService

  @BeforeEach
  fun setUp() {
    productRepository = mockk()
    productService = ProductService(productRepository)
  }

  @Test
  fun `getPriceByCategory should return correct min and max price products`() {
    val category = "상의"
    val brand = Brand(id = 1L, name = "A", products = mutableListOf())
    val product1 = Product(id = 1L, category = category, price = 10000, brand = brand)
    val product2 = Product(id = 2L, category = category, price = 15000, brand = brand)
    val product3 = Product(id = 3L, category = category, price = 8000, brand = brand)

    every { productRepository.findByCategory(category) } returns listOf(product1, product2, product3)

    val result = productService.getPriceByCategory(category)

    assertEquals(category, result["카테고리"])
    assertEquals(
      listOf(mapOf("브랜드" to "A", "가격" to "8000")),
      result["최저가"]
    )
    assertEquals(
      listOf(mapOf("브랜드" to "A", "가격" to "15000")),
      result["최고가"]
    )
  }

  @Test
  fun `getPriceByCategory should throw exception if no products found`() {
    val category = "비존재카테고리"

    every { productRepository.findByCategory(category) } returns emptyList()

    val exception = assertThrows(IllegalArgumentException::class.java) {
      productService.getPriceByCategory(category)
    }

    assertEquals("해당 카테고리에 대한 상품이 없습니다.", exception.message)
    verify(exactly = 1) { productRepository.findByCategory(category) }
  }

  @Test
  fun `addProduct should save product`() {
    val brand = Brand(id = 1L, name = "A", products = mutableListOf())
    val product = Product(id = 1L, category = "바지", price = 3000, brand = brand)

    every { productRepository.save(product) } returns product

    productService.addProduct(product)

    verify(exactly = 1) { productRepository.save(product) }
  }

  @Test
  fun `updateProduct should update existing product`() {
    val brand = Brand(id = 1L, name = "A", products = mutableListOf())
    val existingProduct = Product(id = 1L, category = "아우터", price = 5000, brand = brand)
    val updatedProduct = Product(id = 1L, category = "아우터", price = 5500, brand = brand)

    every { productRepository.findById(1L) } returns Optional.of(existingProduct)
    every { productRepository.save(existingProduct) } returns existingProduct

    productService.updateProduct(1L, updatedProduct)

    assertEquals(5500, existingProduct.price)
    verify(exactly = 1) { productRepository.findById(1L) }
    verify(exactly = 1) { productRepository.save(existingProduct) }
  }

  @Test
  fun `updateProduct should throw exception if product not found`() {
    val brand = Brand(id = 1L, name = "A", products = mutableListOf())
    val updatedProduct = Product(id = 1L, category = "아우터", price = 5500, brand = brand)

    every { productRepository.findById(1L) } returns Optional.empty()

    val exception = assertThrows(IllegalArgumentException::class.java) {
      productService.updateProduct(1L, updatedProduct)
    }

    assertEquals("상품을 찾을 수 없습니다.", exception.message)
    verify(exactly = 1) { productRepository.findById(1L) }
    verify(exactly = 0) { productRepository.save(any()) }
  }

  @Test
  fun `deleteProduct should delete existing product`() {
    every { productRepository.existsById(1L) } returns true
    every { productRepository.deleteById(1L) } just Runs

    productService.deleteProduct(1L)

    verify(exactly = 1) { productRepository.existsById(1L) }
    verify(exactly = 1) { productRepository.deleteById(1L) }
  }

  @Test
  fun `deleteProduct should throw exception if product does not exist`() {
    every { productRepository.existsById(1L) } returns false

    val exception = assertThrows(IllegalArgumentException::class.java) {
      productService.deleteProduct(1L)
    }

    assertEquals("해당 ID의 상품이 존재하지 않습니다.", exception.message)
    verify(exactly = 1) { productRepository.existsById(1L) }
    verify(exactly = 0) { productRepository.deleteById(any()) }
  }
}
