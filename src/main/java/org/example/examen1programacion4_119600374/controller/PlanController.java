package org.example.examen1programacion4_119600374.controller;

import org.example.examen1programacion4_119600374.model.Farmacia;
import org.example.examen1programacion4_119600374.model.Paciente;
import org.example.examen1programacion4_119600374.model.PacienteMedicamento;
import org.example.examen1programacion4_119600374.repository.FarmaciaRepository;
import org.example.examen1programacion4_119600374.repository.PacienteMedicamentoRepository;
import org.example.examen1programacion4_119600374.repository.PacienteRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/presentation/plan")
public class PlanController {

    private final FarmaciaRepository farmaciaRepo;
    private final PacienteRepository pacienteRepo;
    private final PacienteMedicamentoRepository pmRepo;

    public PlanController(FarmaciaRepository farmaciaRepo,
                          PacienteRepository pacienteRepo,
                          PacienteMedicamentoRepository pmRepo) {
        this.farmaciaRepo = farmaciaRepo;
        this.pacienteRepo = pacienteRepo;
        this.pmRepo       = pmRepo;
    }

    @GetMapping("/show")
    public String show(@AuthenticationPrincipal UserDetails userDetails,
                       Model model) {

        cargarFarmacia(userDetails, model);
        model.addAttribute("paciente",      null);
        model.addAttribute("medicamentos",  Collections.emptyList());
        model.addAttribute("cedula",        "");
        return "plan/show";
    }

    @GetMapping("/refrescar")
    public String refrescar(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam(required = false) String cedula,
                            Model model) {

        cargarFarmacia(userDetails, model);
        buscarPaciente(cedula, model);
        return "plan/show";
    }

    @PostMapping("/buscar")
    public String buscar(@RequestParam String cedula,
                         RedirectAttributes ra) {
        ra.addAttribute("cedula", cedula);
        return "redirect:/presentation/plan/refrescar";
    }

    //primer flujo mencionado por el profe, suma de acumuladas a las compradas
    @PostMapping("/registrar")
    public String registrar(@RequestParam Integer pmId,
                            @RequestParam Integer cantidad,
                            @RequestParam String cedula,
                            RedirectAttributes ra) {

        PacienteMedicamento pm = pmRepo.findById(pmId).orElse(null);

        if (pm != null && cantidad != null && cantidad > 0) {
            pm.setDosisafavor(pm.getDosisfavor() + cantidad);
            pmRepo.save(pm);
        }

        ra.addAttribute("cedula", cedula);
        return "redirect:/presentation/plan/refrescar";
    }

    //en el flujo dos mencionado por le profe se hace la disponibilidad y un posible error
    @PostMapping("/medicamento/entregar")
    public String entregar(@RequestParam Integer idd, @RequestParam String ced, RedirectAttributes redireccion) {

        PacienteMedicamento paciMe = pmRepo.findById(idd).orElse(null);
        if (paciMe != null) {
            int plan = paciMe.getMedicamento().getPlan();
            int acumu    = paciMe.getDosisfavor();
            if (acumu >= plan) {
                paciMe.setDosisafavor(acumu - plan);
                pmRepo.save(paciMe);
            } else {
               //aqui esta el error del enunciado
                redireccion.addFlashAttribute("errorEntregar", "No hay dosis suficientes a favor para entregar");
            }
        }
        redireccion.addAttribute("cedula", ced);
        return "redirect:/presentation/plan/refrescar";
    }


    private void cargarFarmacia(UserDetails userDetails, Model model) {
        String usuarioId = userDetails.getUsername();
        Farmacia farmacia = farmaciaRepo.findByUsuario(usuarioId);
        model.addAttribute("usuarioId",    usuarioId);
        model.addAttribute("farmaciaNombre", farmacia != null ? farmacia.getNombre() : usuarioId);
    }
//se busca el paciente solamente por la cedula
    private void buscarPaciente(String ced, Model model) {
        model.addAttribute("cedula", ced != null ? ced : "");
        if (ced == null || ced.isBlank()) {
            model.addAttribute("paciente",     null);
            model.addAttribute("medicamentos", Collections.emptyList());
            return;
        }

        Paciente paciente = pacienteRepo.findById(ced.trim()).orElse(null);
        model.addAttribute("paciente", paciente);

        List<PacienteMedicamento> medicamentos = paciente != null ? pmRepo.findByPaciente(paciente)
                : Collections.emptyList();
        model.addAttribute("medicamentos", medicamentos);
    }
}