package com.dewpoint.rts.controller;

import com.dewpoint.rts.service.*;
import com.dewpoint.rts.util.ApiValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Logger;

@Api(value="RTS", description="Operations pertaining to RTS")
@RestController
@RequestMapping("/v1/")
public class RestApiController {

	public static final Logger logger = Logger.getLogger("RestApiController");

	@Autowired
	private CandidateSearchService candidateSearchService;

	@Autowired
	private UserService userService;

	@Autowired
	private CandidateService candidateService;

	@ApiOperation(value = "Returns all users (Active and InActive)")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<UserResponseDTO> retrieveAllUsers() {
		UserResponseDTO response = userService.retrieveAllUsers();
		ApiValidation.validateRetriveAllUsersResponse(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Return all users based on userid based search")
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public ResponseEntity<UserResponseDTO> searchUser(@PathVariable("userId") String userId) {
		ApiValidation.validateSearchUserRequest(userId);
		UserResponseDTO response = userService.searchUsers(userId);
		ApiValidation.validateSearchUserResponse(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Creates a new user")
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody UserRequestDTO requestDTO) {
		ApiValidation.validateCreateUserRequest(requestDTO);
	    userService.createUser(requestDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Reset user password")
	@RequestMapping(value = "/users/{userId}/reset", method = RequestMethod.PUT)
	public ResponseEntity<String> resetUserPassword(@PathVariable("userId") String userId) {
		ApiValidation.validateSearchUserRequest(userId);
		UserRequestDTO requestDTO = new UserRequestDTO();
		requestDTO.setUserId(userId);
		userService.resetUserPassword(requestDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Reset user status (make inactive profile to active profile again)")
	@RequestMapping(value = "/users/{userId}/activate", method = RequestMethod.PUT)
	public ResponseEntity<String> resetUserStatus(@PathVariable("userId") String userId) {
		ApiValidation.validateSearchUserRequest(userId);
		UserRequestDTO requestDTO = new UserRequestDTO();
		requestDTO.setUserId(userId);
		userService.resetUserStatus(requestDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Change default user password")
	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	public ResponseEntity<String> changeUserPassword(@RequestBody UserRequestDTO requestDTO) {
		ApiValidation.validateUpdateUserRequest(requestDTO);
		userService.updateUser(requestDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Inactivate user profile")
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {
		ApiValidation.validateSearchUserRequest(userId);
		UserRequestDTO requestDTO = new UserRequestDTO();
		requestDTO.setUserId(userId);
		userService.deleteUser(requestDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Retrieves candidate recent resume (in PDF format)")
	@RequestMapping(value = "/candidates/resume", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> retrieveResume(@RequestParam(value = "candidateId")  String candidateId) throws Exception {
		ApiValidation.validateSearchCandidateRequest(candidateId);
		File resumeFile  = candidateSearchService.identifyFile(candidateId);
		String mediaType = candidateSearchService.determineMediaTypeFromFile(resumeFile);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.parseMediaType(mediaType))
				.body(new InputStreamResource(new FileInputStream(resumeFile + ".pdf")));
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