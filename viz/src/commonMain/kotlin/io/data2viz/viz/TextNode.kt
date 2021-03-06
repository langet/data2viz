/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.viz

import io.data2viz.color.*

class TextNode : Node(),
        HasFill,
        HasStroke,
        HasTransform {

    override var transform: Transform? = null

    var x: Double = .0
    var y: Double = .0
    var textContent: String = "Type something"
    set(value) {
        field = value.makeVizFriendlyText()
    }

    var fontSize: Double = 12.0
    var fontFamily: FontFamily          = FontFamily.SANS_SERIF
    var fontWeight: FontWeight          = FontWeight.NORMAL
    var fontStyle: FontPosture          = FontPosture.NORMAL

}

private fun String.makeVizFriendlyText(): String = replaceNewLineWithSpace()

private fun String.replaceNewLineWithSpace(): String  = replace('\n', ' ')


class FontFamily private constructor(val name: String) {

    companion object {
        val MONOSPACE  = FontFamily("monospace")
        val SANS_SERIF = FontFamily("sans-serif")
        val SERIF 	   = FontFamily("serif")

        fun specifiedFont(name: String) = FontFamily(name)
    }

    override fun toString(): String {
        return "FontFamily(name='$name')"
    }


}

enum class FontWeight {
    BOLD,
    NORMAL,
}

enum class FontPosture {
    ITALIC,
    NORMAL,
}