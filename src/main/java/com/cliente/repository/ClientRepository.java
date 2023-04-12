package com.cliente.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;
import com.cliente.model.Client;

@ApplicationScoped
public class ClientRepository implements PanacheRepository<Client> {

  public Client findByName(String name){
    return find("name", name).firstResult();
  }
}