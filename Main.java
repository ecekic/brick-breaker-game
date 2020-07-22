package brickBreakerGame;

import javax.swing.*;

public class Main extends JFrame {

	public static void main(String[] args) {

		JFrame obj = new JFrame();
		
		Gameplay gplay = new Gameplay();
		
		obj.setBounds(10, 10, 700, 600);
		obj.setTitle("Break them Bricks");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(EXIT_ON_CLOSE);
		obj.add(gplay);
	}

}
