package com.dewpoint.rts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.dewpoint.rts.model.Candidate;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * This service is responsible for retrieving information from Lucene index files
 */
@Component
public class CandidateSearchService {

    private final static Logger logger = Logger.getLogger("CandidateSearchService");

    @Value("${document.url}")
    private String documentURL;

    @Autowired
    private final EntityManager entityManager;

    @Autowired
    public CandidateSearchService(EntityManager entityManager) {
       this.entityManager = entityManager;
    }

    public void initializeLuceneSearch() {
        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Could not initialize hibernate Lucene Indexing " + e.getMessage());
        }
    }

    @Transactional
    public CandidateSearchResponseDTO searchForCandidates(CandidateSearchRequestDTO searchRequestDTO) {
        logger.log(Level.INFO, "trying to index query recs fuzzySearch");
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Candidate.class).get();
        Query luceneQuery = qb.keyword()
                                .fuzzy()
                                .withEditDistanceUpTo(1)
                                .withPrefixLength(1)
                                .onFields("clientName")
                                .matching(searchRequestDTO.getClientName())
                                .createQuery();

        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Candidate.class);

        List<Candidate> candidateList = new ArrayList<>();
        try {
            candidateList = jpaQuery.getResultList();
        } catch (NoResultException nre) {
            // do nothing
        }

        CandidateSearchResponseDTO response = new CandidateSearchResponseDTO();

        if(!candidateList.isEmpty()) {
            List<CandidateSummaryDTO> candidatesPartialList = new ArrayList<>();
            for(Candidate aCandidate: candidateList) {
                candidatesPartialList.add(convertEntityToSummaryDTO(aCandidate));
            }
            response.setCandidates(candidatesPartialList);
            logger.log(Level.INFO, " fuzzy search recs size" + candidatesPartialList.size());

        }

        return response;
    }

    public CandidateSearchResponseDTO searchForCandidates(CandidateResponseDTO candidateResponseDTO) {
        CandidateSearchResponseDTO response = new CandidateSearchResponseDTO();

        if(candidateResponseDTO != null
                && !candidateResponseDTO.getCandidates().isEmpty()) {
            List<CandidateSummaryDTO> candidatesPartialList = new ArrayList<>();
            for(CandidateDTO aCandidate: candidateResponseDTO.getCandidates()) {
                candidatesPartialList.add(convertEntityToSummaryDTO(aCandidate));
            }
            response.setCandidates(candidatesPartialList);
            logger.log(Level.INFO, " DB search recs size" + candidatesPartialList.size());
        }

        return response;
    }

    private CandidateSummaryDTO convertEntityToSummaryDTO(Candidate candidate) {
        CandidateSummaryDTO candidateDTO = new CandidateSummaryDTO();
        candidateDTO.setStatus(candidate.getStatus());
        candidateDTO.setEmail(candidate.getEmail());
        candidateDTO.setFirstName(candidate.getFirstName());
        candidateDTO.setLastName(candidate.getLastName());
        candidateDTO.setResumeLink(documentURL + candidate.getEmail());
        return candidateDTO;
    }

    private CandidateSummaryDTO convertEntityToSummaryDTO(CandidateDTO candidate) {
        CandidateSummaryDTO candidateDTO = new CandidateSummaryDTO();
        candidateDTO.setStatus(candidate.getApplicationStatus());
        candidateDTO.setEmail(candidate.getEmail());
        candidateDTO.setFirstName(candidate.getFirstName());
        candidateDTO.setLastName(candidate.getLastName());
        candidateDTO.setResumeLink(documentURL + candidate.getEmail());
        return candidateDTO;
    }




}