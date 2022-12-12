/**
 * 
 */
package com.server.data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


/**
 * 图片加载工具类
 */
public class LoadImages {

	public LoadImages() {
	}

	/**
	 * 通过IO流获取到相关的图片
	 * @param name
	 * @return
	 */
    public Image loadImage(String name) {
    	File file= new File("image/Chat/"+name+".png");
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
 
