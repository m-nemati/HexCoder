package ir.mnemati.coder.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ir.mnemati.coder.R
import kotlin.experimental.and
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        /*
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        */

        /* ***** Define Widgets ***** */
        val txtInput:EditText = root.findViewById(R.id.etInput)
        val txtOutput:TextView = root.findViewById(R.id.tvOutput)
        val btnDecode:Button = root.findViewById(R.id.btnDecode)
        val btnEncode:Button = root.findViewById(R.id.btnEncode)
        val btnClear:Button = root.findViewById(R.id.btnClear)
        val btnCopy: Button = root.findViewById(R.id.btnCopy)

        val hexArray = "0123456789ABCDEF".toCharArray()

        /* ***** Implement  of byteToHex function ***** */
        fun bytesToHex(bytes: ByteArray): String {
            val hexChars = CharArray(bytes.size * 2)
            for (j in bytes.indices) {
                val v = (bytes[j] and 0xFF.toByte()).toInt()

                hexChars[j * 2] = hexArray[v ushr 4]
                hexChars[j * 2 + 1] = hexArray[v and 0x0F]
            }
            return String(hexChars)
        }

        /* ***** Implement  of decodeHexString function***** */
        fun decodeHexString(hexString: String) :ByteArray{
            if (hexString.length % 2 == 1) {
                /* throw new IllegalArgumentException(
                        "Invalid hexadecimal String supplied."); */
            }
            val bytes: ByteArray = ByteArray(hexString.length / 2)
            var j = 0
            var l = hexString.length
            l -=1
            for (i  in  0..l step 2) {

                bytes[j] = Integer.valueOf(hexString.substring(i, i + 2), 16).toByte()
                j += 1
            }
            return bytes;
        }

        /* *****  When decode button touched ***** */
        btnDecode.setOnClickListener {

            var inputStr = txtInput.text.toString()

            val charset = Charsets.UTF_8
            val byteArray = inputStr.toByteArray(charset)
            val s = bytesToHex(byteArray)
            txtOutput.text = s
        }

        /* *****  When encode button touched ***** */
        btnEncode.setOnClickListener {
            val inputStr2 = txtInput.text.toString()
            val bytearray: ByteArray= decodeHexString(inputStr2)
            val strOut = String(bytearray)
            txtOutput.text = strOut
        }

        /* *****  When Clear button touched ***** */
        btnClear.setOnClickListener {
            txtInput.text.clear()
            txtOutput.text = ""
            txtInput.requestFocus()
            Toast.makeText(activity, "Clear", Toast.LENGTH_SHORT).show()
        }

        /* *****  When Copy button touched ***** */
        btnCopy.setOnClickListener {

            (requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).apply {
                setPrimaryClip(ClipData.newPlainText("simple text", txtOutput.text))
            }

            Toast.makeText(activity, "Copy Done", Toast.LENGTH_SHORT).show()
        }
        return root
    }
}