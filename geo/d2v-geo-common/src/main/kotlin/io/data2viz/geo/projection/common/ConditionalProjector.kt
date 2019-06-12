package io.data2viz.geo.projection.common

import io.data2viz.geo.projection.*
/**
 * Abstract Projector which delegate projector & invert to another projection depending current settings
 * @see BaseConditionalProjector
 */
abstract class ConditionalProjector : Projector {

    override fun invert(x: Double, y: Double): DoubleArray = activeProjector.invert(x, y)

    override fun project(lambda: Double, phi: Double): DoubleArray = activeProjector.project(lambda, phi)

    abstract val activeProjector: Projector
}

/**
 * ConditionalProjector implementation for two projectors
 *
 * @see ConditionalProjector
 * @see ConicEqualAreaBaseConditionalProjector
 * @see ConicConformalBaseConditionalProjector
 * @see ConicEquidistantBaseConditionalProjector
 */
abstract class BaseConditionalProjector : ConditionalProjector() {

    abstract val baseProjector: Projector
    abstract val nestedProjector: Projector
    abstract val isNeedUseBaseProjector: Boolean


    override val activeProjector: Projector
        get() = if (isNeedUseBaseProjector) {
            baseProjector
        } else {
            nestedProjector
        }
}