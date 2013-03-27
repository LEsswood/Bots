package Root;

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
}
