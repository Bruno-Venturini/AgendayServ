package com.agenday.agendayserv.agendamento;

import com.agenday.agendayserv.agendamento.horariolivre.AgendamentoLivreDTO;
import com.agenday.agendayserv.agendamento.horariolivre.HorarioLivreDTO;
import com.agenday.agendayserv.agendamento.pagamento.MetodoPagamentoEnum;
import com.agenday.agendayserv.agendamento.pagamento.Pagamento;
import com.agenday.agendayserv.agendamento.pagamento.StatusPagamentoEnum;
import com.agenday.agendayserv.cliente.ClienteService;
import com.agenday.agendayserv.email.EmailService;
import com.agenday.agendayserv.empresa.expediente.ExpedienteEmpresa;
import com.agenday.agendayserv.empresa.expediente.ExpedienteEmpresaService;
import com.agenday.agendayserv.empresa.funcionario.Funcionario;
import com.agenday.agendayserv.empresa.funcionario.FuncionarioService;
import com.agenday.agendayserv.empresa.servico.ServicoService;
import com.agenday.agendayserv.exceptions.BusinessException;
import com.agenday.agendayserv.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AgendamentoService {
    private ModelMapper modelMapper;
    private AgendamentoRepository repository;
    private ServicoService servicoService;
    private ExpedienteEmpresaService expedienteEmpresaService;
    private ClienteService clienteService;
    private FuncionarioService funcionarioService;
    private EmailService emailService;

    private static final QAgendamento qAgendamento = QAgendamento.agendamento;

    public List<Agendamento> getAll() {
        return repository.findAll();
    }

    public Agendamento getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Agendamento"));
    }

    public Agendamento add(AgendamentoRepresentation.AgendamentoCreate create) {
        var agendamento = repository.save(Agendamento.builder()
                .dataHora(create.getDataHora())
                .status(create.getStatus())
                .descricao(create.getDescricao())
                .cliente(clienteService.getById(create.getCliente()))
                .servico(servicoService.getById(create.getServico()))
                .funcionario(funcionarioService.getById(create.getFuncionario()))
                .build());

        var email = agendamento.getCliente().getEmail();

        if (!email.isEmpty()) {
            emailService.sendEmail(email, "Agendamento", "Seu agendamento foi enviado e está pendente para aceitação!");
        }

        return agendamento;
    }

    private Agendamento update(Long id, Agendamento entity) {
        var dbEntity = repository.findById(id).orElseThrow(() -> new NotFoundException("Agendamento"));

        modelMapper.map(entity, dbEntity);

        return repository.save(dbEntity);
    }

    public Agendamento update(Long id, AgendamentoRepresentation.AgendamentoCreate entity) {
        var dbEntity = repository.findById(id).orElseThrow(() -> new NotFoundException("Agendamento"));

        modelMapper.map(entity, dbEntity);

        return repository.save(dbEntity);
    }

    public List<Agendamento> obterPorStatus(StatusAgendamentoEnum status) {
        return repository.findAll(QAgendamento.agendamento.status.eq(status));
    }

    public List<Agendamento> obterAbertosPorData(LocalDate inicio, LocalDate fim) {
        return repository.findAll(QAgendamento.agendamento.status.eq(StatusAgendamentoEnum.ABERTO)
                .and(QAgendamento.agendamento.dataHora.between(inicio.atStartOfDay(), fim.atTime(23, 59))));
    }

    public List<LocalDate> obterDiasDisponiveis(Long idServico, LocalDate inicio, LocalDate fim) {
        var dias = new ArrayList<LocalDate>();
        var servico = servicoService.getById(idServico);
        var expedientes = servico.getEmpresa().getExpedientes();

        if (expedientes.isEmpty())
            return dias;

        var agendamentos = obterAbertosPorData(inicio, fim);

        for (var i = inicio; i.isBefore(fim) || i.isEqual(fim); i = i.plusDays(1)) {
            var dia = i;

            var expediente = expedientes.stream()
                    .filter(exp -> exp.getDiaSemana() == dia.getDayOfWeek())
                    .findFirst();

            if (expediente.isEmpty())
                continue;

            var minutosExpediente = expediente.get().obterTotalMinutos();
            var minutosAgendadas = agendamentos.stream()
                    .filter(agendamento -> agendamento.getDataHora().toLocalDate() == dia)
                    .mapToInt(agendamento -> agendamento.getServico().getDuracao())
                    .sum();

            if (minutosExpediente < (minutosAgendadas + servico.getDuracao()))
                continue;

            dias.add(i);
        }

        return dias;
    }

    public List<HorarioLivreDTO> obterHorariosLivres(LocalDate data, Long idServico) {
        var idEmpresa = servicoService.getById(idServico).getEmpresa().getId();
        List<Funcionario> funcionarios = funcionarioService.obterPorServico(idServico);
        ExpedienteEmpresa expedienteEmpresa = expedienteEmpresaService.findByDayOfWeek(idEmpresa, data.getDayOfWeek());

        List<HorarioLivreDTO> horariosLivres = new ArrayList<>();

        funcionarios.forEach(funcionario -> {
            HorarioLivreDTO horarioLivreInicial = montarHorarioLivreInicial(funcionario, expedienteEmpresa);

            List<Agendamento> agendamentos = buscarAgendamentosPorFuncionarioData(funcionario.getId(), data);
            if (agendamentos.isEmpty()) {
                horariosLivres.add(horarioLivreInicial);
            } else {
                HorarioLivreDTO horarioLivreFuncionario = montarHorariosLivresFuncionarioComBaseAgendamentos(horarioLivreInicial, agendamentos);
                horariosLivres.add(horarioLivreFuncionario);
            }
        });

        return horariosLivres;
    }

    private static HorarioLivreDTO montarHorarioLivreInicial(Funcionario funcionario, ExpedienteEmpresa expedienteEmpresa) {
        HorarioLivreDTO horarioLivreFuncionario = new HorarioLivreDTO();
        horarioLivreFuncionario.setFuncionario(funcionario);

        AgendamentoLivreDTO matutino = new AgendamentoLivreDTO(expedienteEmpresa.getAberturaMatutino(), expedienteEmpresa.getFechamentoMatutino());
        AgendamentoLivreDTO vespertino = new AgendamentoLivreDTO(expedienteEmpresa.getAberturaVespertino(), expedienteEmpresa.getFechamentoVespertino());

        horarioLivreFuncionario.addAgendamento(matutino);
        horarioLivreFuncionario.addAgendamento(vespertino);

        return horarioLivreFuncionario;
    }

    public HorarioLivreDTO montarHorariosLivresFuncionarioComBaseAgendamentos(HorarioLivreDTO horarioLivreFuncionario, List<Agendamento> agendamentos) {
        List<AgendamentoLivreDTO> agendamentosLivre = horarioLivreFuncionario.getAgendamentosLivre();
        for (int i = 0; i < agendamentosLivre.size();) {
            AgendamentoLivreDTO periodoLivre = agendamentosLivre.get(i);
            i++;

            for (Agendamento agendamento : agendamentos) {
                var inicio = periodoLivre.getInicioHorarioLivre();
                var fim = periodoLivre.getFimHorarioLivre();

                if (agendamento.horarioIsBetween(inicio, fim)) {
                    var horaAgendamento = agendamento.getDataHora().toLocalTime();

                    AgendamentoLivreDTO horarioAposAgendamento = new AgendamentoLivreDTO(inicio, horaAgendamento);
                    AgendamentoLivreDTO horarioAntesAgendamento = new AgendamentoLivreDTO(
                            horaAgendamento.plus(Duration.ofMinutes(agendamento.getServico().getDuracao())), fim);

                    horarioLivreFuncionario.removeAgendamento(periodoLivre);
                    horarioLivreFuncionario.addAgendamento(horarioAposAgendamento);
                    horarioLivreFuncionario.addAgendamento(horarioAntesAgendamento);

                    atualizaPeriodoLivreComBaseAgendamento(periodoLivre, agendamento);
                    i = 0;
                }
            }
        }

        return horarioLivreFuncionario;
    }

    private static void atualizaPeriodoLivreComBaseAgendamento(AgendamentoLivreDTO periodoLivre, Agendamento agendamento) {
        if (agendamento.getHoraInicioAgendamento().equals(periodoLivre.getInicioHorarioLivre())) {
            periodoLivre.setInicioHorarioLivre(agendamento.getHoraFimAgendamento());
        }

        if (agendamento.getHoraFimAgendamento().equals(periodoLivre.getFimHorarioLivre())) {
            periodoLivre.setFimHorarioLivre(agendamento.getHoraInicioAgendamento());
        }
    }

    public List<Agendamento> buscarAgendamentosPorFuncionarioData(Long idFuncionario, LocalDate data) {
        return repository.findAll(qAgendamento.funcionario.id.eq(idFuncionario)
                .and(qAgendamento.dataHora.between(data.atStartOfDay(), data.atTime(23, 59))));
    }

    public Agendamento confirmar(Long id) {
        var agendamento = getById(id);

        if (agendamento.getStatus() != StatusAgendamentoEnum.ABERTO)
            throw new BusinessException("Só pode confirmar um agendamento em aberto.");

        agendamento.setStatus(StatusAgendamentoEnum.AGENDADO);

        agendamento.setPagamento(Pagamento.builder()
                .valor(BigDecimal.valueOf(0.0))
                .statusPagamento(StatusPagamentoEnum.PENDENTE)
                .metodoPagamento(MetodoPagamentoEnum.A_VISTA).build());

        var email = agendamento.getCliente().getEmail();

        if (!email.isEmpty()) {
            var title = "Confirmação de Agendamento";
            var text = "Ficamos felizes em informar que seu agendamento na Agenday foi confirmado com sucesso.";

            emailService.sendEmail(email, title, getHtml(title, text, agendamento));
        }

        return update(id, agendamento);
    }

    public Agendamento cancelar(Long id) {
        var agendamento = getById(id);

        if (agendamento.getStatus() != StatusAgendamentoEnum.ABERTO &&
            agendamento.getStatus() != StatusAgendamentoEnum.AGENDADO)
            throw new BusinessException("Só pode cancelar um agendamento em aberto ou agendado.");

        agendamento.setStatus(StatusAgendamentoEnum.CANCELADO);

        agendamento.setPagamento(Pagamento.builder()
                .valor(BigDecimal.valueOf(0.0))
                .statusPagamento(StatusPagamentoEnum.CANCELADO)
                .metodoPagamento(MetodoPagamentoEnum.A_VISTA).build());

        var email = agendamento.getCliente().getEmail();

        if (!email.isEmpty()) {
            var title = "Cancelamento de Agendamento";
            var text = "Infelizmente seu agendamento na Agenday foi cancelado, tente algum outro horário disponível.";

            emailService.sendEmail(email, title, getHtml(title, text, agendamento));
        }

        return update(id, agendamento);
    }

    public Agendamento concluir(Long id, BigDecimal valor) {
        var agendamento = getById(id);

        if (agendamento.getStatus() != StatusAgendamentoEnum.AGENDADO)
            throw new BusinessException("Só pode concluir um agendamento agendado.");

        agendamento.setStatus(StatusAgendamentoEnum.CONCLUIDO);

        agendamento.setPagamento(Pagamento.builder()
                .valor(valor)
                .statusPagamento(StatusPagamentoEnum.CONCLUIDO)
                .metodoPagamento(MetodoPagamentoEnum.A_VISTA).build());

        var email = agendamento.getCliente().getEmail();

        if (!email.isEmpty()) {
            var title = "Cancelamento de Agendamento";
            var text = "Seu agendamento na Agenday foi finalizado com sucesso! Que tal avaliar seu atendimento?";

            emailService.sendEmail(email, title, getHtml(title, text, agendamento));
        }

        return update(id, agendamento);
    }

    private String getHtml(String title, String text, Agendamento agendamento) {
        return "<!DOCTYPE html>\n" +
               "<html lang=\"pt-br\">\n" +
               "<head>\n" +
               "    <meta charset=\"UTF-8\">\n" +
               "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
               "    <title>" + title + " - Agenday</title>\n" +
               "    <style>\n" +
               "        body {\n" +
               "            font-family: Arial, sans-serif;\n" +
               "            background-color: #f4f4f4;\n" +
               "            margin: 0;\n" +
               "            padding: 0;\n" +
               "        }\n" +
               "\n" +
               "        .container {\n" +
               "            max-width: 600px;\n" +
               "            margin: 20px auto;\n" +
               "            background-color: #fff;\n" +
               "            padding: 20px;\n" +
               "            border-radius: 8px;\n" +
               "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
               "        }\n" +
               "\n" +
               "        h1 {\n" +
               "            color: #333;\n" +
               "        }\n" +
               "\n" +
               "        p {\n" +
               "            color: #555;\n" +
               "        }\n" +
               "\n" +
               "        .footer {\n" +
               "            margin-top: 20px;\n" +
               "            text-align: center;\n" +
               "            color: #888;\n" +
               "            font-size: 12px;\n" +
               "        }\n" +
               "    </style>\n" +
               "</head>\n" +
               "<body>\n" +
               "    <div class=\"container\">\n" +
               "        <h1>" + title + "</h1>\n" +
               "        <p>Olá, " + agendamento.getCliente().getNome() + ",</p>\n" +
               "        <p>" + text + "</p>\n" +
               "        <p>Detalhes do Agendamento:</p>\n" +
               "        <ul>\n" +
               "            <li><strong>Data:</strong> " + agendamento.getDataHora().toLocalDate() + "</li>\n" +
               "            <li><strong>Hora:</strong> " + agendamento.getDataHora().toLocalTime() + "</li>\n" +
               "            <li><strong>Serviço:</strong> " + agendamento.getServico().getNome() + "</li>\n" +
               "        </ul>\n" +
               "        <p>Agradecemos por escolher a Agenday. Estamos ansiosos para vê-lo em breve!</p>\n" +
               "        <div class=\"footer\">\n" +
               "            <p>Atenciosamente,<br>Equipe Agenday</p>\n" +
               "        </div>\n" +
               "    </div>\n" +
               "</body>\n" +
               "</html>";
    }
}
