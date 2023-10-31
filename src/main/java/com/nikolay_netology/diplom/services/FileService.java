package com.nikolay_netology.diplom.services;

import com.nikolay_netology.diplom.model.FileData;
import com.nikolay_netology.diplom.model.UserData;
import com.nikolay_netology.diplom.repository.AuthenticationRepository;
import com.nikolay_netology.diplom.repository.FileRepository;
import com.nikolay_netology.diplom.repository.UserRepository;
import com.nikolay_netology.diplom.util.request.EditNameFileRequest;
import com.nikolay_netology.diplom.util.response.FileResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class FileService {

    private FileRepository fileRepository;
    private AuthenticationRepository authenticationRepository;
    private UserRepository userRepository;

    public void uploadFile(String authToken, String filename, MultipartFile file) {
        final UserData user = getUserByAuthToken(authToken);
        try {
            fileRepository.save(new FileData(filename, LocalDateTime.now(), file.getSize(), file.getBytes(), user));
            log.info("Success upload file. User {}", user.getUsername());
        } catch (IOException e) {
            log.error("Upload file: Error input data");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error input data");
        }
    }

    @Transactional
    public void deleteFile(String authToken, String filename) {
        final UserData user = getUserByAuthToken(authToken);
        fileRepository.deleteByUserAndFilename(user, filename);
        if (fileRepository.findByUserAndFilename(user, filename) != null) {
            log.error("Delete file: Error delete file");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error input data");
        }
        log.info("Success delete file. User {}", user.getUsername());

    }


    public byte[] downloadFile(String authToken, String filename) {
        final UserData user = getUserByAuthToken(authToken);
        final FileData file = fileRepository.findByUserAndFilename(user, filename);
        if (file == null) {
            log.error("Download file: Input data exception");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error input data");
        }
        log.info("Success download file. User {}", user.getUsername());
        return file.getFileСontent();
    }

    @Transactional
    public void editFileName(String authToken, String filename, EditNameFileRequest editNameFileRequest) {
        final UserData user = getUserByAuthToken(authToken);
        fileRepository.editFileNameByUser(user, filename, editNameFileRequest.getFilename());

        final FileData fileWithOldName = fileRepository.findByUserAndFilename(user, filename);
        if (fileWithOldName != null) {
            log.error("Edit file name: Input data exception");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error input data");
        }
        log.info("Success edit file name. User {}", user.getUsername());
    }


    public List<FileResponse> getAllFiles(String authToken) {
        final UserData user = getUserByAuthToken(authToken);
        log.info("Success get all files. User {}", user.getUsername());
        return fileRepository.findAllByUser(user).stream()
                .map(file -> new FileResponse(file.getFilename(), file.getSize()))
                .collect(Collectors.toList());
    }

    public UserData getUserByAuthToken(String authToken) {
        UserData user = null;
        if (authToken.startsWith("Bearer ")) {
            final String authTokenWithoutBearer = authToken.split(" ")[1];
            final String username = authenticationRepository.getUsernameByToken(authTokenWithoutBearer);
            user = userRepository.findByLogin(username);
        }
        if (user == null) {
            log.error("Unauthorized operation - invalid access token");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized error");
        }
        return user;
    }

}
