package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StartWindow extends JFrame {
    private List<FallingImage> images = new ArrayList<>();
    private static Random random = new Random(); // Make random static
    private Image enemyImage;
    private Image myImage;

    public StartWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        getContentPane().setBackground(new Color(52,61,70));

        for (int i = 1; i <= 14; i++) {
            try {
                int x = random.nextInt(0,18);
                int y = random.nextInt(0,18);
                enemyImage = ImageIO.read(new File("Projekt\\src\\pictures\\Enemy-" + x + ".png"));
                myImage = ImageIO.read(new File("Projekt\\src\\pictures\\My-" + y + ".png"));
                enemyImage = enemyImage.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                myImage = myImage.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                images.add(new FallingImage(enemyImage));
                images.add(new FallingImage(myImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Timer timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (FallingImage image : images) {
                    image.y += 1;
                    if (image.y > getHeight()) {
                        image.y = 0 - random.nextInt(0,5000);
                        image.x = random.nextInt(5, getWidth()-5);
                    }
                }
                repaint();
            }
        });
        timer.start();

        add(new DrawPanel());

        setVisible(true);
    }

    private class DrawPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.85f));
            for (FallingImage image : images) {
                g2d.drawImage(image.image, image.x, image.y, null);
            }
            g2d.dispose();
        }
    }

    private static class FallingImage {
        public Image image;
        public int x;
        public int y;

        public FallingImage(Image image) {
            this.image = image;
            this.x = random.nextInt(0,1000);
            this.y = 0 - random.nextInt(0,5000);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StartWindow();
            }
        });
    }
}
