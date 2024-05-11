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
    )

    private val itemList2 = mutableListOf(
        ItemModel(R.mipmap.cloud),
        ItemModel(R.mipmap.cloud),
        ItemModel(R.mipmap.cloud),
    )

    private val itemList3 = mutableListOf(
        ItemModel(R.mipmap.cloud),
        ItemModel(R.mipmap.cloud),
        ItemModel(R.mipmap.cloud),
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
        adapter = HorizontalAdapter(shoptopitems, sharedPreferences, itemList1, itemList2, itemList3, recyclerViewAdapter1,recyclerViewAdapter2,recyclerViewAdapter3,this)
        binding.shopprec.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.shopprec.layoutManager = layoutManager
        if (itemList3.all { it.isReplaced }) {
            binding.blackspin.visibility = View.GONE
        }
    }
    fun someMethod(){
        binding.blackspin.visibility = View.GONE
    }

}
data class ShopTop(
    val imageRes1: Int,
    val imageRes2: Int,
    var clickCount: Int = 0,
) {
    fun decreaseClickCountAndChangeImage(itemList: MutableList<ItemModel>, newImageRes: Int) {
        if (clickCount > 0 && itemList.isNotEmpty()) {
            val firstNotReplacedIndex = itemList.indexOfFirst { !it.isReplaced }
            if (firstNotReplacedIndex != -1) {
                clickCount--
                itemList[firstNotReplacedIndex] = ItemModel(newImageRes, true)
            }
        }
    }
}
class HorizontalAdapter(private val items: MutableList<ShopTop>,
                        private val sharedPreferences: SharedPreferences,
                        private val itemList1: MutableList<ItemModel>,
                        private val itemList2: MutableList<ItemModel>,
                        private val itemList3: MutableList<ItemModel>,
                        private val itemClickListener: ItemClickListener,
                        private val itemClickListener2: ItemClickListener,
                        private val itemClickListener3: ItemClickListener,
                        private val fragment: ShopingFragment
) : RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(item: ShopTop)
    }

    fun updateItemList1WithClickedItem(item: ShopTop) {
        val clickedItemIndex = items.indexOf(item)
        if (clickedItemIndex != -1 && items[clickedItemIndex].clickCount > 0) {
            val clickedItem = items[clickedItemIndex]
            clickedItem.decreaseClickCountAndChangeImage(itemList1, clickedItem.imageRes1)
            if (itemList1.all { it.isReplaced }) {
                updateItemList2WithClickedItem(item)
            } else {
                notifyDataSetChanged()
            }
        }
    }

    fun updateItemList2WithClickedItem(item: ShopTop) {
        val clickedItemIndex = items.indexOf(item)
        if (clickedItemIndex != -1 && items[clickedItemIndex].clickCount > 0) {
            val clickedItem = items[clickedItemIndex]
            clickedItem.decreaseClickCountAndChangeImage(itemList2, clickedItem.imageRes1)
            if (itemList2.all { it.isReplaced }) {
                updateItemList3WithClickedItem(item)
            } else {
                notifyDataSetChanged()
            }
        }
    }
    fun updateItemList3WithClickedItem(item: ShopTop) {
        val clickedItemIndex = items.indexOf(item)
        if (clickedItemIndex != -1 && items[clickedItemIndex].clickCount > 0) {
            val clickedItem = items[clickedItemIndex]
            clickedItem.decreaseClickCountAndChangeImage(itemList3, clickedItem.imageRes1)
            if (itemList3.all { it.isReplaced }) {
                fragment.someMethod()
            } else {
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shoptop, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.imageView1.setImageResource(item.imageRes1)
        holder.imageView2.setImageResource(item.imageRes2)

        val currentDisplayCount = sharedPreferences.getInt("image_${item.imageRes1}", 0)

        // 更新点击次数逻辑
        if (currentDisplayCount == item.imageRes1) {
            item.clickCount += 1
            Log.i("clickCounttoString", item.clickCount.toString())
        }
        // 将更新后的点击次数保存到 SharedPreferences
        sharedPreferences.edit().putInt("image_${item.imageRes1}", item.clickCount).apply()
        // 设置展示次数到 TextView
        holder.displayCountTextView.text = item.clickCount.toString()
        holder.itemView.setOnClickListener {
            updateItemList1WithClickedItem(item)
            itemClickListener.onItemClick(item)
            itemClickListener2.onItemClick(item)
            itemClickListener3.onItemClick(item)
        }
        // 记录日志
        Log.i("ClickCountUpdate", "Item: ${item.imageRes1}, Click Count: ${item.clickCount}")
        Log.i("displayCountTextView", item.clickCount.toString())
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

data class ItemModel(val imageResId: Int, var isReplaced: Boolean = false)
class GameAdapter(private val items: MutableList<ItemModel>) : RecyclerView.Adapter<GameAdapter.ViewHolder>(), HorizontalAdapter.ItemClickListener {
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


    override fun onItemClick(item: ShopTop) {
        notifyDataSetChanged()
    }
}