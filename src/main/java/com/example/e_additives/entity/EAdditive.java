package com.example.e_additives.entity;

import javax.persistence.*;

@Entity
@Table(name = "EAdditives")
public class EAdditive implements Comparable<EAdditive>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "type")
    private String type;

    @Column(name = "index")
    private String index;

    @Column(name = "name")
    private String name;

    @Column(name = "information")
    private String information;

    @Override
    public int compareTo(EAdditive anotherAdditives) {
        return index.compareTo(anotherAdditives.index);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.type = index;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
