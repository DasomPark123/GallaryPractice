package com.example.gallarypractice.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import org.jetbrains.anko.padding

class ImageAdapter(val context: Context, uriArr: ArrayList<String>) : BaseAdapter() {
    private var items = ArrayList<String>()

    init {
        items = uriArr
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView = ImageView(context)
        val display = context.resources.displayMetrics
        /* ImageView와 image 사이의 간격 */
        imageView.padding = 2
        /* 넓이 높이 같게 해서 정가운데가 보이도록 함 */
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        /* 가로, 세로 높이 결정 */
        imageView.layoutParams = LinearLayout.LayoutParams(display.widthPixels/3, display.widthPixels/3)
        /* Image를 imageview로 로드함 */
        Glide.with(context).load(items[position]).into(imageView)
        return imageView
    }

    override fun getItem(position: Int): Long {
        return position.toLong()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}