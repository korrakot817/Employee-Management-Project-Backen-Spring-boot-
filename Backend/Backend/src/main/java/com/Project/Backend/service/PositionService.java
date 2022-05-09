package com.Project.Backend.service;

import com.Project.Backend.entity.Employee;
import com.Project.Backend.entity.Position;
import com.Project.Backend.exception.BaseException;
import com.Project.Backend.exception.PositionException;
import com.Project.Backend.repository.PositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public Optional<Position> findById(String id) {
        return positionRepository.findById(id);
    }

    //createPosition
    public Position createPosition(String position) throws BaseException {

        Position positionJob = new Position();
        positionJob.setPosition(position);

        if (Objects.isNull(position)) {
            //throw error position null
            throw PositionException.createPositionNull();

        }

        return positionRepository.save(positionJob);

    }

    //listPosition
    public List<Position> listPosition() {
        List<Position> positions = (List<Position>) positionRepository.findAll();

        return positions;
    }

    //GetPosition
    public Position getPosition(String id) throws BaseException {
        Optional<Position> opt = positionRepository.findById(id);
        if (opt.isEmpty()) {
            // throw error not found
            throw PositionException.positionNull();

        }

        Position position = opt.get();

        return position;
    }

    //Delete
    public void deleteEmployeeById(String id) {
        positionRepository.deleteById(id);
    }

}
