package com.zhaohg.kisso.oauth2.service.impl;

import java.util.List;

import com.zhaohg.kisso.oauth2.dao.ClientDao;
import com.zhaohg.kisso.oauth2.entity.Client;
import com.zhaohg.kisso.oauth2.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.kisso.common.util.RandomUtil;

@Transactional
@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientDao clientDao;


	@Override
	public Client createClient(Client client ) {
		/**
		 * 这里使用了 32位 UUID ， 当然你可以根据具体业务生成自己的唯一 ID
		 */
		client.setClientId(RandomUtil.get32UUID());
		client.setClientSecret(RandomUtil.get32UUID());
		return clientDao.createClient(client);
	}


	@Override
	public Client updateClient( Client client ) {
		return clientDao.updateClient(client);
	}


	@Override
	public void deleteClient( Long clientId ) {
		clientDao.deleteClient(clientId);
	}


	@Override
	public Client findOne( Long clientId ) {
		return clientDao.findOne(clientId);
	}


	@Override
	public List<Client> findAll() {
		return clientDao.findAll();
	}


	@Override
	public Client findByClientId( String clientId ) {
		return clientDao.findByClientId(clientId);
	}


	@Override
	public Client findByClientSecret( String clientSecret ) {
		return clientDao.findByClientSecret(clientSecret);
	}
}
