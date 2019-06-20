package com.nuhkoca.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.nuhkoca.myapplication.api.IExoAPI
import com.nuhkoca.myapplication.data.remote.player.PlayerResponse
import com.nuhkoca.myapplication.repository.PlayerRepository
import com.nuhkoca.myapplication.ui.video.PlayerUseCase
import com.nuhkoca.myapplication.ui.video.VideoViewModel
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@Suppress("UNCHECKED_CAST")
class PlayableContentTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val compositeDisposable = CompositeDisposable()

    private val iExoAPI = mock(IExoAPI::class.java)

    private val observer = mock(Observer::class.java) as Observer<PlayerResponse>

    lateinit var playerRepository: PlayerRepository
    lateinit var playerUseCase: PlayerUseCase
    lateinit var videoViewModel: VideoViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        playerRepository = PlayerRepository(iExoAPI)
        playerUseCase = PlayerUseCase(playerRepository)
        videoViewModel = VideoViewModel(compositeDisposable, playerUseCase)
    }

    @Test
    fun `playable_content_should_be_fetched_successfully`() {
        Mockito.`when`(iExoAPI.getPlayableContent(ID_VIDEO)).thenAnswer {
            return@thenAnswer Single.just(ArgumentMatchers.anyList<PlayerResponse>())
        }

        videoViewModel.content.observeForever(observer)

        videoViewModel.getPlayableContent(ID_VIDEO)

        Truth.assertThat(videoViewModel.content).isNotNull()
    }

    @After
    fun tearDown() {
        videoViewModel.content.removeObserver(observer)
    }

    companion object {
        private const val ID_VIDEO = "342694257"
    }
}
