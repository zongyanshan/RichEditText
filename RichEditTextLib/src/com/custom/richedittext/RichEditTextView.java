package com.custom.richedittext;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.EditText;

public class RichEditTextView extends EditText {

	private int changedTextColor = Color.parseColor("#ff0000");
	
	private boolean autochanged = true;
	
	private List<SpanInfo> spanInfos  = new ArrayList<SpanInfo>();;
	
	private List<SpanInfo> needDelList = new ArrayList<SpanInfo>();
	
	private String text="";
	
	
	public RichEditTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context,attrs);
	}

	private void initView(Context context, AttributeSet attrs){
		text = getText().toString();
		addTextChangedListener();
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RichEditTextView);
		changedTextColor = ta.getColor(R.styleable.RichEditTextView_changedTextCcolor, getResources().getColor(android.R.color.holo_red_light));
		ta.recycle();
	}
	
	
	private void addTextChangedListener(){
		addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (!autochanged) {
					int changeCount = Math.abs(text.length()-s.toString().length());
					boolean isAdd = true;
					if (!TextUtils.isEmpty(text) && text.length()>s.length()) {
						isAdd =false;
					}
					if (isAdd) {
						spanInfos.add(new SpanInfo(start,changeCount,Spanned.SPAN_INCLUSIVE_INCLUSIVE,true));
					}
					autochanged = true;
					SpannableString sb = new SpannableString(getText().toString());
					for(int i=0;i<spanInfos.size();i++){
						SpanInfo spanInfo = spanInfos.get(i);
						if (!spanInfo.isCurrent) {
							if (isAdd) {//如果是文本输入
								if (start <= spanInfo.start) {//文本输入的位置在这个span开始之前，移位
									spanInfo.start = spanInfo.start  + changeCount;
								}else if (start<= spanInfo.start+spanInfo.count) {//在这个span中添加
									spanInfo.count = spanInfo.count + changeCount;
									spanInfos.remove(spanInfos.size()-1);
								}
							}else {//文本删除
								if(start + changeCount <= spanInfo.start){
									spanInfo.start = spanInfo.start -changeCount;
								}else if(start + changeCount <= spanInfo.start + spanInfo.count) {
									if (start < spanInfo.start) {
										spanInfo.count = spanInfo.count - (changeCount-(spanInfo.start - start));
										spanInfo.start = start;
									}else{
										spanInfo.count = spanInfo.count - changeCount;
									}
								}else if(start<= spanInfo.start){
									needDelList.add(spanInfo);
									continue;
								}
							}
						}
						spanInfo.isCurrent= false;
						sb.setSpan(new ForegroundColorSpan(changedTextColor), spanInfo.start, spanInfo.start + spanInfo.count, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
					}
					for(SpanInfo span:needDelList){
						spanInfos.remove(span);
					}
					setText(sb);
					setSelection(start+count);
				}else {
					autochanged = false;
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				text =s.toString();
			}
		});
	}

}
