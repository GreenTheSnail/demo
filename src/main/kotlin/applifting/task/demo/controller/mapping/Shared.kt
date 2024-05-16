package applifting.task.demo.controller.mapping

import java.util.UUID

fun String.convertToUUID(): UUID = UUID.fromString(this)
