package com.projeto.interdisciplinar.repository;

import com.projeto.interdisciplinar.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
