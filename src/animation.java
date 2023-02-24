import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

    public class animation extends Canvas implements Runnable{
        private BufferStrategy bs;

        private boolean running = false;
        private Thread thread;

        private int mousex = 0;

        private int mousey = 0;

        private int panelwidth = getWidth();

        private int panelheight = getHeight();

//        private int randomx;

//        private int randomy;

        private Rectangle hitbox;

        public animation() {
            setSize(600,400);
//            randomx = (int)(Math.random() * this.getWidth());

//            randomy = (int)(Math.random() * this.getHeight());
            hitbox = new Rectangle((int)(Math.random() * this.getWidth()), (int)(Math.random() * this.getHeight()), 50,50);

            JFrame frame = new JFrame();
            frame.add(this);
            frame.addKeyListener(new MyKeyListener());
            this.addMouseMotionListener(new MyMouseMotionListener());
            this.addMouseListener(new MyMouseListener());
            requestFocus();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }

        public void render() {
            bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(3);
                return;
            }
            Graphics g = bs.getDrawGraphics();

            // Rita ut den nya bilden
            draw(g);

            g.dispose();
            bs.show();
        }

        public void draw(Graphics g) {
//            g.clearRect(0,0,getWidth(),getHeight());
            g.clearRect(0,0,getWidth(),getHeight());
            g.setColor(new Color(0x8C0E98));
            g.fillRect(hitbox.x,hitbox.y, hitbox.width, hitbox.height);
            g.setColor(new Color(0xBD0D0D));
            g.fillOval(mousex,mousey, 30, 30);
        }

        private void update() {
        }

        public static void main(String[] args) {
            animation minGrafik = new animation();
            minGrafik.start();
        }

        public synchronized void start() {
            running = true;
            thread = new Thread(this);
            thread.start();
        }

        public synchronized void stop() {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            double ns = 1000000000.0 / 25.0;
            double delta = 0;
            long lastTime = System.nanoTime();

            while (running) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;

                while(delta >= 1) {
                    // Uppdatera koordinaterna
                    update();
                    // Rita ut bilden med updaterad data
                    render();
                    delta--;
                }
            }
            stop();
        }

        public class MyMouseMotionListener implements MouseMotionListener {

            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mousex = e.getX();
                mousey = e.getY();

            }
        }

        public class MyMouseListener implements MouseListener {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (hitbox.contains(mousex,mousey)) {
                    hitbox.y = (int)(Math.random() * 350);
                    hitbox.x = (int)(Math.random() * 550);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        }

        public class MyKeyListener implements KeyListener {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        }
    }