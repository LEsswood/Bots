package Root;

public abstract class UserBot extends BasicBot
{

	@Override
	public final int getHealth() {
		return super.getHealth();
	}

	@Override
	public final int getAmmo() {
		return super.getAmmo();
	}

	@Override
	public final double[] getLocation() {
		return new double[]{0,0};
	}

	@Override
	public final double getDirection() {
		return super.getDirection();
	}

	@Override
	public abstract void update(WorldView view);
	
	public final void turnTo(double angle) {
		super.turnTo(angle);
	}

	public final void startShooting() {
		super.startShooting();
	}

	public final void stopShooting() {
		super.stopShooting();
	}

	public final void startWalking(double angle) {
		super.startWalking(angle);
	}

	public final void stopWalking() {
		super.stopWalking();
	}

	public final void reload() {
		super.reload();
	}

	public final void shout(String message) {
		super.shout(message);
	}

	public final int getID() {
		return super.getID();
	}

	public final int getTeamID() {
		return super.getTeamID();
	}
}