package com.jpp.myhealthassistant

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jpp.myhealthassistant.databinding.FragmentLanguagesBinding
import com.jpp.myhealthassistant.databinding.FragmentSettingsBinding
import com.jpp.myhealthassistant.model.LanguageViewModel
import com.jspj.shoppingassistant.adapter.CustomAdapter
import com.jspj.shoppingassistant.adapter.LanguageAdapter
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LanguagesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LanguagesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentLanguagesBinding;
    private lateinit var navController: NavController
    private val langs = arrayListOf(LanguageViewModel(R.drawable.sh_flag,"Srpsko-Hrvatski","sh"),
        LanguageViewModel(R.drawable.serbia,"Српски","sr"),
        LanguageViewModel(R.drawable.eng,"English","en"),
        LanguageViewModel(R.drawable.germany,"Deutsch","de"),
        LanguageViewModel(R.drawable.slovenia,"Slovenščina","sl")
    );
    private var selectedindex= langs.indexOfFirst { it.payload==context?.getSharedPreferences("settings", Context.MODE_PRIVATE)?.getString("LANG","en") };

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLanguagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=findNavController();

        UpdateUI();
        binding.btnBack.setOnClickListener {
            navController.navigate(R.id.action_languagesFragment_to_settingsFragment);

        }
        binding.btnAccept.setOnClickListener{
            context?.getSharedPreferences("settings", Context.MODE_PRIVATE)?.edit()?.putString("LANG",langs[selectedindex].payload)?.apply();
            setlocale(langs[selectedindex].payload);
            navController.navigate(R.id.action_languagesFragment_to_settingsFragment);
        }
    }

    fun UpdateUI()
    {
        SetAdapter(langs);
    }
    private fun SetAdapter(data:ArrayList<LanguageViewModel>)
    {
        var recyclerView = binding.rwLangs;
        var linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        var adapter = LanguageAdapter(data)
        adapter.setOnLongClickListener(object: LanguageAdapter.OnLongClickListener{
            override fun onLongClick(position: Int, model: LanguageViewModel) : Boolean
            {
                return true;
            }
        })
        adapter.setOnClickListener(object: LanguageAdapter.OnClickListener{
            override fun onClick(position: Int, model: LanguageViewModel)
            {
                updateCardBackgroundColor(selectedindex,R.color.sys_background);
                selectedindex=position;
                updateCardBackgroundColor(position,R.color.sys_selection);
                setlocale(model.payload);
            }
        })
        recyclerView.adapter=adapter;
    }
    private fun setlocale(locale:String){
        var locale = Locale(locale);
        var resources=activity?.resources;
        var config = resources?.configuration;
        config?.setLocale(locale);
        resources?.updateConfiguration(config,resources?.displayMetrics);
        //(activity as? MainActivity)?.recreate()
    }

    private fun updateCardBackgroundColor(position: Int,color:Int) {
        // Update the background color of the clicked card
        // You can access the card at the specified position and modify its background color
        var recyclerView = binding.rwLangs;
        val cardView = recyclerView.layoutManager?.findViewByPosition(position)?.findViewById<CardView>(R.id.cardview)
        cardView?.setCardBackgroundColor(resources.getColor(color))
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LanguagesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LanguagesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}