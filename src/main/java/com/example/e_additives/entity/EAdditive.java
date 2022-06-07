package com.example.e_additives.entity;

import javax.persistence.*;

/**
 * Класс-сущность.
 * Представляет некоторую информацию о пищевой добавке.
 * Содержит поля:
 * <b>key</b> - id в базе данных;
 * <b>type</b> - класс пищевых добавок;
 * <b>index</b> - индекс пищевой добавки;
 * <b>name</b> - название пищевой добавки;
 * <b>information</b> - некоторая информация о добавке.
 */
@Entity
@Table(name = "EAdditives")
public class EAdditive implements Comparable<EAdditive>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key")
    private int key;

    @Column(name = "type")
    private String type;

    @Column(name = "index")
    private String index;

    @Column(name = "name")
    private String name;

    @Column(name = "information")
    private String information;

    public int getKey() {
        return key;
    }

    public void setKey(int id) {
        this.key = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public int compareTo(EAdditive anotherAdditive) {
        return index.compareTo(anotherAdditive.index);
    }
}
