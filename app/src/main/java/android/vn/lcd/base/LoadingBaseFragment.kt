package android.vn.lcd.base

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.adomino.ddsdb.R

abstract class LoadingBaseFragment : BaseFragment() {

    private lateinit var loadingLayout: ConstraintLayout
    private lateinit var tvLoading: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadingLayout = view.findViewById(R.id.loadingLayout)
        tvLoading = view.findViewById(R.id.tvLoading)
    }

    open fun displayLoading() {
        loadingLayout.visibility = View.VISIBLE
        tvLoading.visibility = View.GONE
    }

    open fun displayLoading(message: String) {
        loadingLayout.visibility = View.VISIBLE
        tvLoading.visibility = View.VISIBLE
        tvLoading.text = message
    }

    open fun dismissLoading() {
        loadingLayout.visibility = View.GONE
    }
}