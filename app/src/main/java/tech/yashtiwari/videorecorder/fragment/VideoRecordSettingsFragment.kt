package tech.yashtiwari.videorecorder.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import tech.yashtiwari.videorecorder.R
import tech.yashtiwari.videorecorder.RecordActivity
import tech.yashtiwari.videorecorder.databinding.FragmentVideoRecordSettingsBinding
import tech.yashtiwari.videorecorder.viewmodels.VMVideoRecordSettings

class VideoRecordSettingsFragment : Fragment() {

    private lateinit var binding : FragmentVideoRecordSettingsBinding
    private val viewModel by viewModels<VMVideoRecordSettings>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_record_settings, container, false)
        binding.ibRecord.setOnClickListener{_ -> openRecordActivity()}
        return binding.root
    }

    private fun openRecordActivity() {
        val intent = Intent(activity, RecordActivity::class.java)
        with(intent){
            val bundle = Bundle()
            bundle.putInt("duration", viewModel.obsDuration.get())
            bundle.putString("name", viewModel.obsVideoName.get())
            putExtras(bundle)
        }
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = VideoRecordSettingsFragment()
    }
}