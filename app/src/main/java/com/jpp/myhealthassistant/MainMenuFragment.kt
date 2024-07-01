package com.jpp.myhealthassistant

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.jpp.myhealthassistant.controller.MyHealthAssistantController
import com.jpp.myhealthassistant.databinding.FragmentLoginBinding
import com.jpp.myhealthassistant.databinding.FragmentMainMenuBinding
import com.jpp.myhealthassistant.ui.theme.MyHealthAssistantTheme

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentMainMenuBinding;
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
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view);
        binding.btnLogout.setOnClickListener {
            var ctrl= MyHealthAssistantController(requireContext());
            var sharedPrefstr = context?.getSharedPreferences("SESSION_DATA", Context.MODE_PRIVATE)?.getString("USR_ID","-1");
            var uid = sharedPrefstr!!.toInt();
            ctrl.logoutUser(uid);
            navController.navigate(R.id.action_mainMenuFragment_to_loginFragment);
            context?.getSharedPreferences("SESSION_DATA", Context.MODE_PRIVATE)?.edit()?.putString("FLAG_LOGON","0")?.apply();
        }

        binding.btnSymptoms.setOnClickListener {
            menu("SYMPTOMS");
        }
        binding.ibSymptoms.setOnClickListener{
            menu("SYMPTOMS");
        }

    }

    fun menu(choice:String)
    {
        if(choice=="SYMPTOMS")
        {
            //TODO:MOVE TO SYMPTOMS MENU
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainMenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}