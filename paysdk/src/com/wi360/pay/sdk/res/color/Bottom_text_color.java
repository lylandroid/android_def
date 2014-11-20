package com.wi360.pay.sdk.res.color;

import java.nio.channels.Selector;

import android.R;
import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.widget.LinearLayout;

//<selector xmlns:android="http://schemas.android.com/apk/res/android">
//<item android:state_pressed="true" android:color="@color/color_bt"/>
//<item android:state_checked="true" android:color="@color/color_bt"/>
//<item android:color="#333333"/> <!-- not selected -->
//</selector>
public class Bottom_text_color {

	public Selector getSelector(Context context) {
		StateListDrawable selector = new StateListDrawable();
		selector.addState(new int[] { R.attr.state_pressed, R.attr.state_checked },
				MyColor.parseColor(MyColor.color_bt));
		selector.addState(new int[] { R.attr.defaultValue},
				MyColor.parseColor("#333333"));
		
		LinearLayout ll = new LinearLayout(context);
		ll.setBackground(selector);
		return null;
	}
}
