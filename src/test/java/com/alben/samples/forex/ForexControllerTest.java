package com.alben.samples.forex;

import com.alben.samples.forex.services.ForexService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ForexControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ForexController forexController;

    @Mock
    private ForexService forexService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(forexController).build();
    }

    @Test
    public void getProperties() throws Exception {
        Mockito.when(forexService.getCurrentRate(eq("GBP"))).thenReturn(1.23d);

        mockMvc.perform(
                get( "/api/forex/GBP"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate").value(1.23d));
    }
}