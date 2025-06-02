package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Graph {
  private Map<String, Country> countries = new HashMap<>();
  private Map<String, List<String>> adjList = new HashMap<>();

  public void addCountry(String name, String continent, int fuelCost) {
    countries.put(name, new Country(name, continent, fuelCost));
    adjList.putIfAbsent(name, new ArrayList<>());
  }

  public void addEdge(String country, String neighbor) {
    if (!adjList.get(country).contains(neighbor)) {
      adjList.get(country).add(neighbor);
    }
  }

  public List<String> getNeighbors(String country) {
    return adjList.getOrDefault(country, new ArrayList<>());
  }

  public String getContinent(String country) {
    return countries.get(country).getContinent();
  }

  public int getFuelCost(String country) {
    return countries.get(country).getFuelCost();
  }

  public boolean hasCountry(String country) {
    return countries.containsKey(country);
  }

  // Finds the shortest route between two countries using BFS
  public List<String> findShortestRoute(String startCountry, String destCountry) {
    Map<String, String> prev = new HashMap<>();
    Queue<String> queue = new LinkedList<>();
    Set<String> visited = new HashSet<>();
    queue.add(startCountry);
    visited.add(startCountry);

    while (!queue.isEmpty()) {
      String current = queue.poll();
      if (current.equals(destCountry)) {
        // Build path
        LinkedList<String> path = new LinkedList<>();
        for (String at = destCountry; at != null; at = prev.get(at)) {
          path.addFirst(at);
        }
        return path;
      }
      for (String neighbor : getNeighbors(current)) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          prev.put(neighbor, current);
          queue.add(neighbor);
        }
      }
    }
    return null; // No route found
  }
}
