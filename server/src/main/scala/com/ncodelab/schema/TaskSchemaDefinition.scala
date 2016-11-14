package com.ncodelab.schema

import com.ncodelab.data._
import sangria.execution.deferred.{Fetcher, HasId}
import sangria.schema._
import sangria.macros.derive._

import scala.concurrent.Future

case class MyCTX(mutation: TaskRepoImpl)

object TaskSchemaDefinition {

  implicit val StatusEnumType = deriveEnumType[Status.Value]()

  implicit val InputTaskType = deriveInputObjectType[Task](InputObjectTypeName("InputTask"))

  implicit val TaskType = deriveObjectType[TaskRepoImpl, Task]()

  implicit val InputFolderType = deriveInputObjectType[Folder](InputObjectTypeName("InputFolder"))

  implicit val FolderType = deriveObjectType[TaskRepoImpl, Folder](
    ReplaceField("tasks", Field("tasks", ListType(TaskType), resolve = _.value.tasks))
  )

  val MutationType = deriveContextObjectType[MyCTX, TaskRepoImpl, Unit](_.mutation,
    IncludeMethods(
      "removeTask",
      "addTask",
      "removeFolder",
      "getFolder",
      "getFolders",
      "addFolder",
      "setStatus",
      "setTaskText",
      "setTaskTitle",
      "setFolderName"
    ))

  val TaskSchema = Schema(MutationType)

}
