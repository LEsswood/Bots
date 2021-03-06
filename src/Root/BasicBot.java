package Root;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;


public abstract class BasicBot implements IBot
{
	int health = Globals.HEALTH_MAX;
	
	int ammo = Globals.AMMO_MAX;
	int reloadCount = Globals.RELOAD_TIME;
	
	double x;
	double y;
	
	double vx;
	double vy;
	
	double angle = 0;
	double targetAngle = 0;
	
	int id;
	int teamid;
	
	boolean shooting;
	boolean reloading;
	boolean walking;
	boolean hit;
	
	double walkAngle = 0;
	
	GameType gt = null;
	Area viewCone;
	public abstract void update(WorldView view);
	
	
	Rectangle2D getRectBounds()
	{
		return new Rectangle2D.Double(x-Globals.PLAYER_SIZE,y-Globals.PLAYER_SIZE,Globals.PLAYER_SIZE*2,Globals.PLAYER_SIZE*2);
	}
	Rectangle2D getNextRectBounds()
	{
		return new Rectangle2D.Double(x-Globals.PLAYER_SIZE+vx,y-Globals.PLAYER_SIZE+vy,Globals.PLAYER_SIZE*2,Globals.PLAYER_SIZE*2);
	}
	Shape getBoundingShape()
	{
		return (new Ellipse2D.Double(x-Globals.PLAYER_SIZE,y-Globals.PLAYER_SIZE,Globals.PLAYER_SIZE*2,Globals.PLAYER_SIZE*2));
	}
	Shape getNextBoundingShape()
	{
		return (new Ellipse2D.Double(x-Globals.PLAYER_SIZE+vx,y-Globals.PLAYER_SIZE+vy,Globals.PLAYER_SIZE*2,Globals.PLAYER_SIZE*2));
	}
	public int getHealth() {return health;}

	public int getAmmo() {
		return ammo;
	}

	public double[] getLocation() {
		return new double[]{x,y};
	}

	public double getDirection() {
		return angle;
	}

	public void turnTo(double angle)
	{
		targetAngle = angle%(Math.PI*2);
	}
	public void startShooting() 
	{
		reloading = false;
		shooting = true;
	}
	public void stopShooting()
	{
		shooting = false;
	}
	public void startWalking(double angle) 
	{
		walking = true;
		walkAngle = angle%(Math.PI*2);
	}
	public void stopWalking()
	{
		walking = false;
	}
	public void reload()
	{
		shooting = false;
		reloading = true;
	}
	public void shout(String message)
	{
		for(ShoutListener sl:listeners)
		{
			sl.shoutEvent(this, message);
		}
	}
	private List<ShoutListener> listeners = new LinkedList<ShoutListener>();
	
	void registerListener(ShoutListener lsn)
	{
		listeners.add(lsn);
	}
	void unregisterListener(ShoutListener lsn)
	{
		listeners.remove(lsn);
	}
	
	public int getID()
	{
		return id;
	}
	public int getTeamID()
	{
		return teamid;
	}
}
