package project1;

import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SnakePanel extends JPanel implements ActionListener {

	static final int SCREEN_WIDTH=700;
	static final int SCREEN_HEIGHT=600;
	static final int UNIT_SIZE=25;
	static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY=75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int body=6;
	int appleEaten;
	int appleX;
	int appleY;
	char direc='R';
	boolean moving=false;
	Timer timer;
	Random random;
	
	SnakePanel(){
		random=new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		newApple();
		moving=true;
		timer=new Timer(DELAY,this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		if(moving) {
		/*for( i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
			g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
			g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
		}*/
		g.setColor(Color.red);
		g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		
		for(int i=0;i<body;i++) {
			if(i==0) {
				g.setColor(Color.blue);
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
			
			else {
				g.setColor(new Color(45,0,180));
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
		}
		g.setColor(Color.green);
		g.setFont(new Font("Ink Free",Font.BOLD,40));
		FontMetrics metrics=getFontMetrics(g.getFont());
		g.drawString("Score: "+appleEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+appleEaten))/2, g.getFont().getSize());
	
		}
		else {
			gameOver(g);
		}
	}
	
	public void newApple() {
		appleX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void move() {
		for(int i=body;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		
		switch(direc) {
		case 'U':
			y[0]=y[0]-UNIT_SIZE;
			break;
		
	    case 'D':
	    	y[0]=y[0] + UNIT_SIZE;
		    break;
	    
        case 'L':
	        x[0]=x[0] - UNIT_SIZE;
	        break;
        
        case 'R':
	        x[0]=x[0] + UNIT_SIZE;
	        break;
		}
	}
	
	public void checkApple() {
		if((x[0]==appleX) && (y[0]==appleY)) {
			body++;
			appleEaten++;
			newApple();
		}
	}
	
	public void checkCollision() {
		//snake bites itself
		for(int i=body;i>0;i--) {
			if((x[0]==x[i]) && (y[0]==y[i])) {
				moving=false;
			}
		}
		
		//snake touches left border
		if(x[0]<0) {
			moving=false;
		}
		//snake touches right border
		if(x[0]>SCREEN_WIDTH) {
			moving=false;
		}
		//snake touches top border
		if(y[0]<0) {
			moving=false;
		}
		//snake touches bottom border
		if(y[0]>SCREEN_HEIGHT) {
			moving=false;
		}
		
		if(!moving) {
			timer.stop();
		}
	}
	public void gameOver(Graphics g) {
		//score txt
		g.setColor(Color.green);
		g.setFont(new Font("Ink Free",Font.BOLD,40));
		FontMetrics metrics1=getFontMetrics(g.getFont());
		g.drawString("Score: "+appleEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+appleEaten))/2, g.getFont().getSize());
	
		//over text
		g.setColor(Color.yellow);
		g.setFont(new Font("Ink Free",Font.BOLD,80));
		FontMetrics metrics2=getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(moving) {
			move();
			checkApple();
			checkCollision();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direc !='R') {
					direc='L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direc !='L') {
					direc='R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direc !='D') {
					direc='U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direc !='U') {
					direc='D';
				}
				break;
			}
			
		}
	}

}
