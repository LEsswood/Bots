package Root;
//Please also extend 'BasicBot'

public interface IBot
{
	public void update(WorldView view);
	
	
	
	public int getHealth();
	public int getAmmo();
	public double[] getLocation();
	public double getDirection();
	
	
	public void turnTo(double angle);
	public void startShooting();
	public void stopShooting();
	public void startWalking(double angle);
	public void stopWalking();
	public void reload();
	public void shout(String message);
	
	public int getID();
	public int getTeamID();
	
}
