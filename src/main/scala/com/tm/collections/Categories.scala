package com.tm.collections

import com.tm.domain.{CategoryNode, Id}

trait Categories {

  def root: CategoryNode

  def getNode(id: Id): Option[CategoryNode] = root.getNode(id)

  def getDescendants(categoryNode: CategoryNode): Seq[CategoryNode] = categoryNode.getDescendants

  def toCsv: Stream[String]

  def fromCsv(csv: Stream[String]): Categories

}

