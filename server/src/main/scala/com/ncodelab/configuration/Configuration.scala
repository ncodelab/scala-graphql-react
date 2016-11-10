package com.ncodelab.configuration

import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.StrictLogging

object Configuration extends StrictLogging {
  private val instance: Config = ConfigFactory.defaultApplication().resolve()
  logger.debug(s"Application started with configuration: ${instance.toString}")
  val app: Config = instance.getConfig("app")
}
