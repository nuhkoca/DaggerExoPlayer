package com.nuhkoca.myapplication.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * A {@link Scope} for activities.
 *
 * @author nuhkoca
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
