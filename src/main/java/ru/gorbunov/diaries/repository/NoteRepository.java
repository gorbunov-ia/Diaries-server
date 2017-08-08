/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gorbunov.diaries.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.gorbunov.diaries.domain.Note;

/**
 *
 * @author Gorbunov.ia
 */
@Repository
public interface NoteRepository extends CrudRepository<Note, Integer>{    
}
