package br.com.danielsaes.api_aluraflix.controller;

import br.com.danielsaes.api_aluraflix.config.TokenService;
import br.com.danielsaes.api_aluraflix.controller.dto.TokenDto;
import br.com.danielsaes.api_aluraflix.controller.form.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) throws AuthenticationException {
        UsernamePasswordAuthenticationToken dadosLogin = form.converter();

        Authentication authentication = authManager.authenticate(dadosLogin);
        String token = tokenService.gerarToken(authentication);

        return ResponseEntity.ok(new TokenDto(token, "Bearer"));

    }

}
