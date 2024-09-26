package habuma.musinsaweb.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
data class Brand(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0,

  var name: String,

  @OneToMany(mappedBy = "brand", cascade = [CascadeType.ALL], orphanRemoval = true)
  @JsonIgnore
  var products: MutableList<Product> = mutableListOf()
) {
  constructor() : this(0L, "")
}