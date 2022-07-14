package com.example.weather.fragment
import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.example.weather.adapters.VpAdapter
import com.example.weather.databinding.FragmentMainBinding
import com.example.weather.isPermissionGranted
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {
    private val flist = listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentMainBinding

    private val tList = listOf(
        "HOURS",
        "DAYS"
//        R.string.Hours,
//        R.string.Days
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        init()
    }

    private fun init() = with(binding){
        val adapter = VpAdapter(activity as FragmentActivity, flist)
        vp.adapter = adapter
        TabLayoutMediator(tabLayout, vp){
                tab, pos -> tab.text = tList[pos].toString()
        }.attach()
    }

    private fun permissionListener() {
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            Toast.makeText(activity, "permission is $it", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION))
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}