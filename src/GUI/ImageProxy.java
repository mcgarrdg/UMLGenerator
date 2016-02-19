package GUI;

import java.awt.Component;
import java.awt.Graphics;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageProxy extends ImageIcon {
	
	
	private String filePath;
	private ImageIcon imageIcon;
	private boolean retrieving = false;
	private Thread retrievalThread;

	public ImageProxy(String path) {
		filePath = path;
	}
	
	public int getIconWidth() {
		if (imageIcon != null) {
            return imageIcon.getIconWidth();
        } else {
			return 800;
		}
	}
 
	public int getIconHeight() {
		if (imageIcon != null) {
            return imageIcon.getIconHeight();
        } else {
			return 600;
		}
	}
     
	@Override
	public synchronized void paintIcon(final Component c, Graphics  g, int x,  int y) {
		if (imageIcon != null) {
			imageIcon.paintIcon(c, g, x, y);
		} else {
			g.drawString("Loading, please wait...", x, y+100);
			if (!retrieving) {
				retrieving = true;

				retrievalThread = new Thread(new Runnable() {
					public void run() {
						try {
							imageIcon = new ImageIcon(ImageIO.read(new File(filePath)));
//							try {
//								Thread.sleep(10000);
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
							c.repaint();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				retrievalThread.start();
				
			}
		}
	}
}
