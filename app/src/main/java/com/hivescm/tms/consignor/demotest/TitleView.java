package com.hivescm.tms.consignor.demotest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayong on 2018/7/20.
 */

public class TitleView extends LinearLayout {


    private LinearLayout itemView;
    private Scroller scroller;
    private Bitmap iconArrow;//箭头
    private Bitmap rectArrow;
    private int currentX, currentY;
    private Rect leftRect, rightRect;
    List<View> itemViews = new ArrayList<>();
    private int currentPostion;

    public TitleView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setWillNotDraw(false);
        setOrientation(VERTICAL);
        itemView = getItemView(context);
        addView(itemView, new LinearLayout.LayoutParams(-1, 100, 1f));
        itemView.setBackgroundResource(R.drawable.blk_menubtn_bg);
        iconArrow = BitmapFactory.decodeResource(getResources(), R.drawable.blk_menubtn_arr);
        rectArrow = BitmapFactory.decodeResource(getResources(), R.drawable.blk_menubtn_shadow);

        scroller = new Scroller(context, new DecelerateInterpolator());
        leftRect = new Rect();
        rightRect = new Rect();
        View child1 = new View(getContext());
        addView(child1, new LayoutParams(0, iconArrow.getHeight()));
        child1.setBackgroundColor(Color.GREEN);
    }

    private LinearLayout getItemView(Context context) {
        LinearLayout itemView = new LinearLayout(context);
        itemView.setOrientation(HORIZONTAL);
        itemView.setBackgroundColor(0);
        return itemView;
    }

    public void addItemView(String text) {
        RelativeLayout itemLayout = (RelativeLayout) View.inflate(getContext(), R.layout.anim_tab_item, null);
        TextView child0 = (TextView) itemLayout.getChildAt(0);
        child0.setText(text);
        itemView.addView(itemLayout, new LinearLayout.LayoutParams(0, -1, 1.0F));
        itemViews.add(itemLayout);
        if (currentPostion == 0) {
            currentX = getCurrentX(0);
            invalidate();
        }
        itemLayout.setOnClickListener(v -> {
            int position = itemViews.indexOf(itemLayout);
            setSelect(position);
        });
    }

    public void setSelect(int position) {
        if(!scroller.computeScrollOffset()){
            View item = itemViews.get(position);
            int endX = getEndX(position);
            itemViews.get(currentPostion).setSelected(false);
            currentPostion = position;
            item.setSelected(true);
            scroller.startScroll(currentX, currentY, endX - currentX, currentY, 400);
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        currentY = b - t - iconArrow.getHeight();//游标的y
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("onDraw");
        if (scroller.computeScrollOffset()) {//动画没完成就绘制当前位置
            drawNav(canvas, scroller.getCurrX(), currentY);
            invalidate();
        } else {//动画结束绘制最终位置
            currentX = getCurrentX(currentPostion);
            drawNav(canvas, currentX, currentY);
        }
    }

    private int getCurrentX(int position) {
        return getScroX(position);
    }

    private int getEndX(int position) {
        return getScroX(position);
    }

    private int getScroX(int position) {
        View itemView = itemViews.get(position);
        int x = (itemView.getWidth() - iconArrow.getWidth()) / 2 + itemView.getLeft();
        return x;
    }

    private void drawNav(Canvas canvas, int currentX, int currentY) {
        int scrollHeight = iconArrow.getHeight();
        leftRect.set(0, currentY, currentX, scrollHeight + currentY);
        canvas.drawBitmap(rectArrow, null, leftRect, null);
        rightRect.set(currentX + iconArrow.getWidth(), currentY, getWidth(), scrollHeight + currentY);
        canvas.drawBitmap(rectArrow, null, rightRect, null);
        canvas.drawBitmap(iconArrow, currentX, currentY, null);
    }

}
