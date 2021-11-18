package myapp.ebank.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ResponseMapping {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    public static ObjectNode ApiReponse(HttpStatus httpStatus, String message, String path, Object data) throws ParseException {
        ObjectNode responseJson = jsonMapper.createObjectNode();
        responseJson.put("timestamp", String.valueOf(DateTime.getDateTime()));
        responseJson.put("httpStatus", String.valueOf(httpStatus));
        responseJson.put("message:", message);
        responseJson.put("path", path);
        if (null != data) {
            responseJson.putPOJO("data", data);
        }
        return responseJson;
    }


}
