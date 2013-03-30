package Root;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D.Double;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

import Lucas.*;
import Tests.*;
import Lawrence.*;
import Rob.*;

public class Game extends JFrame
{
	public List<BasicBot> bots;
	public List<String> s;
	public List<Bullet> bullets;
	public List<MsgBox> boxes;
	public Double terrain;
	
	private GameWindow gw;
	private ShoutListener sl;

	public Game()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		sl = new ShoutHandler();
		bots = new ArrayList<BasicBot>();
		s = new LinkedList<String>();
		bullets = new LinkedList<Bullet>();
		boxes = new LinkedList<MsgBox>();
		terrain = new Double();
		setSize(1000, 1000);
		setVisible(true);
		gw = new GameWindow();
		add(gw);
		int delay = 33; // milliseconds per game update
		ActionListener taskPerformer = new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				gameTick();
			}
		};
		new Timer(delay, taskPerformer).start();
	}

	protected void gameTick()
	{
		update();
		render();
	}

	int countID = 0;
	int gameTime = 0;

	protected BasicBot buildBot(BotFactory fac, double x, double y)
	{
		BasicBot b = fac.build(countID, gameTime);
		b.gt = fac.getGameType();
		b.x = x;
		b.y = y;
		b.teamid = fac.getTeamId();
		initBot(b);
		return b;
	}

	protected void initBot(BasicBot b)
	{
		b.id = countID;
		countID++;
		bots.add(b);
		b.registerListener(sl);

	}

	protected void clearBot(BasicBot b)
	{
		b.unregisterListener(sl);
	}

	private void render()
	{
		gw.repaint();
	}

	protected void update()
	{
		List<String> shoutsThisRound = s;
		s = new LinkedList<String>();
		for (BasicBot b : bots)
		{
			WorldView wv = new WorldView(calcLOS(b), shoutsThisRound,null,null,gameTime,b.gt);
			b.update(wv);
			b.hit = false;
			// calculate where you want to walk
			if (b.targetAngle != b.angle)
			{
				double da = shortestDifference(b.targetAngle, b.angle);
				if (Math.abs(da) < Globals.PLAYER_ROTATION_SPEED)
				{
					b.angle = b.targetAngle;
				} else if (da < 0)
				{
					b.angle += Globals.PLAYER_ROTATION_SPEED;
				} else
				{
					b.angle -= Globals.PLAYER_ROTATION_SPEED;
				}
				b.angle %= Math.PI * 2;
			}
			if (b.walking)
			{
				double absoluteWalk = b.walkAngle;
				b.vx = (Globals.PLAYER_SPEED * Math.cos(absoluteWalk));
				b.vy = (Globals.PLAYER_SPEED * Math.sin(absoluteWalk));
			} else
			{
				b.vx = 0;
				b.vy = 0;
			}
			if (b.reloading)
			{
				b.reloadCount--;
				if (b.reloadCount == 0)
				{
					b.ammo = Globals.AMMO_MAX;
					b.reloading = false;
				}
			} else
			{
				b.reloadCount = Globals.RELOAD_TIME;
			}
			if (b.shooting)
			{
				if (b.ammo > 0)
				{
					b.ammo--;
					Bullet bul = new Bullet(b.x, b.y,
							(Globals.BULLET_SPEED * Math.cos(b.angle)),
							(Globals.BULLET_SPEED * Math.sin(b.angle)), b);
					bullets.add(bul);
				}
			}
		}
		// move shit
		for (int i = 0; i < bots.size(); i++)
		{
			BasicBot b1 = bots.get(i);
			double px = b1.x + b1.vx;
			double py = b1.y + b1.vy;
			if (px < Globals.PLAYER_SIZE || px + Globals.PLAYER_SIZE > Globals.GAME_SIZE || py < Globals.PLAYER_SIZE
					|| py  + Globals.PLAYER_SIZE > Globals.GAME_SIZE)
				b1.hit = true;
			for (int j = i + 1; j < bots.size(); j++)
			{
				BasicBot b2 = bots.get(j);
				double dy = py - b2.y - b2.vy;
				double dx = px - b2.x - b2.vx;

				if (radialHit(dx, dy, Globals.PLAYER_SIZE * 2))
				{
					b1.hit = true;
					b2.hit = true;
				}
			}
			if (!b1.hit && b1.walking && !inTerrain(b1.getNextBoundingShape()))
			{
				b1.x += b1.vx;
				b1.y += b1.vy;
			}
		}
		// kill shit
		Iterator<Bullet> iter = bullets.iterator();
		while (iter.hasNext())
		{
			Bullet b = iter.next();
			b.x += b.dx;
			b.y += b.dy;
			Iterator<BasicBot> iter2 = bots.iterator();
			while (iter2.hasNext())
			{
				BasicBot bb = iter2.next();
				if (b.shooter != bb)
				{
					double dx = b.x - bb.x;
					double dy = b.y - bb.y;
					boolean hit = radialHit(dx, dy, Globals.PLAYER_SIZE
							+ Globals.BULLET_SIZE);
					if (hit)
					{
						bb.health--;
						if (bb.health == 0)
						{
							clearBot(bb);
							iter2.remove();
						}
					}
					if (b.x < 0 || b.x > Globals.GAME_SIZE || b.y < 0
							|| b.y > Globals.GAME_SIZE || hit || inTerrain(b.getBoundingShape()))
					{
						iter.remove();
						break;
					}
				}
			}
		}
		gameTime++;
	}
	public Boolean inTerrain(Shape bound)
	{
		if(terrain.intersects(bound.getBounds2D()))
		{
			Area clip = new Area(bound);
			clip.intersect(new Area(terrain));
			return !clip.isEmpty();
		}
		return false;
	}
	public List<IBot> calcLOS(BasicBot from)
	{
		List<IBot> ret = new LinkedList<IBot>();
		for (BasicBot b : bots)
		{
			if (from != b)
			{
				double dy = b.y - from.y;
				double dx = b.x - from.x;

				double theta = Math.abs(shortestDifference(from.angle,
						Math.atan2(dy, dx)));

				if (theta < Globals.VIEWCONE_THETA
						&& radialHit(dx, dy, Globals.VIEWCONE_R
								+ Globals.PLAYER_SIZE))
				{
					Line2D l2d = new Line2D.Double(from.x, from.y, b.x, b.y);
					
					boolean blockedView = false;
					
					for(Line2D l:new TerrainIterator(terrain))
					{
						if(l2d.intersectsLine(l))
						{
							blockedView = true;
							break;
						}
					}
					
					if(!blockedView)
					{
						ret.add(new ShellBot(b, from.x, from.y));
					}
				}
			}
		}
		return ret;
	}

	public static Boolean radialHit(double dx, double dy, double r)
	{
		return (dx * dx) + (dy * dy) < (r * r);
	}

	public static double shortestDifference(double a, double b)
	{
		double angle = (b - a) + (Math.PI * 2);
		angle %= Math.PI * 2;
		if (angle > Math.PI)
		{
			angle -= Math.PI * 2;
		}
		// System.out.println("A:" + a + " B:" + b + "C:" + angle);
		return angle;
	}

	public class GameWindow extends JComponent
	{

		private static final long serialVersionUID = 1L;

		@Override
		public Dimension getMinimumSize()
		{
			return new Dimension(100, 100);
		}

		@Override
		public Dimension getPreferredSize()
		{
			return new Dimension(1000, 1000);
		}

		Color[] teams = new Color[] { Color.BLUE, Color.RED, Color.CYAN,
				Color.PINK, Color.WHITE, Color.MAGENTA, Color.ORANGE };

		@Override
		public void paintComponent(Graphics g)
		{
			Dimension dim = getSize();
			super.paintComponent(g);
			g.setColor(Color.gray);
			g.fillRect(0, 0, dim.width, dim.height);
			g.setColor(Color.RED);
			g.drawRect(0, 0, Globals.GAME_SIZE, Globals.GAME_SIZE);
			Graphics2D g2d = (Graphics2D)g;
			for (BasicBot b : bots)
			{
				g.setColor(teams[b.teamid]);
				g.fillOval((int) (b.x - Globals.PLAYER_SIZE),
						(int) (b.y - Globals.PLAYER_SIZE),
						(int) (Globals.PLAYER_SIZE * 2),
						(int) (Globals.PLAYER_SIZE * 2));
				g.setColor(new Color(teams[b.teamid].getRed(), teams[b.teamid]
						.getBlue(), teams[b.teamid].getBlue(), 50));
				Shape ar = new Arc2D.Double(
						new Rectangle2D.Double(
								(b.x - Globals.VIEWCONE_R),
								(b.y - Globals.VIEWCONE_R),
								(Globals.VIEWCONE_R * 2),
								(Globals.VIEWCONE_R * 2)),
						Math.toDegrees(-b.angle - Globals.VIEWCONE_THETA),
						Math.toDegrees(Globals.VIEWCONE_THETA * 2),
						2);
				g2d.fill(ar);
				g.setColor(Color.BLACK);
				g.drawLine(
						(int) b.x,
						(int) b.y,
						(int) b.x
								+ (int) (Globals.PLAYER_SIZE * Math
										.cos(b.angle)),
						(int) b.y
								+ (int) (Globals.PLAYER_SIZE * Math
										.sin(b.angle)));
			}
			g.setColor(Color.WHITE);
			g2d.fill(terrain);
			g.setColor(Color.BLACK);
			g2d.draw(terrain);
			g.setColor(Color.YELLOW);
			for (Bullet b : bullets)
			{
				g.fillOval((int) (b.x - Globals.BULLET_SIZE),
						(int) (b.y - Globals.BULLET_SIZE),
						(int) (Globals.BULLET_SIZE * 2),
						(int) (Globals.BULLET_SIZE * 2));
			}
			Iterator<MsgBox> iter = boxes.iterator();
			while (iter.hasNext())
			{
				MsgBox mb = iter.next();
				mb.lifetime--;
				if (mb.lifetime == 0)
				{
					iter.remove();
				} else
				{
					String s = mb.message;
					if (s.length() > 100)
					{
						s = s.substring(0, 100) + "...";
					}
					int boxHeight = 10;
					Font f = new Font("Wispy", 0, boxHeight);
					g.setFont(f);
					int boxWidth = g.getFontMetrics().stringWidth(s);
					int xoff = -25;
					int yoff = -30;
					int alpha = (mb.lifetime < 20) ? (mb.lifetime * 255 / 30)
							: 255;
					g.setColor(new Color(255, 255, 255, alpha));
					g.fillRoundRect(mb.x + xoff - 1, mb.y + yoff - 1,
							boxWidth + 2, boxHeight + 2, 5, 5);
					g.setColor(new Color(0, 0, 0, alpha));
					g.drawRoundRect(mb.x + xoff - 1, mb.y + yoff - 1,
							boxWidth + 2, boxHeight + 2, 5, 5);
					g.drawString(s, mb.x + xoff, mb.y + yoff + boxHeight - 1);
				}
			}
		}
	}

	private class ShoutHandler implements ShoutListener
	{
		@Override
		public void shoutEvent(BasicBot sender, String message)
		{
			s.add(message);

			MsgBox mb = new MsgBox(message, (int) sender.x, (int) sender.y);

			boxes.add(mb);
		}
	}
}