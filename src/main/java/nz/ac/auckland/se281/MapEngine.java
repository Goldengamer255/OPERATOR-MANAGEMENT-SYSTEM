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
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    while (true) {
      MessageCli.INSERT_COUNTRY.printMessage();
      String countryInput = Utils.scanner.nextLine().trim();
      String[] words = countryInput.split("\\s+");
      StringBuilder sb = new StringBuilder();
      for (String word : words) {
        if (word.length() > 0) {
          sb.append(Character.toUpperCase(word.charAt(0)));
          if (word.length() > 1) {
            sb.append(word.substring(1)); // keep rest as user input
          }
          sb.append(" ");
        }
      }
      String country = sb.toString().trim();
      try {
        if (!graph.hasCountry(country)) {
          throw new Exception();
        }
        String continent = graph.getContinent(country);
        int fuelCost = graph.getFuelCost(country);
        List<String> neighbours = graph.getNeighbors(country);
        MessageCli.COUNTRY_INFO.printMessage(
            country, continent, String.valueOf(fuelCost), neighbours.toString());
        break; // Exit loop if successful
      } catch (Exception e) {
        MessageCli.INVALID_COUNTRY.printMessage(country);
      }
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    String startCountry = null;
    String destCountry = null;

    // Ask for start country
    while (true) {
      MessageCli.INSERT_SOURCE.printMessage();
      String input = Utils.scanner.nextLine().trim();
      String[] words = input.split("\\s+");
      StringBuilder sb = new StringBuilder();
      for (String word : words) {
        if (word.length() > 0) {
          sb.append(Character.toUpperCase(word.charAt(0)));
          if (word.length() > 1) {
            sb.append(word.substring(1)); // keep rest as user input
          }
          sb.append(" ");
        }
      }
      String country = sb.toString().trim();
      if (!graph.hasCountry(country)) {
        MessageCli.INVALID_COUNTRY.printMessage(country);
      } else {
        startCountry = country;
        break;
      }
    }

    // Ask for destination country
    while (true) {
      MessageCli.INSERT_DESTINATION.printMessage();
      String input = Utils.scanner.nextLine().trim();
      String[] words = input.split("\\s+");
      StringBuilder sb = new StringBuilder();
      for (String word : words) {
        if (word.length() > 0) {
          sb.append(Character.toUpperCase(word.charAt(0)));
          if (word.length() > 1) {
            sb.append(word.substring(1)); // keep rest as user input
          }
          sb.append(" ");
        }
      }
      String country = sb.toString().trim();
      if (!graph.hasCountry(country)) {
        MessageCli.INVALID_COUNTRY.printMessage(country);
      } else if (country.equals(startCountry)) {
        MessageCli.NO_CROSSBORDER_TRAVEL.printMessage(country);
        return; // End the method if no cross-border travel is required
      } else {
        destCountry = country;
        break;
      }
    }

    // --- Compute the fastest route (BFS for shortest path in unweighted graph) ---
    List<String> route = graph.findShortestRoute(startCountry, destCountry);
    if (route == null || route.isEmpty()) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage(startCountry, destCountry);
      return;
    }

    // Display the route
    MessageCli.ROUTE_INFO.printMessage(route.toString());

    // --- Compute total fuel required (excluding start and destination) ---
    int totalFuel = 0;
    // Map to store continent and its fuel consumption
    java.util.Map<String, Integer> continentFuel = new java.util.LinkedHashMap<>();
    // Set to store continents visited in order
    java.util.LinkedHashSet<String> continentsVisited = new java.util.LinkedHashSet<>();

    for (int i = 1; i < route.size() - 1; i++) {
      String country = route.get(i);
      int fuel = graph.getFuelCost(country);
      String continent = graph.getContinent(country);
      totalFuel += fuel;
      continentsVisited.add(continent);
      continentFuel.put(continent, continentFuel.getOrDefault(continent, 0) + fuel);
    }

    MessageCli.FUEL_INFO.printMessage(String.valueOf(totalFuel));

    // --- Display continents visited and fuel per continent ---
    for (String continent : continentsVisited) {
      int fuel = continentFuel.getOrDefault(continent, 0);
      MessageCli.FUEL_CONTINENT_INFO.printMessage(continent, String.valueOf(fuel));
    }

    // --- Find continent with highest fuel spent ---
    String maxContinent = null;
    int maxFuel = -1;
    for (java.util.Map.Entry<String, Integer> entry : continentFuel.entrySet()) {
      if (entry.getValue() > maxFuel) {
        maxFuel = entry.getValue();
        maxContinent = entry.getKey();
      }
    }
    if (maxContinent != null) {
      MessageCli.FUEL_CONTINENT_INFO.printMessage(maxContinent, String.valueOf(maxFuel));
    }
  }
}
