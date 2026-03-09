package io.github.zzzyyylllty.chotenchem.function


import com.google.gson.GsonBuilder


val jsonUtils = GsonBuilder()
    .setVersion(1.0)
    .disableHtmlEscaping()
    .disableInnerClassSerialization()
    .setPrettyPrinting()
    .excludeFieldsWithModifiers()
    .setLenient()
    .create()


fun Any?.asListEnhanced() : List<String>? {
    if (this == null) return null
    val thisList = if (this is List<*>) this else listOf(this)
    val list = mutableListOf<String>()
    for (string in thisList) {
        if (string == null) continue
        list.addAll(string.toString().split("\n","<br>", ignoreCase = true))
    }
    if (!list.isEmpty() && list.last() == "") list.removeLast()
    return list
}

fun Any?.asListedStringEnhanded() : String? {
    return this.asListEnhanced()?.joinToString("\n")
}
