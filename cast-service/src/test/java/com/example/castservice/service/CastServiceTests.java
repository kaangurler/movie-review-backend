package com.example.castservice.service;

import com.example.castservice.repository.CastRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CastServiceTests {
    @Mock
    private CastRepository castRepository;
    @InjectMocks
    private CastService castService;

    @Test
    public void testGetBy() {
    }
}
