package nz.ac.auckland.se281;

import java.util.*;

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
}
