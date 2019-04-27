package com.tm.collections

import com.tm.domain.{CategoryNode, Id, Lang, TagId}


trait Categories {

  def root: CategoryNode

  def getNode(id: Id): Option[CategoryNode] = root.getNode(id)

  def getDescendants(categoryNode: CategoryNode): Seq[CategoryNode] = categoryNode.getDescendants

  def getNodesWithTag(tagId: TagId): Seq[CategoryNode] = root.getNodesWithTag(tagId)

  def toCsv(lang: Lang): Stream[String] = {
    root.getDescendantsWithLevel(0).toStream.map{
      case (node, level) => ("," * level) + s"${node.asCsv}"
    }
  }

  def fromCsv(csv: Stream[String]): Categories

}

