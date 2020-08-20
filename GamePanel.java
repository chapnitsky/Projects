import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener{
	private Thread thread;
	private BodyBlock b;
	private Apple apple;
	private ArrayList<Apple> apples;
	private ArrayList<BodyBlock> snake;
	private Random r;
	private int ticks = 0;
	private int xCoor = 10, yCoor = 10, size = 10;
	private boolean running = false, right = true, left = false, up = false, down = false;
	private static final int WIDTH = 1000, HEIGHT = 800;
	private static final long serialVersionUID = 1L;
	
	public GamePanel() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);
		snake = new ArrayList<BodyBlock>();
		apples = new ArrayList<Apple>();
		r = new Random();
		start();
	}
	
	@Override
	public void run() {
		while(running) {
			tick();
			repaint();
		}
	}
	
	public void start() {
		this.running = true;
		this.thread = new Thread(this);
		this.thread.start();
	}
	public void stop() {
		this.running = false;
		try {
			this.thread.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void tick() {
		if(snake.size() == 0) {
			b = new BodyBlock(xCoor, yCoor, 10);
			snake.add(b);
		}
		ticks++;
		if(ticks > 800000) {
			if(right)
				xCoor++;
			if(left)
				xCoor--;
			if(down)
				yCoor++;
			if(up)
				yCoor--;
			
			ticks = 0;
			b = new BodyBlock(xCoor, yCoor, 10);
			snake.add(b);
			if(snake.size() > size) {
				snake.remove(0);
			}
		}
		if(apples.size() == 0) {
			int xCoor = r.nextInt(99);
			int yCoor = r.nextInt(79);
			apple = new Apple(xCoor, yCoor, 10);
			apples.add(apple);
		}
		
		for(int i = 0; i < apples.size(); i++) {
			if(xCoor == apples.get(i).getxCoor() && yCoor == apples.get(i).getyCoor()) {
				size++;
				apples.remove(i);
				i++;
			}
		}
		
		for(int i = 0; i < snake.size(); i++) {
			if(xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
				if(i != (snake.size() - 1)) {
					System.out.println("Game Over");
					stop();
				}
			}
		}
		if(xCoor < 0 || yCoor < 0 || xCoor > 99 || yCoor > 99) {
			System.out.println("Game Over");
			stop();
		}
	}
	public void paint(Graphics g) {
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for(int i = 0; i < WIDTH / 10; i++ )
			g.drawLine(i * 10, 0, i * 10, HEIGHT);
		
		for(int i = 0; i < HEIGHT / 10; i++)
			g.drawLine(0, i * 10, HEIGHT, i * 10);
		
		for(int j = 0; j < snake.size(); j++)
			snake.get(j).draw(g);
		
		for(int j = 0; j < apples.size(); j++)
			apples.get(j).draw(g);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT && !left) {
			right = true;
			up = false;
			down = false;
		}
		else if(key == KeyEvent.VK_LEFT && !right) {
			left = true;
			up = false;
			down = false;
		}
		else if(key == KeyEvent.VK_UP && !down) {
			up = true;
			left = false;
			right = false;
		}
		else if(key == KeyEvent.VK_DOWN && !up) {
			down = true;
			left = false;
			right = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
