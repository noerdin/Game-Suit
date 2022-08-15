package com.example.gamesuit.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.*
import com.example.gamesuit.databinding.FragmentDialogBinding
import androidx.fragment.app.DialogFragment as AndroidDialogFragment

class DialogFragment(
    private var setResultWinner: String,
    private var onButtonClick: () -> Unit,
    private var onClickReset: () -> Unit
) : AndroidDialogFragment() {

    var binding: FragmentDialogBinding? = null

    override fun onResume() {
        super.onResume()
        val widht = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(widht, height)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            tvResult.text = setResultWinner
            btnBackMenu.setOnClickListener {
                onButtonClick.invoke()
                dismiss()
            }
            btnMainLagi.setOnClickListener {
                onClickReset.invoke()
                dismiss()
            }
        }
    }
}