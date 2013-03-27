package Root;

public abstract class BotFactory
{
	private int teamID;
	private GameType gameType;

	public BotFactory(int teamID, GameType gameType)
	{
		super();
		this.teamID = teamID;
		this.gameType = gameType;
	}

	public GameType getGameType()
	{
		return gameType;
	}

	public final int getTeamId()
	{
		return teamID;
	}

	public abstract UserBot build(int id, int gameTimePassed);
	// WARNING. Please use the id's in this class when you create your Bot. They
	// won't actually be assigned until
	// after your method has completed. This is due to me wanting to keep the
	// constructors clear (mostly to stop you fucking them up.).
	// Also, your rotation will also be changed after this being built.
}