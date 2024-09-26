package habuma.musinsaweb.service

import habuma.musinsaweb.domain.Brand
import habuma.musinsaweb.dto.Response
import habuma.musinsaweb.dto.Response.*
import habuma.musinsaweb.repository.BrandRepository
import habuma.musinsaweb.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class PriceService(
  private val brandRepository: BrandRepository,
  private val productRepository: ProductRepository
) {

  // 카테고리 별 최저 가격 조회
  fun getLowestPricesByCategory(): TotalLowestPriceResponse {
    val brands: List<Brand> = brandRepository.findAll()

    // 카테고리 별 최저 가격을 저장할 리스트
    val lowestPricesMap = mutableMapOf<String, LowestPriceResponse>()

    brands.forEach { brand ->
      brand.products.forEach { product ->
        // 카테고리가 이미 존재하는 경우
        if (lowestPricesMap.containsKey(product.category)) {
          // 현재 가격이 저장된 가격보다 낮으면 업데이트
          val currentLowest = lowestPricesMap[product.category]
          if (currentLowest == null || product.price < currentLowest.price) {
            lowestPricesMap[product.category] = LowestPriceResponse(
              category = product.category,
              brand = brand.name,
              price = product.price
            )
          }
        } else {
          // 카테고리가 처음 등장하는 경우 추가
          lowestPricesMap[product.category] = LowestPriceResponse(
            category = product.category,
            brand = brand.name,
            price = product.price
          )
        }
      }
    }

    // 최저 가격 리스트와 총액 계산
    val lowestList = lowestPricesMap.values.toList()
    val totalAmount = lowestList.sumOf { it.price }

    // 응답 구조 생성
    return TotalLowestPriceResponse(
      lowestPrices = lowestList.map {
        mapOf(
          "카테고리" to it.category,
          "브랜드" to it.brand,
          "가격" to it.price.toString()
        )
      },
      total = totalAmount
    )
  }


  fun getLowestPricesByBrand(brandName: String): Response.TotalLowestPriceResponseForBrand {
    val products = productRepository.findByBrandName(brandName) // 브랜드에 해당하는 상품 조회

    if (products.isEmpty()) {
      throw IllegalArgumentException("해당 브랜드의 상품이 없습니다.")
    }

    // 각 카테고리 별 최저 가격 계산
    val lowestPricesMap = mutableMapOf<String, LowestPriceResponse>()

    products.forEach { product ->
      if (lowestPricesMap.containsKey(product.category)) {
        val currentLowest = lowestPricesMap[product.category]
        if (currentLowest == null || product.price < currentLowest.price) {
          lowestPricesMap[product.category] = LowestPriceResponse(
            category = product.category,
            brand = brandName,
            price = product.price
          )
        }
      } else {
        lowestPricesMap[product.category] = LowestPriceResponse(
          category = product.category,
          brand = brandName,
          price = product.price
        )
      }
    }

    // 최저 가격 리스트와 총액 계산
    val lowestList = lowestPricesMap.values.toList()
    val totalAmount = lowestList.sumOf { it.price }

    // 응답 구조 생성
    return TotalLowestPriceResponseForBrand(
      브랜드 = brandName,
      카테고리 = lowestList.map {
        mapOf(
          "카테고리" to it.category,
          "가격" to it.price.toString()
        )
      },
      총액 = totalAmount.toString()
    )
  }

}