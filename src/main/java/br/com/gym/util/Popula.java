package br.com.gym.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.persistence.EntityManager;

import br.com.gym.model.*;
import br.com.gym.model.Modalidade;
import br.com.gym.model.enums.Categoria;
import br.com.gym.model.enums.Modo;
import br.com.gym.model.enums.Tipo;

public class Popula {

    public static void main(String[] args) {
        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();


        Calendar data = Calendar.getInstance();
        data.set(Calendar.YEAR, 1995);
        data.set(Calendar.MONTH, Calendar.MARCH);
        data.set(Calendar.DAY_OF_MONTH, 20);

        Plan a = new Plan(Tipo.A);

        a.addExercicio(new Exercise("Flexora", 4, 15, "4pl"));
        a.addExercicio(new Exercise("Adutora", 4, 15, "4pl"));
        a.addExercicio(new Exercise("Abdução", 3, 15, "1pl"));
        a.addExercicio(new Exercise("Bíceps", 4, 15, "6kg"));
        a.addExercicio(new Exercise("Triceps polia", 4, 15, "10kg"));


        Plan b = new Plan(Tipo.B);
        b.addExercicio(
                new ArrayList<>(Arrays.asList(
                        new Exercise("Supino inclinado", 3, 15, "5kg"),
                        new Exercise("Agachamento com bola", 3, 15, "1kg"),
                        new Exercise("remada alta", 3, 15, "6gl"),
                        new Exercise("remada baixa", 3, 15, "2pl"),
                        new Exercise("abnominal", 3, 15, "-")))
        );


        Client ruan = new Client("Ruan Rosa", data, "00838850219", new Address("66645040"),
                Calendar.getInstance());

        ruan.addPlanos(new ArrayList<Plan>(Arrays.asList(a, b)));

        Plan c = new Plan(Tipo.C);
        c.addExercicio(new ArrayList<Exercise>(Arrays.asList(new Exercise("Flexora", 3, 15, "1pl"),
                new Exercise("Adutora", 3, 15, "1pl"), new Exercise("Abdução", 3, 15, "1pl"),
                new Exercise("Bíceps individual", 3, 15, "3kg"), new Exercise("Triceps francês", 3, 15, "2pl"))

        ));

        Plan d = new Plan(Tipo.D);
        d.addExercicio(new ArrayList<Exercise>(Arrays.asList(new Exercise("Supino reto", 3, 15, "5kg"),
                new Exercise("Hack", 3, 15, "1kg"), new Exercise("remada alta", 3, 15, "6gl"),
                new Exercise("remada baixa", 3, 15, "2pl"), new Exercise("abnominal", 3, 15, "-"))

        ));

        Client caio = new Client("Caio bojosa", data, "00838850219", new Address("66645040"), Calendar.getInstance());
        caio.addPlanos(new ArrayList<Plan>(Arrays.asList(c, d)));


        Instructor euler = new Instructor("Euler euler", "00838850219", "eulerfit@gmail.com", "passwd123");
        Instructor franci = new Instructor("Franci do euler", "00838850219", "francifit@gmail.com", "passwd123");

        manager.persist(euler);
        manager.persist(franci);

        manager.persist(ruan);
        manager.persist(caio);

        Modalidade modalidade = new Modalidade(Categoria.MUSCULAÇAO, 50);
        Modalidade modalidade2 = new Modalidade(Categoria.DANÇA, 20);

        caio.addModalidade(modalidade);
        caio.addModalidade(modalidade2);
        ruan.addModalidade(modalidade);

        franci.addModalidade(modalidade);
        euler.addModalidade(modalidade);
        franci.addModalidade(modalidade2);


        Payment matriculaRuan = new Payment(Calendar.getInstance(), ruan.valorTotalModalidades(), Modo.BOLETO);
        Payment matriculaCaio = new Payment(Calendar.getInstance(), caio.valorTotalModalidades(), Modo.AVISTA);

        caio.pagar(matriculaCaio);
        ruan.pagar(matriculaRuan);

        euler.receber(matriculaCaio);
        franci.receber(matriculaRuan);

        manager.getTransaction().commit();
        manager.close();

    }
}
