package com.ncodelab.data

import java.util.concurrent.ConcurrentHashMap

import com.typesafe.scalalogging.StrictLogging
import sangria.macros.derive.{GraphQLDescription, GraphQLName}

import scala.collection.JavaConverters._


object Status extends Enumeration {
  val OPEN, FINISH = Value
}

@GraphQLName("Task")
@GraphQLDescription("The task")
case class Task(
                 id: String,
                 title: String,
                 text: String,
                 status: Status.Value
               ) {
  def setStatus(status: Status.Value): Task = {
    this.copy(status = status)
  }

  def setTitle(title: String): Task = {
    this.copy(title = title)
  }

  def setText(text: String): Task = {
    this.copy(text = text)
  }
}

@GraphQLName("Folder")
@GraphQLDescription("Folder for tasks")
case class Folder(
                   id: String,
                   name: String,
                   tasks: List[Task]
                 ) {
  def hasTask(taskId: String): Boolean = {
    tasks.map(_.id).contains(taskId)
  }

  def addTask(task: Task): Folder = {
    this.copy(tasks = tasks :+ task)
  }

  def removeTask(taskId: String): Folder = {
    this.copy(tasks = tasks.filterNot(_.id == taskId))
  }

  def getTask(taskId: String): Option[Task] = {
    tasks.find(_.id == taskId)
  }

  def setName(name: String): Folder = {
    this.copy(name = name)
  }
}

class TaskRepoImpl extends StrictLogging {

  private def replaceTask(taskId: String, folderId: String)(mutator: (Task) => Task) = {
    require(folders.contains(folderId), "Wrong folderId")
    val oldFolder = folders(folderId)
    require(oldFolder.hasTask(taskId), "Wrong taskId")
    val oldTask = oldFolder.getTask(taskId).get
    folders.replace(folderId, oldFolder.removeTask(taskId).addTask(mutator(oldTask)))
  }

  def addFolder(id: String, name: String): Boolean =
    folders.putIfAbsent(id, Folder(id, name, List())).isEmpty

  def getFolders: List[Folder] = folders.values.toList

  def removeFolder(folderId: String): Boolean = {
    folders.remove(folderId)
    true
  }

  def setTaskText(taskId: String, folderId: String, text: String): Boolean = {
    replaceTask(taskId, folderId)(_.setText(text))
    true
  }

  def setTaskTitle(taskId: String, folderId: String, title: String): Boolean = {
    replaceTask(taskId, folderId)(_.setTitle(title))
    true
  }

  def setFolderName(folderId: String, name: String): Boolean = {
    require(folders.contains(folderId), "Missing folder")
    val oldFolder = folders(folderId)
    folders.replace(folderId, oldFolder.setName(name))
    true
  }

  def setStatus(taskId: String, folderId: String, status: Status.Value): Boolean = {
    replaceTask(taskId, folderId)(_.setStatus(status))
    true
  }

  def getFolder(folderId: String): Folder = {
    require(folders.contains(folderId), "Missing folder")
    folders(folderId)
  }

  def addTask(id: String, title: String, text: String, folderId: String): Boolean = {
    require(folders.contains(folderId), "Try to add task to missing folder")
    val oldFolder = folders(folderId)
    val task = Task(id, title, text, Status.OPEN)
    if (oldFolder.hasTask(id)) {
      false
    } else {
      folders.replace(folderId, oldFolder.addTask(task))
      true
    }
  }

  def removeTask(taskId: String, folderId: String): Boolean = {
    require(folders.contains(folderId), "Try to remove task from missing folder")
    val oldFolder = folders(folderId)
    require(oldFolder.hasTask(taskId), "Try to remove missing task")
    folders.replace(folderId, oldFolder.removeTask(taskId))
    true
  }

  private val folders = new ConcurrentHashMap[String, Folder]().asScala
  folders.put("1", Folder("1", "Work", List(
    Task("1", "TODO app", "Make awesome TODO app.", Status.FINISH),
    Task("2", "GraphQL support", "Add GraphQL support for TODO app.", Status.OPEN),
    Task("3", "Share app", "Share app with world", Status.OPEN)
  )))
  folders.put("2", Folder("2", "Home", List(
    Task("1", "Relax", "Relax with pizza and PS4", Status.OPEN)
  )))

}
