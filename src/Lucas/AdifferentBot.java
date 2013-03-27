package Lucas;

import java.util.List;
import Root.*;
public class AdifferentBot extends UserBot{

	private boolean amIshooting = false;
	private boolean amIwalking = false;
	private boolean amIShotAt = false;
	
	private IBot aim = null;
	
	private int lastHealth;
	
	private int zigzagCount;
	private int walkCount;
	
	public AdifferentBot() {
		lastHealth = this.getHealth();
		zigzagCount = 0;
		walkCount = 0;
	}
	
	@Override
	public void update(WorldView view) 
	{
		List<IBot> bots = view.getBotsInView();
		checkHealth();
		firing(bots);
		
		if(aim == null) {
			lookAround();
			this.walkInCircle();
		}
		
		
	}
	
	private void lookAround() {
		turnTo(Math.PI / 5.0);
	}
	
	private void firing(List<IBot> bots) {
		if(getAmmo() == 0) {
			stopShooting();
			amIshooting = false;
			reload();
		}
		else {
			//your target disappears: he got killed or away
			if(aim != null && bots.indexOf(aim)== -1) {
				aim = null;
				amIshooting = false;
				stopShooting();
			}
			//if you have no target, look for a new one within sight
			if(aim == null && !bots.isEmpty()) {
				boolean first = false;
				for(IBot b : bots) if(this.getTeamID() != b.getTeamID() || first) {
					this.aim = b;
					first = true;
				}
			}
			//if you get an aim fire at it!!!!
			if(aim != null) fireAtSomeone(aim);
			
			//if no-one is around, reload
			else reload();
		}
	}
	
	private void fireAtSomeone(IBot b){
	    double angle = getAngle (b); //get the angle between the bot and your direct line of sight
	    this.turnTo(angle);
	    this.startShooting();
	    this.amIshooting = true;
	}
	private double getAngle(IBot b){
		return Math.atan2(b.getLocation()[1] , b.getLocation()[0]);
	}
	/**
	 * checks whether you are shot at or not
	 */
	private void checkHealth() {
		amIShotAt = (this.getHealth() != this.lastHealth);
		lastHealth = this.getHealth();
	}
	private void walkZigZag() {
		double angle1 =  (Math.PI / 4.0);
		double angle2 = -1.0 * angle1;
		if(!amIwalking) startWalking(angle1);
		if(zigzagCount == 20) {
			stopWalking();
			startWalking(angle2);
		}
		zigzagCount = (zigzagCount + 1) / 40; 
	}
	private void walkInCircle() {
		stopWalking();
		startWalking(Math.PI / 50.0);
	}
}
