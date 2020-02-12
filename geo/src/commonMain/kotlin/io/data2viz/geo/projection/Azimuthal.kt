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

package io.data2viz.geo.projection

import io.data2viz.geo.GeoJsonPoint
import io.data2viz.geo.Point3D
import io.data2viz.geo.geometry.asin
import io.data2viz.geo.geometry.limitedAsin
import io.data2viz.geo.projection.common.Projector
import io.data2viz.math.rad
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun azimuthalInvert(angle: (Double) -> Double) = { x: Double, y: Double ->
    val z = sqrt(x * x + y * y)
    val c = angle(z)
    val sc = sin(c)
    doubleArrayOf(atan2(x * sc, z * cos(c)), (if (z == 0.0) z else y * sc / z).limitedAsin)
}

open class Azimuthal(val scale: (Double) -> Double, val angle: (Double) -> Double) : Projector<GeoJsonPoint, Point3D> {

    override fun project(point: GeoJsonPoint): Point3D {
        val cx = point.lon.cos
        val cy = point.lat.cos
        val k = scale(cx * cy)
        return Point3D(k * cy * point.lon.sin, k * point.lat.sin)
    }

    override fun invert(point: Point3D): GeoJsonPoint {
        val z = sqrt(point.x * point.x + point.y * point.y)
        val c = angle(z)
        val sc = sin(c)
        return GeoJsonPoint(atan2(point.x * sc, z * cos(c)).rad, (if (z != .0) point.y * sc / z else z).asin.rad)
    }
}
