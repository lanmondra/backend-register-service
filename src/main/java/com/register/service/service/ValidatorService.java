package com.register.service.service;

import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.register.service.interfaces.IValidatorService;


@Service
public class ValidatorService extends AbstractService implements IValidatorService, IValidatorServiceErrorCode {
	
	/**
	 * Valida que un campo no esté vacío
	 * @param target target
	 * @return void
	 */
	@Override
	public void validateNotNull(Object target, String errorCode) {
		if (target == null) {
			notifyError(errorCode);
			return;
		}

		if ((target instanceof String) && ((String) target).isEmpty()) {
			notifyError(errorCode);
		}
	}
	
	@Override
	public void validateNotNull(Object object) {
		validateNotNull(object, FIELD_REQUIRED);
	}

	/**
	 * Valida que un String no contenga números
	 * @param target
	 * @return void
	 */
	@Override
	public void validateNoNumbers(String target, String errorCode) {
		if (target.matches("^([^0-9]*)$")) {
			return;
		}

		notifyError(errorCode, HttpStatus.BAD_REQUEST);

	}

	@Override
	public void validateNoNumbers(String target) {
		validateNoNumbers(target, NOT_NUMBERS);
	}

	/**
	 * Valida que un String no contenga letras
	 * @param  target
	 * @return void
	 */
	@Override
	public void validateNoLetter(String target, String errorCode) {
		if (target.matches("^[0-9]*$")) {
			return;
		}

		notifyError(errorCode, HttpStatus.BAD_REQUEST);

	}

	@Override
	public void validateNoLetter(String target) {
		validateNoLetter(target, NOT_LETTERS);
	}

	/**
	 * Valida la longitud de un string
	 * @param  target, Integer longitud que queremos validar
	 * @return void
	 */
	@Override
	public void validateLength(String target, Integer lng, String errorCode) {
		if(target.length() > lng) {
			notifyError(errorCode, HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public void validateLength(String target, Integer lng) {
		validateLength(target, lng, NOT_LENGTH);
	}
	
	/**
	 * Valida que los 2 primeros números del CP estén entre 01-52
	 * @param  target
	 * @return void
	 */
	@Override
	public void validatePostalCode(String target, String errorCode) {
		if(!target.matches("^([1-4][0-9]|[5][0-2])[0-9]{3}$")) {
			notifyError(errorCode, HttpStatus.BAD_REQUEST);
		}
	}


	@Override
	public void validatePostalCode(String object) {
		validatePostalCode(object, CP_NOT_EXIST);
	}
	
	/**
	 * Valida que el telefono fijo empiece por 8 ó 9
	 * @param  target
	 * @return void
	 */
	@Override
	public void validatePhone(String target, String errorCode) {
		validateNoLetter(target, errorCode);

		if(!target.matches("^[8|9].[0-9]{8}$")) {
			notifyError(errorCode, HttpStatus.BAD_REQUEST);
		}
	}


	@Override
	public void validatePhone(String object) {
		validatePhone(object, START_NUMBER);
	}
	
	/**
	 * Valida que el telefono móvil empiece por 6 ó 7
	 * @param  target
	 * @return void
	 */
	@Override
	public void validateMobile(String target, String errorCode) {
		validateNoLetter(target, errorCode);

		if(!target.matches("^[6|7].[0-9]{8}$")) {
			notifyError(errorCode, HttpStatus.BAD_REQUEST);
		}
	}


	@Override
	public void validateMobile(String object) {
		validateMobile(object, START_NUMBER);
	}
	
	/**
	 * Valida que se tenga la edad indicada
	 * @param  birthDate, Integer age
	 * @return void
	 */
	@Override
	public void validateBirthDate(Date birthDate, Integer age, String errorCode) {
		// Creo la data actual 
		Date currentDate = new Date();
		
		// Sumo els anys a la data de naixement 
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(birthDate);
		gc.add(GregorianCalendar.YEAR, age);
		
		Date birthDatePlusAge = gc.getTime();

		// Comparo dates
		if (birthDatePlusAge.after(currentDate)) {
			notifyError(errorCode, HttpStatus.BAD_REQUEST);
		}
	}


	@Override
	public void validateBirthDate(Date birthDate, Integer age) {
		validateBirthDate(birthDate, age, AGE_REQUIRED);
	}



	/**
	 * Valida que el email tenga un formato correcto
	 * @param  email
	 * @return void
	 */
	@Override
	public void validateEmail(String email, String errorCode) {
		if(!email.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")) {
			notifyError(errorCode, HttpStatus.BAD_REQUEST);
		}	
	}


	@Override
	public void validateEmail(String email) {
		validateEmail(email, BAD_EMAIL_FORMAT);
	}
	

	/**
	 * Valida que la cuenta bancaria tenga un formato correcto
	 * @param  bankAccount
	 * @return void
	 */
	@Override
	public void validateBankAccount(String bankAccount, String errorCode) {
		/*
		try {
			fcbq.common.utils.number.CompteBancari cb = new fcbq.common.utils.number.CompteBancari(bankAccount);
			@SuppressWarnings("unused")
			Integer tipusFormat = cb.getTipusFormat();
			
		} catch (FcbqException e) {
			notifyError(errorCode, HttpStatus.BAD_REQUEST);
		}
		*/
	}


	@Override
	public void validateBankAccount(String bankAccount) {
		validateBankAccount(bankAccount, BANK_ACCOUNT_BAD_FORMATED);
	}



	@Override
	public void validateCif(String cif, String errorCode) {
		/*
		if (!IdentificationDocumentsUtils.isCifValido(cif)) {
			notifyError(errorCode);
		}
		*/
	}

	@Override
	public void validateCif(String cif) {
		validateCif(cif, INVALID_CIF);
	}


	@Override
	public void validatePersonalIdNumber(String idNumber, Integer identificationType) {
		/*
		switch (identificationType) {
			case Person.ID_NUM_TYPE_NIF: validateNif(idNumber, INVALID_NIF); break;
			case Person.ID_NUM_TYPE_NIE: validateNie(idNumber, INVALID_NIE); break;
			case Person.ID_NUM_TYPE_PASSPORT: // TODO: Veure com validar passaport break;
		}
		*/
	}


	@Override
	public void validateNif(String nif, String errorCode) {
		/*
		if (!IdentificationDocumentsUtils.isNifValido(nif)) {
			notifyError(errorCode);
		}
		*/
	}


	@Override
	public void validateNif(String nif) {
		validateNif(nif, INVALID_NIF);
	}

	@Override
	public void validateNie(String nie, String errorCode) {
		/*
		if (!IdentificationDocumentsUtils.isNieValido(nie)) {
			notifyError(errorCode);
		}
		*/
	}

	@Override
	public void validateNie(String nie) {
		validateNie(nie);
	}



}
