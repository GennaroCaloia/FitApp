package it.gennaro.fitapp.controller;

import it.gennaro.fitapp.entity.Workout;
import it.gennaro.fitapp.security.*;
import it.gennaro.fitapp.service.WorkoutPlanService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = WorkoutPlanController.class)
class WorkoutPlanControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WorkoutPlanService workoutPlanService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    AuthenticationManager authenticationManager;

    @Test
    @WithMockUser(username = "demo")
    void list_returns_200_and_json_array() throws Exception {
        Workout w = Workout.builder()
                .id(1L)
                .build();

        when(workoutPlanService.listWorkouts(eq("demo"), any(Long.class)))
                .thenReturn(List.of(w));

        mockMvc.perform(get("/api/plans/{planId}/workouts", w.getId())
                .with(user("demo").roles("ADMIN")) // Esegue la richiesta come utente ADMIN
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "demo")
    void add_workout_returns_200() throws Exception {
        Long planId = 1L;
        Long workoutId = 1L;

        mockMvc.perform(post("/api/plans/{planId}/workouts/{workoutId}", planId, workoutId)
                .with(user("demo").roles("ADMIN")) // Esegue la richiesta come utente ADMIN
                .with(csrf()))
                .andExpect(status().isOk());

        Mockito.verify(workoutPlanService)
                .addWorkout(eq("demo"), eq(planId), eq(workoutId));
    }

    @Test
    @WithMockUser(username = "demo")
    void remove_workout_returns_200() throws Exception {
        Long planId = 1L;
        Long workoutId = 1L;

        mockMvc.perform(delete("/api/plans/{planId}/workouts/{workoutId}", planId, workoutId)
                .with(user("demo").roles("ADMIN")) // Esegue la richiesta come utente ADMIN
                .with(csrf()))
                .andExpect(status().isOk());
        Mockito.verify(workoutPlanService)
                .removeWorkout(eq("demo"), eq(planId), eq(workoutId));
    }

}