package habuma.musinsaweb.controller

import habuma.musinsaweb.domain.Brand
import habuma.musinsaweb.service.BrandService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/brands")
class BrandController(private val brandService: BrandService) {

  @PostMapping
  fun addBrand(@RequestBody brand: Brand): ResponseEntity<String> {
    return try {
      brandService.addBrand(brand)
      ResponseEntity.status(HttpStatus.CREATED).body("브랜드가 성공적으로 추가되었습니다.")
    } catch (e: Exception) {
      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("브랜드 추가에 실패했습니다: ${e.message}")
    }
  }

  @PutMapping("/{id}")
  fun updateBrand(@PathVariable id: Long, @RequestBody updatedBrand: Brand): ResponseEntity<String> {
    return try {
      brandService.updateBrand(id, updatedBrand)
      ResponseEntity.ok("브랜드가 성공적으로 수정되었습니다.")
    } catch (e: Exception) {
      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("브랜드 수정에 실패했습니다: ${e.message}")
    }
  }

  @DeleteMapping("/{id}")
  fun deleteBrand(@PathVariable id: Long): ResponseEntity<String> {
    return try {
      brandService.deleteBrand(id)
      ResponseEntity.ok("브랜드가 성공적으로 삭제되었습니다.")
    } catch (e: Exception) {
      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("브랜드 삭제에 실패했습니다: ${e.message}")
    }
  }
}
