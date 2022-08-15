package com.example.gamesuit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.example.gamesuit.databinding.FragmentGameBinding
import com.example.gamesuit.dialog.DialogFragment

class GameFragment : Fragment() {
    companion object {
        const val KEY_VS_PLAYER = "vsplayer"
        const val KEY_NAME = "name"
    }

    var binding: FragmentGameBinding? = null
    val isvsPlayer by lazy { arguments?.getBoolean(KEY_VS_PLAYER) ?: false }
    val name by lazy { arguments?.getString(KEY_NAME) ?: "Player" }

    private var player2: Player? = null
    private var player: MainPlayer? = null
    private var hasWinner = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)


        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        player = MainPlayer(name)
        player2 = if (isvsPlayer) {
            MainPlayer("Player 2")
        } else {
            Bot()
        }
        if (isvsPlayer) {
            Toast.makeText(
                requireContext(),
                "${name} silahkan pilih",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding?.tvPlayer?.text = player?.name
        binding?.tvCom?.text = player2?.name

        binding?.playBatu?.setOnClickListener {
            if (hasWinner) return@setOnClickListener

            if (isvsPlayer) {
                onClickPlayPlayer(SuitType.BATU)
            } else {
                onClickPlayBot(binding?.playBatu, SuitType.BATU)
            }
        }

        binding?.playGunting?.setOnClickListener {
            if (hasWinner) return@setOnClickListener

            if (isvsPlayer) {
                onClickPlayPlayer(SuitType.GUNTING)
            } else {
                onClickPlayBot(binding?.playGunting, SuitType.GUNTING)
            }
        }

        binding?.playKertas?.setOnClickListener {
            if (hasWinner) return@setOnClickListener

            if (isvsPlayer) {
                onClickPlayPlayer(SuitType.KERTAS)
            } else {
                onClickPlayBot(binding?.playKertas, SuitType.KERTAS)
            }
        }
        binding?.ivReset?.setOnClickListener {
            setButtonResetGame()
        }
        binding?.ivClose?.setOnClickListener {
            setButtonBackToMenu()
        }

        if (isvsPlayer) {
            binding?.comBatu?.setOnClickListener {
                if (hasWinner) return@setOnClickListener

                onPlayer2Click(SuitType.BATU, binding?.comBatu)
            }
            binding?.comGunting?.setOnClickListener {
                if (hasWinner) return@setOnClickListener

                onPlayer2Click(SuitType.GUNTING, binding?.comGunting)
            }
            binding?.comKertas?.setOnClickListener {
                if (hasWinner) return@setOnClickListener

                onPlayer2Click(SuitType.KERTAS, binding?.comKertas)
            }
        }
    }

    private fun onPlayer2Click(type: SuitType, view: ImageView?) {
        if (player?.pilihan != null) {
            setPlayer2(type, view)
            when (player?.pilihan) {
                SuitType.KERTAS -> binding?.playKertas?.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.bg_rounded, null)
                SuitType.GUNTING -> binding?.playGunting?.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.bg_rounded, null)
                SuitType.BATU -> binding?.playBatu?.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.bg_rounded, null)
            }
            startSuit()
        } else {
            Toast.makeText(requireContext(), "Pilih Pemain 1 Dulu", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setPlayer(pilih: SuitType, view: ImageView?) {
        player?.pilihan = pilih
        view?.background = resources.getDrawable(R.drawable.bg_rounded)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setPlayer2(pilih: SuitType, view: ImageView?) {
        player2?.pilihan = pilih
        view?.background = resources.getDrawable(R.drawable.bg_rounded)
    }

    private fun selectBot() {
        if (!isvsPlayer) {
            player2?.pilihan = (player2 as? Bot)?.randomPilihan()
        }
        when (player2?.pilihan) {
            SuitType.KERTAS -> binding?.comKertas?.background =
                ResourcesCompat.getDrawable(resources, R.drawable.bg_rounded, null)
            SuitType.GUNTING -> binding?.comGunting?.background =
                ResourcesCompat.getDrawable(resources, R.drawable.bg_rounded, null)
            SuitType.BATU -> binding?.comBatu?.background =
                ResourcesCompat.getDrawable(resources, R.drawable.bg_rounded, null)
        }
    }

    private fun startSuit() {
        val winner = when {
            player?.pilihan == SuitType.KERTAS && player2?.pilihan == SuitType.GUNTING -> {
                player2
            }
            player?.pilihan == SuitType.GUNTING && player2?.pilihan == SuitType.BATU -> {
                player2
            }
            player?.pilihan == SuitType.BATU && player2?.pilihan == SuitType.KERTAS -> {
                player2
            }
            player?.pilihan == SuitType.GUNTING && player2?.pilihan == SuitType.KERTAS -> {
                player
            }
            player?.pilihan == SuitType.BATU && player2?.pilihan == SuitType.GUNTING -> {
                player
            }
            player?.pilihan == SuitType.KERTAS && player2?.pilihan == SuitType.BATU -> {
                player
            }
            else -> {
                null
            }
        }
        val textWin = if (winner != null) {
            "${winner.name} \n MENANG!"
        } else {
            "SERI!"
        }
        Toast.makeText(
            requireContext(),
            "${player2?.name} memilih ${player2?.pilihan}",
            Toast.LENGTH_SHORT
        ).show()

        val dialog = DialogFragment(textWin,
            { setButtonBackToMenu() },
            {
                setButtonResetGame()
                if (isvsPlayer) {
                    Toast.makeText(
                        requireContext(),
                        "${name} silahkan pilih",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
        dialog.show(childFragmentManager, null)
        hasWinner = true

    }

    private fun setButtonBackToMenu() {
        findNavController().navigateUp()

    }

    private fun setButtonResetGame() {
        binding?.playBatu?.background = null
        binding?.playGunting?.background = null
        binding?.playKertas?.background = null
        binding?.comBatu?.background = null
        binding?.comGunting?.background = null
        binding?.comKertas?.background = null
        player?.pilihan = null
        player2?.pilihan = null
        hasWinner = false

    }

    private fun onClickPlayBot(view: ImageView?, suitType: SuitType) {
        setPlayer(suitType, view)
        selectBot()
        startSuit()
    }

    private fun onClickPlayPlayer(suitType: SuitType) {
        player?.pilihan = suitType
        Toast.makeText(
            requireContext(),
            "${player2?.name} silahkan pilih",
            Toast.LENGTH_SHORT
        ).show()
    }
}