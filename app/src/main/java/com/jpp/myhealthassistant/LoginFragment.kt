package com.jpp.myhealthassistant

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.nearby.connection.Payload
import com.jpp.myhealthassistant.controller.MyHealthAssistantController
import com.jpp.myhealthassistant.databinding.FragmentLoginBinding;
import com.jpp.myhealthassistant.model.User
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding:FragmentLoginBinding;
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
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Init();
        navController= Navigation.findNavController(view);
        var sharedprefloggedin=context?.getSharedPreferences("SESSION_DATA", Context.MODE_PRIVATE)?.getString("FLAG_LOGON","0");
        if(sharedprefloggedin=="1")
        {
            navController.navigate(R.id.action_loginFragment_to_mainMenuFragment);
        }
        binding.linkSignUp.setOnClickListener{
            navController.navigate(R.id.action_loginFragment_to_signupFragment2)

        }
        binding.btnLogin.setOnClickListener{
            val user = binding.txtUsername.text.toString();
            val pass = binding.txtPassword.text.toString();
            LoginUser(user,pass);

        }
    }
    private fun Init()
    {
        var tw = binding.linkSignUp;
        tw.paintFlags=tw.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    private fun Validate()
    {
        //TODO: Dodati validaciju!
    }

    private fun LoginUser(email:String,pass:String)
    {
       /* auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful)
                navController.navigate(R.id.action_loginFragment_to_mainMenuFragment)
            else
                Toast.makeText(context, R.string.tst_error_log, Toast.LENGTH_SHORT).show()

        }*/
        var ctrl=MyHealthAssistantController(requireContext());
        var usr:User? = User(-1,email,"",pass);

        usr = ctrl.loginUser(usr!!);
        if(usr!=null) {
            if (usr.ID != -1) {
                val sharedPref = context?.getSharedPreferences("SESSION_DATA", Context.MODE_PRIVATE)
                sharedPref?.edit()?.putString("USR_ID", usr.ID.toString())?.apply();
                sharedPref?.edit()?.putString("USR_NAME", usr.Username)?.apply();
                sharedPref?.edit()?.putString("FLAG_LOGON", "1")?.apply();
                navController.navigate(R.id.action_loginFragment_to_mainMenuFragment);
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}