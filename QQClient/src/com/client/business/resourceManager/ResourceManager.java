/**
 * 
 */
package com.client.business.resourceManager;

import java.awt.Image;

import javax.swing.ImageIcon;

import com.client.data.LoadImages;

public class ResourceManager {
	
	/*
	 * 图片资源管理器
	 */
	/**
	 * 获得图片
	 * @param imageName
	 * @return
	 */
	public ImageIcon GetImage(String imageName)
	{
		LoadImages image = new LoadImages();
		return image.LoadImageIcon(imageName);
	}
	/**
	 * 获得图片
	 * @param imageName
	 * @return
	 */
	public Image GetImage1(String imageName){
		LoadImages image = new LoadImages();
		return image.loadImage(imageName);
	}
}

