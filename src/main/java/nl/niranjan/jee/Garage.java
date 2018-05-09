package nl.niranjan.jee;


import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Holds cars.
 */
public class Garage {
    @Size(min = 1)
    @Valid
    private final Set<Car> cars = new HashSet<>();

    public Garage() {
    }

    public void addCar(final Car newCar) {
        cars.add(newCar);
    }

    public Set<Car> getCars() {
        return Collections.unmodifiableSet(this.cars);
    }
}
