package no.hvl.dat110.system.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import no.hvl.dat110.system.controller.Controller;
import no.hvl.dat110.system.display.DisplayDevice;
import no.hvl.dat110.system.sensor.SensorDevice;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

class TestSystem {

    @Test
    void test() {

        System.out.println("System starting ...");

        AtomicBoolean failure = new AtomicBoolean(false);
        CountDownLatch ready = new CountDownLatch(2);

        Thread displayThread = new Thread(() -> {
            try {
                DisplayDevice.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ready.countDown();
            }
        });

        Thread sensorThread = new Thread(() -> {
            try {
                SensorDevice.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ready.countDown();
            }
        });

        Thread controllerThread = new Thread(() -> {
            try {
                ready.await(); // Ensure display and sensor are ready before starting
                Controller.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        try {
            displayThread.start();
            sensorThread.start();

            controllerThread.start();

            displayThread.join();
            sensorThread.join();
            controllerThread.join();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            System.out.println("System stopping ...");

            if (failure.get()) {
                fail();
            }
        }

        // Ensure all components ran successfully
        assertFalse(failure.get());
    }
}
