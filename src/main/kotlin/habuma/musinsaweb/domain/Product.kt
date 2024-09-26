package habuma.musinsaweb.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
data class Product(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0,

  var category: String,

  var price: Int,

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  var brand: Brand
) {
  // JPA 기본 생성자
  constructor() : this(0L, "", 0, Brand())
}