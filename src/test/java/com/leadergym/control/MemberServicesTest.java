package com.leadergym.control;

import com.leadergym.control.dto.MemberCredentialsDTO;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    @DisplayName("Test for update a member with valid data")
    void updateMember_shouldReturn204() throws Exception {
        String dni = "40123456";

        MemberCredentialsDTO req = new MemberCredentialsDTO();
        req.setFirstName("Juan Updated");
        req.setLastName("Pérez Updated");
        req.setPhoneNumber("+5491199988877");
        req.setEmail("juan.updated@gmail.com");
        req.setPlanId(2L);

        doNothing().when(memberService).updateMember(ArgumentMatchers.eq(dni), ArgumentMatchers.any(MemberCredentialsDTO.class));

        mockMvc.perform(put("/members/{dni}", dni).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req))).andExpect(status().isNoContent());
    }

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
}