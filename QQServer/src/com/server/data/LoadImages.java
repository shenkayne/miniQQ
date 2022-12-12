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
 * ͼƬ���ع�����
 */
public class LoadImages {

	public LoadImages() {
	}

	/**
	 * ͨ��IO����ȡ����ص�ͼƬ
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
	 * ��Image����ת��ImageIcon����
	 * @param name
	 * @return
	 */
    public ImageIcon LoadImageIcon(String name){
        ImageIcon icon = new ImageIcon(loadImage(name));
		return icon;
    }
}
 
