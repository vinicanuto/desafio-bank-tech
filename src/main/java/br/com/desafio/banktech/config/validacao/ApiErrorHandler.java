package br.com.desafio.banktech.config.validacao;

import br.com.desafio.banktech.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiErrorHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroValidacaoDTO> handle(MethodArgumentNotValidException exception){
        List<ErroValidacaoDTO> dto = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach( e ->{
            String msg = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            dto.add(new ErroValidacaoDTO(e.getField(),msg));
        });

        dto.forEach(d-> System.out.println(d.getAtributo() +d.getErro()));
        return dto;
    }




    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public BusinessExceptionDTO handle(EntityNotFoundException exception){
        BusinessExceptionDTO dto = new BusinessExceptionDTO(exception.getLocalizedMessage());
        System.out.println(dto.getMensagem());
        return dto;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public BusinessExceptionDTO handle(BusinessException exception){
        BusinessExceptionDTO dto = new BusinessExceptionDTO(exception.getLocalizedMessage());
        System.out.println(dto.getMensagem());
        return dto;
    }

}
