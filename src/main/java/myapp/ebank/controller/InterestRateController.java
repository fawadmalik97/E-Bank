package myapp.ebank.controller;

import myapp.ebank.model.entity.InterestRates;
import myapp.ebank.service.InterestRateService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Date;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/interestRate")
@Validated
public class InterestRateController {
    private static final String defaultAuthValue = "12345";
    InterestRateService interestRateService;

    public InterestRateController(InterestRateService interestRateService) {
        this.interestRateService = interestRateService;
    }


    /**
     * check user is authorized or not
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
    public ResponseEntity<Object> getDailyInterestRate(HttpServletRequest httpServletRequest) throws ParseException {
        return interestRateService.getDailyInterestRate(httpServletRequest);
    }

    /**
     * get rates by specific date
     *
     * @param date
     * @return
     */
    @GetMapping("/getByDate")
    public ResponseEntity<Object> getInterestRateByDate(@RequestParam Date date, HttpServletRequest httpServletRequest) {
        return interestRateService.getInterestRateByDate(date);
    }

    /**
     * get interest rates from start date to  current date
     *
     * @param startDate
     * @return
     */
    @GetMapping("/getByStartDate")
    public ResponseEntity<Object> getInterestRateByStartDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date startDate, HttpServletRequest httpServletRequest) {
        return interestRateService.getInterestRateByStartDate(startDate);
    }

    /**
     * get interest rates from start date to  end date
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/getByDateBetween")
    public ResponseEntity<Object> getInterestRateByStartAndEndDate(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date startDate,
                                                                   @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") java.util.Date endDate, HttpServletRequest httpServletRequest) {
        return interestRateService.getInterestRateBetweenDates(startDate, endDate);
    }


    /**
     * @param id
     * @return interestRate object
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getInterestRate(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return interestRateService.getInterestRatesById(id);
    }


    /**
     * @return list of interest rates
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllInterestRates(HttpServletRequest httpServletRequest) {
        return interestRateService.listAllInterestRates();
    }


    /**
     * save interest rate
     *
     * @param interestRates
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addInterestRate(@RequestHeader(value = "Authorization") String authValue,
                                                  @Valid @RequestBody InterestRates interestRates, HttpServletRequest httpServletRequest) {
        if (authorize(authValue)) {
            return interestRateService.addInterestRate(interestRates);
        } else
            return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param authValue
     * @param interestRate
     * @return
     * @createdDate 29-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateInterestRate(@RequestHeader(value = "Authorization") String authValue,
                                                     @Valid @RequestBody InterestRates interestRate, HttpServletRequest httpServletRequest) {
        if (authorize(authValue)) {
            return interestRateService.updateInterestRate(interestRate);
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
    public ResponseEntity<Object> deleteInterestRate(@RequestHeader(value = "Authorization") String authValue,
                                                     @PathVariable Long id, HttpServletRequest httpServletRequest) {

        if (authorize(authValue)) {
            return interestRateService.deleteInterestRate(id);
        } else
            return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
    }


}
