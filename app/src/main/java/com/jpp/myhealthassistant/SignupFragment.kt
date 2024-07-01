package com.jpp.myhealthassistant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.jpp.myhealthassistant.controller.MyHealthAssistantController
import com.jpp.myhealthassistant.databinding.FragmentLoginBinding
import com.jpp.myhealthassistant.databinding.FragmentSignupBinding
import com.jpp.myhealthassistant.model.User
import com.jpp.myhealthassistant.utils.ToastHandler

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding;
    private lateinit var navController: NavController;
    private lateinit var t:ToastHandler;

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
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view);
        t=ToastHandler(requireContext());
        binding.btnCancel.setOnClickListener {
            navController.navigate(R.id.action_signupFragment_to_loginFragment)
        }
        binding.btnSignup.setOnClickListener {
             if(Validate())
             {
                 var username = binding.txtUsername.text.toString();
                 var password = binding.txtPassword.text.toString();
                 var email = binding.txtEmail.text.toString();
                 MakeUser(username,password,email);
             }
        }
    }

    fun Init() {

    }

    fun Validate():Boolean
    {

        if(binding.txtUsername.text.isNullOrBlank() || binding.txtUsername.text!!.isEmpty())
        {
            t.showToast("Username empty!", Toast.LENGTH_SHORT);
            return false;
        }
        if(binding.txtEmail.text.isNullOrBlank() || binding.txtEmail.text!!.isEmpty())
        {
            t.showToast("Email empty!", Toast.LENGTH_SHORT);
            return false;
        }
        if(binding.txtPassword.text.isNullOrBlank() || binding.txtPassword.text!!.isEmpty())
        {
            t.showToast("Password empty!", Toast.LENGTH_SHORT);
            return false;
        }
        if(binding.txtRepass.text.isNullOrBlank() || binding.txtRepass.text!!.isEmpty())
        {
            t.showToast("Repeated password empty!", Toast.LENGTH_SHORT);
            return false;
        }
        if(binding.txtRepass.text.toString()!=binding.txtPassword.text.toString())
        {
            t.showToast("Repeated password must match original password!", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    fun MakeUser(username:String,password:String,email:String)
    {
        var ctrl=MyHealthAssistantController(requireContext());
        var usr = User(-1,username,email,password,false);
        ctrl.insertUser(usr);
        t.showToast("Successfully added user.",Toast.LENGTH_SHORT);
        navController.navigate(R.id.action_signupFragment_to_loginFragment);

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}