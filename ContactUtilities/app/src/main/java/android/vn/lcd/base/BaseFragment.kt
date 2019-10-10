package android.vn.lcd.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.adomino.ddsdb.R
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.loading.view.*

abstract class BaseFragment : Fragment() {

    abstract fun getLayoutResource() : Int

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }
}