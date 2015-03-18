package com.yfc_lib.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.yfc_lib.util.AppTools;

public class ShowImageView extends ImageView {
	/** 记录是拖拉照片模式还是放大缩小照片模式 */
	private int mode = 0;// 初始状态
	/** 拖拉照片模式 */
	private static final int MODE_DRAG = 1;
	/** 放大缩小照片模式 */
	private static final int MODE_ZOOM = 2;

	/** 用于记录开始时候的坐标位置 */
	private PointF startPoint = new PointF();
	/** 用于记录拖拉图片移动的坐标位置 */
	private Matrix matrix = new Matrix();
	/** 用于记录图片要进行拖拉时候的坐标位置 */
	private Matrix currentMatrix = new Matrix();

	/** 两个手指的开始距离 */
	private float startDis;
	/** 两个手指的中间点 */
	private PointF midPoint;

	// -------------------
	private Bitmap mBitmap;
	private Context context;

	public ShowImageView(Context context) {
		super(context);
		set(context);
	}

	public ShowImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		set(context);
	}

	public ShowImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		set(context);
	}

	private void set(Context context) {
		try {
			this.context = context;
			Matrix matrix = new Matrix();
			int width = AppTools.getScreenWidth((Activity) context);
			int height = AppTools.getScreenHeight((Activity) context);
			int w = mBitmap.getWidth();
			int h = mBitmap.getHeight();
			matrix.set(getImageMatrix());
			matrix.postTranslate((width - w) / 2, (height - h) / 2);
			setImageMatrix(matrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
		mBitmap = bm;
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);
		mBitmap = getBitmapFromDrawable(drawable);
	}

	@Override
	public void setImageResource(int resId) {
		super.setImageResource(resId);
		mBitmap = getBitmapFromDrawable(getDrawable());
	}

	@Override
	public void setImageURI(Uri uri) {
		super.setImageURI(uri);
		mBitmap = getBitmapFromDrawable(getDrawable());
	}

	private Bitmap getBitmapFromDrawable(Drawable drawable) {
		if (drawable == null) {
			return null;
		}

		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		try {
			Bitmap bitmap;

			if (drawable instanceof ColorDrawable) {
				bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
			} else {
				bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
			}

			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);
			return bitmap;
		} catch (OutOfMemoryError e) {
			return null;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		// 手指压下屏幕
		case MotionEvent.ACTION_DOWN:
			mode = MODE_DRAG;
			// 记录ImageView当前的移动位置
			currentMatrix.set(getImageMatrix());
			matrix.set(currentMatrix);
			startPoint.set(event.getX(), event.getY());
			break;
		// 手指在屏幕上移动，改事件会被不断触发
		case MotionEvent.ACTION_MOVE:
			// 拖拉图片
			if (mode == MODE_DRAG) {
				float dx = event.getX() - startPoint.x; // 得到x轴的移动距离
				float dy = event.getY() - startPoint.y; // 得到x轴的移动距离
				// 在没有移动之前的位置上进行移动
				matrix.set(currentMatrix);
				matrix.postTranslate(dx, dy);
			}
			// 放大缩小图片
			else if (mode == MODE_ZOOM) {
				float endDis = distance(event);// 结束距离
				if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10
					float scale = endDis / startDis;// 得到缩放倍数
					matrix.set(currentMatrix);
					matrix.postScale(scale, scale, midPoint.x, midPoint.y);
				}
			}
			break;
		// 手指离开屏幕
		case MotionEvent.ACTION_UP:
			// 当触点离开屏幕，但是屏幕上还有触点(手指)
		case MotionEvent.ACTION_POINTER_UP:
			mode = 0;
			break;
		// 当屏幕上已经有触点(手指)，再有一个触点压下屏幕
		case MotionEvent.ACTION_POINTER_DOWN:
			mode = MODE_ZOOM;
			/** 计算两个手指间的距离 */
			startDis = distance(event);
			/** 计算两个手指间的中间点 */
			if (startDis > 10f) { // 两个手指并拢在一起的时候像素大于10
				midPoint = mid(event);
				// 记录当前ImageView的缩放倍数
				currentMatrix.set(getImageMatrix());
			}
			break;
		}
		setImageMatrix(matrix);
		return true;
	}

	/** 计算两个手指间的距离 */
	private float distance(MotionEvent event) {
		float dx = event.getX(1) - event.getX(0);
		float dy = event.getY(1) - event.getY(0);
		/** 使用勾股定理返回两点之间的距离 */
		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	/** 计算两个手指间的中间点 */
	private PointF mid(MotionEvent event) {
		float midX = (event.getX(1) + event.getX(0)) / 2;
		float midY = (event.getY(1) + event.getY(0)) / 2;
		return new PointF(midX, midY);
	}
}
