package nl.niranjan.jee;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static java.lang.System.out;

/**
 * Demonstrate use of Hibernate Validator.
 */
public class HibernateValidatorDemonstration {
    private final Validator validator;

    public HibernateValidatorDemonstration() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public void demonstrateValidator() {
        final Car nullManufacturerCar = new Car(null, "ABC123", 4);
        final Set<ConstraintViolation<Car>> nullMfgViolations = validator.validate(nullManufacturerCar);
        printConstraintViolationsToStandardOutput("Null Manufacturer Example", nullMfgViolations);

        final Car nullLicenseCar = new Car("Honda", null, 3);
        final Set<ConstraintViolation<Car>> nullLicenseViolations = validator.validate(nullLicenseCar);
        printConstraintViolationsToStandardOutput("Null License Example", nullLicenseViolations);

        final Car oneSeatCar = new Car("Toyota", "123ABC", 1);
        final Set<ConstraintViolation<Car>> tooFewSeatsViolations = validator.validate(oneSeatCar);
        printConstraintViolationsToStandardOutput("Too Few Seats Example", tooFewSeatsViolations);

        final Car oneDigitLicenseCar = new Car("General Motors", "I", 2);
        final Set<ConstraintViolation<Car>> tooFewLicenseDigitsViolation = validator.validate(oneDigitLicenseCar);
        printConstraintViolationsToStandardOutput("Too Few License Digits Example", tooFewLicenseDigitsViolation);

        final Car nullManufacturerNullLicenseCar = new Car(null, null, 4);
        final Set<ConstraintViolation<Car>> nullMfgLicenseViolation = validator.validate(nullManufacturerNullLicenseCar);
        printConstraintViolationsToStandardOutput("Null Manufacturer and Null License Example", nullMfgLicenseViolation);

        final Garage garage = new Garage();
        final Set<ConstraintViolation<Garage>> noCarsInGarage = validator.validate(garage);
        printConstraintViolationsToStandardOutput("No Cars in Garage", noCarsInGarage);

        garage.addCar(oneDigitLicenseCar);
        garage.addCar(oneSeatCar);
        garage.addCar(nullManufacturerNullLicenseCar);
        final Set<ConstraintViolation<Garage>> messedUpCarsInGarage = validator.validate(garage);
        printConstraintViolationsToStandardOutput("Messed Up Cars in Garage", messedUpCarsInGarage);
    }

    private <T> void printConstraintViolationsToStandardOutput(
            final String title,
            final Set<ConstraintViolation<T>> violations) {
        out.println(title);
        for (final ConstraintViolation<T> violation : violations) {
            out.println("\t" + violation.getPropertyPath() + " " + violation.getMessage());
        }
    }

    public static void main(final String[] arguments) {
        final HibernateValidatorDemonstration instance = new HibernateValidatorDemonstration();
        instance.demonstrateValidator();
    }
}