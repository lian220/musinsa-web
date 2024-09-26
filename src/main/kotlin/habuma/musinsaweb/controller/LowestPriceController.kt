package habuma.musinsaweb.controller

import habuma.musinsaweb.dto.Response
import habuma.musinsaweb.repository.BrandRepository
import habuma.musinsaweb.service.PriceService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class LowestPriceController(
  private val priceService: PriceService
) {

  @GetMapping("/category/lowest-prices")
  fun getLowestPrices(): ResponseEntity<Any> {
    return try {
      val response = priceService.getLowestPricesByCategory()
      ResponseEntity.ok(response)
    } catch (e: Exception) {
      ResponseEntity.internalServerError().body(
        Response.TotalLowestPriceResponse(lowestPrices = emptyList(), total = 0)
      )
    }
  }

  @GetMapping("/brand/{brandName}/lowest-prices")
  fun getLowestPricesForBrand(@PathVariable brandName: String): ResponseEntity<Any> {
    return try {
      val response = priceService.getLowestPricesByBrand(brandName) // 브랜드 이름을 인자로 전달하는 메소드 호출
      ResponseEntity.ok(
        mapOf("최저가" to response)
      )
    } catch (e: Exception) {
      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        mapOf("최저가" to Response.TotalLowestPriceResponseForBrand(브랜드 = brandName, 카테고리 = emptyList(), 총액 = "0")) // 적절한 기본값으로 설정
      )
    }
  }
}