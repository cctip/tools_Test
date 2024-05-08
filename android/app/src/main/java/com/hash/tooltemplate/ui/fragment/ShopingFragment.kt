package com.hash.tooltemplate.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hash.tooltemplate.R
import com.hash.tooltemplate.base.BaseFragment
import com.hash.tooltemplate.databinding.FragmentShopingBinding

class ShopingFragment : BaseFragment<FragmentShopingBinding>() {
    private lateinit var adapter: HorizontalAdapter
    private var shoptopitems = mutableListOf<ShopTop>()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentShopingBinding = FragmentShopingBinding.inflate(inflater, container, false)
    private val itemList1 = mutableListOf(
        ItemModel(R.mipmap.cloud),
        ItemModel(R.mipmap.cloud),
        ItemModel(R.mipmap.cloud),
        ItemModel(R.mipmap.yellowcar),
        ItemModel(R.mipmap.aeroplane),
        ItemModel(R.mipmap.orangecar)
    )
    private val itemList2 = mutableListOf(
        ItemModel(R.mipmap.cloud),
        ItemModel(R.mipmap.cloud),
        ItemModel(R.mipmap.cloud),
        ItemModel(R.mipmap.yellowcar),
        ItemModel(R.mipmap.aeroplane),
        ItemModel(R.mipmap.orangecar)
    )

    private val itemList3 = mutableListOf(
        ItemModel(R.mipmap.cloud),
        ItemModel(R.mipmap.cloud),
        ItemModel(R.mipmap.cloud),
        ItemModel(R.mipmap.yellowcar),
        ItemModel(R.mipmap.aeroplane),
        ItemModel(R.mipmap.orangecar)
    )
    private val recyclerViews = mutableListOf<RecyclerView>()
    private lateinit var recyclerViewAdapter1: GameAdapter
    private lateinit var recyclerViewAdapter2: GameAdapter
    private lateinit var recyclerViewAdapter3: GameAdapter

    override fun onResume() {
        super.onResume()
        updateItemsWithDisplayCounts()
    }
    private fun updateItemsWithDisplayCounts() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        for (item in shoptopitems) {
            val displayCount = sharedPreferences.getInt("image_${item.imageRes1}", 0)
            item.clickCount = displayCount
        }
        adapter.notifyDataSetChanged()
    }
    override fun initData() {
        recyclerViewAdapter1 = GameAdapter(itemList1)
        recyclerViewAdapter2 = GameAdapter(itemList2)
        recyclerViewAdapter3 = GameAdapter(itemList3)
        binding.shoppingrec1.layoutManager = LinearLayoutManager(context)
        binding.shoppingrec2.layoutManager = LinearLayoutManager(context)
        binding.shoppingrec3.layoutManager = LinearLayoutManager(context)
        binding.shoppingrec1.adapter = recyclerViewAdapter1
        binding.shoppingrec2.adapter = recyclerViewAdapter2
        binding.shoppingrec3.adapter = recyclerViewAdapter3
        // 禁止手动滑动
        binding.shoppingrec1.setOnTouchListener { _, _ -> true }
        binding.shoppingrec2.setOnTouchListener { _, _ -> true }
        binding.shoppingrec3.setOnTouchListener { _, _ -> true }
        recyclerViews.addAll(listOf(binding.shoppingrec1, binding.shoppingrec2, binding.shoppingrec3))
        shoptopitems = mutableListOf(
            ShopTop(R.mipmap.bluecar, R.mipmap.memoritembg),
            ShopTop(R.mipmap.memorcyan, R.mipmap.memoritembg),
            ShopTop(R.mipmap.memoryelow, R.mipmap.memoritembg),
            ShopTop(R.mipmap.aeroplane, R.mipmap.memoritembg),
            ShopTop(R.mipmap.memorblue, R.mipmap.memoritembg),
            ShopTop(R.mipmap.island, R.mipmap.memoritembg),
            ShopTop(R.mipmap.memorwhite, R.mipmap.memoritembg),
        )
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        adapter = HorizontalAdapter(shoptopitems, sharedPreferences)
        binding.shopprec.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.shopprec.layoutManager = layoutManager
//        adapter.updateDisplayCounts(PreferenceManager.getDefaultSharedPreferences(requireContext()))

    }
//    private fun updateListWithImage(imageRes1: Int, imageRes2: Int, context: Context) {
//        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//        val displayedImageId = sharedPreferences.getInt("displayed_image", 0)
//
//        // 更新适配器中的数据
//        adapter.addItem(ShopTop(imageRes1, imageRes2, sharedPreferences.getInt("image_$displayedImageId", 0)))
//         adapter.updateItem(adapter.itemCount - 1, sharedPreferences.getInt("image_$displayedImageId", 0))
//    }
}
data class ShopTop(val imageRes1: Int, val imageRes2: Int, var clickCount: Int = 0)
class HorizontalAdapter(private val items: MutableList<ShopTop>, private val sharedPreferences: SharedPreferences) : RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {

//    fun updateDisplayCounts(sharedPreferences: SharedPreferences) {
//        for (item in items) {
//            val displayCount = sharedPreferences.getInt("displayed_image", 0)
//            if(displayCount==item.imageRes1){
//                item.clickCount+=1
//                Log.i("clickCounttoString",item.clickCount.toString())
//            }
//            Log.i("ClickCountUpdate", "Item: ${item.imageRes1}, Click Count: $displayCount")
//        }
//        notifyDataSetChanged()
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shoptop, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.imageView1.setImageResource(item.imageRes1)
        holder.imageView2.setImageResource(item.imageRes2)
            val displayCount = sharedPreferences.getInt("displayed_image", 0)
            if(displayCount==item.imageRes1){
                item.clickCount+=1
                Log.i("clickCounttoString",item.clickCount.toString())
            }
            Log.i("ClickCountUpdate", "Item: ${item.imageRes1}, Click Count: $displayCount")
        Log.i("displayCountTextView", item.clickCount.toString())
        holder.displayCountTextView.text = item.clickCount.toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView1: ImageView = itemView.findViewById(R.id.item_shoppimgtopimg)
        val imageView2: ImageView = itemView.findViewById(R.id.item_shoppimgtopimgbg)
        val displayCountTextView: TextView = itemView.findViewById(R.id.shoppingtoprectext)
    }
}

data class ItemModel(val imageResId: Int)
class GameAdapter (private val items: List<ItemModel>) :
    RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shoppingturn, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val imageView: ImageView = holder.itemView.findViewById(R.id.itemimg)
        imageView.setImageResource(item.imageResId)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}