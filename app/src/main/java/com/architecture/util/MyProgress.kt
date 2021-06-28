package com.architecture.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.architecture.databinding.LayoutProgressBinding


class MyProgress : DialogFragment() {

    lateinit var mBinding : LayoutProgressBinding

    companion object{

        var isShowing = false

        fun show(activity: AppCompatActivity){
            if (isShowing) return
            val dialog = MyProgress()
            dialog.show(activity.supportFragmentManager,"_progress_dialog")
            activity.supportFragmentManager.executePendingTransactions()
            isShowing = true
        }

        fun hide(activity: AppCompatActivity){
            val pD = activity.supportFragmentManager.findFragmentByTag("_progress_dialog")
            if (pD!=null) {
                activity.supportFragmentManager.beginTransaction().remove(pD).commit()
                activity.supportFragmentManager.executePendingTransactions()
            }
            isShowing = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireContext())
        val lI = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = LayoutProgressBinding.inflate(lI, null, false)
        builder.setView(mBinding.root)
        val aD = builder.create()
        aD.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return aD
    }
}