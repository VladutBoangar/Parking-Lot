package com.parkinglot.parkinglot.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "car_photo")
public class CarPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    private String filename;
    private String fileType;
    private byte[] fileContent;
    private Car car;


    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }


    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }


    public byte[] getFileContent() {
        return fileContent;
    }
    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

@OneToOne
    public Car getCar() {return car;}



    public void setCar(Car car) {
        this.car = car;
    }



}