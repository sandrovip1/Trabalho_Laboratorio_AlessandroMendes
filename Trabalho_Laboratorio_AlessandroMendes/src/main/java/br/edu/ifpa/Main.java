package br.edu.ifpa;

import br.edu.ifpa.laboratorio.dao.EmprestimoDAO;
import br.edu.ifpa.laboratorio.model.Emprestimo;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmprestimoDAO dao = new EmprestimoDAO();
        Scanner teclado = new Scanner(System.in);
        String dataHoje = "05/05/2026"; 

        System.out.println("===== GESTÃO DE LABORATÓRIO IFPA =====");

        // --- PARTE 1: EMPRÉSTIMOS ---
        System.out.println("\n[AÇÃO]: Registrando empréstimos iniciais...");
        boolean possui1 = dao.realizarEmprestimo(new Emprestimo(0, 1, 1, dataHoje, null, "ATIVO"));
        boolean possui2 = dao.realizarEmprestimo(new Emprestimo(0, 2, 2, dataHoje, null, "ATIVO"));

        // --- PARTE 2: MENU DE DEVOLUÇÃO ---
        System.out.println("\n-------------------------------------------------------");
        System.out.println("1 - Devolver Item 1 (Aluno 1)");
        System.out.println("2 - Devolver Item 2 (Aluno 2)");
        System.out.println("3 - Devolver AMBOS");
        System.out.print("Escolha a devolução: ");

        int opcao = teclado.nextInt();
        teclado.nextLine();

        if (opcao == 1 && possui1) {
            dao.registrarDevolucao(1, dataHoje);
            possui1 = false;
        } else if (opcao == 2 && possui2) {
            dao.registrarDevolucao(2, dataHoje);
            possui2 = false;
        } else if (opcao == 3) {
            if (possui1) dao.registrarDevolucao(1, dataHoje);
            if (possui2) dao.registrarDevolucao(2, dataHoje);
            possui1 = false;
            possui2 = false;
        }

        // --- PARTE 3: CONFLITO PELO ITEM 1 ---
        System.out.println("\n-------------------------------------------------------");
        System.out.println("[SOLICITAÇÃO]: Aluno 2 deseja o Item 1...");

        if (possui1) {
            System.out.println("\n[AVISO]: O Aluno 1 ainda não devolveu o Item 1!");
            System.out.println(">>> Pressione ENTER para realizar a devolução e prosseguir.");
            teclado.nextLine();
            dao.registrarDevolucao(1, dataHoje);
            possui1 = false;
        }

        // Aluno 2 pega o Item 1
        dao.realizarEmprestimo(new Emprestimo(0, 2, 1, dataHoje, null, "ATIVO"));

        // --- PARTE 4: FINALIZAÇÃO ---
        System.out.println("\n[SISTEMA]: Finalizando expediente e limpando registros ativos...");
        dao.registrarDevolucao(1, dataHoje);
        dao.registrarDevolucao(2, dataHoje);

        System.out.println("\n===== SIMULAÇÃO CONCLUÍDA =====");
        teclado.close();
    }
}
