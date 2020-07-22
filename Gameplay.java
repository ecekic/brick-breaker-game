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
	private int ballXDirection = -1; //
	private int ballYDirection = -2; 
	
	public Gameplay() {
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
		// borders
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		// the paddle
		g.setColor(Color.CYAN);
		g.fillRect(playerX, 550, 100, 8);
		// ball
		g.setColor(Color.BLACK);
		g.fillOval(ballPosX, ballPosY, 20, 20);
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
