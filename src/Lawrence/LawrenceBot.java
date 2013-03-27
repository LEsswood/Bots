package Lawrence;

import java.util.List;

import Root.*;
//An example bot;
public class LawrenceBot extends UserBot {


	IBot targ = null;
	@Override
	public void update(WorldView view) 
	{
		//getting the old view
		List<IBot> bots = view.getBotsInView();
		if(Math.random() < 0.0001f)
		{
			shout("I like big butts and I cannot lie");
		}
		this.reload();

		if(bots.size() == 0)
		{
			jitter();
		}
		else
		{
			//see if our friend is around
			if(targ != null)
			{
				boolean around = false;
				for(IBot ib:bots)
				{
					if(ib.getID() == targ.getID())
					{
						around = true;
						targ = ib;
						break;
					}
				}
				if(!around)
				{
					shout("Target Lost!");
					targ = null;
				}
			}
			//find target
			if(targ == null)
			{
				for(IBot ib:bots)
				{
					if(ib.getTeamID() != this.getTeamID())
					{
						shout("Target found!");
						targ = ib;
						break;
					}
				}
			}
			if(targ != null)
			{
				fireAt(targ);
			}
			else
			{
				jitter();
			}
		}
	}
	private void fireAt(IBot ib)
	{
		stopWalking();
		double ta = Math.atan2(ib.getLocation()[1], ib.getLocation()[0]);
		double r = Math.hypot(ib.getLocation()[0], ib.getLocation()[1]);
		if(r > Globals.VIEWCONE_R*0.8f)
		{
			startWalking(ta);
		}
		if(r < Globals.VIEWCONE_R*0.2f)
		{
			startWalking(ta+Math.PI);
		}
		turnTo(ta);
		if(this.getAmmo() > 0)
		{
			this.startShooting();
		}
	}
	double mova = 0f;
	private void jitter()
	{
		this.turnTo(this.getDirection() + Globals.PLAYER_ROTATION_SPEED);
		mova += (Math.random()*Math.PI/8) - (Math.PI/16);
		this.startWalking(this.getDirection() +  mova);
	}
}
