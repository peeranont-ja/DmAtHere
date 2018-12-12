package com.kku.pharm.project.dmathere.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.kku.pharm.project.dmathere.R
import kotlinx.android.synthetic.main.fragment_medicine_list.*
import android.widget.TextView
import android.app.Activity
import com.kku.pharm.project.dmathere.data.Constant


class MedicineListFragment : Fragment() {
    private var imgArray = intArrayOf(
            R.drawable.img_med_1,
            R.drawable.img_med_3,
            R.drawable.img_med_9,
            R.drawable.img_med_2,
            R.drawable.img_med_10,
            R.drawable.img_med_7,
            R.drawable.img_med_6,
            R.drawable.img_med_4,
            R.drawable.img_med_8,
            R.drawable.img_med_5)

    companion object {
        fun newInstance(): MedicineListFragment {
            val fragment = MedicineListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_medicine_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        gv_android_example.adapter = ImageAdapterGridView(context!!, imgArray, Constant.medicineList)

        gv_android_example.onItemClickListener = AdapterView.OnItemClickListener { parent,
                                                                                   v,
                                                                                   position,
                                                                                   id ->
            Toast.makeText(context, "Grid Item " + (position + 1) + " Selected", Toast.LENGTH_LONG).show()
        }
    }

    class ImageAdapterGridView(private val context: Context,
                               private val imgArray: IntArray,
                               private val medicineList: Array<String>) : BaseAdapter() {

        override fun getCount(): Int {
            return imgArray.size
        }

        override fun getItem(position: Int): Any? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
//            val imageView: ImageView
            var row = convertView
            var holder = ViewHolder()

            if (row == null) {
//                imageView = ImageView(context)
//                val lp = AbsListView.LayoutParams(510, 510)
//                imageView.layoutParams = lp
//                imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
//                imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
////                imageView.setPadding(16, 16, 16, 16)
//                imageView.elevation = 8f
                val inflater = (context as Activity).layoutInflater
                row = inflater.inflate(R.layout.grid_item_layout, parent, false)
                holder.imageTitle = row.findViewById(R.id.text) as TextView
                holder.image = row.findViewById(R.id.image) as ImageView
                row.tag = holder
            } else {
//                imageView = (convertView as ImageView?)!!
                holder = row.tag as ViewHolder
            }
            Glide.with(context)
                    .load(imgArray[position])
                    .into(holder.image!!)
            holder.imageTitle!!.text = medicineList[position]
            return row
        }

        class ViewHolder {
            var imageTitle: TextView? = null
            var image: ImageView? = null
        }
    }
}