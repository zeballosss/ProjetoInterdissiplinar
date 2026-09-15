package com.projeto.interdisciplinar.repository;

import com.projeto.interdisciplinar.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
