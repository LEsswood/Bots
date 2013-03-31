package Root;

import java.awt.geom.Path2D.Double;

import Lawrence.*;
import Tests.*;

public class TestGame extends Game
{
	public TestGame(BotFactory a, BotFactory b)
	{
		super();
		//add some terrain
		
		terrain.moveTo(200, 200);
		terrain.lineTo(400, 200);
		terrain.lineTo(300, 310);
		terrain.lineTo(220, 300);
		terrain.lineTo(200, 200);
		
		terrain.moveTo(400, 400);
		terrain.lineTo(600, 400);
		terrain.lineTo(500, 510);
		terrain.lineTo(520, 600);
		terrain.quadTo(500, 500, 400, 400);
		
		
		//create 5 a side.
		BasicBot bot;
		for(int i = 0;i < 1;i++)
		{
			bot = this.buildBot(a, 100, 400 + 100*i);
			bot.angle = 0;
			bot = this.buildBot(b, 800, 100 + 100*i);
			bot.angle = Math.PI;
		}
		
	}
	
	public static void main(String[] args) throws Exception
	{
		final Game g = new TestGame(new TestFactory(0,GameType.Deathmatch),new LawrenceFactory(1,GameType.Deathmatch));
	}
}
