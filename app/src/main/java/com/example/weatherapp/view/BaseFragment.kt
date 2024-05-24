package com.example.weatherapp.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.getScreenWidth

abstract class BaseFragment <VM : BaseViewModel> : Fragment() {

    private var loadingDialog : AlertDialog? = null

    protected abstract val viewModel : VM

    protected val loginDialogWidth by lazy { getScreenWidth(requireActivity() , 0.25) }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showLoadingDialog() {
        checkIfFragmentAttached {
            if (loadingDialog == null) {
                loadingDialog =
                    createCustomDialog(requireContext() , createView(R.layout.layout_loading_dialog) , Gravity.CENTER , false)
            }
            modifyWindowsParamsAndShow(loadingDialog!! , loginDialogWidth , ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    fun modifyWindowsParamsAndShow(dialog : AlertDialog , width : Int , height : Int) {
        dialog.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog.show()
//        dialog.window?.attributes = lWindowParams
        dialog.window!!.setLayout(width , height)
    }

    fun createView(@LayoutRes layout : Int) =
        LayoutInflater.from(requireContext()).inflate(layout , null)!!

    fun createCustomDialog(context : Context?, view : View?, gravity : Int, cancelable : Boolean) : AlertDialog {
        val dialog = AlertDialog.Builder(context).create()
        dialog.setView(view)
        val window = dialog.window
        window?.setGravity(gravity)
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val layoutParams = window.attributes
        layoutParams.y = 40 // bottom margin
        window.attributes = layoutParams
        dialog.setCancelable(cancelable)
        return dialog
    }

    fun checkIfFragmentAttached(operation : Context.() -> Unit) {
        if (isAdded && context != null) {
            operation(requireContext())
        }
    }

    fun hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing)
            loadingDialog?.dismiss()
        loadingDialog = null
    }

}