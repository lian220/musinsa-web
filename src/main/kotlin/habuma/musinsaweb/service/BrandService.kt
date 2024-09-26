package habuma.musinsaweb.service

import habuma.musinsaweb.domain.Brand
import habuma.musinsaweb.repository.BrandRepository
import org.springframework.stereotype.Service

@Service
class BrandService(private val brandRepository: BrandRepository) {

  fun addBrand(brand: Brand) {
    brandRepository.save(brand)
  }

  fun updateBrand(id: Long, updatedBrand: Brand) {
    brandRepository.findById(id).map { existingBrand ->
      existingBrand.name = updatedBrand.name
      brandRepository.save(existingBrand)
    }.orElseThrow { IllegalArgumentException("해당 ID의 브랜드를 찾을 수 없습니다.") }
  }

  fun deleteBrand(id: Long) {
    brandRepository.findById(id).ifPresentOrElse(
      { brandRepository.delete(it) },
      { throw IllegalArgumentException("해당 ID의 브랜드를 찾을 수 없습니다.") }
    )
  }
}
