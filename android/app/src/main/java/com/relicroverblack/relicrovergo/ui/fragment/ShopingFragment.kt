package com.relicroverblack.relicrovergo.ui.fragment

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.relicroverblack.relicrovergo.R
import com.relicroverblack.relicrovergo.base.BaseFragment
import com.relicroverblack.relicrovergo.databinding.FragmentShopingBinding

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
    @SuppressLint("ServiceCast")
    override fun initData() {
        recyclerViewAdapter1 = GameAdapter(itemList1)
        recyclerViewAdapter2 = GameAdapter(itemList2)
        recyclerViewAdapter3 = GameAdapter(itemList3)
//        binding.shoppingrec1.layoutManager = LinearLayoutManager(context)
//        binding.shoppingrec2.layoutManager = LinearLayoutManager(context)
//        binding.shoppingrec3.layoutManager = LinearLayoutManager(context)
//        binding.shoppingrec1.adapter = recyclerViewAdapter1
//        binding.shoppingrec2.adapter = recyclerViewAdapter2
//        binding.shoppingrec3.adapter = recyclerViewAdapter3
//        // 禁止手动滑动
//        binding.shoppingrec1.setOnTouchListener { _, _ -> true }
//        binding.shoppingrec2.setOnTouchListener { _, _ -> true }
//        binding.shoppingrec3.setOnTouchListener { _, _ -> true }
//        recyclerViews.addAll(listOf(binding.shoppingrec1, binding.shoppingrec2, binding.shoppingrec3))
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
        binding.spin.setOnClickListener {
//            binding.spin.visibility = View.GONE
            binding.blackspin.visibility=View.VISIBLE
//            startScrollAnimation(binding.shoppingrec1)
            startLuckyView()
        }

    }
    private fun startLuckyView() {
        // 在按钮点击时开始 LuckyView 的旋转动画
        binding.luckyView.startPin()
        binding.luckyView.onSpinEnd = { index1, index2, index3 ->

        }
    }


    fun someMethod(){
        binding.blackspin.visibility = View.GONE
        binding.spin.visibility = View.VISIBLE
    }

}
data class ShopTop(
    val imageRes1: Int,
    val imageRes2: Int,
    var clickCount: Int = 0,
) {
    fun decreaseClickCountAndChangeImage(itemList: MutableList<ItemModel>, newImageRes: Int, sharedPreferences: SharedPreferences) {
        if (clickCount > 0 && itemList.isNotEmpty()) {
            val firstNotReplacedIndex = itemList.indexOfFirst { !it.isReplaced }
            if (firstNotReplacedIndex != -1) {
                clickCount--
                itemList[firstNotReplacedIndex] = ItemModel(newImageRes, true)

                // 更新SharedPreferences
                sharedPreferences.edit().putInt("image_${imageRes1}", clickCount).apply()
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
            clickedItem.decreaseClickCountAndChangeImage(itemList1, clickedItem.imageRes1, sharedPreferences)
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
            clickedItem.decreaseClickCountAndChangeImage(itemList2, clickedItem.imageRes1, sharedPreferences)
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
            clickedItem.decreaseClickCountAndChangeImage(itemList3, clickedItem.imageRes1, sharedPreferences)
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

        // 获取当前图片的显示次数
        val currentDisplayCount = sharedPreferences.getInt("image_${item.imageRes1}", 0)

        // 设置展示次数到 TextView
        holder.displayCountTextView.text = currentDisplayCount.toString()

        holder.itemView.setOnClickListener {
            // Item点击逻辑
            updateItemList1WithClickedItem(item)
            itemClickListener.onItemClick(item)
            itemClickListener2.onItemClick(item)
            itemClickListener3.onItemClick(item)
        }

        // 记录日志
        Log.i("displayCountUpdate", "Item: ${item.imageRes1}, Display Count: $currentDisplayCount")
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
        val item = items[position % 3]
        val imageView: ImageView = holder.itemView.findViewById(R.id.itemimg)

        // 设置图片资源
        imageView.setImageResource(item.imageResId)

    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

    override fun onItemClick(item: ShopTop) {
        notifyDataSetChanged()
    }
}
