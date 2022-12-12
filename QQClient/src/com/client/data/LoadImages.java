package com.client.data;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class LoadImages {

	public LoadImages() {
	}

	/**
	 * 通过IO流获取图片
	 * @param name
	 * @return
	 */
    public Image loadImage(String name) {

    	String path ="image/Chat/"+name+".png";
    	File file= new File(path);
//		System.out.println(path);
    	Image img=null;
    	try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return img;
	}

	/**
	 * 通过IO流获取到   表情包  相关的动态图
	 * @param name
	 * @return
	 */
    public Image loadEpImage(String name) {
		File file= new File("image/Chat/12/"+name+".gif");
		Image img=null;
		try {
				img = ImageIO.read(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return img;
	}

	/**
	 * 将Image对象转成ImageIcon对象
	 * @param name
	 * @return
	 */
	public ImageIcon LoadImageIcon(String name){

		ImageIcon icon = new ImageIcon(loadImage(name));
		return icon;
	}
}
 
