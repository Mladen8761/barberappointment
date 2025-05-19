package com.Mladen.barberappointment.repository;

import com.Mladen.barberappointment.model.Appointment;
import com.Mladen.barberappointment.model.Status;
import com.Mladen.barberappointment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
    List<Appointment> findByAppointmentDate(LocalDate localDate);
    List<Appointment> findByStatus(Status status);
    List<Appointment> findByUser(User user);

}
