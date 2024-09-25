package edu.pe.cibertec.demo_front_mautino.controlador;

import edu.pe.cibertec.demo_front_mautino.dto.VehiculoRequest;
import edu.pe.cibertec.demo_front_mautino.dto.VehiculoResponse;
import edu.pe.cibertec.demo_front_mautino.viewmodel.VehiculoModelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoControlador {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/buscarVehiculo")
    public String buscarVehiculo(Model model) {
        VehiculoModelView VehiculoModelView = new VehiculoModelView(
                "00", "", "", "", "", "", "");
        model.addAttribute("vehiculoModelView", VehiculoModelView);
        return "buscar";
    }

    @PostMapping("/buscandoVehiculo")
    public String buscandoVehiculo(
            @RequestParam("placa") String placa,
            Model model) {
        if (placa == null || placa.trim().isEmpty()) {
            VehiculoModelView vehiculoModel = new VehiculoModelView(
                    "01", "Placa incorrecta.",
                    "", "", "", "", "");
            model.addAttribute("vehiculoModelView", vehiculoModel);
            return "buscar";
        }

        try {
            String endpoint = "http://localhost:8080/vehiculos";
            VehiculoRequest vehiculoRequest = new VehiculoRequest(placa);
            VehiculoResponse vehiculoResponse = restTemplate.postForObject(endpoint, vehiculoRequest, VehiculoResponse.class);
            if (vehiculoResponse.codigo().equals("00")) {
                VehiculoModelView vehiculoModelView = new VehiculoModelView(
                        "00", "", vehiculoResponse.VehiculoMarca(), vehiculoResponse.VehiculoModelo(), vehiculoResponse.VehiculoNroAsientos(),
                        vehiculoResponse.VehiculoPrecio(), vehiculoResponse.VehiculoColor());
                model.addAttribute("vehiculoModelView", vehiculoModelView);
                return "detalles";
            } else {
                VehiculoModelView vehiculoModelView = new VehiculoModelView(
                        "01", "Vehículo no encontrado.",
                        "", "", "", "", "");
                model.addAttribute("vehiculoModelView", vehiculoModelView);
                return "buscar";
            }
        } catch(Exception e) {
            VehiculoModelView vehiculoModelView = new VehiculoModelView(
                    "99", "Error al buscar vehículo.",
                    "", "", "", "", "");
            model.addAttribute("vehiculoModelView", vehiculoModelView);
            System.out.println(e.getMessage());
            return "buscar";
        }
    }
}
