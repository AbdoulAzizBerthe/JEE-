package ma.enset.hopital.Web;

import lombok.AllArgsConstructor;
import ma.enset.hopital.entities.Patient;
import ma.enset.hopital.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;
    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "size",defaultValue = "4") int size,
                        @RequestParam(name = "keyword",defaultValue = "") String kw) {
        Page<Patient> Pagepatients = patientRepository.findByNomContains(kw,PageRequest.of(page, size));
        model.addAttribute("listePAtients", Pagepatients.getContent());
        model.addAttribute("pages",new int[Pagepatients.getTotalPages()]);//SAuvegarder les pages dans les modes
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);

        return "patients";

    }
    @GetMapping("/delete")
    public String delete(Long id, String keyword , int page){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

}
