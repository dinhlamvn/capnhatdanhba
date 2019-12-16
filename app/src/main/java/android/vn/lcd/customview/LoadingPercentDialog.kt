package android.vn.lcd.customview

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.adomino.ddsdb.R

class LoadingPercentDialog : DialogFragment() {

  private var pgPercent: ProgressBar? = null
  private var tvPercent: TextView? = null

  var isShowing: Boolean = false
    private set

  companion object {
    private const val TAG = "LoadingPercentDialog"

    fun newInstance(): LoadingPercentDialog {
      return LoadingPercentDialog()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    isCancelable = false
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.progress_percent, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    pgPercent = view.findViewById(R.id.pgPercent)
    tvPercent = view.findViewById(R.id.tvPercent)
  }

  fun show(fragmentManager: FragmentManager) {
    if (isShowing) return
    isShowing = true
    show(fragmentManager, TAG)
  }

  override fun onDismiss(dialog: DialogInterface) {
    isShowing = false
    super.onDismiss(dialog)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    pgPercent = null
    tvPercent = null
  }

  fun doUpdate(percent: Int, total: Int) {
    tvPercent?.text = String.format("%d/%d", percent, total)
    pgPercent?.progress = percent
  }
}