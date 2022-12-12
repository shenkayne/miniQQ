package com.client.cut;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import com.client.business.uiManager.ChatPanel;

/**
 * 实现截屏功能
 */
public class Cut extends JFrame {
    Magnifier mf = new Magnifier();
    JMagnifierPanel magnifierPanel = new JMagnifierPanel(100);
    ;
    private String save_type[] = {".png", ".jpg", ".bmp", "gif"}; // 保存截图的类型
    private String[] save_type_de = {"PNG (*.png)", "JPG (*.jpg)",
            "BMP (*.bmp)", "GIF (*.gif)"};
    private Container container = getContentPane(); // 主容器

    private static final long serialVersionUID = 1L;
    int startx, starty, endx, endy;// 鼠标按下和释放时x、y轴坐标
    int sx, sy;
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();// 获得屏幕大小
    BufferedImage image;// 存储整个屏幕
    BufferedImage tempImage;// 缓存
    BufferedImage saveImage;// 保存(截取的区域)
    Graphics g;

    ChatPanel cp;
    boolean flag = false;
    CutToolbarJWindow ctjw;

    public void cutSc() {
        try {
            Robot robot = new Robot();// 在基本屏幕坐标系中构造一个 Robot对象
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            image = robot.createScreenCapture(new Rectangle(0, 0, d.width,
                    d.height));// 获得整个屏幕
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        RescaleOp ro = new RescaleOp(0.8f, 0, null);// 构造一个具有所希望的缩放因子和偏移量
        tempImage = ro.filter(image, null);// 对源 BufferedImage对象image进行重缩放
        g.drawImage(tempImage, 0, 0, this);
    }

    // 构造函数
    public Cut(ChatPanel cp) {
        this.cp = cp;
        cp.setVisible(false);
        setUndecorated(true);
        mf.setVisible(false);
        container.add(magnifierPanel);
        cutSc(); // 截屏
        setResizable(false);
        setVisible(true);// 设置窗口可见

        setSize(d);// 最大化窗口


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 默认关闭方式
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (flag == false) {
                    mf.setVisible(false);
                    sx = startx = e.getX();
                    sy = starty = e.getY();
                    if (ctjw != null) {
                        ctjw.dispose();
                    }
                }

            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseMoved(MouseEvent e) {
                // Repaint();
                if (!flag) {
                    mf.setLocation(e.getX() + 5, e.getY() + 5);
                    mf.magnifierPanel.setMagnifierLocation(e.getX(), e.getY());
                    mf.setVisible(true);
                }


            }

            @Override
            public void mouseDragged(MouseEvent e) {

            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {

                if (flag == false
                        && e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                    endx = e.getX();
                    endy = e.getY();
                    g = getGraphics();
                    g.drawImage(tempImage, 0, 0, Cut.this);
                    int x = Math.min(startx, endx);
                    int y = Math.min(starty, endy);
                    int width = Math.abs(endx - startx) + 1; // 加上1，防止width或height为0
                    int height = Math.abs(endy - starty) + 1;
                    g.setColor(Color.BLUE);
                    g.drawRect(x - 1, y - 1, width + 1, height + 1);// 减1，加1都是为了防止图片将矩形框覆盖掉
                    saveImage = image.getSubimage(x, y, width, height);
                    g.drawImage(saveImage, x, y, Cut.this);

                }
            }
        });

        this.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { // 按Esc键退出
                if (e.getKeyCode() == 27) {
                    if (saveImage != null) {
                        // saveToFile();//保存图片
                    }
                    System.exit(0);// 退出
                }
            }
        });

        this.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {// 单击(双击)组件时调用 左键
                // TODO Auto-generated method stub
                if (e.getClickCount() == 2) {
                    if (saveImage != null) {// 如果截图了则保存图片到桌面
                        // closeWindows();

                        /*
                         * CutScreen cs = new CutScreen(saveImage, endx, endy);
                         * cs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                         * cs.setSize(saveImage.getWidth(),
                         * saveImage.getHeight()); cs.setVisible(true);
                         */

                    }

                }
                // flag= false;
                if (ctjw != null) {
                    ctjw.dispose();
                }
                if (e.getModifiers() == MouseEvent.BUTTON3_MASK
                        && e.getClickCount() == 1) {

                    closeWindow();

                    // mf.setLocation(e.getX()+5, e.getY()+5);
                    // mf.magnifierPanel.setMagnifierLocation(e.getX(),
                    // e.getY());
                    mf.dispose();
                    // mf.setVisible(true);
                    //new Cut();
                    // flag = false;

                }

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {// 点击鼠标右键则退出程序
                /*
                 * if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
                 *
                 * // closeWindow(); Repaint(); //if(ctjw!=null) //{
                 * ctjw.dispose(); //} //new Cut(); }
                 */
            }

            public void mouseReleased(MouseEvent e) {
                //

                if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {

                    if (endx - sx != saveImage.getWidth()
                            && endy - sy != saveImage.getHeight()) {
                        // if(ctjw!=null)
                        // ctjw.dispose();
                        flag = true;
                        mf.setVisible(false);
                        initCtb();
                        ctjw.setVisible(true);
                    }

                }

            }
        });

    }

    public Cut() {

        this.cp = cp;
        setUndecorated(true);
        mf.setVisible(false);
        container.add(magnifierPanel);
        cutSc(); // 截屏
        setVisible(true);// 设置窗口可见
        setSize(d);// 最大化窗口
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 默认关闭方式
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (flag == false) {
                    mf.setVisible(false);
                    sx = startx = e.getX();
                    sy = starty = e.getY();
                    if (ctjw != null) {
                        ctjw.dispose();
                    }
                }

            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseMoved(MouseEvent e) {
                // Repaint();
                if (!flag) {
                    mf.setLocation(e.getX() + 5, e.getY() + 5);
                    mf.magnifierPanel.setMagnifierLocation(e.getX(), e.getY());

                    mf.setVisible(true);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {

            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {

                if (flag == false
                        && e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                    endx = e.getX();
                    endy = e.getY();
                    g = getGraphics();
                    g.drawImage(tempImage, 0, 0, Cut.this);
                    int x = Math.min(startx, endx);
                    int y = Math.min(starty, endy);
                    int width = Math.abs(endx - startx) + 1; // 加上1，防止width或height为0
                    int height = Math.abs(endy - starty) + 1;
                    g.setColor(Color.BLUE);
                    g.drawRect(x - 1, y - 1, width + 1, height + 1);// 减1，加1都是为了防止图片将矩形框覆盖掉
                    saveImage = image.getSubimage(x, y, width, height);
                    g.drawImage(saveImage, x, y, Cut.this);

                }
            }
        });

        this.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { // 按Esc键退出
                if (e.getKeyCode() == 27) {
                    if (saveImage != null) {
                        // saveToFile();//保存图片
                    }
                    System.exit(0);// 退出
                }
            }
        });

        this.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {// 单击(双击)组件时调用 左键
                if (e.getClickCount() == 2) {
                    if (saveImage != null) {// 如果截图了则保存图片到桌面
                        // closeWindows();

                        /*
                         * CutScreen cs = new CutScreen(saveImage, endx, endy);
                         * cs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                         * cs.setSize(saveImage.getWidth(),
                         * saveImage.getHeight()); cs.setVisible(true);
                         */

                    }

                }
                // flag= false;
                if (ctjw != null) {
                    ctjw.dispose();
                }
                if (e.getModifiers() == MouseEvent.BUTTON3_MASK
                        && e.getClickCount() == 1) {

                    closeWindow();

                    // mf.setLocation(e.getX()+5, e.getY()+5);
                    // mf.magnifierPanel.setMagnifierLocation(e.getX(),
                    // e.getY());
                    mf.dispose();
                    // mf.setVisible(true);
                    new Cut();
                    // flag = false;

                }

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {// 点击鼠标右键则退出程序
                /*
                 * if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
                 *
                 * // closeWindow(); Repaint(); //if(ctjw!=null) //{
                 * ctjw.dispose(); //} //new Cut(); }
                 */
            }

            public void mouseReleased(MouseEvent e) {
                //

                if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {

                    if (endx - sx != saveImage.getWidth()
                            && endy - sy != saveImage.getHeight()) {
                        // if(ctjw!=null)
                        // ctjw.dispose();
                        flag = true;
                        mf.setVisible(false);
                        initCtb();
                        ctjw.setVisible(true);
                    }

                }

            }
        });

    }




    public void closeWindows() {
        this.dispose();
    }

    public void initCtb() {
        int x = endx, y = endy;
        if (endx < sx)
            x = sx;
        if (endy < sy)
            y = sy;
        if (x < 367)
            x = 367;
        if (y + 28 > 769) {
            y = y - saveImage.getHeight() - 28 - 6;
            if (sy - 28 < 0)
                y = 0;
        }

        ctjw = new CutToolbarJWindow(this, x + 5, y + 6);
    }

    public void insertCutImage() {
        Image i = saveImage;
        ImageIcon ic = new ImageIcon(i);

        cp.insertCutImage(ic);
    }

    public void closeWindow() {
        if (ctjw != null)
            ctjw.dispose();
        this.dispose();
        // this.closeWindow();

    }

    public void Repaint() {
        this.repaint();
    }

    // 保存响应
    public void Save() {


        closeWindow();
        String defaultDir = "c:/"; // 设置默认路径 为C盘
        JFileChooser jf = new JFileChooser(); // 获得对象
        jf.setDialogTitle("保存文件"); // 自定义选择框标题
        jf.setSelectedFile(new File("my")); // 设置默认文件名

        // 打开保存对话框
        jf.setCurrentDirectory(new File(defaultDir));// 设置默认目录 打开直接默认C盘

        jf.setFileFilter(new FileNameExtensionFilter(save_type_de[0],
                save_type[0])); // 保存格式设置
        jf.setFileFilter(new FileNameExtensionFilter(save_type_de[1],
                save_type[1])); // 保存格式设置
        jf.setFileFilter(new FileNameExtensionFilter(save_type_de[2],
                save_type[2])); // 保存格式设置
        jf.setFileFilter(new FileNameExtensionFilter(save_type_de[3],
                save_type[3])); // 保存格式设置
        jf.showSaveDialog(null);
        File fi = jf.getSelectedFile(); //
        String path = fi.getPath(); // 路径
        String name = fi.getName(); // 获得文件名
        System.out.println(jf.getFileFilter().getDescription());
        System.out.println(jf.getFileFilter().hashCode());
        File f = null;
        for (int i = 0; i < 4; i++) {
            if (jf.getFileFilter().getDescription().equals(save_type_de[i])) {
                f = new File(path + "." + save_type[i]);
            }
        }
        System.out.println("save: " + f); // 提示
        try {

            ImageIO.write(saveImage, "png", f);// 生成图片

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

//    /**
//     * 测试
//     * @param args
//     */
//    public static void main(String[] args) {
//        new Cut();
//
//    }
}
