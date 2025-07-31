package gr.aueb.cf.eduapp.api;

import gr.aueb.cf.eduapp.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.eduapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.aueb.cf.eduapp.core.exceptions.ValidationException;
import gr.aueb.cf.eduapp.core.filters.Paginated;
import gr.aueb.cf.eduapp.core.filters.TeacherFilters;
import gr.aueb.cf.eduapp.dto.TeacherInsertDTO;
import gr.aueb.cf.eduapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.eduapp.service.ITeacherService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TeacherRestController {

    private final ITeacherService teacherService;

    public ResponseEntity<TeacherReadOnlyDTO> saveTeacher(
            @Valid @RequestPart(name ="teacher")TeacherInsertDTO teacherInsertDTO,
            @Nullable @RequestPart(value = "amkaFile", required = false)MultipartFile amkaFile,
            BindingResult bindingResult)

            throws AppObjectAlreadyExists, AppObjectInvalidArgumentException, IOException, ValidationException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        TeacherReadOnlyDTO teacherReadOnlyDTO = teacherService.saveTeacher(teacherInsertDTO, amkaFile);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(teacherReadOnlyDTO.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(teacherReadOnlyDTO);


    }

    @GetMapping
    public ResponseEntity<Page<TeacherReadOnlyDTO>> getPaginatedTeachers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<TeacherReadOnlyDTO> teachersPage = teacherService.getPaginatedTeachers(page, size);
        return ResponseEntity.ok(teachersPage);
    }

    public ResponseEntity<Paginated<TeacherReadOnlyDTO>> getFilteredAndPaginatedTeachers(
            @Nullable @RequestBody TeacherFilters filters) {
        if (filters == null ) filters = TeacherFilters.builder().build();
        Paginated<TeacherReadOnlyDTO> dtoPaginated = teacherService.getTeachersFilteredPaginated(filters);
        return ResponseEntity.ok(dtoPaginated);
    }



}
