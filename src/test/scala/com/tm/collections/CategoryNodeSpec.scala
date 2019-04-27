package collections

import com.tm.domain.{CategoryNode, Id, TagId}
import org.scalatest.{Matchers, WordSpec}

class CategoryNodeSpec extends WordSpec with Matchers {

  val frenchRestaurantNode = CategoryNode(Id("french"), "french", Nil)
  val node = CategoryNode(Id("restaurants"), "restaurants",
    List(
      CategoryNode(Id("chinese2"), "chinese", Nil, Seq(TagId("chinese"))),
      frenchRestaurantNode,
      CategoryNode(Id("italian"), "italian", Nil)
    ),
    Seq(TagId("restaurant"))
  )

  "Category node" should {
    "represent node with tags as CSV" in {
      node.asCsv shouldBe "restaurants,restaurant"
    }
    "represent node without tags as CSV" in {
      frenchRestaurantNode.asCsv shouldBe "french"
    }
  }
}
