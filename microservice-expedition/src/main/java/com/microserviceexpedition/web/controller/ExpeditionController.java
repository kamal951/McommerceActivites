package com.microserviceexpedition.web.controller;

import com.microserviceexpedition.dao.ExpeditionDao;
import com.microserviceexpedition.model.Expedition;
import com.microserviceexpedition.web.exceptions.ExpeditionNotFoundException;
import com.microserviceexpedition.web.exceptions.ImpossibleAjouterExpeditionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ExpeditionController {

    @Autowired
    ExpeditionDao expeditionDao;

    Logger log = LoggerFactory.getLogger(this.getClass());

    /*
    * Création d'une expedition par méthode POST
    * */
    @PostMapping(value = "/expedition")
    public ResponseEntity<Expedition> ajouterExpedition(@RequestBody Expedition expedition){

        Expedition nouvelleExpedition = expeditionDao.save(expedition);

        log.info(nouvelleExpedition.toString());

        if(nouvelleExpedition == null) throw new ImpossibleAjouterExpeditionException("Impossible d'ajouter cette expedition");

        return new ResponseEntity<Expedition>(expedition, HttpStatus.CREATED);
    }

    /*
    * On récupère une expedition avec son id
    * */
    @GetMapping(value = "expedition/{id}")
    public Optional<Expedition> recupererUneExpedition(@PathVariable int id){

        Optional<Expedition> expedition = expeditionDao.findById(id);

        if(!expedition.isPresent()) throw new ExpeditionNotFoundException("Cette expedition n'existe pas");

        return expedition;
    }

    /*
    * Mise à jour d'une expedition avec PUT
    * */
    @PutMapping(value = "/expedition")
    public void updateExpedition(@RequestBody Expedition expedition) {

        //On vérifie si l'expedition existe pour renvoyer le bon code en cas d'erreur
        Optional<Expedition> tempExpedition = expeditionDao.findById(expedition.getId());

        if(!tempExpedition.isPresent()) throw new ExpeditionNotFoundException("Cette expedition n'existe pas");

        expeditionDao.save(expedition);
    }
}
