package com.Project3.BackEnd.RoutesManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dijsktra {
	private ArrayList<Station> uncheckedStations, route;
	private HashMap<Station, Float> weights;
	private HashMap<Station, Station> procedence;
	private Graph graph;

	public Dijsktra() {
		graph = Graph.getInstance();
		uncheckedStations = graph.getStations();
		route = new ArrayList<Station>();
		weights = new HashMap<Station, Float>();
		procedence = new HashMap<Station, Station>();
	}

	public ArrayList<Station> execute(Station origin) {
		uncheckedStations.remove(origin);
		weights.put(origin, (float) 0);
		procedence.put(origin, null);
		Station tempStation = origin;
		while (uncheckedStations.size() > 0) {
			if (tempStation.getConnections() != null) {
				for (Connection connection : tempStation.getConnections()) {
					Station destiny = connection.getDestiny();
					Float distance = connection.getDistance() + tempStation.getAccumWeight();
					if (!weights.containsKey(destiny)) {// if the destiny hasn't been analyzed, it is added to the map
						weights.put(destiny, distance);
						destiny.setAccumWeight(distance);
						procedence.put(destiny, tempStation);
					} else if (weights.get(destiny) > distance) {// replaces distance if it's shorter than the actual
																	// value
						weights.replace(destiny, distance);
						destiny.setAccumWeight(distance);
						procedence.replace(destiny, tempStation);
					}
				}

			}
			tempStation = getNearest();
		}
		return route;
	}

	public Station getNearest() {
		Station nearest = null;
		Float distance = Float.MAX_VALUE;
		for (Map.Entry<Station, Float> entry : this.weights.entrySet()) {
			if (entry.getValue() < distance & uncheckedStations.contains(entry.getKey())) {
				nearest = entry.getKey();
				distance = entry.getValue();
			}
		}
		uncheckedStations.remove(nearest);
		return nearest;
	}
}
