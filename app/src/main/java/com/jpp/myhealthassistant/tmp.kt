package com.jpp.myhealthassistant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.jpp.myhealthassistant.databinding.FragmentMainMenuBinding
import com.jpp.myhealthassistant.databinding.FragmentTmpBinding
import com.jpp.myhealthassistant.utils.Utility

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [tmp.newInstance] factory method to
 * create an instance of this fragment.
 */
class tmp : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentTmpBinding;
    private var painscale = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTmpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sb2=binding.seekBar2;
        sb2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                painscale=progress;
                UpdateUI();
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Do something when touch is started
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Do something when touch is stopped
            }
        })
    }

    fun UpdateUI()
    {
        if(painscale==0)
        {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(),"s0"));
            binding.twtitle2.text="No pain";
            binding.twDescription.text = "You are currently experiencing no pain."
        }
        else if(painscale==1)
        {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(),"s0"));
            binding.twtitle2.text="Very mild pain";
            binding.twDescription.text = "You are having some pain, but it's hardly noticeable."
        }
        else if(painscale==2)
        {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(),"s1"));
            binding.twtitle2.text="Mild pain";
            binding.twDescription.text = "You are beginning to notice the pain."
        }
        else if(painscale==3)
        {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(),"s1"));
            binding.twtitle2.text="Mild pain";
            binding.twDescription.text = "You are sometimes distracted by the pain."
        }
        else if(painscale==4)
        {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(),"s1"));
            binding.twtitle2.text="Mild to Moderate pain";
            binding.twDescription.text = "You are constantly distracted by pain but can perform usual activities."
        }
        else if(painscale==5)
        {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(),"s2"));
            binding.twtitle2.text="Moderate pain";
            binding.twDescription.text = "The pain is bad enough that you need to interrupt or limit some activities.\n Have some rest and follow your condition, contact a physician if you need to."
        }
        else if(painscale==6)
        {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(),"s3"));
            binding.twtitle2.text="Moderate to Severe pain";
            binding.twDescription.text = "The pain is bad enough that you need to cease all activities.\nIt would be wise to consult with your physician."

        }
        else if(painscale==7)
        {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(),"s3"));
            binding.twtitle2.text="Severe pain";
            binding.twDescription.text = "The pain is in constant focus. You can not do daily activities.\nIt would be wise to consult with your physician."

        }
        else if(painscale==8)
        {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(),"s4"));
            binding.twtitle2.text="Severe pain";
            binding.twDescription.text = "The pain is awful, you can not do anything.\nIt would be wise to contact medical staff as soon as possible."

        }
        else if(painscale==9)
        {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(),"s4"));
            binding.twtitle2.text="Unbearable pain";
            binding.twDescription.text = "You feel the pain is unbearable.\n ⚠\uFE0F CONTACT A DOCTOR IMMEDIATELY ⚠\uFE0F"

        }
        else if(painscale==10)
        {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(),"s5"));
            binding.twtitle2.text="Extreme pain";
            binding.twDescription.text = "The pain is the worst you have ever experienced.\n ⚠\uFE0F CONTACT YOUR EMERGENCY SERVICE IMMEDIATELY ⚠\uFE0F"
        }



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment tmp.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            tmp().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}