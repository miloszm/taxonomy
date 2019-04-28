package com.tm.collections

import com.tm.domain.{CategoryNode, Id, TagId}

trait Categories {

  def root: CategoryNode

  def repository: Map[Id, CategoryNode]

  def getNode(id: Id): Option[CategoryNode] = root.getNode(id, repository)

  def getDescendants(categoryNode: CategoryNode): Seq[CategoryNode] = categoryNode.getDescendants(repository)

  def getNodesWithTag(tagId: TagId): Seq[CategoryNode] = root.getNodesWithTag(tagId, repository)

}
