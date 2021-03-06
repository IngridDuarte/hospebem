package com.thoughtworks.aceleradora.quarto.controller;

import com.thoughtworks.aceleradora.quarto.dominio.QuartoRepository;
import com.thoughtworks.aceleradora.solicitacao.dominio.Solicitacao;
import com.thoughtworks.aceleradora.solicitacao.dominio.SolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/")
public class CheckinCheckoutController {
    private QuartoRepository quartoRepository;
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    public CheckinCheckoutController(QuartoRepository repositorio, SolicitacaoRepository solicitacaoRepository) {
        this.quartoRepository = repositorio;
        this.solicitacaoRepository = solicitacaoRepository;
    }

    @GetMapping("/listacheckincheckout")
    public String listaCheckinCheckout(Model model){ ;
        model.addAttribute("quartos", quartoRepository.findAll());
        model.addAttribute("solicitacoesAceitas", solicitacaoRepository.findAllByStatusOrderByNome(Solicitacao.Status.ACEITO));
        model.addAttribute("solicitacoesHospedes", solicitacaoRepository.findAllByStatusOrderByNome(Solicitacao.Status.HOSPEDE));
        model.addAttribute("solicitacao", new Solicitacao());
        model.addAttribute("formataData", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return "quarto/listagens/listaCheckinCheckout";
    }

    @PostMapping("/solicitacao/{id}/checkin")
    public String checkin (@PathVariable Long id, Solicitacao solicitacaoCheckin) {
        Solicitacao solicitacao = solicitacaoRepository.getOne(id);

        solicitacao.setDataCheckin(solicitacaoCheckin.getDataCheckin());
        solicitacao.setStatus(Solicitacao.Status.HOSPEDE);

        solicitacaoRepository.save(solicitacao);

        return "redirect:/listacheckincheckout";
    }

    @PostMapping("/solicitacao/{id}/checkout")
    public String checkout (@PathVariable Long id, Solicitacao solicitacaoCheckout) {
        Solicitacao solicitacao = solicitacaoRepository.getOne(id);

        solicitacao.setDataCheckout(solicitacaoCheckout.getDataCheckout());
        solicitacao.setStatus(Solicitacao.Status.EX_HOSPEDE);
        solicitacao.setQuarto(null);

        solicitacaoRepository.save(solicitacao);
        
        return "redirect:/listacheckincheckout";
    }
}
