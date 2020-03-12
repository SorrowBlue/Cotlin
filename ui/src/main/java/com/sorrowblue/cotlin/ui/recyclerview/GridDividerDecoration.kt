package com.sorrowblue.cotlin.ui.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.R as MaterialR


class GridDividerDecoration(context: Context) : RecyclerView.ItemDecoration() {
	companion object {
		const val GRID = DividerItemDecoration.VERTICAL + DividerItemDecoration.HORIZONTAL
	}

	@SuppressLint("PrivateResource")
	private val divider =
		ContextCompat.getDrawable(context, MaterialR.drawable.abc_list_divider_material)!!
	private val insets: Int = divider.intrinsicHeight
	override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
		if (parent.childCount == 0) return
		drawVertical(c, parent)
		drawHorizontal(c, parent)
	}

	override fun getItemOffsets(
		outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
	) = outRect.set(insets, insets, insets, insets)

	private fun drawVertical(c: Canvas, parent: RecyclerView) =
		drawDivider(c, parent) { child, params ->
			val left = child.left - params.leftMargin - insets
			val right = child.right + params.rightMargin + insets
			val top = child.bottom + params.bottomMargin + insets
			val bottom = top + divider.intrinsicHeight
			Rect(left, top, right, bottom)
		}

	private fun drawHorizontal(c: Canvas, parent: RecyclerView) =
		drawDivider(c, parent) { child, params ->
			val left = child.right + params.rightMargin + insets
			val right = left + divider.intrinsicWidth
			val top = child.top - params.topMargin - insets
			val bottom = child.bottom + params.bottomMargin + insets
			Rect(left, top, right, bottom)
		}

	private fun drawDivider(
		canvas: Canvas, parent: RecyclerView,
		calculate: (child: View, params: RecyclerView.LayoutParams) -> Rect
	) {
		0.until(parent.childCount).forEach { i ->
			divider.bounds = parent.getChildAt(i)
				.let { calculate(it, it.layoutParams as RecyclerView.LayoutParams) }
			divider.draw(canvas)
		}
	}
}
