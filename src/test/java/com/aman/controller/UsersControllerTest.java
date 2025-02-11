package com.aman.controller;

import com.aman.WalsmartTests;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

public class UsersControllerTest extends WalsmartTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void usersList() throws Exception{
        mockMvc.perform(get("/users")).andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("userId", is(1L)),
                                hasProperty("firstName", is("Rakshit")),
                                hasProperty("lastName", is("Shetty")),
                                hasProperty("userGroup", is("Employee"))
                        )
                )))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("userId", is(2L)),
                                hasProperty("firstName", is("Aayush")),
                                hasProperty("lastName", is("Srivastava")),
                                hasProperty("userGroup", is("Employee"))
                        )
                )))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("userId", is(3L)),
                                hasProperty("firstName", is("Siddharth")),
                                hasProperty("lastName", is("Saran")),
                                hasProperty("userGroup", is("Manager"))
                        )
                )));
    }

    @Test
    public void saveUser() throws Exception{
        mockMvc.perform(post("/saveuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"firstName\": \"Test\",\n" +
                        "  \"lastName\": \"User\",\n" +
                        "  \"userGroup\": \"Employee\"\n" +
                        "}"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("4"));
    }
}