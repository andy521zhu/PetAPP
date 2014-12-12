package com.gdut.pet.common.utils;

import java.io.Serializable;

public class BitmapSerializable implements Serializable
{
	byte[] bitmapByte;

	public BitmapSerializable()
	{

	}

	public byte[] getBitmap()
	{
		return bitmapByte;
	}

	public void setBitmap(byte[] bitmap)
	{
		this.bitmapByte = bitmap;
	}

}
