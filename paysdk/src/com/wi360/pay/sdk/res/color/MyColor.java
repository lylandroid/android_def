package com.wi360.pay.sdk.res.color;

import android.graphics.drawable.ColorDrawable;

public class MyColor {
	// <color name="black_overlay">#66000000</color>
	// <color name="gray_background">#ffececec</color>
	// <color name="white">#FFFFFF</color>
	// <color name="black">#000000</color>
	// <color name="red">#E51A18</color>
	// <color name="line_color">#523E2E</color>
	// <color name="search_tab_nomarl">#363636</color>
	// <color name="search_tab_select">#E82926</color>
	// <color name="menu_item_text_color">#E60200</color>
	// <color name="news_item_has_read_textcolor">#ff0000</color>
	// <color name="news_item_no_read_textcolor">#363636</color>
	// <!-- 拷贝布局文件 -->
	// <color name="background_color">#fff2f2f2</color>
	// <color name="dark_color">#ff808080</color>
	// <color name="my_textcolor_bg">#ffffffff</color>
	// <color name="my_split_top">#ffdfdfdf</color>
	// <color name="my_split_bottom">#66bcbcbc</color>
	// <color name="my_textcolor">#ff391527</color>
	//
	// <!-- 自定义的 -->
	// <color name="selector_pressed_back">#66C0C0C0</color>
	// <color name="selector_def_back">#FFFFFF</color>
	//
	// <!-- 美工给的参数 -->
	//
	// <color name="dialog_back">#BB424242</color>
	// <color name="background_title_bar">#FA365E</color>
	// <color name="color_et_hint">#CACACA</color>
	// <color name="color_tv_left">#333333</color>
	// <color name="color_bt">#EA7914</color>
	public static String color_bt = "#EA7914";

	// <color name="color_desc">#a9a9a9</color>
	// <color name="color_momey">#FF6600</color>
	// <color name="color_momey_success">#009900</color>
	// <color name="gray_c">#cccccc</color>
	// <!-- <color name="half_black">#e0000000</color> -->

	/**
	 * 把16进制颜色值转换成10进制的颜色值
	 * 
	 * @param color16
	 * @return
	 */
	public static ColorDrawable parseColor(String color16) {
		int color10 = 0;
		ColorDrawable colorDrawable = null;
		try {
			color10 = Integer.parseInt(color16, 16);
			colorDrawable = new ColorDrawable(color10);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return colorDrawable;
	}
}
