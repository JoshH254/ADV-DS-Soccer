
public class Person {
   private double x, y;
   private double speedX, speedY;
   private double radius;
   private Color color;
   private double speedFactor;
   private double angle;
  
   /**
    * Constructor: For user friendliness, user specifies velocity in speed and
    * moveAngle in usual Cartesian coordinates. Need to convert to speedX and
    * speedY in Java graphics coordinates for ease of operation.
    */
   public Person(double x, double y, double radius, double speed, double angleInDegree,
         Color color) {
      this.x = x;
      this.y = y;
      // Convert (speed, angle) to (x, y), with y-axis inverted
      this.speedX = (double)(speed * Math.cos(Math.toRadians(angleInDegree)));
      this.speedY = (double)(-speed * (double)Math.sin(Math.toRadians(angleInDegree)));
      this.radius = radius;
      this.color = color;
   }

   /** Draw itself using the given graphics context. */
   public void draw(Graphics g) {
      g.setColor(color);
      g.fillOval((int)(x - radius), (int)(y - radius), (int)(2 * radius), (int)(2 * radius));
   }
   public double getX() {return x;}
   public double getY() {return y;}
   public double getR() {return radius;}
   /** 
    * Make one move, check for collision and react accordingly if collision occurs.
    * 
    * @param box: the container (obstacle) for this ball. 
    */
   public void move(ContainerBox box) {
      // Get the ball's bounds, offset by the radius of the ball
      double ballMinX = box.minX + radius;
      double ballMinY = box.minY + radius;
      double ballMaxX = box.maxX - radius;
      double ballMaxY = box.maxY - radius;
   
      // Calculate the ball's new position
      x += speedX;
      y += speedY;
      // Check if the ball moves over the bounds. If so, adjust the position and speed.
      if (x < ballMinX) {
         speedX = -speedX; // Reflect along normal
         x = ballMinX;     // Re-position the ball at the edge
      } else if (x > ballMaxX) {
         speedX = -speedX;
         x = ballMaxX;
      }
      // May cross both x and y bounds
      if (y < ballMinY) {
         speedY = -speedY;
         y = ballMinY;
      } else if (y > ballMaxY) {
         speedY = -speedY;
         y = ballMaxY;
      }  
   }

}