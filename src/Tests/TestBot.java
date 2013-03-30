package Tests;

import Root.*;

public class TestBot extends UserBot {

	IBot b = null;
	int think = 10;
	@Override
	public void update(WorldView view) 
	{
		this.startWalking(0);
		this.startShooting();
	}

}
