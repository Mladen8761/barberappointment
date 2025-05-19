package com.Mladen.barberappointment.controller;

import com.Mladen.barberappointment.exceptionHandling.ClientAppointmentException;
import com.Mladen.barberappointment.model.Appointment;
import com.Mladen.barberappointment.model.Status;
import com.Mladen.barberappointment.model.dto.AppointmentDTO;
import com.Mladen.barberappointment.model.dto.UserDTO;
import com.Mladen.barberappointment.service.AppointmentService;
import com.Mladen.barberappointment.service.UserService;
import org.apache.catalina.filters.RemoteIpFilter;
import org.eclipse.angus.mail.imap.protocol.MODSEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AppointmentController {

    AppointmentService appointmentService;
    UserService    userService;
    @Autowired
    public AppointmentController(AppointmentService appointmentService, UserService userService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
    }



    @PostMapping("/processAppointment")
    public String processAppointment(Model model)
    {
        List<LocalDate> localDates=getLocalDatesInThreeMonths();

        model.addAttribute("localDates",localDates);


        return "appointment-form-time";
    }
    @PostMapping("/processFullAppointment")
    public String processFullAppointment(@ModelAttribute AppointmentDTO appointmentDTO, Principal principal)
    {
        List<Appointment> appointments=appointmentService.findByStatusAndUser(Status.SCHEDULED,userService.findByEmail(principal.getName()));
        if(appointments.size()!=0&& principal.getName()!="admin@gmail.com")
        {
            throw new ClientAppointmentException("Korisnik vec ima zakazan termin");
        }
        Appointment appointment=appointmentDTO.convert();

        appointmentService.saveAppointment(appointment,principal);
       return "redirect:/";
    }

    @GetMapping("/getAvailableTimes")
    @ResponseBody
    public List<String> getAvailableTimes(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<LocalTime> times = appointmentService.getAvailableTimes(date);

        return times.stream()
                .map(LocalTime::toString)
                .collect(Collectors.toList());
    }

    @GetMapping("/getAppointments")
    public String getAppointments(Model model,Principal principal)
    {
        List<Appointment> appointments=appointmentService.findByUser(userService.findByEmail(principal.getName()));
        model.addAttribute("appointments",appointments);
        return "appointments";
    }
    @GetMapping("/cancelAppointment")
    public String cancelAppointment(@RequestParam("id") Integer id)
    {
        Appointment appointment=appointmentService.findById(id);
        if(appointment.getStatus()== Status.SCHEDULED)
        {
            appointment.setStatus(Status.CANCELLED);
            appointmentService.save(appointment);
        }
        return "redirect:/";
    }

    private List<LocalDate> getLocalDatesInThreeMonths() {
        List<LocalDate> localDates=new ArrayList<>();
        LocalDate localDate=LocalDate.now();
        for (int i = 1; i < 90; i++) {
            localDates.add(LocalDate.now().plusDays(i));
        }

        return localDates;
    }

    @GetMapping("/showAdminDashboard")
    public String adminDashboard(Model model)
    {
        List<Appointment> appointments=appointmentService.findAllSortedByDate();
        model.addAttribute("appointments",appointments);
        return "admin-dashboard";
    }


}
