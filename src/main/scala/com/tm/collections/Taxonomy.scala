package com.tm.collections

import com.tm.collections.Taxonomy.Separator
import com.tm.domain.{CategoryNode, Id, Lang, NodeLevel}

case class Taxonomy(override val root: CategoryNode, override val repository: Map[Id, CategoryNode]) extends Categories {

  def toCsv(lang: Lang, tags: Tags): Stream[String] = {
    root.getDescendantsWithLevel(0, repository).toStream.map{
      nodeLevel => (Separator.toString * nodeLevel.level) + s"${nodeLevel.node.asCsv(lang, tags)}"
    }
  }

}

object Taxonomy {

  val Separator = ','

  def fromCsv(csv: Stream[String], lang: Lang, tags: Tags ): Taxonomy = {

    val nodes: List[NodeLevel] = csv.toList.zipWithIndex.flatMap { case (line, lineNumber) =>
      val level = line.takeWhile(_ == Separator).length
      CategoryNode.fromCsv(line.substring(level), Id(s"$lineNumber"), lang, tags).map(NodeLevel(_, level))
    }

    def updateChildren(remainingNodes: List[NodeLevel], doneNodes: List[CategoryNode]): List[CategoryNode] = remainingNodes match {
      case Nil => doneNodes
      case current::xs =>
        val children = xs
          .takeWhile(_.level > current.level)
          .filter(_.level == current.level + 1)
          .map(_.node)
        updateChildren(xs, doneNodes ::: List(current.node.copy(children = children.map(_.id))))
    }

    val updatedNodes = updateChildren(nodes, Nil)
    val root = updatedNodes.head
    val repository = updatedNodes.map { node =>
      node.id -> node
    }.toMap

    Taxonomy(root, repository)
  }
}
