package io.data2viz.geo.geometry.path

import io.data2viz.geo.geojson.geoPath
import io.data2viz.geo.geometry.calculateSvgPath
import io.data2viz.geo.projection.equirectangularProjection
import io.data2viz.geo.projection.pt
import io.data2viz.geojson.*
import io.data2viz.geom.PathGeom
import io.data2viz.geom.svgPath
import io.data2viz.math.PI
import io.data2viz.test.TestBase
import kotlin.test.Test


/**
 * TODO: Check values
 * It looks like d3 have different svgPath implementation for points. so values from d3 test fails
 */
class SvgStringsTests : TestBase() {

    var equirectangular = equirectangularProjection {
        scale = 900.0 / PI
        precision = 0.0
    }

    @Test
    fun geoPath_Point_renders_a_point() {

        println("project = " + equirectangular.project(-63.0, 18.0).joinToString())

        val pathGeom = PathGeom()
        val geoPath = geoPath(
            equirectangular, pathGeom
        )

        geoPath.project(Point(pt(-63.0, 18.0)))
        val actualSvgPath = pathGeom.svgPath
        actualSvgPath shouldBe "M169.5,160A4.5,4.5,0,1,1,160.5,160A4.5,4.5,0,1,1,169.5,160"
        //actualSvgPath shouldBe "M165,160m0,4.500000a4.500000,4.500000 0 1,1 0,-9a4.500000,4.500000 0 1,1 0,9z" // from d3
    }

    @Test
    fun geoPath_pointRadius_radius_Point_renders_a_point_of_the_given_radius() {

        val actualSvgPath = calculateSvgPath(
            equirectangular, Point(pt(-63.0, 18.0)), pointRadius = 10.0
        )
        actualSvgPath shouldBe "M175,160A10,10,0,1,1,155,160A10,10,0,1,1,175,160"
        //actualSvgPath shouldBe "M165,160m0,10a10,10 0 1,1 0,-20a10,10 0 1,1 0,20z" // from d3
    }


    @Test
    fun geoPath_MultiPoint_renders_a_point() {

        val actualSvgPath = calculateSvgPath(
            equirectangular, MultiPoint(
                arrayOf(
                    pt(-63.0, 18.0),
                    pt(-62.0, 18.0),
                    pt(-62.0, 17.0)
                )
            )
        )
        actualSvgPath shouldBe "M169.5,160A4.5,4.5,0,1,1,160.5,160A4.5,4.5,0,1,1,169.5,160M174.5,160A4.5,4.5,0,1,1,165.5,160A4.5,4.5,0,1,1,174.5,160M174.5,165A4.5,4.5,0,1,1,165.5,165A4.5,4.5,0,1,1,174.5,165"
//        actualSvgPath shouldBe "M165,160m0,4.500000a4.500000,4.500000 0 1,1 0,-9a4.500000,4.500000 0 1,1 0,9zM170,160m0,4.500000a4.500000,4.500000 0 1,1 0,-9a4.500000,4.500000 0 1,1 0,9zM170,165m0,4.500000a4.500000,4.500000 0 1,1 0,-9a4.500000,4.500000 0 1,1 0,9z" // from d3
    }

    @Test
    fun geoPath_LineString_renders_a_line_string() {

        val actualSvgPath = calculateSvgPath(
            equirectangular, LineString(
                arrayOf(
                    pt(-63.0, 18.0),
                    pt(-62.0, 18.0),
                    pt(-62.0, 17.0)
                )
            )
        )
        actualSvgPath shouldBe "M165,160L170,160L170,165"
    }

    @Test
    fun geoPath_Polygon_renders_a_polygon() {

        val actualSvgPath = calculateSvgPath(
            equirectangular, Polygon(
                arrayOf(
                    arrayOf(
                        pt(-63.0, 18.0),
                        pt(-62.0, 18.0),
                        pt(-62.0, 17.0),
                        pt(-63.0, 18.0)
                    )
                )
            )
        )
        actualSvgPath shouldBe "M165,160L170,160L170,165Z"
    }


    @Test
    fun geoPath_GeometryCollection_renders_a_geometry_collection() {

        val actualSvgPath = calculateSvgPath(
            equirectangular, GeometryCollection(
                arrayOf(
                    Polygon(
                        arrayOf(
                            arrayOf(
                                pt(-63.0, 18.0),
                                pt(-62.0, 18.0),
                                pt(-62.0, 17.0),
                                pt(-63.0, 18.0)
                            )
                        )

                    )
                )
            )
        )
        actualSvgPath shouldBe "M165,160L170,160L170,165Z"
    }


    @Test
    fun geoPath_Feature_renders_a_feature() {

        val actualSvgPath = calculateSvgPath(
            equirectangular, Feature(

                Polygon(
                    arrayOf(
                        arrayOf(
                            pt(-63.0, 18.0),
                            pt(-62.0, 18.0),
                            pt(-62.0, 17.0),
                            pt(-63.0, 18.0)
                        )
                    )

                )

            )
        )
        actualSvgPath shouldBe "M165,160L170,160L170,165Z"
    }


    @Test
    fun geoPath_FeatureCollection_renders_a_feature_collection() {

        val actualSvgPath = calculateSvgPath(
            equirectangular, FeatureCollection(

                arrayOf(
                    Feature(

                        Polygon(
                            arrayOf(
                                arrayOf(
                                    pt(-63.0, 18.0),
                                    pt(-62.0, 18.0),
                                    pt(-62.0, 17.0),
                                    pt(-63.0, 18.0)
                                )
                            )

                        )

                    )
                )

            )
        )
        actualSvgPath shouldBe "M165,160L170,160L170,165Z"
    }

    @Test
    fun geoPath_LineString_then_geoPath_Point_does_not_treat_the_point_as_part_of_a_line() {

        var actualSvgPath = calculateSvgPath(
            equirectangular, LineString(
                arrayOf(
                    pt(-63.0, 18.0),
                    pt(-62.0, 18.0),
                    pt(-62.0, 17.0)
                )
            )
        )
        actualSvgPath shouldBe "M165,160L170,160L170,165"

        actualSvgPath = calculateSvgPath(
            equirectangular, LineString(
                arrayOf(
                    pt(-63.0, 18.0)
                )
            )
        )
        actualSvgPath shouldBe "M165,160"
        //actualSvgPath shouldBe "M165,160m0,4.500000a4.500000,4.500000 0 1,1 0,-9a4.500000,4.500000 0 1,1 0,9z" // from d3
    }
}

