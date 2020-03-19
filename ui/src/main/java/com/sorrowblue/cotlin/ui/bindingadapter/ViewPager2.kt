package com.sorrowblue.cotlin.ui.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.viewpager2.widget.ViewPager2

@BindingAdapter("currentItem", "smoothScroll", requireAll = false)
fun ViewPager2.setCurrentItemForBinding(item: Int?, smoothScroll: Boolean? = null) {
	item?.let { setCurrentItem(it, smoothScroll ?: false) }
}

@InverseBindingAdapter(attribute = "currentItem")
fun ViewPager2.getCurrentItemForBinding() = currentItem

@BindingAdapter("currentItemAttrChanged")
fun ViewPager2.setOnCurrentItemChangeListener(attrChange: InverseBindingListener) {
	registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
		override fun onPageSelected(position: Int) = attrChange.onChange()
	})
}