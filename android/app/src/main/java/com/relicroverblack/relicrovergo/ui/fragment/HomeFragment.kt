package com.relicroverblack.relicrovergo.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.relicroverblack.relicrovergo.base.BaseFragment
import com.relicroverblack.relicrovergo.databinding.FragmentHomeBinding
import com.relicroverblack.relicrovergo.ui.activity.MemorActivity
import com.relicroverblack.relicrovergo.ui.activity.WhichCarActivity
import com.relicroverblack.relicrovergo.ui.activity.WhichCardActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)


    override fun initData() {
        binding.memor.setOnClickListener {
            val intent = Intent(context, MemorActivity::class.java)
            startActivity(intent)
        }
        binding.whichcard.setOnClickListener {
            val intent = Intent(context, WhichCardActivity::class.java)
            startActivity(intent)
        }
        binding.whichcar.setOnClickListener {
            val intent = Intent(context, WhichCarActivity::class.java)
            startActivity(intent)

        }
    }

}