package com.dewpoint.rts.service;

import com.dewpoint.rts.dao.CandidateDao;
import com.dewpoint.rts.dto.CandidateDTO;
import com.dewpoint.rts.dto.CandidateRequestDTO;
import com.dewpoint.rts.dto.CandidateResponseDTO;
import com.dewpoint.rts.dto.CandidateSearchRequestDTO;
import com.dewpoint.rts.mapper.CandidateMapper;
import com.dewpoint.rts.model.Candidate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CandidateService {

    private CandidateMapper candidateMapper;
    private CandidateDao candidateDao;

    public CandidateService(CandidateDao candidateDao, CandidateMapper candidateMapper) {
        this.candidateDao = candidateDao;
        this.candidateMapper = candidateMapper;
    }

    public CandidateResponseDTO retrieveAllCandidates() {
        CandidateResponseDTO response = new CandidateResponseDTO();
        List<Candidate> candidates = this.candidateDao.findAll();
        if(!candidates.isEmpty()) {
            List<CandidateDTO> candidateList = new ArrayList<>();
            for(Candidate aCandidate: candidates) {
                candidateList.add(candidateMapper.convertEntityToDTO(aCandidate));
            }
            response.setCandidates(candidateList);
        }

        return response;
    }

    public void createCandidate(CandidateRequestDTO candidateRequestDTO) {
        Candidate candidate = candidateMapper.formatCreateEntry(candidateRequestDTO, null);
        this.candidateDao.create(candidate);
    }

    public void updateCandidate(CandidateRequestDTO candidateRequestDTO) {
        Candidate searchCandidate = candidateMapper.formatSearchEntry(candidateRequestDTO);
        List<Candidate> candidates = this.candidateDao.findByEntity(searchCandidate);

        if(!candidates.isEmpty()) {
            Candidate candidate = candidateMapper.formatUpdateEntry(candidates.get(0), candidateRequestDTO, null);
            this.candidateDao.update(candidate);
        }
    }

    public CandidateResponseDTO searchCandidates(String candidateId) {
        CandidateResponseDTO response = new CandidateResponseDTO();
        Map<String, String> params = new HashMap<>();
        params.put("candidateId", candidateId);
        List<Candidate> candidates = this.candidateDao.findByNamedQueryAndNamedParams("Candidate.findSpecific", params);

        if(!candidates.isEmpty()) {
            List<CandidateDTO> candidatesList = new ArrayList<>();
            for(Candidate aCandidate: candidates) {
                candidatesList.add(candidateMapper.convertEntityToDTO(aCandidate));
            }
            response.setCandidates(candidatesList);
        }

        return response;
    }

    public CandidateResponseDTO searchCandidates(CandidateSearchRequestDTO requestDTO) {
        CandidateResponseDTO response = new CandidateResponseDTO();
        Candidate searchEntity = new Candidate();
        searchEntity.setStatus(requestDTO.getStatus());
        searchEntity.setSource(requestDTO.getSource());
        searchEntity.setSkills(requestDTO.getSkills());
        searchEntity.setClientName(requestDTO.getClientName());
        searchEntity.setClientCity(requestDTO.getClientCity());
        searchEntity.setClientState(requestDTO.getClientState());
        searchEntity.setClientZip(requestDTO.getClientZip());

        List<Candidate> candidates = this.candidateDao.findByEntity(searchEntity);

        if(!candidates.isEmpty()) {
            List<CandidateDTO> candidatesList = new ArrayList<>();
            for(Candidate aCandidate: candidates) {
                candidatesList.add(candidateMapper.convertEntityToDTO(aCandidate));
            }
            response.setCandidates(candidatesList);
        }

        return response;
    }
}
