package com.parkinglot.parkinglot.ejb;

import com.parkinglot.parkinglot.common.CarDto;
import com.parkinglot.parkinglot.entities.Car;
import com.parkinglot.parkinglot.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sun.rmi.server.UnicastServerRef;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {

    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<CarDto> findAllCars() {
        LOG.info("findAllCars");
        try {
            TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);

            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    private List<CarDto> copyCarsToDto(List<Car> cars) {
        List<CarDto> carDtos = new ArrayList<>();
        for (Car car : cars) {
            CarDto carDto = new CarDto(
                    car.getId(),
                    car.getLicensePlate(),
                    car.getParkingSpot(),
                    car.getOwner().getUsername()
            );
            carDtos.add(carDto);
        }
        return carDtos;
    }

   /* public void createCar(String licensePlate, String parkingSpot, Long userId) {
        LOG.info("createCar: " + licensePlate + ", " + parkingSpot + ", userId: " + userId);

        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        // Găsim utilizatorul după ID
        User user = entityManager.find(User.class, userId);

        if (user != null) {
            car.setOwner(user);
            user.getCars().add(car);
            LOG.info("Car owner set to: " + user.getUsername());
        } else {
            LOG.warning("User not found with id: " + userId);
        }

        // Salvăm mașina în baza de date
        entityManager.persist(car);
        LOG.info("Car persisted successfully");
    }*/

    public void createCar(String licensePlate, String parkingSpot, Long userId) {

        LOG.info("createCar");

        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        User user = entityManager.find(User.class, userId);
        user.getCars().add(car);
        car.setOwner(user);

        entityManager.persist(car);
    }


    public CarDto findById(Long carId) {
        LOG.info("findById: " + carId);
        try {
            Car car = entityManager.find(Car.class, carId);
            if (car == null) {
                return null;
            }
            return new CarDto(
                    car.getId(),
                    car.getLicensePlate(),
                    car.getParkingSpot(),
                    car.getOwner().getUsername()
            );
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }


    public void updateCar(Long carId, String licensePlate, String parkingSpot, Long userId) {
        LOG.info("updateCar");

        Car car = entityManager.find(Car.class, carId);
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);


        User oldUser = car.getOwner();
        oldUser.getCars().remove(car);


        User user= entityManager.find(User.class, userId);
        user.getCars().add(car);
        car.setOwner(user);

    }

        public void deleteCarsByIds(Collection<Long> carIds) {
            LOG.info("deleteCarsByIds");

            for (Long carId : carIds) {
                Car car = entityManager.find(Car.class, carId);
                entityManager.remove(car);
            }
        }


}
