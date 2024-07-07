package com.jpp.myhealthassistant

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.jpp.myhealthassistant.controller.MyHealthAssistantController
import com.jpp.myhealthassistant.databinding.FragmentPainWizardBinding
import com.jpp.myhealthassistant.model.Location
import com.jpp.myhealthassistant.model.Measurement
import com.jpp.myhealthassistant.utils.ToastHandler
import com.jpp.myhealthassistant.utils.Utility

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PainWizardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PainWizardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentPainWizardBinding;
    private lateinit var navController: NavController;
    private var location: Location = Location.DEFAULT;
    private lateinit var dictOfLocations:Map<Location,ImageButton>;
    private var painscale = 0;
    private var wizardprogress=1;
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
        binding = FragmentPainWizardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view);
        dictOfLocations= mapOf(
            Location.HEAD to binding.ibHead, Location.STOMACH to binding.ibAbdomen,
            Location.CHEST to binding.ibChest,
            Location.RIGHT_ARM to binding.ibRA,
            Location.LEFT_ARM to binding.ibLA,
            Location.RIGHT_HAND to binding.ibRH,
            Location.LEFT_HAND to binding.ibLH,
            Location.LEFT_LEG to binding.ibLL,
            Location.RIGHT_LEG to binding.ibRL,
            Location.RIGHT_FOOT to binding.ibRF,
            Location.LEFT_FOOT to binding.ibLF
        );
        binding.ibHead.setOnClickListener{
            UpdateUI(Location.HEAD);
        }

        binding.ibChest.setOnClickListener{
            UpdateUI(Location.CHEST);
        }

        binding.ibRA.setOnClickListener{
            UpdateUI(Location.RIGHT_ARM);
        }

        binding.ibLA.setOnClickListener{
            UpdateUI(Location.LEFT_ARM);
        }

        binding.ibRH.setOnClickListener{
            UpdateUI(Location.RIGHT_HAND);
        }

        binding.ibLH.setOnClickListener{
            UpdateUI(Location.LEFT_HAND);
        }

        binding.ibAbdomen.setOnClickListener{
            UpdateUI(Location.STOMACH);
        }

        binding.ibLL.setOnClickListener{
            UpdateUI(Location.LEFT_LEG);
        }

        binding.ibRL.setOnClickListener{
            UpdateUI(Location.RIGHT_LEG);
        }

        binding.ibRF.setOnClickListener{
            UpdateUI(Location.RIGHT_FOOT);
        }

        binding.ibLF.setOnClickListener{
            UpdateUI(Location.LEFT_FOOT);
        }

        binding.btnNext.setOnClickListener{
            UpdateUI(Location.DEFAULT,wizardprogress+1)
            if(wizardprogress==3)
            {
                addMeasurement();
            }
            if(wizardprogress==4)
            {
                navController.navigate(R.id.action_painWizardFragment_to_symptomsMenuFragment);
            }
        }
        binding.btnBack.setOnClickListener {
            UpdateUI(Location.DEFAULT, wizardprogress - 1)
        }


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



    fun UpdateUI(l: Location = Location.DEFAULT, newwp:Int=wizardprogress)
    {
        if(newwp!=wizardprogress)
        {
            if(newwp<1)
            {
                navController.navigate(R.id.action_painWizardFragment_to_symptomsMenuFragment)
            }
            if(newwp>4)
            {
                return;
            }
            else
            {
                wizardprogress=newwp;
            }
        }
        if(wizardprogress<3)
        {
            binding.btnNext.text=getString(R.string.Next);
        }
        if(wizardprogress==1)
        {
            binding.pnlSelect.visibility=View.INVISIBLE;
            binding.pnlMap.visibility=View.VISIBLE;
            setimagebuttons(l);

        }
        else if(wizardprogress==2)
        {
            binding.pnlSelect.visibility=View.VISIBLE;
            binding.pnlMap.visibility=View.INVISIBLE;
            UpdatePainScale();
        }
        else if(wizardprogress==3)
        {
            binding.pnlSelect.visibility=View.INVISIBLE;
            binding.pnlMap.visibility=View.INVISIBLE;
            binding.pnlFinish.visibility=View.VISIBLE;
            binding.btnNext.text=getString(R.string.Finish);
        }



    }
    fun setimagebuttons(l: Location)
    {
        if(l== Location.DEFAULT) return;
        if(l==location)
        {
            dictOfLocations[l]?.setImageDrawable(Utility().GetImage(requireContext(),l.name.lowercase()));
            location= Location.DEFAULT;
        }
        else
        {
            for(x in dictOfLocations.values)
            {
                x.setImageDrawable(Utility().GetImage(requireContext(),l.name.lowercase()))
            }
            dictOfLocations[l]?.setImageDrawable(Utility().GetImage(requireContext(),l.name.lowercase()+"red"));
            location=l;
        }

    }

    fun UpdatePainScale()
    {
        if (painscale == 0) {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(), "s0"));
            binding.twtitle2.text = getString(R.string.NoPain);
            binding.twDescription.text = getString(R.string.Pain0)
        } else if (painscale == 1) {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(), "s0"));
            binding.twtitle2.text = getString(R.string.VeryMildPain);
            binding.twDescription.text = getString(R.string.Pain1);
        } else if (painscale == 2) {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(), "s1"));
            binding.twtitle2.text = getString(R.string.MildPain);
            binding.twDescription.text = getString(R.string.Pain2);
        } else if (painscale == 3) {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(), "s1"));
            binding.twtitle2.text = getString(R.string.MildPain);
            binding.twDescription.text = getString(R.string.Pain3);
        } else if (painscale == 4) {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(), "s1"));
            binding.twtitle2.text = getString(R.string.MildModeratePain);
            binding.twDescription.text = getString(R.string.Pain4);
        } else if (painscale == 5) {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(), "s2"));
            binding.twtitle2.text = getString(R.string.ModeratePain);
            binding.twDescription.text = getString(R.string.Pain5);
        } else if (painscale == 6) {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(), "s3"));
            binding.twtitle2.text = getString(R.string.ModerateSeverePain);
            binding.twDescription.text =getString(R.string.Pain6);

        } else if (painscale == 7) {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(), "s3"));
            binding.twtitle2.text = getString(R.string.SeverePain);
            binding.twDescription.text =getString(R.string.Pain7);

        } else if (painscale == 8) {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(), "s4"));
            binding.twtitle2.text = getString(R.string.SeverePain);
            binding.twDescription.text =getString(R.string.Pain8);

        } else if (painscale == 9) {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(), "s4"));
            binding.twtitle2.text = getString(R.string.SeverePain);
            binding.twDescription.text =getString(R.string.Pain9);

        } else if (painscale == 10) {
            binding.ivwIcon.setImageDrawable(Utility().GetImage(requireContext(), "s5"));
            binding.twtitle2.text = getString(R.string.ExtremePain);
            binding.twDescription.text =getString(R.string.Pain10);
        }
    }

    fun addMeasurement()
    {
        var ctrl:MyHealthAssistantController= MyHealthAssistantController(requireContext())
        var uid = context?.getSharedPreferences("SESSION_DATA", Context.MODE_PRIVATE)?.getString("USR_ID","-1")?.toInt();
        var user = ctrl.getUserByID(uid!!);
        var scale = ctrl.getScaleByName("Pain Scale");
        var measure:Measurement = Measurement(-1,user,painscale.toFloat(),scale,location,1);
        ctrl.insertMeasurement(measure);
        var th = ToastHandler(requireContext());
        //th.showToast("Successfully inserted measurement.", Toast.LENGTH_SHORT);
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PainWizardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PainWizardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}