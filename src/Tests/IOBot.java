package Tests;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Root.*;

public class IOBot extends UserBot implements KeyListener {

	boolean w,a,s,d = false;
	
	@Override
	public void update(WorldView view) 
	{
		if(w)
		{
			this.startWalking(this.getDirection());
		}
		else if(s)
		{
			this.startWalking(this.getDirection()+Math.PI);
		}
		else
		{
			this.stopWalking();
		}
		
		if(a)
		{
			this.turnTo(this.getDirection()-0.1);
		}
		else if(d)
		{
			this.turnTo(this.getDirection()+0.1);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		w = arg0.getKeyCode() == KeyEvent.VK_W | w;
		a = arg0.getKeyCode() == KeyEvent.VK_A | a;
		s = arg0.getKeyCode() == KeyEvent.VK_S | s;
		d = arg0.getKeyCode() == KeyEvent.VK_D | d;
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		w = arg0.getKeyCode() != KeyEvent.VK_W & w;
		a = arg0.getKeyCode() != KeyEvent.VK_A & a;
		s = arg0.getKeyCode() != KeyEvent.VK_S & s;
		d = arg0.getKeyCode() != KeyEvent.VK_D & d;
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

}
