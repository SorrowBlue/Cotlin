package com.sorrowblue.cotlin.ui.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


@BindingAdapter("isRefreshing")
fun SwipeRefreshLayout.isRefreshingForBinding(isRefreshing: Boolean?) {
	this.isRefreshing = isRefreshing ?: false
}

@BindingAdapter("onRefresh")
fun SwipeRefreshLayout.setOnRefreshListenerForBinding(listener: SwipeRefreshLayout.OnRefreshListener?) =
	setOnRefreshListener(listener)
