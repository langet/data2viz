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
import io.data2viz.geo.projection.common.BaseConditionalProjector
import io.data2viz.geo.projection.common.Projector
import io.data2viz.math.EPSILON
import io.data2viz.math.deg
import io.data2viz.math.rad
import kotlin.math.*

fun conicEquidistantProjection(init: ConicProjection.() -> Unit = {}) =
    conicProjection(ConicEquidistantBaseConditionalProjector()) {
        scale = 131.154
        center(0.0.deg, 13.9389.deg)
        init()
    }

/**
 * The [ConicEquidistantProjector]
 * For some parallels values use [EquirectangularProjector]
 */
internal class ConicEquidistantBaseConditionalProjector(
    private val conicEquidistantProjector: ConicEquidistantProjector = ConicEquidistantProjector(),
    private val equirectangularProjector: EquirectangularProjector = EquirectangularProjector()
) : ConicProjector, BaseConditionalProjector() {

    override var phi0: Double
        get() = conicEquidistantProjector.phi0
        set(value) {
            conicEquidistantProjector.phi0 = value
        }
    override var phi1: Double
        get() = conicEquidistantProjector.phi1
        set(value) {
            conicEquidistantProjector.phi1 = value
        }

    override val baseProjector: Projector<GeoJsonPoint, Point3D>
        get() = equirectangularProjector

    override val nestedProjector: Projector<GeoJsonPoint, Point3D>
        get() = conicEquidistantProjector

    override val isNeedUseBaseProjector: Boolean
        get() = conicEquidistantProjector.isPossibleToUseProjector

}

class ConicEquidistantProjector : ConicProjector, Projector<GeoJsonPoint, Point3D> {

    override var phi0: Double = 0.0
        set(value) {
            field = value
            recalculate()
        }
    override var phi1: Double = io.data2viz.math.PI / 3.0
        set(value) {
            field = value
            recalculate()
        }

    private var cy0 = cy0()
    private var n = n()
    private var g = g()
    var isPossibleToUseProjector = isPossibleToUse()
        private set

    private fun recalculate() {
        cy0 = cy0()
        n = n()
        g = g()
        isPossibleToUseProjector = isPossibleToUse()

    }

    private fun isPossibleToUse() = abs(n) < EPSILON

    private fun g() = cy0 / n + phi0

    private fun n(): Double {
        return if (phi0 == phi1) {
            sin(phi0)
        } else {
            (cy0 - cos(phi1)) / (phi1 - phi0)
        }
    }

    private fun cy0() = cos(phi0)


    override fun invert(point: Point3D): GeoJsonPoint {
        val gy = g - point.y
        return GeoJsonPoint(
            (atan2(point.x, abs(gy)) / n * sign(gy)).rad,
            (g - sign(n) * sqrt(point.x * point.x + gy * gy)).rad
        )

    }

    override fun project(point: GeoJsonPoint): Point3D {
        val gphi = g - point.lat.rad
        val nlambda = n * point.lon.rad
        return Point3D(
            gphi * sin(nlambda),
            g - gphi * cos(nlambda)
        )
    }

}