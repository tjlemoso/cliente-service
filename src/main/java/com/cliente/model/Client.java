package com.cliente.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "client")
@Getter
@Setter
public class Client {

  @Id
  @Column(name = "clientId")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long clientId;

  @Column(name = "createData")
  private LocalDate createData;

  @Column(name = "name")
  private String name;

  @Column(name = "phone")
  private String phone;

  @Column(name = "email")
  private String email;

  @Column(name = "latitude")
  private double latitude;

  @Column(name = "longitude")
  private double longitude;

  @Column(name = "addressComplement")
  private String addressComplement;

  @Column(name = "description")
  private String description;

  @Column(name = "identity")
  private String identity;
}
