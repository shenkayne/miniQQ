package com.client.business.uiManager;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


/**
 * 文本工具类
 */
public class MyTextPane extends JTextPane {

	private StyledDocument doc = null;

	private static final long serialVersionUID = 1L;

	public MyTextPane() {
		doc = getStyledDocument();
	}

	public void insert(String str, AttributeSet attrSet) {
		Document doc = getDocument();
		// if(doc.getLength()!=0){
		// str =str+"\n";
		// }
		try {
			doc.insertString(doc.getLength(), str, attrSet);
		} catch (BadLocationException e) {
			System.out.println("BadLocationException:" + e);
		}
	}

	public void setDocs(String str, Color col, String font, boolean bold,
			boolean italic, boolean underline, int fontSize) {
		// SimpleAttributeSet attrSet = new SimpleAttributeSet();
		// StyleConstants.setForeground(attrSet, col); // 颜色
		// if (bold == true) {
		// StyleConstants.setBold(attrSet, true); // 字形
		// }
		// if (Italic == true) {
		// StyleConstants.setItalic(attrSet, true); // 斜体
		// }
		// if (Underline == true) {
		// StyleConstants.setUnderline(attrSet, true); // 下划线
		// }
		// StyleConstants.setFontFamily(attrSet, font); // 字体
		// StyleConstants.setFontSize(attrSet, fontSize); // 字体大小
		// insert(str, attrSet);
		insertText(str, col, font, bold, italic, underline, fontSize);
	}

	public void insertImage(ImageIcon im) {
		doc = getStyledDocument();
		this.setCaretPosition(doc.getLength()); // 设置插入位置
		insertIcon(im); // 插入图片
		insertText(new FontAttrib()); // 这样做可以换行

	}


	/**
	 * 设置字体
	 * @param str
	 * @param col
	 * @param font
	 * @param bold
	 * @param italic
	 * @param underline
	 * @param fontSize
	 */
	public void insertText(String str, Color col, String font, boolean bold, boolean italic, boolean underline, int fontSize) {
		FontAttrib att = new FontAttrib();

		att.setText(str);
		att.setColor(col);
		att.setName(font);
		att.setSize(fontSize);
		att.Underline = underline;
		att.BOLD = bold;
		att.Underline = underline;

		try { // 插入文本
			doc.insertString(doc.getLength(), att.getText(), att.getAttrSet());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

	}

	private void insertText(FontAttrib att) {
		att.setText("");
		att.setColor(Color.black);
		att.setName("楷体");
		att.setSize(20);
		att.Underline = false;
		att.BOLD = false;
		att.Underline = false;

		try { // 插入文本
			doc.insertString(doc.getLength(), att.getText() + "\n",
					att.getAttrSet());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	private class FontAttrib {
		public boolean BOLD = false; // 粗体
		public boolean ITALIC = false; // 斜体
		public boolean Underline = false;// 下划线
		private SimpleAttributeSet attrSet = null; // 属性集
		private String text = null; // 文字内容，字体
		private String name = null; // 要输入的文本和字体名称
		private int size = 0; // 样式和字号
		private Color color = null; // 文字颜色

		public FontAttrib() {
		}

		public SimpleAttributeSet getAttrSet() {
			attrSet = new SimpleAttributeSet();

			if (name != null) {// 字体
				StyleConstants.setFontFamily(attrSet, name);
			}
			if (BOLD) {
				StyleConstants.setBold(attrSet, true);
			} else {
				StyleConstants.setBold(attrSet, false);
			}
			if (Underline) {
				StyleConstants.setUnderline(attrSet, true);
			} else {
				StyleConstants.setUnderline(attrSet, false);
			}
			if (ITALIC) {
				StyleConstants.setItalic(attrSet, true);
			} else {
				StyleConstants.setItalic(attrSet, false);
			}
			if (color != null) {
				StyleConstants.setForeground(attrSet, color);
			}
			StyleConstants.setFontSize(attrSet, size);
			return attrSet;
		}

		public void setAttrSet(SimpleAttributeSet attrSet) {// 字体
			this.attrSet = attrSet;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {// 文字
			this.text = text;
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {// 字体颜色
			this.color = color;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

	}

}
