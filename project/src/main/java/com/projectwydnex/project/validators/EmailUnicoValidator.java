package com.projectwydnex.project.validators;

import com.projectwydnex.project.service.UsuarioService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class EmailUnicoValidator implements ConstraintValidator<ValidarEmailUnico,String> {

    private final UsuarioService usuarioService;

    // Inyecta tu servicio de usuario en el constructor
    public EmailUnicoValidator(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public boolean isValid(String correo, ConstraintValidatorContext constraintValidatorContext){
        boolean esCorreoUnico = !usuarioService.existeUsuarioConCorreo(correo);
        return esCorreoUnico;
    }
}
