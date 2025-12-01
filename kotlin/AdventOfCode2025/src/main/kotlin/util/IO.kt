package util

fun getResourceAsText(path: String): String {
    return object {}.javaClass.classLoader.getResource(path)!!.readText()
}

fun getInputAsText(day: Int): String {
    return getResourceAsText("./day${day}.in").trim()
}
