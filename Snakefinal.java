//Final Project
// 05/15/2016
//Veronica Estudillo
//Mateja Sela
//CS201 - Snake Game


import java.applet.*;
import java.util.*;
import java.awt.*;
import java.applet.Applet;
import java.awt.event.*;
import java.util.List;
//Background picture taken from:
//http://cdn1.sciencefiction.com/wp-content/uploads/2015/08/shutterstock_313063250.jpg
// Since Eclipse does not allow adjusting of windows automatically, we hope that
// you will set window size to 1315 *845 pixels.

public class Snakefinal extends Applet implements ActionListener, KeyListener, Runnable {

	// We define all the variables that we need
	private static final long serialVersionUID = 1L;
	Button level1Button, level2Button, level3Button, level4Button;
	SnakeCanvas c; // Canvas
	static Thread t;
	Panel title = new Panel();
	Panel buttons = new Panel();
	Random num = new Random();
	static boolean up = false;
	static boolean down = false;
	static boolean left = false;
	static boolean right = false;
	static boolean first = false; // first time you cannot press right, so needs a boolean
	// Chose the colors that match with the background
	Color green = new Color(67, 165, 139);
	Color lightr = new Color(135, 198, 243);
	Color darkr = new Color(62, 162, 236);
	Color ddarkr = new Color(20, 128, 207);
	Color dddarkr = new Color(13,84 , 136);
	int level = 70; // initial "speed" of the snake
	// Avoid problems when key is pressed
	long timePressed1 = 0;
	long timePressed2 = 0;
	long timePressed3 = 0;
	long timePressed4 = 0;

	// Start the thread 
	public void start(){
		t = new Thread(this);
		t.start();
	}

	//Stop the thread
	public void stop(){	
		t = null;
	}

	//In order to use the interface Runnable, we have the method run
	public void run() {
		Thread currentThread = Thread.currentThread();
		while (currentThread == t) {
			if (up ==true){
				c.moveSnake(0, -1);
			}
			if (down ==true){
				c.moveSnake(0, 1);
			}
			if (left ==true){
				c.moveSnake(-1, 0);
			}
			if (right ==true){
				c.moveSnake(1, 0);
			}
			try {
				Thread.sleep(level);
			} catch (InterruptedException e){

			}
		}
	}

	public void init() {

		setLayout(new BorderLayout());
		setFont(new Font("Verdana", Font.BOLD, 20));

		// initialize images needed for the program

		//We downloaded this picture
		Image heart = getImage(getDocumentBase(), "heart.gif");
		//We downloaded this picture
		Image blackhole = getImage(getDocumentBase(), "blackhole.jpg");
		//We designed this picture
		Image snakehead = getImage(getDocumentBase(), "snakehead.gif");
		//We designed this picture
		Image snakeheadVertical = getImage(getDocumentBase(), "snakeheadvert.gif");
		//We designed this picture
		Image body = getImage(getDocumentBase(), "body.png");
		//We designed this picture
		Image instructions = getImage(getDocumentBase(), "instructions.png");
		//We designed this picture, except for the snake
		Image end = getImage(getDocumentBase(), "end.png");

		c = new SnakeCanvas(heart,blackhole, snakehead, body, snakeheadVertical, instructions, end);
		c.addKeyListener(this); // tell canvas to listen to key presses
		// added elements to the canvas
		add("Center", c); 
		add("North", makeTitleLabel()); 
		add("East", makeButtonPanel());
		add("South", makescoreLabel());
		c.reset();
	}
	// make the title label displaying the name of the game
	public Label makeTitleLabel() {
		Label titleLabel = new Label("Snake Game");
		titleLabel.setAlignment(Label.CENTER);
		titleLabel.setBackground(green); // custom color
		titleLabel.setForeground(Color.white);
		return titleLabel;
	}

	// label at the bottom of 
	// the screen displaying the score
	public Label makescoreLabel(){

		c.scoreLabel.setAlignment(Label.CENTER);
		c.scoreLabel.setBackground(green);
		c.scoreLabel.setForeground(Color.white);
		return c.scoreLabel;

	}

	// Make buttons for the levels of the game
	public Panel makeButtonPanel() {

		Panel pbutton = new Panel();
		pbutton.setBackground(Color.white);
		setFont(new Font("Krungthep", Font.BOLD, 25));
		pbutton.setLayout(new GridLayout(4,1));

		level1Button = new Button("Slug");
		level1Button.setBackground(lightr);
		level1Button.setForeground(Color.white);
		level1Button.addActionListener(this);

		level2Button = new Button("Worm");
		level2Button.setBackground(darkr);
		level2Button.setForeground(Color.white);
		level2Button.addActionListener(this);

		level3Button = new Button("Python");
		level3Button.setBackground(ddarkr);
		level3Button.setForeground(Color.white);
		level3Button.addActionListener(this);

		level4Button = new Button("Mamba");
		level4Button.setBackground(dddarkr);
		level4Button.setForeground(Color.white);
		level4Button.addActionListener(this);

		pbutton.add(level1Button);
		pbutton.add(level2Button);
		pbutton.add(level3Button);
		pbutton.add(level4Button);


		return pbutton;
	}


	// handle button pressed events
	public void actionPerformed(ActionEvent e)  {

		if (e.getSource() == level1Button) {
			level = 60;
			restartCanvas();
		}
		if (e.getSource() == level2Button) {
			level = 50;
			restartCanvas(); 
		}
		if (e.getSource() == level3Button) {
			level = 40;
			restartCanvas();
		}
		if (e.getSource() == level4Button) {
			level = 30;
			restartCanvas();
		}
	}		 


	// Handles the arrow keys and the space (reset) button
	// only one direction can be handled at a time
	// hence the booleans are used
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode(); 
		if (keyCode == KeyEvent.VK_UP && down == false) {
			up = true;
			down = false;
			left = false;
			right = false;
			first = true;
		} else if (keyCode == KeyEvent.VK_DOWN && up == false) {
			up = false;
			down = true;
			left = false;
			right = false;
			first = true;
		} else if (keyCode == KeyEvent.VK_LEFT && right == false) {
			up = false;
			down = false;
			left = true;
			right = false;
			first = true;
		} else if (keyCode == KeyEvent.VK_RIGHT && left == false) {
			// the right key must not be pressed 
			// the first time since the snakes orientation
			// is the to the left
			if(first == true){
				up = false;
				down = false;
				left = false;
				right = true;
			}
		}
		else if(t == null && keyCode == KeyEvent.VK_SPACE){
			restartCanvas();
		}
	}

	public void keyTyped(KeyEvent e) { 
	}
	public void keyReleased(KeyEvent e) {

	}

	// used to reset the game using the key listener.
	public void restartCanvas()
	{
		t = new Thread(this);
		t.start();
		up = false;
		down = false;
		left = false;
		right = false;
		c.reset();
		first = false;
	}
}


// *****************************************************************************************


class SnakeCanvas extends Canvas{
	private static final long serialVersionUID = 1L;

	// set up all the images needed for the canvas
	Image offscreen;
	Image heart;
	Image blackhole;
	Image snakehead;
	Image body;
	Image snakeheadVert;
	Image instructions;
	Image end;


	// other variables to move the snake, display the score
	// find random locations and the list for the snakes body
	int score = 0;
	Dimension offscreensize;
	Graphics g2;
	List<Point> sBody;
	int nx = 80;    // Board
	int ny = 50;	
	int dx = -1;    // direction
	int dy = 0;
	int size = 15; // size of the box
	Random num = new Random();
	int locationx = num.nextInt(nx); // random location on the canvas (used for food)
	int locationy = num.nextInt(ny); // random location on the canvas (used for food)
	Label scoreLabel = new Label("Score : " );

	// initialize the canvas
	public SnakeCanvas(Image heart, Image blackhole, Image snakehead, Image body, Image snakeheadVert, Image instructions, Image end) {
		this.heart = heart;
		this.blackhole = blackhole;
		this.snakehead = snakehead;
		this.body = body;
		this.snakeheadVert = snakeheadVert;
		this.instructions = instructions;
		this.end = end;
	}

	// reset the canvas for a new game
	public void reset(){
		locationx = num.nextInt(nx);
		locationy = num.nextInt(ny);
		sBody = new ArrayList<Point>();
		score = 0;
		resetscore();

		for (int i = 0; sBody.size() < 6; i ++){
			sBody.add(new Point(nx/2 + i, ny/2));
		}
		repaint();
	}

	// method to move the snake on the canvas
	public void moveSnake(int dx, int dy) {
		//System.out.println("move snake");
		Point head = sBody.get(0);
		int x = (int)head.getX();
		int y = (int)head.getY();
		Point newHead = new Point(x + dx, y + dy);
		sBody.add(0,  newHead);
		sBody.remove(sBody.size()-1);
		//Checks if the food is in the same position as the head
		if(sBody.get(0).getX() == locationx && sBody.get(0).getY() == locationy){
			Point grow = new Point(locationx, locationy); // Creates a new point when food is eaten
			sBody.add(0,  grow); // adds that new point to the snake, so it grows
			locationx = num.nextInt(nx);
			locationy = num.nextInt(ny);
			score();
		}
		repaint();
	}

	// method to display the score
	// after a heart has been eaten
	public void score(){
		score ++;
		scoreLabel.setText("Score: " + score);
		System.out.println(score);
	}

	// reset the score at the begining of the game
	public void resetscore(){
		scoreLabel.setText("Score: " + score);
		System.out.println(score);
	}


	public void paint(Graphics g) {
		update(g);
	}

	// draw the actual snake and the rest of the game
	// handles flickering
	public void update(Graphics g) {
		Dimension d = getSize();
		// if the snake goes beyond the border, then kill it
		// the head of the snake is not displayed since it is out
		// of the borders of the game.
		if(sBody.get(0).getX() > nx || sBody.get(0).getY() < 0
				|| sBody.get(0).getY() > ny || sBody.get(0).getX() < 0){
			Snakefinal.t = null;
		}
		// initially (or when size changes) create new image
		if ((offscreen == null)
				|| (d.width != offscreensize.width)
				|| (d.height != offscreensize.height)) {
			offscreen = createImage(d.width, d.height);
			offscreensize = d;
			g2 = offscreen.getGraphics();
			g2.setFont(getFont());
		}
		// erase old contents:
		g2.setColor(getBackground());
		g2.fillRect(0, 0, d.width, d.height);

		// now, draw as usual, but use g2 instead of g

		g2.drawImage(blackhole, 0, 0, this);
		g2.drawImage(heart, (locationx)*size, (locationy)*size, this);
		for (int i=0; i<sBody.size(); i++) {

			Point p = sBody.get(i);
			if (i == 0){
				if (Snakefinal.up || Snakefinal.down ){
					g2.drawImage(snakeheadVert, (p.x)*15, (p.y)*15, this);
				}
				if (Snakefinal.right || Snakefinal.left || !Snakefinal.first){
					g2.drawImage(snakehead, (p.x)*15, (p.y)*15, this);
				}
			}
			else{
				g2.drawImage(body, p.x*15, p.y*15, this);
				if(i>1){
					if (sBody.get(0).getX() == p.x && sBody.get(0).getY() == p.y){
						Snakefinal.t = null;
					}
				}
				if(!Snakefinal.first){
					g2.drawImage(instructions, 280, 220, this);
				}
				if(Snakefinal.t == null){
					g2.drawImage(end, 280, 220, this);
				}
			}
		}
		// finally, draw the image on top of the old one
		g.drawImage(offscreen, 0, 0, null); 
	}
}