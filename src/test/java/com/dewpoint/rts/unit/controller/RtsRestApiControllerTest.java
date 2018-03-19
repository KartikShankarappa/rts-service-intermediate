package com.dewpoint.rts.unit.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dewpoint.rts.controller.RtsRestApiController;
import com.dewpoint.rts.dto.CandidateDTO;
import com.dewpoint.rts.dto.CandidateRequestDTO;
import com.dewpoint.rts.dto.CandidateResponseDTO;
import com.dewpoint.rts.dto.CandidateSearchRequestDTO;
import com.dewpoint.rts.dto.CandidateSearchResponseDTO;
import com.dewpoint.rts.dto.CandidateSummaryDTO;
import com.dewpoint.rts.dto.UserDTO;
import com.dewpoint.rts.dto.UserRequestDTO;
import com.dewpoint.rts.dto.UserResponseDTO;
import com.dewpoint.rts.service.CandidateSearchService;
import com.dewpoint.rts.service.CandidateService;
import com.dewpoint.rts.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class RtsRestApiControllerTest {
	
	@Mock
	private CandidateSearchService candidateSearchServiceMock;
	@Mock
	private UserService userServiceMock;
	@Mock
	private CandidateService candidateServiceMock;
	@InjectMocks
	private RtsRestApiController controller;
	@Mock
	Principal principal;

	@Test
	public void retrieveAllUsersTest() {
		UserDTO userDTO1 = new UserDTO();
		userDTO1.setUserFullName("John Doe");
		UserDTO userDTO2 = new UserDTO();
		userDTO2.setUserFullName("Harvey Spector");
		List<UserDTO> users = new ArrayList<UserDTO>();
		users.add(userDTO1);
		users.add(userDTO2);
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		userResponseDTO.setUsers(users);
		
		when(userServiceMock.retrieveAllUsers()).thenReturn(userResponseDTO);
		
		ResponseEntity<UserResponseDTO> response = controller.retrieveAllUsers();
		
		assertEquals(2, response.getBody().getUsers().size());
		assertEquals("John Doe", response.getBody().getUsers().get(0).getUserFullName());
		assertEquals("Harvey Spector", response.getBody().getUsers().get(1).getUserFullName());
	}
	
	@Test
	public void searchUserTest() {
		UserDTO userDTO1 = new UserDTO();
		userDTO1.setUserFullName("John Doe");
		List<UserDTO> users = new ArrayList<UserDTO>();
		users.add(userDTO1);
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		userResponseDTO.setUsers(users);
		
		when(userServiceMock.searchUsers(anyString())).thenReturn(userResponseDTO);
		
		ResponseEntity<UserResponseDTO> response = controller.searchUser("johndoe");
		
		assertEquals(1, response.getBody().getUsers().size());
		assertEquals("John Doe", response.getBody().getUsers().get(0).getUserFullName());
	}
	
	@Test
	@Ignore
	public void createUserTest() {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserFullName("John Doe");
		userRequestDTO.setRole("admin");
		userRequestDTO.setUserId("johndoe");
		userRequestDTO.setPassword("welcome");
		
		doNothing().when(userServiceMock).createUser(userRequestDTO, principal);
		
		ResponseEntity<?> response = controller.createUser(userRequestDTO, principal);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@Ignore
	public void resetUserPasswordTest() {
		doNothing().when(userServiceMock).resetUserPassword(any(UserRequestDTO.class), principal);
		
		ResponseEntity<?> response = controller.resetUserPassword("johndoe", principal);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@Ignore
	public void resetUserStatusTest() {
		doNothing().when(userServiceMock).resetUserStatus(any(UserRequestDTO.class), principal);
		
		ResponseEntity<?> response = controller.resetUserStatus("johndoe", principal);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@Ignore
	public void changeUserPasswordTest() {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserId("johndoe");
		userRequestDTO.setPassword("welcome");
		doNothing().when(userServiceMock).updateUser(any(UserRequestDTO.class), principal);
		
		ResponseEntity<?> response = controller.changeUserPassword(userRequestDTO, principal);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@Ignore
	public void deleteUserTest() {
		doNothing().when(userServiceMock).deleteUser(any(UserRequestDTO.class), principal);
		
		ResponseEntity<?> response = controller.deleteUser("johndoe", principal);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void retrieveAllCandidatesTest() throws Exception {
		CandidateDTO candidateDTO1 = new CandidateDTO();
		candidateDTO1.setFirstName("John");
		candidateDTO1.setLastName("Snow");
		candidateDTO1.setCurrentJobTitle("King of the North");
		candidateDTO1.setEmail("john.snow@gmail.com");
		CandidateDTO candidateDTO2 = new CandidateDTO();
		candidateDTO2.setFirstName("Tyrian");
		candidateDTO2.setLastName("Lannister");
		candidateDTO2.setCurrentJobTitle("Hand of the King");
		candidateDTO2.setEmail("tyrian.lannister@gmail.com");
		List<CandidateDTO> candidates = new ArrayList<CandidateDTO>();
		candidates.add(candidateDTO1);
		candidates.add(candidateDTO2);
		CandidateResponseDTO candidateResponseDTO = new CandidateResponseDTO();
		candidateResponseDTO.setCandidates(candidates);
		
		when(candidateServiceMock.retrieveAllCandidates()).thenReturn(candidateResponseDTO);
		
		ResponseEntity<CandidateResponseDTO> response = controller.retrieveAllCandidates();
		
		assertEquals(2, response.getBody().getCandidates().size());
		assertEquals("John", response.getBody().getCandidates().get(0).getFirstName());
		assertEquals("Snow", response.getBody().getCandidates().get(0).getLastName());
		assertEquals("King of the North", response.getBody().getCandidates().get(0).getCurrentJobTitle());
		assertEquals("john.snow@gmail.com", response.getBody().getCandidates().get(0).getEmail());
		assertEquals("Tyrian", response.getBody().getCandidates().get(1).getFirstName());
		assertEquals("Lannister", response.getBody().getCandidates().get(1).getLastName());
		assertEquals("Hand of the King", response.getBody().getCandidates().get(1).getCurrentJobTitle());
		assertEquals("tyrian.lannister@gmail.com", response.getBody().getCandidates().get(1).getEmail());
	}
	
	@Test
	public void searchCandidateTest() throws Exception {
		CandidateDTO candidateDTO1 = new CandidateDTO();
		candidateDTO1.setFirstName("John");
		candidateDTO1.setLastName("Snow");
		candidateDTO1.setCurrentJobTitle("King of the North");
		candidateDTO1.setEmail("john.snow@gmail.com");
		List<CandidateDTO> candidates = new ArrayList<CandidateDTO>();
		candidates.add(candidateDTO1);
		CandidateResponseDTO candidateResponseDTO = new CandidateResponseDTO();
		candidateResponseDTO.setCandidates(candidates);
		
		when(candidateServiceMock.searchCandidates(anyString())).thenReturn(candidateResponseDTO);
		
		ResponseEntity<CandidateResponseDTO> response = controller.searchCandidate("john.snow@gmail.com");
		
		assertEquals(1, response.getBody().getCandidates().size());
		assertEquals("John", response.getBody().getCandidates().get(0).getFirstName());
		assertEquals("Snow", response.getBody().getCandidates().get(0).getLastName());
		assertEquals("King of the North", response.getBody().getCandidates().get(0).getCurrentJobTitle());
		assertEquals("john.snow@gmail.com", response.getBody().getCandidates().get(0).getEmail());
	}
	
	@Test
	public void createCandidateTest() {
		CandidateRequestDTO candidateRequestDTO = new CandidateRequestDTO();
		candidateRequestDTO.setFirstName("John");
		candidateRequestDTO.setLastName("Snow");
		candidateRequestDTO.setLastJobTitle("King of the North");
		candidateRequestDTO.setEmail("john.snow@gmail.com");
		candidateRequestDTO.setSkills("Java, Angular");
		
		doNothing().when(candidateServiceMock).createCandidate(candidateRequestDTO);
		
		ResponseEntity<?> response = controller.createCandidate(candidateRequestDTO);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void updateCandidateTest() {
		CandidateRequestDTO candidateRequestDTO = new CandidateRequestDTO();
		candidateRequestDTO.setFirstName("John");
		candidateRequestDTO.setLastName("Snow");
		candidateRequestDTO.setLastJobTitle("King of the North");
		candidateRequestDTO.setEmail("john.snow@gmail.com");
		candidateRequestDTO.setSkills("Java, Angular");
		
		doNothing().when(candidateServiceMock).updateCandidate(candidateRequestDTO);
		
		ResponseEntity<?> response = controller.updateCandidate(candidateRequestDTO);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void searchCandidatesTest() {
		CandidateSearchRequestDTO candidateSearchRequestDTO = new CandidateSearchRequestDTO();
		candidateSearchRequestDTO.setStatus("Available");
		candidateSearchRequestDTO.setResumeKeywords("Java");
		CandidateSummaryDTO candidateSummaryDTO1 = new CandidateSummaryDTO();
		candidateSummaryDTO1.setFirstName("John");
		candidateSummaryDTO1.setLastName("Snow");
		candidateSummaryDTO1.setStatus("Available");
		candidateSummaryDTO1.setResumeLink("www.resumes.com/johnsnow");
		candidateSummaryDTO1.setEmail("john.snow@gmail.com");
		CandidateSummaryDTO candidateSummaryDTO2 = new CandidateSummaryDTO();
		candidateSummaryDTO2.setFirstName("Tyrian");
		candidateSummaryDTO2.setLastName("Lannister");
		candidateSummaryDTO2.setStatus("Available");
		candidateSummaryDTO2.setResumeLink("www.resumes.com/tyrianlannister");
		candidateSummaryDTO2.setEmail("tyrian.lannister@gmail.com");
		List<CandidateSummaryDTO> candidates = new ArrayList<CandidateSummaryDTO>();
		candidates.add(candidateSummaryDTO1);
		candidates.add(candidateSummaryDTO2);
		CandidateSearchResponseDTO candidateSearchResponseDTO = new CandidateSearchResponseDTO();
		candidateSearchResponseDTO.setCandidates(candidates);
		
		when(candidateSearchServiceMock.searchForCandidates(candidateSearchRequestDTO)).thenReturn(candidateSearchResponseDTO);
		
		ResponseEntity<CandidateSearchResponseDTO> response = controller.searchCandidates(candidateSearchRequestDTO);
		
		assertEquals(2, response.getBody().getCandidates().size());
		assertEquals("John", response.getBody().getCandidates().get(0).getFirstName());
		assertEquals("Snow", response.getBody().getCandidates().get(0).getLastName());
		assertEquals("Available", response.getBody().getCandidates().get(0).getStatus());
		assertEquals("www.resumes.com/johnsnow", response.getBody().getCandidates().get(0).getResumeLink());
		assertEquals("john.snow@gmail.com", response.getBody().getCandidates().get(0).getEmail());
		assertEquals("Tyrian", response.getBody().getCandidates().get(1).getFirstName());
		assertEquals("Lannister", response.getBody().getCandidates().get(1).getLastName());
		assertEquals("Available", response.getBody().getCandidates().get(1).getStatus());
		assertEquals("www.resumes.com/tyrianlannister", response.getBody().getCandidates().get(1).getResumeLink());
		assertEquals("tyrian.lannister@gmail.com", response.getBody().getCandidates().get(1).getEmail());
	}
	
	@Test
	public void searchCandidates_NoIndexing_NullResponse_Test() {
		CandidateSearchRequestDTO candidateSearchRequestDTO = new CandidateSearchRequestDTO();
		candidateSearchRequestDTO.setStatus("Available");
		candidateSearchRequestDTO.setResumeKeywords("Java");
		CandidateDTO candidateDTO1 = new CandidateDTO();
		candidateDTO1.setFirstName("John");
		candidateDTO1.setLastName("Snow");
		candidateDTO1.setCurrentJobTitle("King of the North");
		candidateDTO1.setEmail("john.snow@gmail.com");
		CandidateDTO candidateDTO2 = new CandidateDTO();
		candidateDTO2.setFirstName("Tyrian");
		candidateDTO2.setLastName("Lannister");
		candidateDTO2.setCurrentJobTitle("Hand of the King");
		candidateDTO2.setEmail("tyrian.lannister@gmail.com");
		List<CandidateDTO> candidates = new ArrayList<CandidateDTO>();
		candidates.add(candidateDTO1);
		candidates.add(candidateDTO2);
		CandidateResponseDTO candidateResponseDTO = new CandidateResponseDTO();
		candidateResponseDTO.setCandidates(candidates);
		CandidateSummaryDTO candidateSummaryDTO1 = new CandidateSummaryDTO();
		candidateSummaryDTO1.setFirstName("John");
		candidateSummaryDTO1.setLastName("Snow");
		candidateSummaryDTO1.setStatus("Available");
		candidateSummaryDTO1.setResumeLink("www.resumes.com/johnsnow");
		candidateSummaryDTO1.setEmail("john.snow@gmail.com");
		CandidateSummaryDTO candidateSummaryDTO2 = new CandidateSummaryDTO();
		candidateSummaryDTO2.setFirstName("Tyrian");
		candidateSummaryDTO2.setLastName("Lannister");
		candidateSummaryDTO2.setStatus("Available");
		candidateSummaryDTO2.setResumeLink("www.resumes.com/tyrianlannister");
		candidateSummaryDTO2.setEmail("tyrian.lannister@gmail.com");
		List<CandidateSummaryDTO> candidateSummaryDTOs = new ArrayList<CandidateSummaryDTO>();
		candidateSummaryDTOs.add(candidateSummaryDTO1);
		candidateSummaryDTOs.add(candidateSummaryDTO2);
		CandidateSearchResponseDTO candidateSearchResponseDTO = new CandidateSearchResponseDTO();
		candidateSearchResponseDTO.setCandidates(candidateSummaryDTOs);
		
		when(candidateSearchServiceMock.searchForCandidates(candidateSearchRequestDTO)).thenReturn(null);
		when(candidateServiceMock.searchCandidates(candidateSearchRequestDTO)).thenReturn(candidateResponseDTO);
		when(candidateSearchServiceMock.searchForCandidates(candidateResponseDTO)).thenReturn(candidateSearchResponseDTO);
		
		ResponseEntity<CandidateSearchResponseDTO> response = controller.searchCandidates(candidateSearchRequestDTO);
		
		assertEquals(2, response.getBody().getCandidates().size());
		assertEquals("John", response.getBody().getCandidates().get(0).getFirstName());
		assertEquals("Snow", response.getBody().getCandidates().get(0).getLastName());
		assertEquals("Available", response.getBody().getCandidates().get(0).getStatus());
		assertEquals("www.resumes.com/johnsnow", response.getBody().getCandidates().get(0).getResumeLink());
		assertEquals("john.snow@gmail.com", response.getBody().getCandidates().get(0).getEmail());
		assertEquals("Tyrian", response.getBody().getCandidates().get(1).getFirstName());
		assertEquals("Lannister", response.getBody().getCandidates().get(1).getLastName());
		assertEquals("Available", response.getBody().getCandidates().get(1).getStatus());
		assertEquals("www.resumes.com/tyrianlannister", response.getBody().getCandidates().get(1).getResumeLink());
		assertEquals("tyrian.lannister@gmail.com", response.getBody().getCandidates().get(1).getEmail());
	}
}