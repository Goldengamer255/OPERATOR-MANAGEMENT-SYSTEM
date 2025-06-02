package nz.ac.auckland.se281;

public class Country {
  private String name;
  private String continent;
  private int fuelCost;

  public Country(String name, String continent, int fuelCost) {
    this.name = name;
    this.continent = continent;
    this.fuelCost = fuelCost;
  }

  public String getName() {
    return name;
  }

  public String getContinent() {
    return continent;
  }

  public int getFuelCost() {
    return fuelCost;
  }
}
