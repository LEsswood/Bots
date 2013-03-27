package Root;

public class MsgBox
{
	public int lifetime;
	public String message;
	public int x;
	public int y;
	public MsgBox(String message, int x, int y)
	{
		lifetime = 30;
		this.message = message;
		this.x = x;
		this.y = y;
	}
}
