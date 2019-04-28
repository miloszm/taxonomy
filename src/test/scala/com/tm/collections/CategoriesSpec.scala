package collections

import com.tm.domain._
import org.scalatest.{Matchers, WordSpec}



class CategoriesSpec extends WordSpec with Matchers {

  val categories = TestCategories
  val tagsRepository = TestTags

  "Categories collection" should {

    "retrieve node by id" in {
      categories.getNode(Id("action")) shouldBe Some(CategoryNode(Id("action"), "action", Nil))
    }

    "retrieve descendants of a node" in {
      val maybeParent = categories.getNode(Id("music"))
      maybeParent should not be empty
      maybeParent.foreach { parent =>
        categories.getDescendants(parent) should contain theSameElementsAs List(
          CategoryNode(Id("jazz"), "jazz", Nil),
          CategoryNode(Id("pop"), "pop", Nil),
          CategoryNode(Id("rock"), "rock", Nil)
        )
      }
    }

    "retrieve multiple nodes with a tag" in {
      categories.getNodesWithTag(TagId("chinese")) should contain theSameElementsAs List(
        CategoryNode(Id("chinese1"), "chinese", Nil, Seq(TagId("chinese"))),
        CategoryNode(Id("chinese2"), "chinese", Nil, Seq(TagId("chinese")))
      )
    }

    "retrieve non-leaf node with a tag" in {
      categories.getNodesWithTag(TagId("restaurant")).map(_.copy(children = Nil)) should contain theSameElementsAs List(
        CategoryNode(Id("restaurants"), "restaurants", Nil, Seq(TagId("restaurant")))
      )
    }

  }

}

