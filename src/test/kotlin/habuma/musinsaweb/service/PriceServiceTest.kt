package habuma.musinsaweb.service

import habuma.musinsaweb.domain.Brand
import habuma.musinsaweb.domain.Product
import habuma.musinsaweb.repository.BrandRepository
import habuma.musinsaweb.repository.ProductRepository
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PriceServiceTest {

  private lateinit var brandRepository: BrandRepository
  private lateinit var productRepository: ProductRepository
  private lateinit var priceService: PriceService

  @BeforeEach
  fun setUp() {
    brandRepository = mockk()
    productRepository = mockk()
    priceService = PriceService(brandRepository, productRepository)
  }

  @Test
  fun `getLowestPricesByCategory should return correct lowest prices and total amount`() {
    val brandA = Brand(
      id = 1L,
      name = "A",
      products = mutableListOf()
    )

    val brandB = Brand(
      id = 2L,
      name = "B",
      products = mutableListOf()
    )

    brandA.products = mutableListOf(
      Product(id = 1L, category = "상의", price = 10000, brand = brandA),
      Product(id = 2L, category = "아우터", price = 5000, brand = brandA)
    )

    brandB.products = mutableListOf(
      Product(id = 3L, category = "상의", price = 9000, brand = brandB),
      Product(id = 4L, category = "바지", price = 3000, brand = brandB)
    )

    every { brandRepository.findAll() } returns listOf(brandA, brandB)

    val response = priceService.getLowestPricesByCategory()

    assertEquals(3, response.lowestPrices.size)
    assertEquals(17000, response.total)

    val expected = listOf(
      mapOf("카테고리" to "상의", "브랜드" to "B", "가격" to "9000"),
      mapOf("카테고리" to "아우터", "브랜드" to "A", "가격" to "5000"),
      mapOf("카테고리" to "바지", "브랜드" to "B", "가격" to "3000")
    )

    assertTrue(response.lowestPrices.containsAll(expected))
  }

  @Test
  fun `getLowestPricesByBrand should return correct lowest prices and total amount`() {
    val brandName = "A"
    val brand = Brand(id = 1L, name = brandName, products = mutableListOf())
    val products = listOf(
      Product(id = 1L, category = "상의", price = 11200, brand = brand),
      Product(id = 2L, category = "아우터", price = 5500, brand = brand),
      Product(id = 3L, category = "바지", price = 4200, brand = brand)
    )

    every { productRepository.findByBrandName(brandName) } returns products

    val response = priceService.getLowestPricesByBrand(brandName)

    assertEquals("A", response.브랜드)
    assertEquals(3, response.카테고리.size)
    assertEquals("20900", response.총액) // 11200 + 5500 + 4200 = 20900

    val expected = listOf(
      mapOf("카테고리" to "상의", "가격" to "11200"),
      mapOf("카테고리" to "아우터", "가격" to "5500"),
      mapOf("카테고리" to "바지", "가격" to "4200")
    )

    assertTrue(response.카테고리.containsAll(expected))
  }

  @Test
  fun `getLowestPricesByBrand should throw exception if no products found`() {
    val brandName = "NonExistentBrand"

    every { productRepository.findByBrandName(brandName) } returns emptyList()

    val exception = assertThrows(IllegalArgumentException::class.java) {
      priceService.getLowestPricesByBrand(brandName)
    }

    assertEquals("해당 브랜드의 상품이 없습니다.", exception.message)
    verify(exactly = 1) { productRepository.findByBrandName(brandName) }
  }
}
