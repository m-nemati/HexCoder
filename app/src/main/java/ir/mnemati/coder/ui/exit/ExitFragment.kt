package ir.mnemati.coder.ui.exit

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ir.mnemati.coder.R

class ExitFragment : Fragment() {

    private lateinit var exitViewModel: ExitViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        exitViewModel =
                ViewModelProvider(this).get(ExitViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes") { dialog, which -> System.exit(0) }
                .setNegativeButton("No", null)
                .show()
        return root
    }
}