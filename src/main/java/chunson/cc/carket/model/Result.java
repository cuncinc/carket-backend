package chunson.cc.carket.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class Result<T> extends ResponseEntity<T>
{
    public Result() {super(HttpStatus.OK);}

    public Result(T body)
    {
        super(body, HttpStatus.OK);
    }

    public Result(HttpStatus status)
    {
        super(status);
    }

    public Result(T body, HttpStatus status)
    {
        super(body, status);
    }

    public Result(MultiValueMap<String, String> headers, HttpStatus status)
    {
        super(headers, status);
    }

    public Result(T body, MultiValueMap<String, String> headers, HttpStatus status)
    {
        super(body, headers, status);
    }

    public Result(T body, MultiValueMap<String, String> headers, int rawStatus)
    {
        super(body, headers, rawStatus);
    }
    //
//    public static <T> Result<T> success(T data, int code, String message)
//    {
//        Result<T> result = new Result<>();
//        result.setCode(code);
//        result.setMessage(message);
//        result.setData(data);
//        return result;
//    }
//
//    @Override
//    public String toString()
//    {
//        return "Result{" +
//                "code=" + code +
//                ", message='" + message + '\'' +
//                ", data=" + data +
//                '}';
//    }
}
