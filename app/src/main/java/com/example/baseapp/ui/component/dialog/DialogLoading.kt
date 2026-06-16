package com.worldcup.app.ui.component.dialog

import android.os.Bundle
import android.view.LayoutInflater
import com.worldcup.app.ui.base.BaseDialogFragment
import com.worldcup.app.databinding.DialogLoadingBinding

class DialogLoading : BaseDialogFragment<DialogLoadingBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenDialogTheme)
        isCancelable = false
    }

    override fun getBindingInflater(inflater: LayoutInflater): DialogLoadingBinding {
        return DialogLoadingBinding.inflate(inflater)
    }

    override fun initData(bundle: Bundle?) {
    }

    override fun initViews() {
    }

    override fun initActions() {
    }

    override fun dismiss() {
        if(isAdded){
            super.dismiss()
        }
    }


}