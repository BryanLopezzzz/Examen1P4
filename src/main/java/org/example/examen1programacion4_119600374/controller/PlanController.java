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

    // ─────────────────────────────────────────────────────────────────────────
    // GET /presentation/plan/show
    // Muestra la página principal: campo de búsqueda vacío, sin paciente aún.
    // ─────────────────────────────────────────────────────────────────────────
    @GetMapping("/show")
    public String show(@AuthenticationPrincipal UserDetails userDetails,
                       Model model) {

        cargarFarmacia(userDetails, model);
        model.addAttribute("paciente",      null);
        model.addAttribute("medicamentos",  Collections.emptyList());
        model.addAttribute("cedula",        "");
        return "plan/show";
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /presentation/plan/refrescar?cedula=111
    // Busca el paciente por cédula y recarga la misma vista con sus datos.
    // Usado como destino de redirect tras Registrar y Entregar.
    // ─────────────────────────────────────────────────────────────────────────
    @GetMapping("/refrescar")
    public String refrescar(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam(required = false) String cedula,
                            Model model) {

        cargarFarmacia(userDetails, model);
        buscarPaciente(cedula, model);
        return "plan/show";
    }

    // ─────────────────────────────────────────────────────────────────────────
    // POST /presentation/plan/buscar
    // El formulario de búsqueda envía la cédula y redirige a /refrescar.
    // ─────────────────────────────────────────────────────────────────────────
    @PostMapping("/buscar")
    public String buscar(@RequestParam String cedula,
                         RedirectAttributes ra) {
        ra.addAttribute("cedula", cedula);
        return "redirect:/presentation/plan/refrescar";
    }

    // ─────────────────────────────────────────────────────────────────────────
    // FLUJO 1 — POST /presentation/plan/registrar
    // Suma las dosis compradas a las acumuladas del paciente-medicamento.
    // ─────────────────────────────────────────────────────────────────────────
    @PostMapping("/registrar")
    public String registrar(@RequestParam Integer pmId,
                            @RequestParam Integer cantidad,
                            @RequestParam String cedula,
                            RedirectAttributes ra) {

        // Buscar el registro PacienteMedicamento por su PK
        PacienteMedicamento pm = pmRepo.findById(pmId).orElse(null);

        if (pm != null && cantidad != null && cantidad > 0) {
            // Sumar las dosis compradas a las acumuladas
            pm.setDosisafavor(pm.getDosisafavor() + cantidad);
            pmRepo.save(pm);
        }

        // Mantener contexto: redirigir al mismo paciente
        ra.addAttribute("cedula", cedula);
        return "redirect:/presentation/plan/refrescar";
    }

    // ─────────────────────────────────────────────────────────────────────────
    // FLUJO 2 — POST /presentation/plan/medicamento/entregar
    // Verifica disponibilidad según el plan y descuenta de las acumuladas.
    // Si plan=2, al entregar una regalía se restan 2 de las acumuladas.
    // Si no alcanzan, muestra error "No hay dosis suficientes".
    // ─────────────────────────────────────────────────────────────────────────
    @PostMapping("/medicamento/entregar")
    public String entregar(@RequestParam Integer pmId,
                           @RequestParam String cedula,
                           RedirectAttributes ra) {

        PacienteMedicamento pm = pmRepo.findById(pmId).orElse(null);

        if (pm != null) {
            int planRequerido = pm.getMedicamento().getPlan(); // dosis necesarias para 1 regalo
            int acumuladas    = pm.getDosisafavor();

            if (acumuladas >= planRequerido) {
                // Descontar el plan de las acumuladas
                pm.setDosisafavor(acumuladas - planRequerido);
                pmRepo.save(pm);
            } else {
                // No alcanza: pasar mensaje de error a la vista via flash
                ra.addFlashAttribute("errorEntregar",
                        "No hay dosis suficientes a favor para entregar");
            }
        }

        // Mantener contexto: redirigir al mismo paciente
        ra.addAttribute("cedula", cedula);
        return "redirect:/presentation/plan/refrescar";
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers privados
    // ─────────────────────────────────────────────────────────────────────────

    /** Pone en el model el nombre de la farmacia del usuario logueado. */
    private void cargarFarmacia(UserDetails userDetails, Model model) {
        String usuarioId = userDetails.getUsername();
        Farmacia farmacia = farmaciaRepo.findByUsuario(usuarioId);
        model.addAttribute("usuarioId",    usuarioId);
        model.addAttribute("farmaciaNombre",
                farmacia != null ? farmacia.getNombre() : usuarioId);
    }

    /** Busca el paciente por cédula y carga paciente + medicamentos en el model. */
    private void buscarPaciente(String cedula, Model model) {
        model.addAttribute("cedula", cedula != null ? cedula : "");

        if (cedula == null || cedula.isBlank()) {
            model.addAttribute("paciente",     null);
            model.addAttribute("medicamentos", Collections.emptyList());
            return;
        }

        Paciente paciente = pacienteRepo.findById(cedula.trim()).orElse(null);
        model.addAttribute("paciente", paciente); // null si no existe → vista muestra "Paciente no encontrado"

        List<PacienteMedicamento> medicamentos = paciente != null
                ? pmRepo.findByPaciente(paciente)
                : Collections.emptyList();
        model.addAttribute("medicamentos", medicamentos);
    }
}