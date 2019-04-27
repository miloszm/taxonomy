package com.tm.collections

import com.tm.domain.{CategoryNode, Id}

trait Categories {

  def getNode(id: Id): Option[CategoryNode]

  def getDescendants(categoryNode: CategoryNode): Seq[CategoryNode]

  def toCsv: Stream[String]

  def fromCsv(csv: Stream[String]): Categories

}

