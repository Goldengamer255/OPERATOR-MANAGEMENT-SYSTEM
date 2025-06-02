package nz.ac.auckland.se281;

import java.util.List;

/** This class is the main entry point. */
public class MapEngine {
  private Graph graph;

  public MapEngine() {
    // add other code here if you wan
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {

    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    graph = new Graph();

    // Add countries as nodes
    for (String line : countries) {
      String[] parts = line.split(",");
      String country = parts[0].trim();
      String continent = parts[1].trim();
      int fuelCost = Integer.parseInt(parts[2].trim());
      graph.addCountry(country, continent, fuelCost);
    }

    // Add adjacencies as edges
    for (String line : adjacencies) {
      String[] parts = line.split(",");
      String country = parts[0].trim();
      // Each subsequent part is a neighbor
      for (int i = 1; i < parts.length; i++) {
        String neighbor = parts[i].trim();
        graph.addEdge(country, neighbor);
      }
    }
    System.out.println("India neighbors: " + graph.getNeighbors("India"));
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    MessageCli.INSERT_COUNTRY.printMessage();
    String country = Utils.scanner.nextLine().trim();
    try {
      if (!graph.hasCountry(country)) {
        throw new Exception();
      }
      String continent = graph.getContinent(country);
      int fuelCost = graph.getFuelCost(country);
      List<String> neighbours = graph.getNeighbors(country);
      MessageCli.COUNTRY_INFO.printMessage(
          country, continent, String.valueOf(fuelCost), neighbours.toString());
    } catch (Exception e) {
      MessageCli.INVALID_COUNTRY.printMessage(country);
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
