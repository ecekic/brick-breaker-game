package brickBreakerGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer; // need this specific one for Timer

// will be the game play panel
public class Gameplay extends JPanel implements KeyListener, ActionListener {

	private boolean play = false;
	private int score = 0;
	private int totalBricks = 21;
	private Timer timer;
	private int delay = 8;
	private int playerX = 310; // starting position of slider 
	private int ballPosX = 120; // ball position x
	private int ballPosY = 350; // ball position y
	private int ballXDirection = -1; 
	private int ballYDirection = -2; 

	private MapGenerator map;
	
	public Gameplay() {
		map = new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start(); // starts timer once game starts
	}
	
	public void paint(Graphics g) {
		// background
		g.setColor(Color.pink);
		g.fillRect(1, 1, 692, 592);
		// drawing map
		map.draw((Graphics2D)g);
		// borders
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		// scores
		g.setColor(Color.GRAY);
		g.setFont(new Font("serif",Font.BOLD, 25));
		g.drawString(""+score, 590, 30);
		// the paddle
		g.setColor(Color.CYAN);
		g.fillRect(playerX, 550, 100, 8);
		// ball
		g.setColor(Color.BLACK);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		// if you finished the game
		if (totalBricks <= 0) {
			play = false;
			ballXDirection = 0;
			ballYDirection = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif",Font.BOLD, 30));
			g.drawString("You Won! Score: "+score, 190, 300);
			g.setFont(new Font("serif",Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 250, 350);
		}
		// game over
		if (ballPosY > 570) { // if ball goes under the paddle
			play = false;
			ballXDirection = 0;
			ballYDirection = 0;
			g.setColor(Color.white);
			g.setFont(new Font("serif",Font.BOLD, 30));
			g.drawString("Game Over. Score: "+score, 190, 300);
			g.setFont(new Font("serif",Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 250, 350);
		}
		g.dispose();
	}

	
	public void actionPerformed(ActionEvent e) {
		timer.start(); // starts timer
		if (play == true) {
			// detecting if ball touches the slider
			// need to create a rectangle surrounding the ball in order to check the intersection of the ball and slider
			if((new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8)))) {
				ballYDirection = -ballYDirection;
			}
			// first map is map in gameplay, second is map in mapgenerator
			A: for (int i=0; i<map.map.length; i++) {
				for (int j=0; j<map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						// detects intersection
						// detect position of ball and brick
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						// create rectangle around brick
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20,20);
						Rectangle brickRect = rect;
						
						if (ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							// left and right intersection
							if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
								ballXDirection = -ballXDirection;
							}
							else
								ballYDirection = -ballYDirection;
							break A;
						}
					}
				}
			}
			// if ball is touching top, left, right
			ballPosX += ballXDirection;
			ballPosY += ballYDirection;
			// left border
			if (ballPosX < 0) {
				ballXDirection = -ballXDirection;
			}
			// top
			if (ballPosY < 0) {
				ballYDirection = -ballYDirection;
			}
			// right
			if (ballPosX > 670) {
				ballXDirection = -ballXDirection;
			}
		}
		repaint(); // re-calls the paint method to redraw the panel when playing 
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// see which arrow key is pressed
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 590) { // make sure it doesn't go outside the panel
				playerX = 590; // keeps the ball in the panel
			}
			else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX < 10) { // make sure it doesn't go outside the panel
				playerX = 10; // keeps the ball in the panel
			}
			else {
				moveLeft();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) { // press enter key
			if (!play) {
				// restarts the game
				play = true;
				ballPosX = 120;
				ballPosY = 350;
				ballXDirection = -1;
				ballYDirection = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3,7);
				
				repaint();
			}
		}
		
	}

	public void moveRight() {
		// TODO Auto-generated method stub
		play = true;
		playerX += 20; // move 20 pixels to the right
	}

	public void moveLeft() {
		// TODO Auto-generated method stub
		play = true;
		playerX -= 20; // move 20 pixels to the left
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}

