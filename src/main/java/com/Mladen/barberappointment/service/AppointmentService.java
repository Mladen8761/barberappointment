package com.Mladen.barberappointment.service;

import com.Mladen.barberappointment.model.Appointment;
import com.Mladen.barberappointment.model.Status;
import com.Mladen.barberappointment.model.User;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {
    List<LocalTime> getAvailableTimes(LocalDate localDate);
    void saveAppointment(Appointment appointment, Principal principal);
    void scheduleTask(Appointment appointment);
    List<Appointment> findByUser(User user);
    Appointment findById(Integer id);
    void save(Appointment appointment);
    List<Appointment> findByStatusAndUser(Status status,User user);
    List<Appointment> findAllSortedByDate();
}
