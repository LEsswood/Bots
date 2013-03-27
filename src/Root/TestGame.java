package Root;

import Lawrence.*;

public class TestGame extends Game
{
	public TestGame(BotFactory a, BotFactory b)
	{
		super();
		//create 8 a side.
		BasicBot bot;
		for(int i = 0;i < 9;i++)
		{
			bot = this.buildBot(a, 100, 100 + 100*i);
			bot.angle = 0;
			bot = this.buildBot(b, 800, 100 + 100*i);
			bot.angle = Math.PI;
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		final Game g = new TestGame(new LawrenceFactory(0,GameType.Deathmatch),new LawrenceFactory(1,GameType.Deathmatch));
	}
}
