package myapp.ebank.controller;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import myapp.ebank.model.entity.Roles;
import myapp.ebank.service.RoleService;
import myapp.ebank.util.ExceptionHandling;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author fawad khan
 * @createdDate 31-oct-2021
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    private static final String defaultAuthValue = "role12345";
    final private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * check role is authorized or not
     *
     * @param authValue
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    public Boolean authorize(String authValue) {
        return defaultAuthValue.equals(authValue);
    }

    /**
     * @param authValue
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllRoles(
            @RequestHeader(required = false, value = "Authorization") String authValue) {
        if (authValue != null) {
            if (authorize(authValue)) {
                return roleService.getAllRoles();
            } else {
                return new ResponseEntity<>("SMS: Not authorize", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }

    }

    /**
     * @param authValue
     * @param id
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getRole(@RequestHeader("Authorization") String authValue, @PathVariable Long id) {
        if (authValue != null) {
            if (authorize(authValue)) {
                return roleService.getRoleById(id);
            } else {
                return new ResponseEntity<>("SMS: Not authorize", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * @param authValue
     * @param role
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @PostMapping("/add")
    public ResponseEntity<Object> saveRole(@RequestHeader(required = false, value = "Authorization") String authValue,
                                           @RequestBody Roles role) {

        if (authValue != null) {
            if (authorize(authValue)) {
                return roleService.addRole(role);
            } else {
                return new ResponseEntity<>(" Not authorize", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }

    }

    /**
     * @param authValue
     * @param categories
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateRole(@RequestHeader("Authorization") String authValue,
                                             @RequestBody List<Roles> roles) {

        if (authorize(authValue)) {
            return roleService.updateRole(roles);
        } else
            return new ResponseEntity<>("SMS:  not authorize ", HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param authValue
     * @param id
     * @author fawad khan
     * @createdDate 31-oct-2021
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteRole(@RequestHeader("Authorization") String authValue, @PathVariable Long id) {
        if (authValue != null) {
            if (authorize(authValue)) {
                return roleService.deleteRole(id);
            } else
                return new ResponseEntity<>("SMS:  not authorize ", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, InvalidFormatException.class, DataIntegrityViolationException.class})
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ExceptionHandling.handleMethodArgumentNotValid(ex);
    }
}
