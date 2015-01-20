package com.gdut.pet.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class MyPetGridView extends GridView
{
	boolean a = true;

	public MyPetGridView(Context paramContext)
	{
		super(paramContext);
	}

	public MyPetGridView(Context paramContext, AttributeSet paramAttributeSet)
	{
		super(paramContext, paramAttributeSet);
	}

	public MyPetGridView(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt)
	{
		super(paramContext, paramAttributeSet, paramInt);
	}

	public boolean isExpanded()
	{
		return this.a;
	}

	public void onMeasure(int paramInt1, int paramInt2)
	{
		if (isExpanded())
		{
			int i = View.MeasureSpec.makeMeasureSpec(16777215, -2147483648);
			super.onMeasure(paramInt1, i);
			ViewGroup.LayoutParams localLayoutParams = getLayoutParams();
			int j = getMeasuredHeight();
			localLayoutParams.height = j;
			return;
		}
		super.onMeasure(paramInt1, paramInt2);
	}

	public void setExpanded(boolean paramBoolean)
	{
		this.a = paramBoolean;
	}
}