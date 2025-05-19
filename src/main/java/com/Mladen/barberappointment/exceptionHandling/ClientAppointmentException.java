package com.Mladen.barberappointment.exceptionHandling;

public class ClientAppointmentException extends RuntimeException {
    public ClientAppointmentException(String message) {
        super(message);
    }
}
