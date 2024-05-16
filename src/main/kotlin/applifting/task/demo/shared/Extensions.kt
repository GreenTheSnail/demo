package applifting.task.demo.shared

fun String.isValidUrl() = """^(https?|ftp)://[^\s/$.?#].[^\s]*$""".toRegex().matches(this)

fun Int.isValidInterval() = this <= 0
