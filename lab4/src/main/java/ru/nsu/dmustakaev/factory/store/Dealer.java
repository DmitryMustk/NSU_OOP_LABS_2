package ru.nsu.dmustakaev.factory.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.dmustakaev.factory.parts.Car;

import java.math.BigDecimal;
import java.util.Random;

public class Dealer {
    private static final Logger logger = LogManager.getLogger(Dealer.class);

    private static final int DEFAULT_CAR_PRICE = 500;
    private static final int DEFAULT_CAR_DELTA = 100;

    private static final int FIVE_SECONDS = 5000;

    private final Object monitor = new Object();

    private final Random random = new Random();
    private final BigDecimal delta = new BigDecimal(DEFAULT_CAR_DELTA);

    private BigDecimal carPrice = new BigDecimal(DEFAULT_CAR_PRICE);

   public Dealer() {
       Thread priceChanger = new Thread(this::dynamicChangePrice);
       priceChanger.setDaemon(true);
       priceChanger.start();
   }

   private void dynamicChangePrice() {
       while (true) {
           double deltaK = random.nextGaussian();
           synchronized (monitor) {
               if (DEFAULT_CAR_DELTA + carPrice.intValue() < DEFAULT_CAR_PRICE) {
                   carPrice = new BigDecimal(DEFAULT_CAR_PRICE);
               }
               else {
                   BigDecimal newPrice = carPrice.add(delta.multiply(BigDecimal.valueOf(deltaK)));
                   if (newPrice.compareTo(BigDecimal.ZERO) > 0) {
                       carPrice = carPrice.add(delta.multiply(BigDecimal.valueOf(deltaK)));
                   }
               }
           }
           try {
               Thread.sleep(FIVE_SECONDS);
           } catch (InterruptedException e) {
               logger.warn(e);
               break;
           }
       }
   }

   public BigDecimal sell(Car car) {
       return carPrice;
   }

   public BigDecimal getCarPrice() {
       return carPrice;
   }
}
