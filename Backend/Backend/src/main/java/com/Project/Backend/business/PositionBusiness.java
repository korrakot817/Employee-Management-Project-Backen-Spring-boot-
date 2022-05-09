package com.Project.Backend.business;

import com.Project.Backend.entity.Position;
import com.Project.Backend.exception.BaseException;
import com.Project.Backend.model.positionModel.MPositionCreateRequest;
import com.Project.Backend.service.PositionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionBusiness {

    private final PositionService positionService;

    public PositionBusiness(PositionService positionService) {
        this.positionService = positionService;
    }

    //CreatePosition
    public Position createPosition(MPositionCreateRequest request) throws BaseException {
        Position position = positionService.createPosition(request.getPosition());

        return position;
    }

    //listPosition
    public List<Position> employeeLists() {
        List<Position> response = positionService.listPosition();

        return response;
    }

    //getById
    public Position getEmployeeProfile(String id) throws BaseException {
        Position position = positionService.getPosition(id);

        return position;
    }

    //deleteById
    public void deleteById(String id) {
        positionService.deleteEmployeeById(id);

    }

}
