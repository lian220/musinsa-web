package habuma.musinsaweb.config

import habuma.musinsaweb.domain.Brand
import habuma.musinsaweb.domain.Product
import habuma.musinsaweb.repository.BrandRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataLoader(private val brandRepository: BrandRepository) : CommandLineRunner {
  override fun run(vararg args: String?) {
    val brandsData = mapOf(
      "A" to listOf(11200, 5500, 4200, 9000, 2000, 1700, 1800, 2300),
      "B" to listOf(10500, 5900, 3800, 9100, 2100, 2000, 2000, 2200),
      "C" to listOf(10000, 6200, 3300, 9200, 2200, 1900, 2200, 2100),
      "D" to listOf(10100, 5100, 3000, 9500, 2500, 1500, 2400, 2000),
      "E" to listOf(10700, 5000, 3800, 9900, 2300, 1800, 2100, 2100),
      "F" to listOf(11200, 7200, 4000, 9300, 2100, 1600, 2300, 1900),
      "G" to listOf(10500, 5800, 3900, 9000, 2200, 1700, 2100, 2000),
      "H" to listOf(10800, 6300, 3100, 9700, 2100, 1600, 2000, 2000),
      "I" to listOf(11400, 6700, 3200, 9500, 2400, 1700, 1700, 2400)
    )

    for ((brandName, prices) in brandsData) {
      val brand = Brand(name = brandName, products = mutableListOf())
      val categories = listOf(
        "상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리"
      )
      prices.forEachIndexed { index, price ->
        val product = Product(category = categories[index], price = price, brand = brand)
        brand.products.add(product)
      }
      brandRepository.save(brand)
    }
  }
}
