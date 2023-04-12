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

  @Column(name = "createDate")
  private LocalDate createDate;

  @Column(name = "name")
  private String name;

  @Column(name = "phone")
  private String phone;

  @Column(name = "email")
  private String email;

  @Column(name = "address")
  private String address;

  @Column(name = "address2")
  private String address2;
  
  @Column(name = "city")
  private String city;

  @Column(name = "state")
  private String state;

  @Column(name = "zip")
  private String zip;

  @Column(name = "country")
  private String country;
}
