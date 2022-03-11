package com.cardozo.multidb.services;

import com.cardozo.multidb.repositories.maindatabase.EquipmentRepository;
import com.cardozo.multidb.repositories.anothersdatabase.PeopleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
@Service
public class MainService {
    private final PeopleRepository peopleRepository;
    private final EquipmentRepository equipmentRepository;

    public void run() {
        equipmentRepository.findAll()
                .forEach(System.out::println);
        peopleRepository.findAll()
                .forEach(System.out::println);
        equipmentRepository.findAll()
                .forEach(System.out::println);
        peopleRepository.findAll()
                .forEach(System.out::println);
        equipmentRepository.findAll()
                .forEach(System.out::println);
        peopleRepository.findAll()
                .forEach(System.out::println);
        equipmentRepository.findAll()
                .forEach(System.out::println);
    }
}
