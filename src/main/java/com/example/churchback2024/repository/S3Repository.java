package com.example.churchback2024.repository;

import com.example.churchback2024.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface S3Repository extends JpaRepository<Image, Long>{
}


