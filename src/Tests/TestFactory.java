package Tests;

import Root.BotFactory;
import Root.GameType;
import Root.UserBot;

public class TestFactory extends BotFactory
{
	public TestFactory(int teamID, GameType gameType)
	{
		super(teamID, gameType);
	}

	@Override
	public UserBot build(int id, int gameTimePassed)
	{
		return new TestBot();
	}
}