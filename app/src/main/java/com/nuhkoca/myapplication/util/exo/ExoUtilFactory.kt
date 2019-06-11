package com.nuhkoca.myapplication.util.exo

import javax.inject.Inject

/**
 * A factory pattern that initializes the [ExoUtil]
 *
 * @param exoUtil represents an instance of [ExoUtil]
 * @author nuhkoca
 */
class ExoUtilFactory @Inject constructor(val exoUtil: ExoUtil)
