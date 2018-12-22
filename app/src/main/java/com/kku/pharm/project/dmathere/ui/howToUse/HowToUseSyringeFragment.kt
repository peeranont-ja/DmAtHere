package com.kku.pharm.project.dmathere.ui.howToUse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kku.pharm.project.dmathere.R
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.fragment_how_to_use_pen_fill.*

class HowToUseSyringeFragment : Fragment() {
    companion object {
        fun newInstance(): HowToUseSyringeFragment {
            val fragment = HowToUseSyringeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_how_to_use_syringe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initYouTubePlayerView()
    }

    private fun initYouTubePlayerView() {
        youtube_player_view.playerUIController.showFullscreenButton(false)

        lifecycle.addObserver(youtube_player_view)
        youtube_player_view.initialize({ initializedYouTubePlayer ->
            initializedYouTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                override fun onReady() {
                    val videoId = "JQD6JMq_vgU"
                    initializedYouTubePlayer.cueVideo(videoId, 0f)
                }
            })
        }, true)

    }

}