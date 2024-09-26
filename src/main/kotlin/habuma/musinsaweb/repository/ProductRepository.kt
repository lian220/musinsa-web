package habuma.musinsaweb.repository

import habuma.musinsaweb.domain.Brand
import habuma.musinsaweb.domain.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
  fun findByCategory(category: String): List<Product>
  fun findAllByBrand(brand: Brand): List<Product>
  fun findByBrandName(brandName: String): List<Product>
}