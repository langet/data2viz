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


/**
 * Handle both horizontal and vertical alignments.
 */
data class TextAlign internal constructor(val horizontal: TextHAlign, val vertical: TextVAlign)


/**
 * Extension property to get and set all text alignments properties in one expression.
 */
var TextNode.textAlign:TextAlign
    get() = textAlign(hAlign, vAlign)
    set(value) {
        hAlign = value.horizontal
        vAlign = value.vertical
    }


/**
 * Extension function to facilitate the alignment setting in a TextNode
 */
fun TextNode.textAlign(horizontal: TextHAlign = TextHAlign.LEFT, vertical: TextVAlign = TextVAlign.BASELINE) =
    TextAlign(horizontal, vertical)

