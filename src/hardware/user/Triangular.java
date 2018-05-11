package hardware.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import javax.swing.JButton;

public class Triangular extends JButton {

	public enum Rotation {

		UP,
		DOWN,
		LEFT,
		RIGHT;

	}

	private Polygon triangle;
	private Rotation rotation;

	/**
	 * Polygonal shape
	 * 
	 *      Let A be an arbitrary parallelogram, then we can summarize it's points
	 *      in a 1x1 matrix by which A(V) = |(V1-V0)x(V3-V0)| this is simple and trivial
	 *      although take now the 3 points to form a triangle such that V0,V1,V3=V2 (for the
	 *      sake of simplicity) equal the midpoint on a circle by which it's middle point line
	 *      M0M1 is parallel to the base of V0V2, then in a triangle will be the same where
	 *      V0V2 || M2M3. Thus M0M1 and M2M3 are parallel to each other and M0M3, M1M2 as well.
	 *      Now let's call upon our elementary school math and make a new shape realizing that A(thetha)=2A(M0M1M2M3)
	 *      =2|(M1-M0)x(M3-M0)|=2|((V1+V2/2)-(V0+V1))x((V3+V0/2)-(V0+V1/2))| and thus we have our
	 *      3 wonderful points by which (look at param)
	 * 
	 * @param 0 p1x top
	 * @param 0 p1y top
	 * @param 1 p2x m0
	 * @param 1 p2y m0
	 * @param 2 p3x m1
	 * @param 2 p3y m1
	 */

	public Triangular(Rotation rotation){

		this.rotation = rotation;
		Dimension size = getPreferredSize();
		size.width = size.height = Math.max(size.width, size.height);
		setPreferredSize(size);
		setText("");
		setContentAreaFilled(false);

	}
	
	/**
	 * Paints component
	 */
	protected void paintComponent(Graphics g) {
		
		if (getModel().isArmed()) {
			g.setColor(Color.lightGray);
		} else {
			g.setColor(getBackground());
		}
		
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		

		switch(rotation){
		case UP:
			
			xPoints[0] = getSize().width/2;
			xPoints[1] = 0;
			xPoints[2] = getSize().width;
			yPoints[0] = 0;
			yPoints[1] = getSize().height;
			yPoints[2] = getSize().height;
			
			
			break;
		case DOWN:

			
			xPoints[0] = 0;
			xPoints[1] = getWidth();
			xPoints[2] = getWidth()/2;
			yPoints[0] = 0;
			yPoints[1] = 0;
			yPoints[2] = getWidth()+5;
			
			break;
		case LEFT:
			
			xPoints[0] = 0;
			xPoints[1] = 0;
			xPoints[2] = getWidth();
			yPoints[0] = 0;
			yPoints[1] = getWidth();
			yPoints[2] = getWidth()/2;
			
			break;
		case RIGHT:
			
			xPoints[0] = 0;
			xPoints[1] = getSize().height+20;
			xPoints[2] = getSize().height;
			yPoints[0] = getSize().width/2;
			yPoints[1] = -15;
			yPoints[2] = getSize().width+4;
			
			break;
		}

		g.fillPolygon(xPoints, yPoints, xPoints.length);
		super.paintComponent(g);

	}
	
	
	/**
	 * @return if point is contained
	 */
	public boolean contains(int x, int y) {
		if (triangle == null ||
				!triangle.getBounds().equals(getBounds())) {
			int[] xPoints = {0, 0, getWidth()};
			int[] yPoints = {0, getHeight(), getHeight()/2};
			triangle = new Polygon(xPoints,yPoints,xPoints.length);
		}
		return triangle.contains(x, y);
	}
}