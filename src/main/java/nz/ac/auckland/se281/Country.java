package nz.ac.auckland.se281;

public class Country {
  private String name; // Name of the country
  private String continent; // Continent where the country is located
  private int fuelCost; // Fuel cost for traveling to this country

  public Country(
      String name, String continent, int fuelCost) { // Constructor to initialize a country
    this.name = name;
    this.continent = continent;
    this.fuelCost = fuelCost;
  }

  public String getName() { // Get the name of this country
    return name;
  }

  public String getContinent() { // Get the continent of this country
    return continent;
  }

  public int getFuelCost() { // Get the fuel cost for this country
    return fuelCost;
  }
}
