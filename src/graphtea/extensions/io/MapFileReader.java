package graphtea.extensions.io;

import graphtea.graph.graph.GraphPoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Sebastian Glaﬂ
 * @since 03.05.2015
 *
 */

public class MapFileReader {

	int actualID = 0;
	List<MapPoint> points = new ArrayList<MapPoint>();

	public MapFileReader(String path) {
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(
					new File(path)));
			String str = null;
			String actual = "";
			int xbuff = 0, ybuff = 0, idbuff = 0;

			while ((str = br.readLine()) != null) {
				if (str.startsWith("</" + actual)) {
					actualID++;
					points.add(new MapPoint(actual, idbuff, xbuff, ybuff));
					actual = "";
					
				} else if (str.startsWith("<")) {
					actual = str.substring(str.indexOf("<") + 1,
							str.indexOf(">"));
				} else {
					if (!actual.equals("") & str.contains("="))// if in actual
																// block
					{
						String[] list = str.split("=");
						switch (list[0].toLowerCase()) {
						case "x":
							xbuff = Integer.parseInt(list[1]);
							break;
						case "y":
							ybuff = Integer.parseInt(list[1]);
							break;
						case "id":
							idbuff = Integer.parseInt(list[1]);
							break;
						default:
							break;
						}
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public GraphPoint getPositionByID(int id) {
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).getID() == id)
				return (GraphPoint) points.get(i);
		}
		return null;
	}

	public GraphPoint getPositionByLabel(String label) {
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).getName().equals(label))
				return (GraphPoint) points.get(i);
		}
		return null;
	}
}

class MapPoint extends GraphPoint {

	private static final long serialVersionUID = -1121005549351322119L;
	private String name;
	private int id;

	public MapPoint(String s, int id, int x, int y) {
		super(x, y);
		this.id = id;
		this.name = s;
	}

	public String getName() {
		return name;
	}

	public int getID() {
		return id;
	}
}
