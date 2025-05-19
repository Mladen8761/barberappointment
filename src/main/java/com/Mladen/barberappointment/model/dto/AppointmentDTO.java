package com.Mladen.barberappointment.model.dto;

import com.Mladen.barberappointment.model.Appointment;
import com.Mladen.barberappointment.model.Status;
import jakarta.persistence.Column;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDTO {

    LocalDate appointmentDate;

    LocalTime appointmentTime;


    public AppointmentDTO() {
    }

    public Appointment convert()
    {
        Appointment appointment=new Appointment();
        appointment.setAppointmentDate(this.appointmentDate);
        appointment.setAppointmentTime(this.appointmentTime);
        return appointment;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }


}
