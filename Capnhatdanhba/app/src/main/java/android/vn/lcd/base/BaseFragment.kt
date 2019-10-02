package android.vn.lcd.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kaopiz.kprogresshud.KProgressHUD

abstract class BaseFragment : Fragment() {

    private val progress: KProgressHUD by lazy {
        KProgressHUD.create(requireContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Loading...")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
    }

    abstract fun getLayoutResource() : Int

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }

    fun showLoading(title: String, message: String = "") {
        progress.setLabel(title)
        progress.setDetailsLabel(message)
        if (activity?.isFinishing == false) progress.show()
    }

    fun showLoading() {
        progress.setLabel("Loading...")
        progress.setDetailsLabel(null)
        if (activity?.isFinishing == false) progress.show()
    }

    fun dismissLoading() {
        if (activity?.isFinishing == false && progress.isShowing) progress.dismiss()
    }
}