package com.example.metier;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.ClientRepository;
import com.example.dao.CompteRepository;
import com.example.dao.OperationRepository;
import com.example.entities.Compte;
import com.example.entities.CompteCourant;
import com.example.entities.Operation;
import com.example.entities.Retrait;
import com.example.entities.Versement;
@Service
@Transactional
public class MetierImpl implements Imetier {

	@Autowired
	ClientRepository clientRepository;
	@Autowired

	OperationRepository  operationRepository;
	@Autowired

	CompteRepository  compteRepository;
	@Override
	public Compte consulterCompte(String codeCpte) {
		Compte compte=compteRepository.findOne(codeCpte);
		if(compte==null) throw new RuntimeException("Compte introuvable");
		return compte;
	}

	@Override
	public void verser(String codeCpte, double montant) {
		Compte cp=compteRepository.findOne(codeCpte);
		Versement versement=new Versement(new Date(),montant,cp);
		operationRepository.save(versement);
		cp.setSolde(cp.getSolde()+montant);
		compteRepository.save(cp);
				
		
		
	}

	@Override
	public void retirer(String codeCpte, double montant) {
		double facilitesCaisse=0;
		Compte cp=compteRepository.findOne(codeCpte);
		if(cp instanceof CompteCourant)
			facilitesCaisse=((CompteCourant) cp).getDecouverte();
		if(cp.getSolde()+facilitesCaisse>montant)
			throw new RuntimeException("Solde insuffisant");
		
			
		Retrait retrait=new Retrait(new Date(),montant,cp);
		operationRepository.save(retrait);
		cp.setSolde(cp.getSolde()-montant);
		compteRepository.save(cp);
		
	}

	@Override
	public void virement(String codeCpte1, String codeCpte2, double montant) {
		retirer( codeCpte1,  montant);
		verser(codeCpte2, montant);
		
	}

	@Override
	public Page<Operation> listOperation(String codeCpte, int page, int size) {
		
		return operationRepository.listOperation(codeCpte,  new PageRequest(page, size));
	}

}
