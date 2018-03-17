package com.dewpoint.rts.controller;

import java.io.File;
import java.io.FileInputStream;

import com.dewpoint.rts.service.CandidateSearchService;
import com.dewpoint.rts.service.*;
import com.dewpoint.rts.util.ApiValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
		ApiValidation.validateRetriveAllUsersResponse(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Return all RTS users based on user id based search")
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public ResponseEntity<UserResponseDTO> searchUser(@PathVariable("userId") String userId) {
		ApiValidation.validateSearchUserRequest(userId);
		UserResponseDTO response = userService.searchUsers(userId);
		ApiValidation.validateSearchUserResponse(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Creates a new RTS user")
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody UserRequestDTO requestDTO) {
		ApiValidation.validateCreateUserRequest(requestDTO);
	    userService.createUser(requestDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Updates a RTS user (User can change default password or Admin can reset user password)")
	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUser(@RequestBody UserRequestDTO requestDTO) {
		ApiValidation.validateUpdateUserRequest(requestDTO);
		userService.updateUser(requestDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Retrieves candidate recent resume (in any format)")
	@RequestMapping(value = "/candidates/resume?candidateId={candidateId}", method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<InputStreamResource> retrieveResume(@RequestParam("candidateId") String candidateId) throws Exception {
		File resumeFile  = candidateSearchService.identifyFile(candidateId);
		String mediaType = candidateSearchService.determineMediaTypeFromFile(resumeFile);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.parseMediaType(mediaType))
				.body(new InputStreamResource(new FileInputStream(resumeFile)));
	}

	@ApiOperation(value = "Retrieves all candidates (non indexed data)")
	@RequestMapping(value = "/candidates", method = RequestMethod.GET)
	public ResponseEntity<CandidateResponseDTO> retrieveAllCandidates() {
		CandidateResponseDTO response = candidateService.retrieveAllCandidates();
		ApiValidation.validateRetriveAllCandidatesResponse(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Retrieves an existing candidate full details")
	@RequestMapping(value = "/candidates/{candidateId}", method = RequestMethod.GET)
	public ResponseEntity<CandidateResponseDTO> searchCandidate(@PathVariable("candidateId") String candidateId) {
		ApiValidation.validateSearchCandidateRequest(candidateId);
		CandidateResponseDTO response = candidateService.searchCandidates(candidateId);
		ApiValidation.validateSearchCandidateResponse(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Creates a new candidate profile")
	@RequestMapping(value = "/candidates", method = RequestMethod.POST)
	public ResponseEntity<?> createCandidate(@RequestBody CandidateRequestDTO requestDTO) {
		ApiValidation.validateCreateCandidateRequest(requestDTO);
		candidateService.createCandidate(requestDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Updates an existing candidate profile")
	@RequestMapping(value = "/candidates", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCandidate(@RequestBody CandidateRequestDTO requestDTO) {
		ApiValidation.validateUpdateCandidateRequest(requestDTO);
		candidateService.updateCandidate(requestDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Retrieves RTS candidates (from indexed data first and then from database)")
	@RequestMapping(value = "/candidates/search", method = RequestMethod.POST)
	public ResponseEntity<CandidateSearchResponseDTO> searchCandidates(@RequestBody CandidateSearchRequestDTO requestDTO) {
		ApiValidation.validateSearchCandidatesRequest(requestDTO);
		// first search index records
		CandidateSearchResponseDTO responseDTO = candidateSearchService.searchForCandidates(requestDTO);
		// if no index records found, search database (This works as Cache hit or Cache miss scenario). Mostly below call will never be used.
		if(responseDTO == null || responseDTO.getCandidates() == null || responseDTO.getCandidates().size() == 0) {
			 CandidateResponseDTO dbResponse = candidateService.searchCandidates(requestDTO);
			 responseDTO = candidateSearchService.searchForCandidates(dbResponse);
		}
		ApiValidation.validateSearchCandidatesResponse(responseDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
}