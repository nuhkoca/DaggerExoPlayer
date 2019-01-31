package com.nuhkoca.myapplication.util.exo;

import javax.inject.Inject;

/**
 * A factory pattern that initializes the {@link ExoUtil}
 *
 * @author nuhkoca
 */
public class ExoUtilFactory {

    private ExoUtil exoUtil;

    /**
     * A default constructor that injects dependencies
     *
     * @param exoUtil represents an instance of {@link ExoUtil}
     */
    @Inject
    ExoUtilFactory(ExoUtil exoUtil) {
        this.exoUtil = exoUtil;
    }

    /**
     * Returns an instance of {@link ExoUtil}
     *
     * @return an instance of {@link ExoUtil}
     */
    public ExoUtil getExoUtil() {
        return exoUtil;
    }
}
