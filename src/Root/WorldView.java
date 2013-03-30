package Root;

import java.util.List;
import java.awt.geom.Path2D.Double;

public class WorldView
{
	private List<IBot> botsInView;
	private List<String> shouts;
	private List<Double> terrain;
	private List<WorldObject> objectsInView;
	private int gameTime;
	private GameType gameType;
	
	public WorldView(List<IBot> botsInView, List<String> shouts,
			List<Double> terrain, List<WorldObject> objectsInView,
			int gameTime, GameType gameType)
	{
		super();
		this.botsInView = botsInView;
		this.shouts = shouts;
		this.terrain = terrain;
		this.objectsInView = objectsInView;
		this.gameTime = gameTime;
		this.gameType = gameType;
	}

	public int getGameTime()
	{
		return gameTime;
	}

	public GameType getGameType()
	{
		return gameType;
	}

	// covers everything now apart from terrain . Even bots. (because I felt
	// giving all the terrain info because you can see a little
	// corner was OP.
	// I offer the bots view for backwards compatibility.
	public List<WorldObject> getObjectsInView()
	{
		return objectsInView;
	}

	// these are copies of the Bots, calling updates on them do nothing, and
	// their location is relative to you
	// They will all be of type 'ShellBot'. If you want to see what data is
	// hidden, just look in the class.
	public List<IBot> getBotsInView()
	{
		return botsInView;
	}

	// I will make these limited range soon. For now, you can shout as much as
	// you want, as far as you want. Yay!
	public List<String> getShouts()
	{
		return shouts;
	}

	// please comment if this is a really bad way of representing objects in
	// view.
	public List<Double> getTerrain()
	{
		return terrain;
	}
}
