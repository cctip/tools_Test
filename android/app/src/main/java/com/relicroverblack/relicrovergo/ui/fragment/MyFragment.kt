package com.relicroverblack.relicrovergo.ui.fragment


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.relicroverblack.relicrovergo.R
import com.relicroverblack.relicrovergo.base.BaseFragment
import com.relicroverblack.relicrovergo.databinding.FragmentMyBinding
import com.relicroverblack.relicrovergo.ui.adapter.MyItem
import com.relicroverblack.relicrovergo.ui.adapter.MyRecAdapter
import com.relicroverblack.relicrovergo.widgets.LuckyView

class MyFragment : BaseFragment<FragmentMyBinding>() {
    private lateinit var myRecAdapter: MyRecAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyBinding = FragmentMyBinding.inflate(inflater, container, false)

    override fun initData() {

        val sharedPreferences = context?.getSharedPreferences(LuckyView.PREFS_NAME, Context.MODE_PRIVATE)
        val shardCount = sharedPreferences?.getInt(LuckyView.SHARD_KEY, 0)
        val items = listOf(
            MyItem(R.mipmap.chichen, "Chichen itza", "MEXICO", R.mipmap.myintent),
            MyItem(R.mipmap.petra, "Petra", "JORDAN", R.mipmap.mybackintent),
            MyItem(R.mipmap.taj, "Taj Mahal", "INDIA", R.mipmap.mybackintent),
            MyItem(R.mipmap.machu, "Machu Picchu", "PERU", R.mipmap.mybackintent),
            MyItem(R.mipmap.greatwall, "Great Wall", "CHINA", R.mipmap.mybackintent),
            MyItem(R.mipmap.colosseum, "Colosseum", "ITALY", R.mipmap.mybackintent),
            MyItem(R.mipmap.christ, "Christ\nthe Redeemer", "BRAZIL", R.mipmap.mybackintent),
        )
// 初始化适配器
        myRecAdapter = shardCount?.let { MyRecAdapter(items, it) }!!
        binding.myrec.layoutManager = GridLayoutManager(context, 2)
        binding.myrec.adapter = myRecAdapter
    }
}
