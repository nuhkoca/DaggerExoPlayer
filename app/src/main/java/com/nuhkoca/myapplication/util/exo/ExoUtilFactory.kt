package com.nuhkoca.myapplication.util.exo

import javax.inject.Inject

/**
 * A factory pattern that initializes the [ExoUtil]
 *
 * @author nuhkoca
 */
class ExoUtilFactory
/**
 * A default constructor that injects dependencies
 *
 * @param exoUtil represents an instance of [ExoUtil]
 */
@Inject
internal constructor(
    /**
     * Returns an instance of [ExoUtil]
     *
     * @return an instance of [ExoUtil]
     */
    val exoUtil: ExoUtil
)
