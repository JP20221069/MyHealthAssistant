package com.jpp.myhealthassistant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.jpp.myhealthassistant.databinding.FragmentMainMenuBinding
import com.jpp.myhealthassistant.databinding.FragmentSymptomsMenuBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SymptomsMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SymptomsMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentSymptomsMenuBinding;
    private lateinit var navController: NavController

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
        binding = FragmentSymptomsMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view);

        binding.ibPain.setOnClickListener{
            submenu("PAIN")
        }
        binding.btnPain.setOnClickListener{
            submenu("PAIN")
        }
    }

    fun submenu(choice:String)
    {
        if(choice=="PAIN")
        {
            navController.navigate(R.id.action_symptomsMenuFragment_to_painWizardFragment);
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SymptomsMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SymptomsMenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}