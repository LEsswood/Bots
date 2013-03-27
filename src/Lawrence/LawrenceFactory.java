package Lawrence;

import Root.BotFactory;
import Root.GameType;
import Root.UserBot;

public class LawrenceFactory extends BotFactory
{
	public LawrenceFactory(int teamID, GameType gameType)
	{
		super(teamID, gameType);
	}

	@Override
	public UserBot build(int id, int gameTimePassed)
	{
		return new LawrenceBot();
	}
}