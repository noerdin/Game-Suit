package com.example.gamesuit

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.example.gamesuit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var bot: SuitType? = null
    private var player: SuitType? = null
    private var hasWinner = false

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.playBatu.setOnClickListener {
            if (hasWinner) return@setOnClickListener

            player = SuitType.BATU
            Log.d("MainActivity", player.toString())
            binding.playBatu.background = resources.getDrawable(R.drawable.bg_rounded)
            randomBot()
            startSuit()
        }

        binding.playGunting.setOnClickListener {
            if (hasWinner) return@setOnClickListener

            player = SuitType.GUNTING
            Log.d("MainActivity", player.toString())
            binding.playGunting.background = resources.getDrawable(R.drawable.bg_rounded)
            randomBot()
            startSuit()
        }

        binding.playKertas.setOnClickListener {
            if (hasWinner) return@setOnClickListener

            player = SuitType.KERTAS
            Log.d("MainActivity", player.toString())
            binding.playKertas.background = resources.getDrawable(R.drawable.bg_rounded)
            randomBot()
            startSuit()
        }

        binding.ivReset.setOnClickListener {
            binding.playBatu.background = null
            binding.playGunting.background = null
            binding.playKertas.background = null
            binding.comBatu.background = null
            binding.comGunting.background = null
            binding.comKertas.background = null
            binding.ivVs.setImageResource(R.drawable.vs)
            bot = null
            player = null
            hasWinner = false
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setPlayer(pilih: SuitType, view: ImageView) {
        player = pilih
        Log.d("MainActivity", player.toString())
        view.background = resources.getDrawable(R.drawable.bg_rounded)
    }

    private fun randomBot() {
        bot = SuitType.values().random()
        when (bot) {
            SuitType.KERTAS -> binding.comKertas.background =
                ResourcesCompat.getDrawable(resources, R.drawable.bg_rounded, null)
            SuitType.GUNTING -> binding.comGunting.background =
                ResourcesCompat.getDrawable(resources, R.drawable.bg_rounded, null)
            SuitType.BATU -> binding.comBatu.background =
                ResourcesCompat.getDrawable(resources, R.drawable.bg_rounded, null)
        }
    }

    private fun startSuit() {
        val winner = when {
            player == SuitType.KERTAS && bot == SuitType.GUNTING -> {
                bot
            }
            player == SuitType.GUNTING && bot == SuitType.BATU -> {
                bot
            }
            player == SuitType.BATU && bot == SuitType.KERTAS -> {
                bot
            }
            player == SuitType.GUNTING && bot == SuitType.KERTAS -> {
                player
            }
            player == SuitType.BATU && bot == SuitType.GUNTING -> {
                player
            }
            player == SuitType.KERTAS && bot == SuitType.BATU -> {
                player
            }
            else -> null
        }

        val winnerResId = when {
            winner == player -> R.drawable.playerwin
            winner == bot -> R.drawable.comwin
            else -> R.drawable.draw
        }
        binding.ivVs.setImageResource(winnerResId)
        hasWinner = true
    }
}


