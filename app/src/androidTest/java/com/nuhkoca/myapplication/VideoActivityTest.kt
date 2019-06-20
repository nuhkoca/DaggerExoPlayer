package com.nuhkoca.myapplication

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.google.common.truth.Truth
import com.nuhkoca.myapplication.ui.video.VideoActivity
import org.junit.Rule
import org.junit.Test

@LargeTest
class VideoActivityTest {

    @get:Rule
    val rule = ActivityTestRule(VideoActivity::class.java)

    @Test
    fun `activity_should_be_at_least_created`() {
        Truth.assertThat((rule.activity as LifecycleOwner).lifecycle.currentState).isAtLeast(Lifecycle.State.CREATED)
    }
}

