package com.three.u.ui.tipsandtricks.tips_detail

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.android.exoplayer2.analytics.AnalyticsListener.EventTime
import com.google.android.exoplayer2.source.MediaSourceEventListener
import com.google.android.exoplayer2.util.RepeatModeUtil
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import com.jarvanmo.exoplayerview.orientation.OnOrientationChangedListener.SENSOR_LANDSCAPE
import com.jarvanmo.exoplayerview.orientation.OnOrientationChangedListener.SENSOR_PORTRAIT
import com.three.u.R
import com.three.u.base.push
import com.three.u.base.toast
import com.three.u.databinding.ActivityVideoPlayerBinding
import kotlinx.android.synthetic.main.activity_video_player.*


class VideoPlayerActivity : AppCompatActivity() {

    lateinit var binding : ActivityVideoPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_player)

        initVideoView()
    }


    private fun initVideoView() {

        videoView?.changeWidgetVisibility(R.id.exo_player_controller_back, View.GONE)
        videoView?.changeWidgetVisibility(R.id.exo_player_controller_back_landscape, View.GONE)

        cross?.push()?.setOnClickListener {
            videoView?.releasePlayer()
            finish()
        }

        videoView.isPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        videoView.setBackListener { view, isPortrait ->
            if (isPortrait) {
                finish()
            }
            false
        }

        videoView.setOrientationListener { orientation ->
            if (orientation === SENSOR_PORTRAIT)
                changeToPortrait()
             else if (orientation === SENSOR_LANDSCAPE)
                changeToLandscape()
        }

        val mediaSource = SimpleMediaSource(intent.getStringExtra("url"))
        videoView.play(mediaSource, false)

        videoView?.player?.addListener(object : Player.EventListener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_IDLE -> {}
                    Player.STATE_BUFFERING -> {}
                    Player.STATE_READY -> {}
                    Player.STATE_ENDED -> {
                        finish()
                    }
                }
            }
        })

    }

    private fun changeToPortrait() {
        // WindowManager operation is not necessary
        val attr = window.attributes
        attr.flags = (WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val window: Window = window
        window.setAttributes(attr)
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        videoView?.changeWidgetVisibility(R.id.exo_player_controller_back, View.GONE)
    }

    private fun changeToLandscape() {
        // WindowManager operation is not necessary
        val lp = window.attributes
        lp.flags = (WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val window: Window = window
        window.setAttributes(lp)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        videoView?.changeWidgetVisibility(R.id.exo_player_controller_back_landscape, View.GONE)
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT > 23) {
            videoView.resume()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT <= 23) {
            videoView.resume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT <= 23) {
            videoView.pause()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT > 23) {
            videoView.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.releasePlayer()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            videoView.onKeyDown(keyCode, event)
        } else super.onKeyDown(keyCode, event)
    }

}