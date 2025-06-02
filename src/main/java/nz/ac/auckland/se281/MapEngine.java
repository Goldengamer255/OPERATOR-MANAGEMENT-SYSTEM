package nz.ac.auckland.se281;

import java.util.List;

/** This class is the main entry point. */
public class MapEngine {
  private Graph graph;

  public MapEngine() {
    // add other code here if you wan
    loadMap(); // keep this mehtod invocation
  }

  private String promptAndFormatCountry(
      MessageCli promptMessage) { // Method to prompt user for a country name and format it
    promptMessage.printMessage();
    String countryInput = Utils.scanner.nextLine().trim();
    String[] words = countryInput.split("\\s+");
    StringBuilder sb = new StringBuilder();
    for (String word : words) { // Iterate through each word in the input
      if (word.length() > 0) {
        sb.append(Character.toUpperCase(word.charAt(0))); // Capitalize the first letter
        if (word.length() > 1) {
          sb.append(word.substring(1)); // keep rest as user input
        }
        sb.append(" ");
      }
    }
    return sb.toString().trim(); // Return the formatted country name
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {

    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    graph = new Graph(); // Initialize the graph

    // Add countries as nodes
    for (String line : countries) { // Read each line from the countries file
      String[] parts = line.split(",");
      String country = parts[0].trim();
      String continent = parts[1].trim();
      int fuelCost = Integer.parseInt(parts[2].trim()); // Parse the fuel cost as an integer
      graph.addCountry(country, continent, fuelCost); // Add the country to the graph
    }

    // Add adjacencies as edges
    for (String line : adjacencies) {
      String[] parts = line.split(","); // Split by comma
      String country = parts[0].trim();
      // Each subsequent part is a neighbor
      for (int i = 1; i < parts.length; i++) {
        String neighbor = parts[i].trim(); // Trim whitespace around neighbor names
        graph.addEdge(country, neighbor); // Add the edge to the graph
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    while (true) {
      String country =
          promptAndFormatCountry(MessageCli.INSERT_COUNTRY); // Prompt user for country name
      try {
        if (!graph.hasCountry(country)) {
          throw new Exception();
        }
        String continent = graph.getContinent(country); // Get the continent of the country
        int fuelCost = graph.getFuelCost(country);
        List<String> neighbours = graph.getNeighbors(country);
        MessageCli.COUNTRY_INFO.printMessage(
            country,
            continent,
            String.valueOf(fuelCost),
            neighbours.toString()); // Print country information
        break;
      } catch (Exception e) {
        MessageCli.INVALID_COUNTRY.printMessage(
            country); // If the country does not exist, print an error message
      }
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    String startCountry = null;
    String destCountry = null;

    // Ask for start country
    while (true) {
      startCountry =
          promptAndFormatCountry(MessageCli.INSERT_SOURCE); // Prompt user for start country
      if (!graph.hasCountry(startCountry)) { // Check if the country exists in the graph
        MessageCli.INVALID_COUNTRY.printMessage(startCountry);
      } else {
        break;
      }
    }

    // Ask for destination country
    while (true) {
      destCountry =
          promptAndFormatCountry(
              MessageCli.INSERT_DESTINATION); // Prompt user for destination country
      if (!graph.hasCountry(destCountry)) { // Check if the country exists in the graph
        MessageCli.INVALID_COUNTRY.printMessage(destCountry);
      } else if (destCountry.equals(startCountry)) {
        MessageCli.NO_CROSSBORDER_TRAVEL.printMessage(destCountry);
        return; // End the method if no cross-border travel is required
      } else {
        break;
      }
    }

    // --- Compute the fastest route (BFS for shortest path in unweighted graph) ---
    List<String> route =
        graph.findShortestRoute(startCountry, destCountry); // Find the shortest route using BFS
    if (route == null || route.isEmpty()) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage(
          startCountry, destCountry); // If no route is found, print a message
      return;
    }

    // Display the route
    MessageCli.ROUTE_INFO.printMessage(route.toString());

    // --- Compute total fuel required (excluding start and destination) ---
    int totalFuel = 0;
    java.util.Map<String, Integer> continentFuel =
        new java.util.LinkedHashMap<>(); // Map to store fuel spent per continent
    java.util.LinkedHashSet<String> continentsVisited =
        new java.util.LinkedHashSet<>(); // Set to store unique continents visited

    // Always add start continent first
    String startContinent =
        graph.getContinent(startCountry); // Get the continent of the start country
    continentsVisited.add(startContinent);
    continentFuel.putIfAbsent(startContinent, 0);

    // Add fuel for intermediate countries
    for (int i = 1; i < route.size() - 1; i++) {
      String country = route.get(i);
      int fuel = graph.getFuelCost(country);
      String continent = graph.getContinent(country); // Get the continent of the country
      totalFuel += fuel;
      continentsVisited.add(continent);
      continentFuel.put(
          continent, continentFuel.getOrDefault(continent, 0) + fuel); // Add fuel to the continent
    }

    // Always add destination continent last
    String destContinent = graph.getContinent(destCountry);
    continentsVisited.add(destContinent);
    continentFuel.putIfAbsent(destContinent, 0);

    MessageCli.FUEL_INFO.printMessage(String.valueOf(totalFuel));

    // --- Display continents visited and fuel per continent ---
    StringBuilder continentInfo = new StringBuilder();
    continentInfo.append("[");
    boolean first = true;
    for (String continent : continentsVisited) {
      if (!first) {
        continentInfo.append(", ");
      }
      continentInfo // Append continent name and fuel spent
          .append(continent)
          .append(" (")
          .append(continentFuel.getOrDefault(continent, 0))
          .append(")");
      first = false;
    }
    continentInfo.append("]");
    MessageCli.CONTINENT_INFO.printMessage(continentInfo.toString());

    // --- Print the continent with the highest fuel spent (if any > 0) ---
    String maxContinent = null;
    int maxFuel = -1;
    for (String continent : continentsVisited) {
      int fuel = continentFuel.getOrDefault(continent, 0); // Get the fuel spent on this continent
      if (fuel > maxFuel) { // Check if this continent has the highest fuel spent
        maxFuel = fuel;
        maxContinent = continent;
      }
    }
    if (maxContinent != null && maxFuel > 0) {
      MessageCli.FUEL_CONTINENT_INFO.printMessage(
          maxContinent + " (" + maxFuel + ")"); // Print the continent with the highest fuel spent
    }
  }
}
