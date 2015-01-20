package com.gdut.pet.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyPetImageView extends ImageView
{
  public MyPetImageView(Context paramContext)
  {
    super(paramContext);
  }

  public MyPetImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public MyPetImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt1);
  }
}