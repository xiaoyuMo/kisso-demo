package com.zhaohg.kisso.oauth2.service;

import com.zhaohg.kisso.oauth2.entity.Client;

import java.util.List;

public interface ClientService {

    public Client createClient(Client client);


    public Client updateClient(Client client);


    public void deleteClient(Long clientId);


    Client findOne(Long clientId);


    List<Client> findAll();


    Client findByClientId(String clientId);


    Client findByClientSecret(String clientSecret);

}
