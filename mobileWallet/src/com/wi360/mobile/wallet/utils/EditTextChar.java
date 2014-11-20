package com.wi360.mobile.wallet.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class EditTextChar implements TextWatcher {
	private EditText mPhoneNumberET;
	private static int index1 = 7;
	private static int index2 = 12;

	public EditTextChar(EditText editText) {
		if(editText != null){
			mPhoneNumberET = editText;
			// TODO Auto-generated constructor stub
			mPhoneNumberET.addTextChangedListener(this);
		}
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (s.length() == index1 || s.length() == index2) {
			mPhoneNumberET.setText(s + " ");
			mPhoneNumberET.setSelection(s.length() + 1);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		System.out.println(s);
	}

	@Override
	public void afterTextChanged(Editable s) {
		System.out.println(s);
	}

	/**
	 * 去掉字符串中所有,想去掉的特殊字符
	 * 
	 * @param delimiterStr
	 * @return
	 */
	public static String getEditTextReplace(String delimiterStr) {
		return delimiterStr.replace(" ", "");
	}

	/**
	 * 在指定位置插入特殊字符
	 * 
	 * @return
	 */
	public static String insertIndexChar(String str) {
		StringBuilder builder = new StringBuilder(str);
		builder.insert(index1, " ");
		builder.insert(index2, " ");
		return builder.toString();
	}
}