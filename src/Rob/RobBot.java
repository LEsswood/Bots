package Rob;

import java.util.List;
import Root.*;

public class RobBot extends UserBot {
	private boolean beingShot = false;
	private int lastHealth = 1;
	
	private int walkingDuration = 0;
	private double walkingDir = 0;
	
	private IBot leader = null;
	private IBot target = null;
	
	public RobBot() {
		lastHealth = this.getHealth();
	}
	
	@Override
	public void update(WorldView view) 
	{
		List<IBot> bots = view.getBotsInView();
		checkHealth();
		
		for (IBot b:bots) {
			if (
				((leader == null) && (b.getTeamID() == this.getTeamID())) ||
				((leader != null) && (b.getID() == leader.getID()))
			) {
				leader = b;
			}
		}

		if (leader != null) {
			if (bots.indexOf(leader) == -1) {
				leader = null;
			} else {
				if ((getDistance(leader) < 30) || (walkingDuration > getDistance(leader))) {
					this.stopWalking();
					walkingDuration = 0;
				} else {
					this.startWalking(getAngle(leader));
					walkingDuration ++;
				}
			}
		}
		
		if ((leader == null) || (target == null)) {
			if (walkingDuration == 0) {
				this.startWalking(Math.random() * 2 * Math.PI);
				this.turnTo(Math.random() * 2 * Math.PI);
			}
			walkingDuration ++;
			if (walkingDuration > 10) {
				this.stopWalking();
			}
		}

		boolean searchForNewTarget = target == null;
		for (IBot b:bots) {
			if (
				((target == null) && (b.getTeamID() != this.getTeamID())) ||
				((target != null) && ((b.getID() == target.getID()) || (searchForNewTarget)))
			) {
				if (searchForNewTarget && (target != null)) {
					if (getDistance(target) > getDistance(b)) {
						target = b;
					}
				} else {
					target = b;
				}
			}
		}

		if (target != null) {
			if (bots.indexOf(target) == -1) {
				target = null;
				this.stopShooting();
			} else {
				double targetAngle = getAngle(target);
				this.turnTo(targetAngle);
				if (getDistance(target) < 10) {
					this.startWalking(targetAngle + Math.PI);
				}
				boolean aimingAtAllies = false;
				for (IBot b:bots) {
					if ((b.getTeamID() == this.getTeamID()) && wouldHit(b) && (getDistance(b) < getDistance(target))) {
						aimingAtAllies = true;
						break;
					}
				}
				if (wouldHit(target) && !(aimingAtAllies)) {
					openFire();
				}
			}
		} else {
			this.reload();
			if (beingShot) {
				this.turnTo(this.getDirection() + Math.PI);
			}
		}
	}
	
	private void openFire() {
		if (this.getAmmo() <= 0) {
			this.reload();
		} else {
			this.startShooting();
		}
	}
	
	private void checkHealth() {
		beingShot = (this.getHealth() != this.lastHealth);
		lastHealth = this.getHealth();
	}
	
	private boolean wouldHit(IBot b) {
		return Math.abs(getAngle(b) - this.getDirection()) < Math.asin(12 / getDistance(b));
	}
	
	private double getDistance(IBot b) {
		double[] loc = b.getLocation();
		return Math.sqrt(Math.pow(loc[0], 2) + Math.pow(loc[1], 2));
	}

	private double getAngle(IBot b) {
		return Math.atan2(b.getLocation()[1], b.getLocation()[0]);
	}
}
