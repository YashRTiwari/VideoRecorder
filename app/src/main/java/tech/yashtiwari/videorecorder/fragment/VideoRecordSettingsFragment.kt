package tech.yashtiwari.videorecorder.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_video_record_settings.*
import tech.yashtiwari.videorecorder.R
import tech.yashtiwari.videorecorder.activity.RecordActivity
import tech.yashtiwari.videorecorder.Utility
import tech.yashtiwari.videorecorder.databinding.FragmentVideoRecordSettingsBinding
import tech.yashtiwari.videorecorder.viewmodels.VMVideoRecordSettings


class VideoRecordSettingsFragment : Fragment() {

    private lateinit var binding : FragmentVideoRecordSettingsBinding
    private val viewModel by viewModels<VMVideoRecordSettings>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_record_settings, container, false)
        binding.ibRecord.setOnClickListener{ openRecordActivity()}
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.edtFileName.doOnTextChanged { text, start, before, count -> viewModel.updateName(text.toString()) }
        binding.slider.addOnChangeListener{slider, value, fromUser -> viewModel.updateDurationValue(value)}
    }

    private fun openRecordActivity() {
        if (!viewModel.IsFilevalid(Utility.getOutputDirectory(requireContext()), edtFileName.text.toString())) return
        val intent = Intent(activity, RecordActivity::class.java)
        startActivity(
            with(intent){
                this.putExtra("duration", viewModel.obsDuration.get())
                this.putExtra("name", viewModel.obsVideoName.get())
            })
    }


    companion object {
        @JvmStatic
        fun newInstance() = VideoRecordSettingsFragment()
        private val TAG = "VideoRecordSettings"
    }
}