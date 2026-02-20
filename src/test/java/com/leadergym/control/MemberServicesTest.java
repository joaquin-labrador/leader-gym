package com.leadergym.control;

import com.leadergym.control.dto.MemberCredentialsDTO;
import com.leadergym.control.dto.MemberUpdateCredentialsDTO;
import com.leadergym.control.exception.MemberNotFoundException;
import com.leadergym.control.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
public class MemberServicesTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test for creating a member with invalid data")
    void createMember_shouldReturn400() throws Exception {
        MemberCredentialsDTO req = new MemberCredentialsDTO();
        req.setDni("invalid-dni");
        req.setFirstName("");
        req.setLastName("");
        req.setPhoneNumber("invalid-phone");
        req.setEmail("invalid-email");
        req.setPlanId(null);

        verifyNoInteractions(memberService); // Verificar que el servicio no se llama debido a la validación fallida

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());


    }

    @Test
    @DisplayName("Test for creating a member with valid data")
    void createMember_shouldReturn201() throws Exception {
        MemberCredentialsDTO req = new MemberCredentialsDTO();
        req.setDni("40123456");
        req.setFirstName("Juan");
        req.setLastName("Pérez");
        req.setPhoneNumber("+5491199988877");
        req.setEmail("joaquinlabrador1010@gmail.com");
        req.setBirthDate("1990-01-01");
        req.setPlanId(1L);

        doNothing().when(memberService).createMember(ArgumentMatchers.any(MemberCredentialsDTO.class));

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());


    }

    @Test
    @DisplayName("Test for update a member with valid data")
    void updateMember_shouldReturn204() throws Exception {
        String dni = "40123456";

        MemberUpdateCredentialsDTO req = new MemberUpdateCredentialsDTO();
        req.setFirstName("Juan Updated");
        req.setLastName("Pérez Updated");
        req.setPhoneNumber("+5491199988877");
        req.setEmail("juan.updated@gmail.com");
        req.setPlanId(2L);

        doNothing().when(memberService).updateMember(ArgumentMatchers.eq(dni), ArgumentMatchers.any(MemberUpdateCredentialsDTO.class));

        mockMvc.perform(put("/members/{dni}", dni).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req))).andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("Test for updating a member with invalid data")
    void updateMember_shouldReturn400() throws Exception {
        String dni = "40123456";

        MemberUpdateCredentialsDTO req = new MemberUpdateCredentialsDTO();
        req.setFirstName("");
        req.setLastName("");
        req.setPhoneNumber("invalid-phone");
        req.setEmail("invalid-email");
        req.setPlanId(null);

        verifyNoInteractions(memberService); // Verificar que el servicio no se llama debido a la validación fallida

        mockMvc.perform(put("/members/{dni}", dni)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());


    }

    @Test
    @DisplayName("Test for updating a member with non-existing DNI")
    void updateMember_shouldReturn404() throws Exception {
        String dni = "99999999";

        MemberUpdateCredentialsDTO req = new MemberUpdateCredentialsDTO();
        req.setFirstName("Juan Updated");
        req.setLastName("Pérez Updated");
        req.setPhoneNumber("+5491199988877");
        req.setEmail("Joaquinlabrador@gmail.com");
        req.setPlanId(2L);

        doThrow(new MemberNotFoundException("Member not found with DNI: " + dni)).when(memberService).updateMember(ArgumentMatchers.eq(dni), ArgumentMatchers.any(MemberUpdateCredentialsDTO.class));

        mockMvc.perform(put("/members/{dni}", dni)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("Get member by DNI with non-existing DNI should return 404")
    void getMemberByDni_shouldReturn404() throws Exception {
        String dni = "99999999";
        doThrow(new MemberNotFoundException("Member not found with DNI: " + dni)).when(memberService).getMemberByDni(ArgumentMatchers.eq(dni));
        mockMvc.perform(get("/members/{dni}", dni))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Get member by DNI with existing DNI should return 200")
    void getMemberByDni_shouldReturn200() throws Exception {
        String dni = "40123456";

        mockMvc.perform(get("/members/{dni}", dni))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Delete member with non-existing DNI should return 404")
    void deleteMember_shouldReturn404() throws Exception {
        String dni = "999999999";
        doThrow(new MemberNotFoundException("Member not found with DNI: " + dni)).when(memberService).deleteMember(ArgumentMatchers.eq(dni));
        mockMvc.perform(delete("/members/{dni}", dni))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete member with existing DNI should return 204")
    void deleteMember_shouldReturn204() throws Exception {
        String dni = "40123456";

        doNothing().when(memberService).deleteMember(ArgumentMatchers.eq(dni));

        mockMvc.perform(delete("/members/{dni}", dni))
                .andExpect(status().isNoContent());
    }
}