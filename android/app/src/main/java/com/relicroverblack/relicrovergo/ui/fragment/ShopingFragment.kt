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
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.relicroverblack.relicrovergo.R
import com.relicroverblack.relicrovergo.base.BaseFragment
import com.relicroverblack.relicrovergo.databinding.FragmentShopingBinding
import com.relicroverblack.relicrovergo.ui.dialog.WinDialog
import com.relicroverblack.relicrovergo.widgets.LuckyView

class ShopingFragment : BaseFragment<FragmentShopingBinding>() {
    private lateinit var adapter: HorizontalAdapter
    private var shoptopitems = mutableListOf<ShopTop>()
    var currentIndex = 0 // 用于追踪当前替换的位置
    private lateinit var grayBackground: View

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentShopingBinding = FragmentShopingBinding.inflate(inflater, container, false)

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
        shoptopitems = mutableListOf(
            ShopTop(R.mipmap.iswaterland, R.mipmap.memoritembg),
            ShopTop(R.mipmap.memorcyan, R.mipmap.memoritembg),
            ShopTop(R.mipmap.smallbluecar, R.mipmap.memoritembg),
            ShopTop(R.mipmap.memoryelow, R.mipmap.memoritembg),
            ShopTop(R.mipmap.bussmall, R.mipmap.memoritembg),
            ShopTop(R.mipmap.greenisland, R.mipmap.memoritembg),
            ShopTop(R.mipmap.memorblue, R.mipmap.memoritembg),
            ShopTop(R.mipmap.island, R.mipmap.memoritembg),
            ShopTop(R.mipmap.fly, R.mipmap.memoritembg),
            ShopTop(R.mipmap.memorwhite, R.mipmap.memoritembg),

        )
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        adapter = HorizontalAdapter(shoptopitems, sharedPreferences, ::updateLuckyViewImages, binding.luckyView) // 传递updateLuckyViewImages方法
        binding.shopprec.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.shopprec.layoutManager = layoutManager
        binding.spin.setOnClickListener {
//            startScrollAnimation(binding.shoppingrec1)
            startLuckyView()
        }
        binding.luckyView.onSpinEnd = { x,y,z->
            Log.d("Result===", "$x,$y,$z")
            if (x == y && x == z) {

            }
        }

    }
    private fun updateLuckyViewImages(newImageRes: Int) {
        binding.luckyView.updateBitmapAt(currentIndex, newImageRes)
        currentIndex = (currentIndex + 1) % binding.luckyView.customBitmaps.size // 更新索引
    }
    private fun startLuckyView() {
        // 在按钮点击时开始 LuckyView 的旋转动画
        binding.luckyView.startPin()

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

class HorizontalAdapter(
    private val items: MutableList<ShopTop>,
    private val sharedPreferences: SharedPreferences,
    private val updateLuckyViewImages: (Int) -> Unit,
    private val luckyView: LuckyView // 添加 LuckyView 的实例
) : RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(item: ShopTop)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shoptop, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.imageView1.setImageResource(item.imageRes1)
        holder.imageView2.setImageResource(item.imageRes2)
        holder.displayCountTextView.text = item.clickCount.toString()

        holder.itemView.setOnClickListener {
            // 检查 LuckyView 中的图片是否都已经被替换
            if (!luckyView.areAllImagesChanged()) {
                if (item.clickCount > 0) {
                    item.clickCount--
                    holder.displayCountTextView.text = item.clickCount.toString()
                    sharedPreferences.edit().putInt("image_${item.imageRes1}", item.clickCount).apply()
                    // 调用传递进来的 updateLuckyViewImages 方法
                    updateLuckyViewImages(item.imageRes1)
                }
            }
        }
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

