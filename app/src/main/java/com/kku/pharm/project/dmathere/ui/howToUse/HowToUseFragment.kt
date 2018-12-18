package com.kku.pharm.project.dmathere.ui.howToUse

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.kku.pharm.project.dmathere.R
import com.kku.pharm.project.dmathere.events.FullScreenPlayerEvent
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.player.playerUtils.FullScreenHelper
import kotlinx.android.synthetic.main.fragment_how_to_use.*
import org.greenrobot.eventbus.EventBus

class HowToUseFragment : Fragment() {
    private val fullScreenHelper = FullScreenHelper()

    companion object {
        fun newInstance(): HowToUseFragment {
            val fragment = HowToUseFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_how_to_use, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.addObserver(youtube_player_view)
        youtube_player_view.initialize({ initializedYouTubePlayer ->
            initializedYouTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                override fun onReady() {
                    val videoId = "6JYIGclVQdw"
                    initializedYouTubePlayer.cueVideo(videoId, 0f)
                }
            })
            addFullScreenListenerToPlayer(initializedYouTubePlayer)
        }, true)

    }

    private fun addFullScreenListenerToPlayer(youTubePlayer: YouTubePlayer) {
        youtube_player_view.addFullScreenListener(object : YouTubePlayerFullScreenListener {
            override fun onYouTubePlayerEnterFullScreen() {
                EventBus.getDefault().post(FullScreenPlayerEvent(true))
                fullScreenHelper.enterFullScreen(activity!!.window.decorView)

                addCustomActionToPlayer(youTubePlayer)
            }

            override fun onYouTubePlayerExitFullScreen() {
                EventBus.getDefault().post(FullScreenPlayerEvent(false))
                fullScreenHelper.exitFullScreen(activity!!.window.decorView)

                removeCustomActionFromPlayer()
            }
        })
    }

    @SuppressLint("PrivateResource")
    private fun addCustomActionToPlayer(youTubePlayer: YouTubePlayer?) {
        val customActionIcon = ContextCompat.getDrawable(context!!, R.drawable.ic_pause_36dp)
        youtube_player_view.playerUIController.setCustomAction1(customActionIcon!!) { youTubePlayer?.pause() }
    }

    private fun removeCustomActionFromPlayer() {
        youtube_player_view.playerUIController.showCustomAction1(false)
    }
}