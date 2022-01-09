package github.thyago.spaceflightnewsintegration.service.spaceflightapi.client;

import feign.Response;
import feign.codec.ErrorDecoder;
import github.thyago.spaceflightnewsintegration.service.spaceflightapi.exception.UnavailableAPIException;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        var status = response.status();
        if (this.tooManyRequests(status) || this.timeOut(status) || this.unavailable(status)) {
            return new UnavailableAPIException(response.reason());
        }
        return new Exception(response.reason());
    }

    private boolean tooManyRequests(int status) {
        return HttpStatus.TOO_MANY_REQUESTS.value() == status;
    }

    private boolean timeOut(int status) {
        return HttpStatus.REQUEST_TIMEOUT.value() == status;
    }

    private boolean unavailable(int status) {
        return HttpStatus.SERVICE_UNAVAILABLE.value() == status;
    }

}
