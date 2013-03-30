package Root;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Bullet
{
	public double x;
	public double y;
	
	public double dx;
	public double dy;
	
	public IBot shooter;
	
	public Bullet(double x, double y, double dx, double dy, IBot shooter)
	{
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.shooter = shooter;
	}
	public Rectangle2D getBoundingRectangle()
	{
		return new Rectangle2D.Double(x-Globals.BULLET_SIZE, y-Globals.BULLET_SIZE, Globals.BULLET_SIZE*2, Globals.BULLET_SIZE*2);
	}
	public Shape getBoundingShape()
	{
		return (new Ellipse2D.Double(x-Globals.BULLET_SIZE, y-Globals.BULLET_SIZE, Globals.BULLET_SIZE*2, Globals.BULLET_SIZE*2));
	}
}
