package com.outr.nextui

import com.outr.nextui.model.Resource

package object desktop {
  def classLoader(path: String): Resource = Resource(getClass.getClassLoader.getResource(path).toString)
}