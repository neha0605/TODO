package com.example.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

class BaseTestCase {

    @BeforeEach
    void init_mocks() {
        MockitoAnnotations.initMocks(this);
    }

}