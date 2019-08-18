package other;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawBufferTest extends JPanel implements MouseListener,
    MouseMotionListener {
  Rectangle rect = new Rectangle(0, 0, 100, 50);

  BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);

  Graphics2D big;

  int last_x, last_y;

  boolean firstTime = true;

  Rectangle area;

  boolean pressOut = false;

  public DrawBufferTest() {
    setBackground(Color.white);
    addMouseMotionListener(this);
    addMouseListener(this);
  }

  // Handles the event of the user pressing down the mouse button.
  public void mousePressed(MouseEvent e) {

    last_x = rect.x - e.getX();
    last_y = rect.y - e.getY();

    // Checks whether or not the cursor is inside of the rectangle while the
    // user is pressing themouse.
    if (rect.contains(e.getX(), e.getY())) {
      updateLocation(e);
    } else {
      pressOut = true;
    }
  }

  // Handles the event of a user dragging the mouse while holding down the
  // mouse button.
  public void mouseDragged(MouseEvent e) {

    if (!pressOut) {
      updateLocation(e);
    }
  }

  // Handles the event of a user releasing the mouse button.
  public void mouseReleased(MouseEvent e) {
    if (rect.contains(e.getX(), e.getY())) {
      updateLocation(e);
    }
  }

  public void mouseMoved(MouseEvent e) {
  }

  public void mouseClicked(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void updateLocation(MouseEvent e) {

    rect.setLocation(last_x + e.getX(), last_y + e.getY());
    repaint();
  }

  public void paint(Graphics g) {
    update(g);
  }

  public void update(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    if (firstTime) {
      Dimension dim = getSize();
      int w = dim.width;
      int h = dim.height;
      area = new Rectangle(dim);
      bi = (BufferedImage) createImage(w, h);
      big = bi.createGraphics();
      rect.setLocation(w / 2 - 50, h / 2 - 25);
      big.setStroke(new BasicStroke(8.0f));
      firstTime = false;
    }

    big.setColor(Color.white);
    big.clearRect(0, 0, area.width, area.height);

    big.setPaint(Color.red);
    big.draw(rect);
    big.setPaint(Color.blue);
    big.fill(rect);

    g2.drawImage(bi, 0, 0, this);
  }

  private boolean checkRect() {
    if (area == null) {
      return false;
    }
    if (area.contains(rect.x, rect.y, 100, 50)) {
      return true;
    }
    int new_x = rect.x;
    int new_y = rect.y;

    if ((rect.x + 100) > area.width) {
      new_x = area.width - 99;
    }
    if (rect.x < 0) {
      new_x = -1;
    }
    if ((rect.y + 50) > area.height) {
      new_y = area.height - 49;
    }
    if (rect.y < 0) {
      new_y = -1;
    }
    rect.setLocation(new_x, new_y);
    return false;
  }

  public static void main(String s[]) {

    JFrame f = new JFrame("BufferedShapeMover");
    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    f.getContentPane().setLayout(new BorderLayout());
    f.getContentPane().add(new DrawBufferTest(), "Center");

    f.pack();
    f.setSize(new Dimension(550, 250));
    f.show();
  }

}