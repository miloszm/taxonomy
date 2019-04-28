package collections

import com.tm.domain._
import org.scalatest.{Matchers, WordSpec}

class CategoryNodeSpec extends WordSpec with Matchers {

  val chineseRestaurantNode = CategoryNode(Id("chinese2"), "chinese", Nil)
  val frenchRestaurantNode = CategoryNode(Id("french"), "french", Nil)
  val italianRestaurantNode = CategoryNode(Id("italian"), "italian", Nil)
  val node = TestCategories.repository.getOrElse(Id("restaurants"), throw new IllegalStateException("test node not found"))

  "Category node" should {
    "represent node with tags as CSV" in {
      node.asCsv(Lang("en_GB"), TestTags) shouldBe "restaurants,Restaurants"
    }
    "represent node without tags as CSV" in {
      frenchRestaurantNode.asCsv(Lang("en_EN"), TestTags) shouldBe "french"
    }
  }
}
