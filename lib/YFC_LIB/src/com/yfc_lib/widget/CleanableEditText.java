package com.yfc_lib.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

/**
 * 在焦点变化时和输入内容发生变化时均要判断是否显示右边clean图标
 */
public class CleanableEditText extends EditText {
	private Drawable mRightDrawable;
	private boolean isHasFocus;

	// private int maxSize;
	// private Handler handler;
	//
	// public void setMaxSize(int maxSize, Handler handler) {
	// this.maxSize = maxSize;
	// this.handler = handler;
	// }
	private boolean flag;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public CleanableEditText(Context context) {
		super(context);
		init();
	}

	public CleanableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CleanableEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		// getCompoundDrawables:
		// Returns drawables for the left, top, right, and bottom borders.
		Drawable[] drawables = this.getCompoundDrawables();

		// 取得right位置的Drawable
		// 即我们在布局文件中设置的android:drawableRight
		mRightDrawable = drawables[2];

		// 设置焦点变化的监听
		this.setOnFocusChangeListener(new FocusChangeListenerImpl());
		// 设置EditText文字变化的监听
		this.addTextChangedListener(new TextWatcherImpl());
		// 初始化时让右边clean图标不可见
		setClearDrawableVisible(false);
	}

	/**
	 * 当手指抬起的位置在clean的图标的区域 我们将此视为进行清除操作 getWidth():得到控件的宽度
	 * event.getX():抬起时的坐标(改坐标是相对于控件本身而言的)
	 * getTotalPaddingRight():clean的图标左边缘至控件右边缘的距离
	 * getPaddingRight():clean的图标右边缘至控件右边缘的距离 于是: getWidth() -
	 * getTotalPaddingRight()表示: 控件左边到clean的图标左边缘的区域 getWidth() -
	 * getPaddingRight()表示: 控件左边到clean的图标右边缘的区域 所以这两者之间的区域刚好是clean的图标的区域
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			boolean isClean = (event.getX() > (getWidth() - getTotalPaddingRight()))
					&& (event.getX() < (getWidth() - getPaddingRight()));
			if (isClean) {
				setText("");
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	private class FocusChangeListenerImpl implements OnFocusChangeListener {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			isHasFocus = hasFocus;
			if (isHasFocus) {
				boolean isVisible = getText().toString().length() >= 1;
				setClearDrawableVisible(isVisible);
			} else {
				setClearDrawableVisible(false);
			}
		}

	}

	// 当输入结束后判断是否显示右边clean的图标
	private class TextWatcherImpl implements TextWatcher {
		@Override
		public void afterTextChanged(Editable s) {
			int size = getText().toString().length();
			boolean isVisible = size >= 1;
			setClearDrawableVisible(isVisible);

			// if (handler != null) {
			// if (maxSize < size) {
			// handler.sendEmptyMessage(0);
			// } else {
			// handler.sendEmptyMessage(1);
			// }
			// }

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

	}

	// 隐藏或者显示右边clean的图标
	public void setClearDrawableVisible(boolean isVisible) {
		Drawable rightDrawable = null;
		if (!flag) {
			if (isVisible) {
				rightDrawable = mRightDrawable;
			}
		}
		// 使用代码设置该控件left, top, right, and bottom处的图标
		setCompoundDrawables(getCompoundDrawables()[0],
				getCompoundDrawables()[1], rightDrawable,
				getCompoundDrawables()[3]);
	}

	// 显示一个动画,以提示用户输入
	public void setShakeAnimation() {
		this.setAnimation(shakeAnimation(5));
	}

	// CycleTimes动画重复的次数
	public Animation shakeAnimation(int CycleTimes) {
		Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
		translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
		translateAnimation.setDuration(1000);
		return translateAnimation;
	}

}