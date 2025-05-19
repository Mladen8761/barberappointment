package com.Mladen.barberappointment.service.implementation;

import com.Mladen.barberappointment.model.Appointment;
import com.Mladen.barberappointment.model.Status;
import com.Mladen.barberappointment.repository.AppointmentRepository;
import com.Mladen.barberappointment.service.AppointmentService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskManager {

    AppointmentRepository appointmentRepository;
    AppointmentService appointmentService;
    @Autowired
    public TaskManager(AppointmentRepository appointmentRepository, AppointmentService appointmentService) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentService = appointmentService;
    }

    @PostConstruct
    public void scheduleExistingTasks() {
        // Pretpostavljamo da status "PENDING" označava neizvršene zadatke
        List<Appointment> appointments = appointmentRepository.findByStatus(Status.SCHEDULED);
        for (Appointment appointment:appointments) {
            appointmentService.scheduleTask(appointment);
        }
    }
}
