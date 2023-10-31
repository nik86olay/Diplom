package com.nikolay_netology.diplom.repository;

import com.nikolay_netology.diplom.model.FileData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static com.nikolay_netology.diplom.TestData.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StorageFileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        fileRepository.deleteAll();
        userRepository.save(USER_1);
        fileRepository.save(STORAGE_FILE_1);
        fileRepository.save(FOR_RENAME_STORAGE_FILE);
    }

    @Test
    void deleteByUserAndFilename() {
        Optional<FileData> beforeDelete = fileRepository.findById(FILENAME_1);
        assertTrue(beforeDelete.isPresent());
        fileRepository.deleteByUserAndFilename(USER_1, FILENAME_1);
        Optional<FileData> afterDelete = fileRepository.findById(FILENAME_1);
        assertFalse(afterDelete.isPresent());
    }

    @Test
    void findByUserAndFilename() {
        assertEquals(STORAGE_FILE_1, fileRepository.findByUserAndFilename(USER_1, FILENAME_1));
    }

    @Test
    void editFileNameByUser() {
        Optional<FileData> beforeEditName = fileRepository.findById(FOR_RENAME_FILENAME);
        assertTrue(beforeEditName.isPresent());
        assertEquals(FOR_RENAME_FILENAME, beforeEditName.get().getFilename());
        fileRepository.editFileNameByUser(USER_1, FOR_RENAME_FILENAME, NEW_FILENAME);
        Optional<FileData> afterEditName = fileRepository.findById(NEW_FILENAME);
        assertTrue(afterEditName.isPresent());
        assertEquals(NEW_FILENAME, afterEditName.get().getFilename());
    }

    @Test
    void findAllByUser() {
        assertEquals(List.of(STORAGE_FILE_1, FOR_RENAME_STORAGE_FILE), fileRepository.findAllByUser(USER_1));
    }
}