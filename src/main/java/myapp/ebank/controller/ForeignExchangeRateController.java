package myapp.ebank.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import myapp.ebank.model.entity.ForeignExchangeRates;
import myapp.ebank.service.CurrencyService;
import myapp.ebank.service.ForeignExchangeRateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/foreignExchangeRates")
@Validated
public class ForeignExchangeRateController {
    private static final String defaultAuthValue = "12345";
    final ForeignExchangeRateService foreignExchangeRateService;
    private static final Logger log = LogManager.getLogger(CurrencyService.class);

    public ForeignExchangeRateController(ForeignExchangeRateService foreignExchangeRateService) {
        this.foreignExchangeRateService = foreignExchangeRateService;
    }

    /**
     * check foreignExchangeRate is authorized or not
     *
     * @param authValue
     * @return
     */
    public Boolean authorize(String authValue) {
        return defaultAuthValue.equals(authValue);
    }

    /**
     * return daily rates
     *
     * @return
     */
    @GetMapping("/dailyRates")
    public ResponseEntity<Object> getDailyForeignExchangeRate() {
        return foreignExchangeRateService.getDailyForeignExchangeRate();
    }

    /**
     * get rates by specific date
     *
     * @param date
     * @return
     */
    @GetMapping("/getByDate")
    public ResponseEntity<Object> getForeignExchangeRateByDate(@RequestParam Date date) {
        return foreignExchangeRateService.getForeignExchangeRateByDate(date);
    }


    /**
     * get foreign rates from start date to  current date
     *
     * @param startDate
     * @return
     */
    @GetMapping("/getByStartDate")
    public ResponseEntity<Object> getForeignExchangeRateByStartDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date startDate) {
        return foreignExchangeRateService.getForeignExchangeRateByStartDate(startDate);
    }

    /**
     * get foreign rates from start date to  end date
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/getByDateBetween")
    public ResponseEntity<Object> getForeignExchangeRateByStartAndEndDate(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date startDate,
                                                                          @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date endDate) {

        return foreignExchangeRateService.getForeignExchangeRateBetweenDates(startDate, endDate);
    }


    /**
     * @param id
     * @return foreignExchangeRate object
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getForeignExchangeRate(@PathVariable Long id) {
        return foreignExchangeRateService.getForeignExchangeRatesById(id);
    }


    /**
     * @return list of foreign exchange rates
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllForeignExchangeRates() {
        return foreignExchangeRateService.listAllForeignExchangeRates();
    }


    /**
     * save foreignExchangeRate
     *
     * @param foreignExchangeRates
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addForeignExchangeRate(@RequestHeader(value = "Authorization") String authValue,
                                                         @Valid @RequestBody List<ForeignExchangeRates> foreignExchangeRates) {
        if (authorize(authValue)) {
            return foreignExchangeRateService.addForeignExchangeRate(foreignExchangeRates);
        } else
            return new ResponseEntity<>("not authorize ", HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param authValue
     * @param foreignExchangeRates
     * @return
     * @createdDate 29-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateForeignExchangeRate(@RequestHeader(value = "Authorization") String authValue,
                                                            @Valid @RequestBody List<ForeignExchangeRates> foreignExchangeRates) {
        if (authorize(authValue)) {
            return foreignExchangeRateService.updateForeignExchangeRate(foreignExchangeRates);
        } else
            return new ResponseEntity<>("not authorize ", HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param authValue
     * @param id
     * @return
     * @createdDate 27-oct-2021
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteForeignExchangeRate(@RequestHeader(value = "Authorization") String authValue,
                                                            @PathVariable Long id) {

        if (authorize(authValue)) {
            return foreignExchangeRateService.deleteForeignExchangeRate(id);
        } else
            return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({javax.validation.ConstraintViolationException.class, InvalidFormatException.class, HttpMessageNotReadableException.class, MissingRequestHeaderException.class, MissingPathVariableException.class})
    public ResponseEntity<Object> inputValidationException(Exception e) {
        log.info(
                "some error has occurred trying to add foreign exchange rates list, in Class foreignExchangeController and its function addForeignExchangeRate see logs for more details "+ e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

    }
}
