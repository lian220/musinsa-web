package habuma.musinsaweb.service

import habuma.musinsaweb.domain.Product
import habuma.musinsaweb.dto.Response
import habuma.musinsaweb.repository.ProductRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

  fun getPriceByCategory(category: String): Map<String, Any> {
    val products = productRepository.findByCategory(category).ifEmpty {
      throw IllegalArgumentException("해당 카테고리에 대한 상품이 없습니다.")
    }

    val minPriceProduct = products.minByOrNull { it.price }
    val maxPriceProduct = products.maxByOrNull { it.price }

    return mapOf(
      "카테고리" to category,
      "최저가" to listOf(Response.PriceResponse(minPriceProduct?.brand?.name ?: "", minPriceProduct?.price?.toString() ?: "")),
      "최고가" to listOf(Response.PriceResponse(maxPriceProduct?.brand?.name ?: "", maxPriceProduct?.price?.toString() ?: ""))
    )
  }

  fun addProduct(product: Product) {
    productRepository.save(product)
  }

  @Transactional
  fun updateProduct(id: Long, product: Product) {
    val existingProduct = productRepository.findById(id).orElseThrow {
      IllegalArgumentException("상품을 찾을 수 없습니다.")
    }.apply {
      category = product.category
      price = product.price
      brand = product.brand // 브랜드도 업데이트
    }
    productRepository.save(existingProduct)
  }

  fun deleteProduct(id: Long) {
    if (productRepository.existsById(id)) {
      productRepository.deleteById(id)
    } else {
      throw IllegalArgumentException("해당 ID의 상품이 존재하지 않습니다.")
    }
  }
}
