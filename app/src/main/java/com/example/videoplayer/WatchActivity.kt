package com.example.videoplayer

import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.videoplayer.databinding.ActivityMainBinding
import com.example.videoplayer.databinding.ActivityWatchBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView.FullscreenButtonClickListener

class WatchActivity : AppCompatActivity() {
    lateinit var binding: ActivityWatchBinding
    var isFullScreen = false
    var isLockScreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bthFullScreen = findViewById<ImageView>(R.id.exo_fullscreen_BTH)
        val bthLockScreen = findViewById<ImageView>(R.id.exo_lock)
        bthFullScreen.setOnClickListener{
            if(!isFullScreen) {
                bthFullScreen.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        com.google.android.exoplayer2.R.drawable.exo_ic_fullscreen_exit
                    )
                )
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE)
            }
            else {
                bthFullScreen.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.fullscreen
                    )

                )
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            }
            isFullScreen = !isFullScreen
        }

        bthLockScreen.setOnClickListener {
            if(!isLockScreen) {
                bthLockScreen.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.lock
                    )

                )
            } else
            {
                bthLockScreen.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.open
                    )

                )
            }
            isLockScreen = !isLockScreen
            bthLockScreen(isLockScreen)
        }



        val simpleExoPlayer = ExoPlayer.Builder(this)
            .setSeekBackIncrementMs(5000)
            .setSeekForwardIncrementMs(5000)
            .build()

        binding.player.player = simpleExoPlayer
        binding.player.keepScreenOn = true
        simpleExoPlayer.addListener(object : Player.Listener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING) {
                    binding.progressBar.visibility = View.VISIBLE
                }
                else if (playbackState == Player.STATE_READY) {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
        val videoSource = Uri.parse("https://github.com/MaxS2021/VideoArch/raw/7f83745d7104f6c7d5418401b5afb08a34a081a2/big-buck-bunny-360p.mp4")
        val mediaItem = MediaItem.fromUri(videoSource)
        simpleExoPlayer.setMediaItem(mediaItem)
        simpleExoPlayer.prepare()
        simpleExoPlayer.playbackState
    }

    private fun bthLockScreen(lockScreen: Boolean) {
            val sec_vid1 = findViewById<LinearLayout>(R.id.set_controller)
            val sec_vid2 = findViewById<LinearLayout>(R.id.set_controller)
        if (lockScreen) {
            sec_vid1.visibility= View.INVISIBLE
            sec_vid2.visibility = View.INVISIBLE
        } else {
            sec_vid1.visibility = View.VISIBLE
            sec_vid2.visibility = View.VISIBLE
        }
    }
}