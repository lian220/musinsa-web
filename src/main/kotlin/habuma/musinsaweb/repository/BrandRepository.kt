package habuma.musinsaweb.repository

import habuma.musinsaweb.domain.Brand
import habuma.musinsaweb.domain.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BrandRepository : JpaRepository<Brand, Long> {
  fun findByName(name: String): Brand?
}
