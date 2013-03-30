package Root;

import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.QuadCurve2D;
import java.util.Iterator;

public class TerrainIterator implements Iterable<Line2D>
{
	private PathIterator pi;

	public TerrainIterator(Path2D path)
	{
		super();
		pi = path.getPathIterator(null);
	}

	@Override
	public Iterator<Line2D> iterator()
	{
		Iterator<Line2D> it = new Iterator<Line2D>()
		{
			double[] pOut = new double[4];
			@Override
			public boolean hasNext()
			{
				return !pi.isDone();
			}

			@Override
			public Line2D next()
			{
				while (!pi.isDone())
				{
					double l0 = pOut[0];
					double l1 = pOut[1];

					int type = pi.currentSegment(pOut);
					pi.next();
					if (type != PathIterator.SEG_MOVETO)
					{
						return new Line2D.Double(l0, l1, pOut[0], pOut[1]);
					}
				}
				return null;
			}

			@Override
			public void remove()
			{
				//does nothing!
			}
		};
		return it;
	}
}
