package com.agenday.agendayserv.services;

import com.agenday.agendayserv.enums.StatusAgendamentoEnum;
import com.agenday.agendayserv.models.*;
import com.agenday.agendayserv.models.horariolivre.AgendamentoLivreDTO;
import com.agenday.agendayserv.models.horariolivre.HorarioLivreDTO;
import com.agenday.agendayserv.repositories.AgendamentoRepository;
import com.agenday.agendayserv.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgendamentoService extends BaseService<Agendamento, Long> {
    @Autowired
    private AgendamentoRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private ExpedienteEmpresaService expedienteEmpresaService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Override
    public AgendamentoRepository getRepository() {
        return repository;
    }

    private static final QAgendamento qAgendamento = QAgendamento.agendamento;

    @Override
    public Agendamento add(Agendamento entity) {
        // validar(entity);

        return super.add(entity);
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
        var expedientes = servicoService.getById(idServico).getEmpresa().getExpedientes();

        if (expedientes.isEmpty())
            return dias;

        var agendamentos = obterAbertosPorData(inicio, fim);

        for (var i = inicio; i.isBefore(fim); i = i.plusDays(1)) {
            var dia = i;

            if (expedientes.stream().noneMatch(expediente -> expediente.getDiaSemana() == dia.getDayOfWeek()))
                continue;

            if (agendamentos.stream().anyMatch(expediente -> expediente.getDataHora().toLocalDate() == dia))
                continue;

            dias.add(i);
        }

        return dias;
    }

    public List<HorarioLivreDTO> obterHorariosLivres(LocalDate data, Long idEmpresa) {
        List<Funcionario> funcionarios = funcionarioService.obterPorEmpresa(idEmpresa);
        ExpedienteEmpresa expedienteEmpresa = expedienteEmpresaService.findByEmpresaId(idEmpresa);

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

        AgendamentoLivreDTO vespertino = new AgendamentoLivreDTO(expedienteEmpresa.getAberturaMatutino(), expedienteEmpresa.getFechamentoVespertino());
        AgendamentoLivreDTO matutino = new AgendamentoLivreDTO(expedienteEmpresa.getAberturaVespertino(), expedienteEmpresa.getFechamentoMatutino());

        horarioLivreFuncionario.addAgendamento(vespertino);
        horarioLivreFuncionario.addAgendamento(matutino);
        return horarioLivreFuncionario;
    }


    public HorarioLivreDTO montarHorariosLivresFuncionarioComBaseAgendamentos(HorarioLivreDTO horarioLivreFuncionario, List<Agendamento> agendamentos) {
        horarioLivreFuncionario.getAgendamentosLivre().forEach(periodoLivre -> {
            agendamentos.forEach(agendamento -> {
                if (agendamento.horarioIsBetween(periodoLivre.getInicioHorarioLivre(), periodoLivre.getFimHorarioLivre())) {
                    AgendamentoLivreDTO horarioAposAgendamento =
                            new AgendamentoLivreDTO(periodoLivre.getInicioHorarioLivre(), agendamento.getDataHora().toLocalTime());

                    AgendamentoLivreDTO horarioAntesAgendamento =
                            new AgendamentoLivreDTO(agendamento.getDataHora().toLocalTime()
                                    .plus(Duration.ofMinutes(agendamento.getServico().getDuracao())), periodoLivre.getFimHorarioLivre());

                    horarioLivreFuncionario.removeAgendamento(periodoLivre);
                    horarioLivreFuncionario.addAgendamento(horarioAntesAgendamento);
                    horarioLivreFuncionario.addAgendamento(horarioAposAgendamento);

                    atualizaPeriodoLivreComBaseAgendamento(periodoLivre, agendamento);
                }
            });
        });

        return horarioLivreFuncionario;
    }

    private static void atualizaPeriodoLivreComBaseAgendamento(AgendamentoLivreDTO periodoLivre, Agendamento agendamento) {
        if(agendamento.getHoraInicioAgendamento().equals(periodoLivre.getInicioHorarioLivre())) {
            periodoLivre.setInicioHorarioLivre(agendamento.getHoraFimAgendamento());
        }

        if(agendamento.getHoraFimAgendamento().equals(periodoLivre.getFimHorarioLivre())) {
            periodoLivre.setFimHorarioLivre(agendamento.getHoraInicioAgendamento());
        }
    }

    public List<Agendamento> buscarAgendamentosPorFuncionarioData (Long idFuncionario, LocalDate data) {
        return repository.findAll(qAgendamento.funcionario.id.eq(idFuncionario)
                .and(qAgendamento.dataHora.between(data.atStartOfDay(), data.atTime(23, 59))));
    }

    public void confirmar(Agendamento agendamento, LocalDateTime horario) {
        agendamento.setStatus(StatusAgendamentoEnum.ABERTO);
        agendamento.setDataHora(horario);

        update(agendamento.getId(), agendamento);
    }

    public void cancelar(Agendamento agendamento) {
        agendamento.setStatus(StatusAgendamentoEnum.CANCELADO);

        update(agendamento.getId(), agendamento);
    }

    public void concluir(Agendamento agendamento, Pagamento pagamento) {
        agendamento.setStatus(StatusAgendamentoEnum.CONCLUIDO);
        agendamento.setPagamento(pagamento);

        update(agendamento.getId(), agendamento);
    }

    private void validar(Agendamento agendamento) throws Exception {
        var agora = LocalDateTime.now();

        if (agendamento.getDataHora().isBefore(agora)) {
            throw new Exception("É necessário que o horário seja maior que " + agora);
        }

        validarCliente(agendamento.getCliente());
        validarServico(agendamento.getServico());
        validarFuncionario(agendamento.getFuncionario());
    }

    private void validarCliente(Cliente cliente) throws Exception {
        if (cliente == null) {
            throw new Exception("É necessário informar um cliente");
        }

        if (StringUtils.isNullOrEmpty(cliente.getNome())) {
            throw new Exception("É necessário preencher o campo 'nome'");
        }

        if (StringUtils.isNullOrEmpty(cliente.getEmail()) || StringUtils.isNullOrEmpty(cliente.getTelefone())) {
            throw new Exception("É necessário preencher os campos 'telefone' ou 'email'");
        }
    }

    private void validarServico(Servico servico) throws Exception {
        if (servico == null) {
            throw new Exception("É necessário informar um serviço");
        }
    }

    private void validarFuncionario(Funcionario funcionario) throws Exception {
        if (funcionario == null) {
            throw new Exception("É necessário informar um funcionário");
        }
    }
}
