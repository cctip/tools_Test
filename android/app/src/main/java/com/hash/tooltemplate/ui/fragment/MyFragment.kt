package com.hash.tooltemplate.ui.fragment


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hash.tooltemplate.R
import com.hash.tooltemplate.base.BaseFragment
import com.hash.tooltemplate.databinding.FragmentMyBinding
import com.hash.tooltemplate.ui.adapter.MyItem
import com.hash.tooltemplate.ui.adapter.MyRecAdapter

class MyFragment : BaseFragment<FragmentMyBinding>() {
    private lateinit var myRecAdapter: MyRecAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyBinding = FragmentMyBinding.inflate(inflater, container, false)

    override fun initData() {
        val items = listOf(
            MyItem(R.mipmap.chichen, "Chichen itza", "MEXICO",R.mipmap.myintent),
            MyItem(R.mipmap.petra, "Petra", "JORDAN",R.mipmap.mybackintent),
            MyItem(R.mipmap.taj, "Taj Mahal", "INDIA",R.mipmap.mybackintent),
            MyItem(R.mipmap.machu, "Machu Picchu", "PERU",R.mipmap.mybackintent),
            MyItem(R.mipmap.greatwall, "Great Wall", "CHINA",R.mipmap.mybackintent),
            MyItem(R.mipmap.colosseum, "Colosseum", "ITALY",R.mipmap.mybackintent),
            MyItem(R.mipmap.christ, "Christ\nthe Redeemer", "BRAZIL",R.mipmap.mybackintent),
        )
        // 初始化适配器
        myRecAdapter = MyRecAdapter(items)
        binding.myrec.layoutManager = GridLayoutManager(context,2)
        binding.myrec.adapter = myRecAdapter
    }
}
