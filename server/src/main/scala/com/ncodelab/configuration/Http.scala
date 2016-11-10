package com.ncodelab.configuration

import com.typesafe.config.Config

case class Http(host: String, port: Int)

object Http {
  def forConfig(path: String = "http", config: Config = Configuration.app): Http = {
    Http(config.getString(s"$path.host"), config.getInt(s"$path.port"))
  }
}
