package com.Mladen.barberappointment.service.implementation;

import com.Mladen.barberappointment.model.Appointment;
import com.Mladen.barberappointment.model.Status;
import com.Mladen.barberappointment.model.User;
import com.Mladen.barberappointment.repository.AppointmentRepository;
import com.Mladen.barberappointment.service.AppointmentService;
import com.Mladen.barberappointment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    AppointmentRepository appointmentRepository;
    UserService userService;
    TaskScheduler taskScheduler;
    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserService userService, TaskScheduler taskScheduler) {
        this.appointmentRepository = appointmentRepository;
        this.userService = userService;
        this.taskScheduler = taskScheduler;
    }

    @Override
    public List<LocalTime> getAvailableTimes(LocalDate localDate) {
        List<Appointment> appointments=appointmentRepository.findByAppointmentDate(localDate);
        List<LocalTime> workHours=getMyLocalTime();
        if(appointments==null)
        {
            return workHours;
        }
        for(Appointment appointment:appointments)
        {
            if(appointment.getStatus()== Status.SCHEDULED || appointment.getStatus()== Status.COMPLETED)
            {
                workHours.remove(appointment.getAppointmentTime());
            }
        }
        return workHours;
    }

    @Override
    public void scheduleTask(Appointment appointment)
    {
        LocalDateTime localDateTime=LocalDateTime.of(appointment.getAppointmentDate(),appointment.getAppointmentTime());
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Date executionDate = Date.from(zonedDateTime.toInstant());


        Runnable task =() ->{
            Optional<Appointment> scheduledAppointment =appointmentRepository.findById(appointment.getId());
            if(scheduledAppointment.isPresent())
            {
                Appointment temp=scheduledAppointment.get();
                temp.setStatus(Status.COMPLETED);
                appointmentRepository.save(temp);

            }
        };

       taskScheduler.schedule(task,executionDate);
    }

    @Override
    public List<Appointment> findByUser(User user) {
        return appointmentRepository.findByUser(user);
    }

    @Override
    public Appointment findById(Integer id) {
        Optional<Appointment> temp=appointmentRepository.findById(id);
        Appointment appointment=null;
        if(temp.isPresent())
        {
            appointment=temp.get();

        }
        return appointment;
    }

    @Override
    public void save(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findByStatusAndUser(Status status,User user) {
        List<Appointment> temp= appointmentRepository.findByStatus(status);
        List<Appointment> appointments=new ArrayList<>();
        for (Appointment appointment:temp)
        {
            if(appointment.getUser()==user)
            {
                appointments.add(appointment);
            }
        }
        return appointments;
    }

    @Override
    public List<Appointment> findAllSortedByDate() {
        List<Appointment> appointments=appointmentRepository.findAll(Sort.by(Sort.Direction.DESC,"appointmentDate"));
        return appointments;
    }

    @Override
    public void saveAppointment(Appointment appointment, Principal principal) {
        appointment.setStatus(Status.SCHEDULED);
        appointment.setUser(userService.findByEmail(principal.getName()));
        appointmentRepository.save(appointment);
    }

    private List<LocalTime> getMyLocalTime() {
        List<LocalTime> localTimes=new ArrayList<>();
        LocalTime localTime=LocalTime.of(10,0);

        for (int i = 0; i <= 16; i++) {
            localTimes.add(localTime);
            localTime= localTime.plusMinutes(30);

        }
        return localTimes;
    }
}
