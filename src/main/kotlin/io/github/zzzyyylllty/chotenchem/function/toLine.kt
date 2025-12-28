package io.github.zzzyyylllty.chotenchem.function

import io.github.zzzyyylllty.chotenchem.data.Filter
import io.github.zzzyyylllty.chotenchem.data.Line
import io.github.zzzyyylllty.chotenchem.data.TaskLine
import io.github.zzzyyylllty.chotenchem.data.TaskStatText
import io.github.zzzyyylllty.chotenchem.data.TextLine

fun List<Map<String, String?>?>.toLines(): List<Line> {
    val lines = mutableListOf<Line>()
    this.forEach {
        if (it != null) {
            val line = when (it["type"]) {
                "text" -> {
                    it["content"].asListEnhanded()?.let { content ->
                        lines.add(
                            TextLine(
                                type = "text",
                                content = content
                            )
                        )
                    }
                }

                "task" -> {
                    lines.add(
                        TaskLine(
                            type = "text",
                            maximum = it["maximum"]?.toIntOrNull(),
                            filter = Filter.valueOf((it["filter"] ?: "ALL").uppercase()),
                            text = TaskStatText(
                                it["completed"].asListEnhanded() ?: emptyList(),
                                it["in-progress"].asListEnhanded() ?: emptyList(),
                                it["no-progress"].asListEnhanded() ?: emptyList(),
                            )
                        )
                    )
                }

                else -> {}
            }
        }
    }
    return lines
}