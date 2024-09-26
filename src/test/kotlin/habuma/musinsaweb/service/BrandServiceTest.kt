package habuma.musinsaweb.service

import habuma.musinsaweb.domain.Brand
import habuma.musinsaweb.repository.BrandRepository
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class BrandServiceTest {

  private lateinit var brandRepository: BrandRepository
  private lateinit var brandService: BrandService

  @BeforeEach
  fun setUp() {
    brandRepository = mockk()
    brandService = BrandService(brandRepository)
  }

  @Test
  fun `addBrand should save brand`() {
    val brand = Brand(id = 1L, name = "TestBrand", products = mutableListOf())

    every { brandRepository.save(brand) } returns brand

    brandService.addBrand(brand)

    verify(exactly = 1) { brandRepository.save(brand) }
  }

  @Test
  fun `updateBrand should update existing brand`() {
    val existingBrand = Brand(id = 1L, name = "OldBrand", products = mutableListOf())
    val updatedBrand = Brand(id = 1L, name = "NewBrand", products = mutableListOf())

    every { brandRepository.findById(1L) } returns Optional.of(existingBrand)
    every { brandRepository.save(existingBrand) } returns existingBrand

    brandService.updateBrand(1L, updatedBrand)

    assertEquals("NewBrand", existingBrand.name)
    verify(exactly = 1) { brandRepository.findById(1L) }
    verify(exactly = 1) { brandRepository.save(existingBrand) }
  }

  @Test
  fun `updateBrand should throw exception if brand not found`() {
    val updatedBrand = Brand(id = 1L, name = "NewBrand", products = mutableListOf())

    every { brandRepository.findById(1L) } returns Optional.empty()

    val exception = assertThrows(IllegalArgumentException::class.java) {
      brandService.updateBrand(1L, updatedBrand)
    }

    assertEquals("해당 ID의 브랜드를 찾을 수 없습니다.", exception.message)
    verify(exactly = 1) { brandRepository.findById(1L) }
    verify(exactly = 0) { brandRepository.save(any()) }
  }

  @Test
  fun `deleteBrand should delete existing brand`() {
    val existingBrand = Brand(id = 1L, name = "TestBrand", products = mutableListOf())

    every { brandRepository.findById(1L) } returns Optional.of(existingBrand)
    every { brandRepository.delete(existingBrand) } just Runs

    brandService.deleteBrand(1L)

    verify(exactly = 1) { brandRepository.findById(1L) }
    verify(exactly = 1) { brandRepository.delete(existingBrand) }
  }

  @Test
  fun `deleteBrand should throw exception if brand not found`() {
    every { brandRepository.findById(1L) } returns Optional.empty()

    val exception = assertThrows(IllegalArgumentException::class.java) {
      brandService.deleteBrand(1L)
    }

    assertEquals("해당 ID의 브랜드를 찾을 수 없습니다.", exception.message)
    verify(exactly = 1) { brandRepository.findById(1L) }
    verify(exactly = 0) { brandRepository.delete(any()) }
  }
}
