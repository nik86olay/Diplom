package com.nikolay_netology.diplom.repository;

import com.nikolay_netology.diplom.model.FileData;
import com.nikolay_netology.diplom.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileData, String> {

    void deleteByUserAndFilename(UserData user, String filename);

    FileData findByUserAndFilename(UserData user, String filename);

    @Modifying
    @Query("update FileData f set f.filename = :newName where f.filename = :filename and f.user = :user")
    void editFileNameByUser(@Param("user") UserData user, @Param("filename") String file_name, @Param("newName") String newName);

    List<FileData> findAllByUser(UserData user);
}
