
public class SoccerGame extends JPanel {
   private static final int UPDATE_RATE = 30;  // Frames per second (fps)
   
   /**
      0 index contains the goalie
      1-3 index contains defenders
      
   */
   private ArrayList<Person> team1;
   private ArrayList<Person> team2;
   
   private ContainerBox box;  // The container rectangular box
  
   private DrawCanvas canvas; // Custom canvas for drawing the box/ball
   private int canvasWidth;
   private int canvasHeight;
  
   /**
    * Set the drawing canvas to fill the screen (given its width and height).
    * Initializes balls
    * @param width : screen width
    * @param height : screen height
    */
   public SoccerGame(int width, int height) {
  
      canvasWidth = width;
      canvasHeight = height;
      balls = new ArrayList(0);
      // Initialize the ball at a random location (inside the box) and moveAngle
      for(int i = 0; i < numberOfPersons; i++){
         Random rand = new Random();
         int radius = 60;
         
         int x = rand.nextInt(canvasWidth - radius * 2 - 20) + radius + 10;
         int y = rand.nextInt(canvasHeight - radius * 2 - 20) + radius + 10;
         int speed = 3; //(int)(Math.random()*10+3);
         int angleInDegree = rand.nextInt(360);
         balls.add(new Person(x, y, radius, speed, angleInDegree, Color.BLUE));
      }
     
      // Initialize the Container Box to fill the screen
      box = new ContainerBox(0, 0, canvasWidth, canvasHeight, Color.LIGHT_GRAY, Color.WHITE);
     
      // Initialize the custom drawing panel for drawing the game
      canvas = new DrawCanvas();
      this.setLayout(new BorderLayout());
      this.add(canvas, BorderLayout.CENTER);
      
      // Handling window resize.
      this.addComponentListener(new ComponentAdapter() {
         @Override
         public void componentResized(ComponentEvent e) {
            Component c = (Component)e.getSource();
            Dimension dim = c.getSize();
            canvasWidth = dim.width;
            canvasHeight = dim.height;
            // Adjust the bounds of the container to fill the window
            box.set(0, 0, canvasWidth, canvasHeight);
         }
      });
  
      // Start the ball bouncing
      gameStart();
   }
   
   /** Start the ball bouncing. */
   public void gameStart() {
      // Run the game logic in its own thread.
      Thread gameThread = new Thread() {
         public void run() {
            while (true) {
               // Execute one time-step for the game 
               gameUpdate();
               // Refresh the display
               repaint();
               // Delay and give other thread a chance
               try {
                  Thread.sleep(1000 / UPDATE_RATE);
               } catch (InterruptedException ex) {}
            }
         }
      };
      gameThread.start();  // Invoke GaemThread.run()
   }
   
   /** 
    * One game time-step. 
    * Update the game objects, with proper collision detection and response.
    */
   public void gameUpdate() {
      for (int a = 0; a < balls.size(); a++){
         for (int c = a+1; c < balls.size(); c++){
            if (Math.pow(balls.get(a).getX() - balls.get(c).getX(), 2) + Math.pow(balls.get(a).getY() - balls.get(c).getY(), 2) <= Math.pow(balls.get(a).getR()+balls.get(c).getR(), 2)){
               balls.remove(c);
            }
         }
         balls.get(a).move(box);
      }
   }
   
   /** The custom drawing panel for the bouncing ball (inner class). */
   class DrawCanvas extends JPanel {
      /** Custom drawing codes */
      @Override
      public void paintComponent(Graphics g) {
         super.paintComponent(g);    // Paint background
         // Draw the box and the ball
         box.draw(g);
         for (int b = 0; b < balls.size(); b++) {
            balls.get(b).draw(g);
         }
      }
  
      /** Called back to get the preferred size of the component. */
      @Override
      public Dimension getPreferredSize() {
         return (new Dimension(canvasWidth, canvasHeight));
      }
   }
}
