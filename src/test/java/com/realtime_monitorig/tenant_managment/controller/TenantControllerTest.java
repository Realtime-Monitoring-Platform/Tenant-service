package com.realtime_monitorig.tenant_managment.controller;

import com.realtime_monitorig.tenant_managment.dto.CreateTenantRequest;
import com.realtime_monitorig.tenant_managment.dto.TenantResponse;
import com.realtime_monitorig.tenant_managment.dto.UpdateTenantRequest;
import com.realtime_monitorig.tenant_managment.entity.TenantStatus;
import com.realtime_monitorig.tenant_managment.exceptions.GlobalExceptionHandler;
import com.realtime_monitorig.tenant_managment.service.TenantService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.SpringDataJacksonConfiguration;
import org.springframework.data.web.config.SpringDataWebSettings;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TenantControllerTest {

    @Mock
    private TenantService tenantService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        mockMvc = MockMvcBuilders.standaloneSetup(new TenantController(tenantService))
                .setValidator(validator)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler())
                .setMessageConverters(pageJacksonConverter())
                .build();
    }

    private MappingJackson2HttpMessageConverter pageJacksonConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SpringDataJacksonConfiguration.PageModule(
                new SpringDataWebSettings(EnableSpringDataWebSupport.PageSerializationMode.DIRECT)));
        return new MappingJackson2HttpMessageConverter(mapper);
    }

    @Test
    void create_returnsCreated() throws Exception {
        TenantResponse response = TenantResponse.builder().id(UUID.randomUUID())
                .name("Acme").companyName("Acme Inc").status(TenantStatus.ACTIVE).build();
        when(tenantService.createTeanant(any(CreateTenantRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/tenants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Acme\",\"companyName\":\"Acme Inc\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Acme"));
    }

    @Test
    void create_invalidRequest_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/tenants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"companyName\":\"Acme Inc\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findById_returnsOk() throws Exception {
        UUID id = UUID.randomUUID();
        TenantResponse response = TenantResponse.builder().id(id).name("Acme").build();
        when(tenantService.getTenantById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/tenants/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Acme"));
    }

    @Test
    void findAll_returnsPage() throws Exception {
        Page<TenantResponse> page = new PageImpl<>(List.of(TenantResponse.builder().name("Acme").build()));
        when(tenantService.getAllTenants(any())).thenReturn(page);

        mockMvc.perform(get("/api/v1/tenants"))
                .andExpect(status().isOk());
    }

    @Test
    void search_returnsPage() throws Exception {
        Page<TenantResponse> page = new PageImpl<>(List.of(TenantResponse.builder().name("Acme").build()));
        when(tenantService.getTenantByCompanyName(eq("acme"), any())).thenReturn(page);

        mockMvc.perform(get("/api/v1/tenants/search").param("company", "acme"))
                .andExpect(status().isOk());
    }

    @Test
    void filterByStatus_returnsPage() throws Exception {
        Page<TenantResponse> page = new PageImpl<>(List.of(TenantResponse.builder().name("Acme").build()));
        when(tenantService.filterByStatus(eq(TenantStatus.ACTIVE), any())).thenReturn(page);

        mockMvc.perform(get("/api/v1/tenants/status/{status}", "ACTIVE"))
                .andExpect(status().isOk());
    }

    @Test
    void update_returnsOk() throws Exception {
        UUID id = UUID.randomUUID();
        TenantResponse response = TenantResponse.builder().id(id).name("Acme").build();
        when(tenantService.updateTenant(any(UUID.class), any(UpdateTenantRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/tenants/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Acme\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/api/v1/tenants/{id}", id))
                .andExpect(status().isNoContent());
    }
}