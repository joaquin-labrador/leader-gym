package com.leadergym.control;

import com.leadergym.control.dto.PlanResponseDTO;
import com.leadergym.control.entity.Plan;
import com.leadergym.control.exception.PlanNotFoundException;
import com.leadergym.control.repository.PlanRepository;
import com.leadergym.control.service.impl.PlanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanServiceImplTest {

    @Mock
    private PlanRepository planRepository;

    @InjectMocks
    private PlanServiceImpl planService;

    private Plan plan;

    @BeforeEach
    void setUp() {
        plan = Plan.builder()
                .id(1L)
                .code("MONTHLY_FREE")
                .description("Monthly - Unlimited access")
                .durationInDays(30)
                .weeklyVisits(null)
                .price(50000.0)
                .build();
    }

    // 1) updatePlanPrice OK
    @Test
    void updatePlanPrice_shouldUpdateAndSave() {
        when(planRepository.findById(1L)).thenReturn(Optional.of(plan));
        when(planRepository.save(any(Plan.class))).thenAnswer(inv -> inv.getArgument(0));

        planService.updatePlanPrice(1L, 60000.0);

        assertEquals(60000.0, plan.getPrice());
        verify(planRepository).findById(1L);
        verify(planRepository).save(plan);
    }

    // 2) updatePlanPrice plan no existe
    @Test
    void updatePlanPrice_planNotFound_shouldThrow() {
        when(planRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(PlanNotFoundException.class,
                () -> planService.updatePlanPrice(999L, 60000.0));

        verify(planRepository).findById(999L);
        verify(planRepository, never()).save(any());
    }

    // 4) getsAllPlans OK
    @Test
    void getsAllPlans_shouldReturnDtoList() {
        Plan p2 = Plan.builder()
                .id(4L)
                .code("THREE_TIMES_PER_WEEK")
                .description("Monthly - 3 times per week")
                .durationInDays(30)
                .weeklyVisits(3)
                .price(40000.0)
                .build();

        when(planRepository.findAll()).thenReturn(List.of(plan, p2));

        List<PlanResponseDTO> result = planService.getsAllPlans();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("MONTHLY_FREE", result.get(0).getName());
        assertEquals("THREE_TIMES_PER_WEEK", result.get(1).getName());
        verify(planRepository).findAll();
    }

    // 5) getsAllPlans lista vacía
    @Test
    void getsAllPlans_empty_shouldThrow() {
        when(planRepository.findAll()).thenReturn(List.of());

        assertThrows(PlanNotFoundException.class, () -> planService.getsAllPlans());

        verify(planRepository).findAll();
    }

    // 6) getPlanById OK
    @Test
    void getPlanById_shouldReturnDto() {
        when(planRepository.findById(1L)).thenReturn(Optional.of(plan));

        PlanResponseDTO dto = planService.getPlanById(1);

        assertNotNull(dto);
        assertEquals("MONTHLY_FREE", dto.getName());
        assertEquals(50000.0, dto.getPrice());
        verify(planRepository).findById(1L);
    }

    // 7) getPlanById no existe
    @Test
    void getPlanById_notFound_shouldThrowWithMessage() {
        when(planRepository.findById(777L)).thenReturn(Optional.empty());

        PlanNotFoundException ex = assertThrows(PlanNotFoundException.class,
                () -> planService.getPlanById(777));

        assertTrue(ex.getMessage().contains("777"));
        verify(planRepository).findById(777L);
    }
}