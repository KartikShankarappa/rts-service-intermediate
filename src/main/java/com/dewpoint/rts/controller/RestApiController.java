package com.dewpoint.rts.controller;

import java.io.IOException;
import java.io.InputStream;

import com.dewpoint.rts.service.CandidateSearchService;
import com.dewpoint.rts.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dewpoint.rts.util.CustomErrorType;

@Api(value="RTS", description="Operations pertaining to RTS")
@RestController
@RequestMapping("/v1/")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	private CandidateSearchService candidateSearchService;

	@Autowired
	private UserService userService;

	@Autowired
	private CandidateService candidateService;

	@ApiOperation(value = "Returns all RTS users")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<UserResponseDTO> retrieveAllUsers() {
		UserResponseDTO response = userService.retrieveAllUsers();
		if (response == null || response.getUsers().isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Return all RTS users based on user id based search")
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public ResponseEntity<UserResponseDTO> searchUser(@PathVariable("userId") String userId) {
		UserResponseDTO response = userService.searchUsers(userId);
		if (response == null || response.getUsers().isEmpty()) {
			return new ResponseEntity(new CustomErrorType("User with id " + userId
					+ " not found"), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Creates a new RTS user")
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody UserRequestDTO requestDTO) {
		userService.createUser(requestDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Updates a RTS user (User can change default password or Admin can reset user password)")
	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUser(@RequestBody UserRequestDTO requestDTO) {
		userService.updateUser(requestDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Retrieves candidate recent resume (in any format)")
	@RequestMapping(value = "/candidates/{candidateId}/resume", method = RequestMethod.GET)
	public @ResponseBody byte[] retrieveResume() throws IOException {
		final InputStream in = getClass().getResourceAsStream("/mytest1.pdf");
		return IOUtils.toByteArray(in);
	}

	@ApiOperation(value = "Retrieves all candidates (non indexed data)")
	@RequestMapping(value = "/candidates", method = RequestMethod.GET)
	public ResponseEntity<CandidateResponseDTO> retrieveAllCandidates() {
		CandidateResponseDTO response = candidateService.retrieveAllCandidates();
		if (response == null || response.getCandidates().isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Retrieves an existing candidate full details")
	@RequestMapping(value = "/candidates/{candidateId}", method = RequestMethod.GET)
	public ResponseEntity<CandidateResponseDTO> searchCandidate(@PathVariable("candidateId") String candidateId) {
		CandidateResponseDTO response = candidateService.searchCandidates(candidateId);
		if (response == null || response.getCandidates().isEmpty()) {
			return new ResponseEntity(new CustomErrorType("Candidate with id " + candidateId
					+ " not found"), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Creates a new candidate profile")
	@RequestMapping(value = "/candidates", method = RequestMethod.POST)
	public ResponseEntity<?> createCandidate(@RequestBody CandidateRequestDTO requestDTO) {
		candidateService.createCandidate(requestDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Updates an existing candidate profile")
	@RequestMapping(value = "/candidates", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCandidate(@RequestBody CandidateRequestDTO requestDTO) {
		candidateService.updateCandidate(requestDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Retrieves RTS candidates (from indexed data first and then from database)")
	@RequestMapping(value = "/candidates/search", method = RequestMethod.POST)
	public ResponseEntity<CandidateSearchResponseDTO> searchCandidates(@RequestBody CandidateSearchRequestDTO requestDTO) {

		// first search index records
		CandidateSearchResponseDTO response = candidateSearchService.searchForCandidates(requestDTO);

		// if no index records found, search database (This works as Cache hit or Cache miss scenario). Mostly below call will never be used.
		if(response == null || response.getCandidates().isEmpty()) {
			 CandidateResponseDTO dbResponse = candidateService.searchCandidates(requestDTO);
			 response = candidateSearchService.searchForCandidates(dbResponse);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}