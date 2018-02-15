package io.data2viz.geo.projection

import io.data2viz.geo.MultiPoint
import io.data2viz.geo.Point
import io.data2viz.geo.path.geoPath
import io.data2viz.path.SvgPath
import io.data2viz.path.svgPath
import io.data2viz.test.TestBase
import kotlin.test.Test

class MercatorProjectionTests : TestBase() {

    val util = ProjectionTests()

    @Test
    fun mercator_clip_extent_null_sets_default_automatic_clip_extent_LEGACY() {
        val projection = MercatorProjection()
        projection.translate = pt(.0, .0)
        projection.scale = 1.0
        projection.clipExtent = null
        projection.precision = .0

        projection.clipExtent shouldBe null
    }

    @Test
    fun mercator_various_projects_1() {
        val projection = mercator {
            translate = pt(.0, .0)
            rotate = pt(20.0, 10.0, 30.0)
        }

        util.checkProjection(projection, 84.0, 59.0, pt(468.39738235470327, -301.9997264679594))
        util.checkProjection(projection, -22.0, 16.0, pt(-41.232469642834104, -58.132750186605435))
        util.checkProjection(projection, 800.0, -800.0, pt(206.79103709446065, 151.98632483005113))
    }

    @Test
    fun mercator_various_projects_2() {
        val projection = mercator {
            translate = pt(40.0, 200.0)
            rotate = pt(5.0, .0, -30.0)
        }

        util.checkProjection(projection, 84.0, 59.0, pt(278.67805496267147, 119.04407321930081))
        util.checkProjection(projection, -22.0, 16.0, pt(22.511032184577658, 138.94899164036798))
        util.checkProjection(projection, 800.0, -800.0, pt(-193.4977306320785, 464.9972618113396))
    }

    @Test
    fun mercator_various_projects_3() {
        val projection = mercator {
            translate = pt(-100.0, 20.0)
            rotate = pt(-15.0, 20.0, .0)
            scale = 1.0
        }

        util.checkProjection(projection, 84.0, 59.0, pt(-98.18516654577843, 18.672644484723165))
        util.checkProjection(projection, -22.0, 16.0, pt(-100.74508864271014, 19.421488829918328))
        util.checkProjection(projection, 800.0, -800.0, pt(-99.63002236034055, 21.473889277898014))
    }
}