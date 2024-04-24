package com.hash.tooltemplate.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hash.tooltemplate.base.BaseFragment
import com.hash.tooltemplate.databinding.FragmentHomeBinding
import com.hash.tooltemplate.ui.activity.MemorActivity
import com.hash.tooltemplate.ui.activity.WhichCarActivity
import com.hash.tooltemplate.ui.activity.WhichCardActivity

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