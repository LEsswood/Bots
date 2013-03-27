package Root;
public class ShellBot extends BasicBot
{

	public ShellBot(BasicBot b, double xoffset, double yoffset)
	{
		this.x = b.x - xoffset;
		this.y = b.y - yoffset;
		
		this.teamid = b.teamid;
		this.id = b.id;
		
		this.health = b.health;
		
	}
	
	public void update(WorldView view) {}

	public int getAmmo() {
		return 0;
	}
	public void turnTo(float angle) {}
	public void startShooting() {}
	public void stopShooting() {}
	public void startWalking(float angle) {}
	public void stopWalking() {}
	public void reload() {}
	public void shout(String message) {}
}
