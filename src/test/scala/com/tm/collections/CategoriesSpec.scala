package collections

import com.tm.collections.Categories
import com.tm.domain.{CategoryNode, Id}
import org.scalatest.{FlatSpec, Matchers, WordSpec}



object TestCategories extends Categories {

  val children = List(
    CategoryNode(Id("shows"),
      List(
        CategoryNode(Id("theatre"), Nil),
        CategoryNode(Id("films"),
          List(
            CategoryNode(Id("chinese"), Nil),
            CategoryNode(Id("comedy"), Nil),
            CategoryNode(Id("action"), Nil)
          )
        )
      )
    ),
    CategoryNode(Id("music"),
      List(
        CategoryNode(Id("jazz"), Nil),
        CategoryNode(Id("pop"), Nil),
        CategoryNode(Id("rock"), Nil)
      )
    ),
    CategoryNode(Id("restaurants"),
      List(
        CategoryNode(Id("chinese"), Nil),
        CategoryNode(Id("french"), Nil),
        CategoryNode(Id("italian"), Nil)
      )
    )
  )
  val root = CategoryNode(Id("categories"), children)

  override def getNode(id: Id): Option[CategoryNode] = root.getNode(id)

  override def getDescendants(categoryNode: CategoryNode): Seq[CategoryNode] = Nil

  override def toCsv = ???

  override def fromCsv(csv: Stream[String]) = ???
}

class CategoriesSpec extends WordSpec with Matchers {

  val categories = TestCategories

  "Categories collection" should {
    "retrieve node by id" in {
      categories.getNode(Id("action")) shouldBe Some(CategoryNode(Id("action"), Nil))
    }
    "retrieve descendants of a node" in {
      val maybeParent = categories.getNode(Id("music"))
      maybeParent should not be empty
      maybeParent.foreach { parent =>
        categories.getDescendants(parent) should contain theSameElementsAs List(
          CategoryNode(Id("jazz"), Nil),
          CategoryNode(Id("pop"), Nil),
          CategoryNode(Id("rock"), Nil)
        )
      }
    }

  }

}

