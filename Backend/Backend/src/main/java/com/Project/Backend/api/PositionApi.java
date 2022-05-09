package com.Project.Backend.api;

import com.Project.Backend.business.PositionBusiness;
import com.Project.Backend.entity.Position;
import com.Project.Backend.exception.BaseException;
import com.Project.Backend.model.positionModel.MPositionCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionApi {

    private final PositionBusiness positionBusiness;

    public PositionApi(PositionBusiness positionBusiness) {
        this.positionBusiness = positionBusiness;
    }

    //Create
    @PostMapping("/create/position")
    public ResponseEntity<Position> createEmployee(@RequestBody MPositionCreateRequest request) throws BaseException {
        Position response = positionBusiness.createPosition(request);

        return ResponseEntity.ok(response);
    }

    //list
    @GetMapping("/positions")
    public ResponseEntity<List<Position>> positionList() {
        List<Position> response = positionBusiness.employeeLists();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(value = "id") String id) {
        positionBusiness.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).build();

    }
}


