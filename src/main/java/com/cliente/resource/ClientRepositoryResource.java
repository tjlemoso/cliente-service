package com.cliente.resource;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cliente.model.Client;
import com.cliente.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


import io.quarkus.panache.common.Sort;

@Path("clients")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
//@RequestScoped
public class ClientRepositoryResource {

    @Inject
    ClientRepository clientRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRepositoryResource.class.getName());

    @GET
    public List<Client> get() {
        return clientRepository.listAll(Sort.by("name"));
    }

    @GET
    @Path("{id}")
    public Client getSingle(Long id) {
        Client entity = clientRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Client with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    @POST
    @Transactional
    public Response create(Client client) {
        if (client.getName() == "") {
            throw new WebApplicationException("Name was invalidly set on request.", 422);
        }

        if (clientRepository.findByIdentity(client.getIdentity())) {
            throw new WebApplicationException("Client with Identity of " + client.getIdentity() + " does exist.", 404);
        }

        clientRepository.persist(client);
        return Response.ok(client).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Client update(Long id, Client client) {
        if (client.getName() == null) {
            throw new WebApplicationException("Client Name was not set on request.", 422);
        }

        Client entity = clientRepository.findById(id);

        if (entity == null) {
            throw new WebApplicationException("Client with id of " + id + " does not exist.", 404);
        }


        entity.setName(client.getName());
        entity.setPhone(client.getPhone());
        entity.setEmail(client.getEmail());
        entity.setLatitude(client.getLatitude());
        entity.setLongitude(client.getLongitude());
        entity.setAddressComplement(client.getAddressComplement());
        entity.setDescription(client.getDescription());
        entity.setIdentity(client.getIdentity());
        
        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(Long id) {
      Client entity = clientRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Client with id of " + id + " does not exist.", 404);
        }

        clientRepository.delete(entity);
        return Response.status(204).build();
    }

    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Inject
        ObjectMapper objectMapper;

        @Override
        public Response toResponse(Exception exception) {
            LOGGER.error("Failed to handle request", exception);

            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }

            ObjectNode exceptionJson = objectMapper.createObjectNode();
            exceptionJson.put("exceptionType", exception.getClass().getName());
            exceptionJson.put("code", code);

            if (exception.getMessage() != null) {
                exceptionJson.put("error", exception.getMessage());
            }

            return Response.status(code)
                    .entity(exceptionJson)
                    .build();
        }

    }
}