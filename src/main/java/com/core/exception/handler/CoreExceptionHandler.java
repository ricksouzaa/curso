package com.core.exception.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.core.exception.ServiceException;

@ControllerAdvice
public class CoreExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String friendlyMessage = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String originalMessage = ExceptionUtils.getRootCauseMessage(ex);
		List<Erro> erros = Arrays.asList(new Erro(friendlyMessage, originalMessage));
		return handleExceptionInternal(ex, erros, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Erro> erros = createErrorList(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, status, request);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String friendlyMessage = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String originalMessage = ExceptionUtils.getRootCauseMessage(ex);
		List<Erro> erros = Arrays.asList(new Erro(friendlyMessage, originalMessage));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class } )
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		String friendlyMessage = messageSource.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());
		String originalMessage = ExceptionUtils.getRootCauseMessage(ex);
		List<Erro> erros = Arrays.asList(new Erro(friendlyMessage, originalMessage));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ ServiceException.class })
	public ResponseEntity<Object> handleServiceException(ServiceException ex) {
		String friendlyMessage = messageSource.getMessage(ex.getCode(), null, LocaleContextHolder.getLocale());
		String originalMessage = ExceptionUtils.getRootCauseMessage(ex);
		List<Erro> errors = Arrays.asList(new Erro(friendlyMessage, originalMessage));
		return ResponseEntity.badRequest().body(errors);
	}

	private List<Erro> createErrorList(BindingResult bindingResult) {
		List<Erro> errors = new ArrayList<>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String friendlyMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String originalMessage = fieldError.toString();
			errors.add(new Erro(friendlyMessage, originalMessage));
		}

		return errors;
	}
	
	public static class Erro {
		
		private String friendlyMessage;
		private String originalMessage;
		
		public Erro(String friendlyMessage, String originalMessage) {
			this.friendlyMessage = friendlyMessage;
			this.originalMessage = originalMessage;
		}

		public String getFriendlyMessage() {
			return friendlyMessage;
		}

		public String getOriginalMessage() {
			return originalMessage;
		}

	}

}
