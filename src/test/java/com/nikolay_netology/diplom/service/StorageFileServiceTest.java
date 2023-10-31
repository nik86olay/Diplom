package com.nikolay_netology.diplom.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import com.nikolay_netology.diplom.repository.*;
import com.nikolay_netology.diplom.services.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static com.nikolay_netology.diplom.TestData.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class StorageFileServiceTest {

    @InjectMocks
    private FileService fileService;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private AuthenticationRepository authenticationRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        Mockito.when(authenticationRepository.getUsernameByToken(BEARER_TOKEN_SPLIT)).thenReturn(USERNAME_1);
        Mockito.when(userRepository.findByLogin(USERNAME_1)).thenReturn(USER_1);
    }

    @Test
    void uploadFile() {
        fileService.uploadFile(BEARER_TOKEN, FILENAME_2, MULTIPART_FILE);
        Mockito.verify(fileRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void fileServiceUnauthorized() {
        assertThrows(ResponseStatusException.class, () -> fileService.getUserByAuthToken(TOKEN_1));

    }

    @Test
    void deleteFile() {
        fileService.deleteFile(BEARER_TOKEN, FILENAME_1);
        Mockito.verify(fileRepository, Mockito.times(1)).deleteByUserAndFilename(USER_1, FILENAME_1);
    }


    @Test
    void deleteFileInputDataException() {
        Mockito.when(fileRepository.findByUserAndFilename(USER_1, FILENAME_1)).thenReturn(STORAGE_FILE_1);
        assertThrows(ResponseStatusException.class, () -> fileService.deleteFile(BEARER_TOKEN, FILENAME_1));
    }

    @Test
    void downloadFile() {
        Mockito.when(fileRepository.findByUserAndFilename(USER_1, FILENAME_1)).thenReturn(STORAGE_FILE_1);
        assertEquals(FILE_CONTENT_1, fileService.downloadFile(BEARER_TOKEN, FILENAME_1));
    }


    @Test
    void downloadFileInputDataException() {
        Mockito.when(fileRepository.findByUserAndFilename(USER_1, FILENAME_1)).thenReturn(STORAGE_FILE_1);
        assertThrows(ResponseStatusException.class, () -> fileService.downloadFile(BEARER_TOKEN, FILENAME_2));
    }

    @Test
    void editFileName() {
        fileService.editFileName(BEARER_TOKEN, FILENAME_1, EDIT_FILE_NAME_RQ);
        Mockito.verify(fileRepository, Mockito.times(1)).editFileNameByUser(USER_1, FILENAME_1, NEW_FILENAME);
    }

    @Test
    void editFileNameInputDataException() {
        Mockito.when(fileRepository.findByUserAndFilename(USER_1, FILENAME_1)).thenReturn(STORAGE_FILE_1);
        assertThrows(ResponseStatusException.class, () -> fileService.deleteFile(BEARER_TOKEN, FILENAME_1));
    }

    @Test
    void getAllFiles() {
        Mockito.when(fileRepository.findAllByUser(USER_1)).thenReturn(STORAGE_FILE_LIST);
        assertEquals(FILE_RS_LIST, fileService.getAllFiles(BEARER_TOKEN));
    }

}