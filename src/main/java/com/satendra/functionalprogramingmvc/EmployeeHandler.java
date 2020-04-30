package com.satendra.functionalprogramingmvc;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.net.URI;

import static org.springframework.web.servlet.function.ServerResponse.*;

@Component
@RequiredArgsConstructor
public class EmployeeHandler {

    final private EmployeeRepository employeeRepository;

    public ServerResponse all(ServerRequest req) {
        return ok().body(employeeRepository.findAll());

    }


    public ServerResponse get(ServerRequest req) {
        return employeeRepository.findById(Long.valueOf(req.pathVariable("id")))
                .map(emp -> ServerResponse.ok().body(emp))
                .orElse(ServerResponse.notFound().build());
    }

    public ServerResponse create(ServerRequest req) throws ServletException, IOException {
        var saved = employeeRepository.save(req.body(Employee.class));
        return created(URI.create("/employees/"+saved.getId())).build();
    }

    public ServerResponse update(ServerRequest req) throws ServletException, IOException {
        var emp = req.body(Employee.class);

        return employeeRepository.findById(Long.valueOf(req.pathVariable("id")))
                .map(
                        empRepo->{
                            empRepo.setTitle(emp.getTitle());
                            empRepo.setContent(emp.getContent());
                            return empRepo;
                        }
                )
                .map(empRepo -> employeeRepository.save(empRepo))
                .map(empRepo->ok().build())
                .orElse(notFound().build());
    }

    public ServerResponse delete(ServerRequest req) {
        return employeeRepository.findById(Long.valueOf(req.pathVariable("id")))
                .map(
                        emp -> {
                            employeeRepository.delete(emp);
                            return ServerResponse.ok().build();
                        }
                )
                .orElse(ServerResponse.notFound().build());
    }

}
