package com.voting.voting_api.cliente;

import java.util.Random;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
public class ValidacaoCliente{
    private final Random random = new Random();

    //chama servico externo e retorna se o cpf é válido
    public ValidacaoCPFResultado validaCPF(String cpf){
        if(!isCPFValido(cpf)){ //se eh invalido
            return null;
        }

        PermissaoVoto permissao = 
        random.nextBoolean() ? 
        PermissaoVoto.VOTO_VALIDO : 
        PermissaoVoto.VOTO_INVALIDO;
        
        return new ValidacaoCPFResultado(permissao);
    }

    //verificacao do cpf
    private boolean isCPFValido(String cpf) {
        if (cpf == null) return false;
        
        cpf = cpf.replaceAll("[^0-9]", "");
        
        if (cpf.length() != 11) return false;
        
        if (cpf.matches("(\\d)\\1{10}")) return false;
        
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int remainder = sum % 11;
        int digit1 = (remainder < 2) ? 0 : 11 - remainder;
        
        if (Character.getNumericValue(cpf.charAt(9)) != digit1) return false;
        
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        remainder = sum % 11;
        int digit2 = (remainder < 2) ? 0 : 11 - remainder;
        
        return Character.getNumericValue(cpf.charAt(10)) == digit2;
    }
    
    public enum PermissaoVoto {
        VOTO_VALIDO,
        VOTO_INVALIDO
    }
    
    @Data
    public static class ValidacaoCPFResultado {
        private final PermissaoVoto status;
        
        public ValidacaoCPFResultado(PermissaoVoto status) {
        	this.status = status;
        }

		public PermissaoVoto getStatus() {
			return status;
		}
        
        
    }

}
