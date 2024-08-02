package carreiras.com.github.java_spring_boot_vendas.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredencialDto {

    private String login;
    private String senha;
}
