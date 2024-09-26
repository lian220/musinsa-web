package habuma.musinsaweb.dto

class Response {

  data class TotalLowestPriceResponse(
    val lowestPrices: List<Map<String, String>>,
    val total: Int
  )

  data class LowestPriceResponse(
    val category: String,
    val brand: String,
    val price: Int
  )

  data class TotalLowestPriceResponseForBrand(
    val 브랜드: String,
    val 카테고리: List<Map<String, String>>,
    val 총액: String
  )

  data class PriceResponse(val 브랜드: String, val 가격: String)

  data class ProductRequest(
    val name: String,
    val price: Int,
    val category: String,
    val brand: String
  )

  data class GenericResponse(
    val status: String,
    val message: String
  )

}